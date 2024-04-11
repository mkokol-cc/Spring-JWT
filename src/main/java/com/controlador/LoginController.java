package com.controlador;

import java.nio.file.AccessDeniedException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.JwtHelper;
import com.jwt.UserDetailsServiceImpl;
import com.modelo.Usuario;
import com.services.UsuarioService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
public class LoginController {
	
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
	
	private final UsuarioService usuarioService;

	public LoginController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	

	@PostMapping("/signup")
	public ResponseEntity<Void> signup(@Valid @RequestBody Usuario usuario) {
		usuarioService.signup(usuario);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody Usuario usuario) {
	    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getClave()));
	    String token = JwtHelper.generateToken(usuario.getEmail());
	    return ResponseEntity.ok(token);
	}

	@GetMapping("/my-account")
	public ResponseEntity<Usuario> getAuthenticatedUser(@RequestHeader("Authorization") String tokenHeader) throws AccessDeniedException {
		return ResponseEntity.ok(getUserByToken(tokenHeader));
	}
	
	@PostMapping("/my-account")
	public ResponseEntity<Usuario> editAuthenticatedUser(@RequestHeader("Authorization") String tokenHeader, @Valid @RequestBody Usuario usuario) throws AccessDeniedException {
		Usuario u = getUserByToken(tokenHeader);
		//hay datos que el mismo usuario no puede modificar, lo debe hacer alguien con el Rol USUARIO_EDITOR
		//desde un endpoint del UsuarioController
		return ResponseEntity.ok(usuarioService.editarUsuario(u));
	}
	
	@GetMapping("/auth")
	public ResponseEntity<?> misRoles(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        // Ejemplo de cómo obtener roles/autoridades del usuario
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        return ResponseEntity.ok("Hola " + username + "! Tus roles son: " + roles);
	}
	
	
	private Usuario getUserByToken(String token) throws AccessDeniedException {
		String t = token.substring(7); // Elimina el prefijo 'Bearer ' del token
        UserDetails userDetails =  this.userDetailsService.loadUserByUsername(JwtHelper.extractUsername(t));
        JwtHelper.validateToken(t,userDetails);
        return usuarioService.getByEmail(userDetails.getUsername());
	}
}
