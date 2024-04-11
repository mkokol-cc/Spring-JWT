package com.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jwt.JwtAuthFilter;
import com.jwt.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsServiceImpl userDetailsService;
	private final JwtAuthFilter jwtAuthFilter;
	private final CustomExceptionHandler customExceptionHandler;

	public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, JwtAuthFilter jwtAuthFilter, CustomExceptionHandler customExceptionHandler) {
		this.userDetailsService = userDetailsService;
		this.jwtAuthFilter = jwtAuthFilter;
		this.customExceptionHandler = customExceptionHandler;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
	    return http
	        .cors(AbstractHttpConfigurer::disable)
	        .csrf(AbstractHttpConfigurer::disable)
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        //Set permissions on endpoints
	        .authorizeHttpRequests(auth -> auth
	        	//our public endpoints
	        	.requestMatchers(HttpMethod.POST, "/signup").permitAll()
	        	.requestMatchers(HttpMethod.POST, "/login").permitAll()
	        	//.requestMatchers(HttpMethod.GET, "/auth").permitAll()//comentar luego
	        	
	        	//our private endpoints
	        	.requestMatchers("/perfil/save","/perfil/delete").hasAuthority("PERFIL_EDITOR")
	        	.requestMatchers("/perfil","/rol").hasAuthority("PERFIL_VIEWER")
	        	.requestMatchers("/usuario/save","/usuario/delete").hasAuthority("USUARIO_EDITOR")
	        	.requestMatchers("/usuario").hasAuthority("USUARIO_VIEWER")

	            .anyRequest().authenticated())
	        //manejo de excepciones
	        .exceptionHandling(exception -> exception
                    .accessDeniedHandler(customExceptionHandler)
                    .authenticationEntryPoint(customExceptionHandler))
	        /*
	        .exceptionHandling((exception)-> exception.accessDeniedHandler((request, response, accessDeniedHandler) -> {
	        	response.setContentType("application/json");
	            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	            response.getOutputStream().println("Usuario no autorizado.");//.println("{ \"error\": \"" + accessDeniedHandler.getMessage() + "\" }");
            }))
	        .exceptionHandling((exception)-> exception.authenticationEntryPoint((request, response, authException) -> {
                //response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
	        	response.setContentType("application/json");
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            response.getOutputStream().println("Usuario no autorizado.");//.println("{ \"error\": \"" + authException.getMessage() + "\" }");
            }))
	        */
	        .authenticationManager(authenticationManager)
	        
	        //.build();
	    	//Add JWT token filter
	      	
	        .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
	      	.build();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	    AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
	    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	    return authenticationManagerBuilder.build();
	}
	
	
	
}
