package com.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.modelo.Usuario;
import com.repositorio.UsuarioRepositorio;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	//usamos la clase UsuarioRepositorio para obtener todos los datos necesarios de los usaurios
	private final UsuarioRepositorio repo;
	public UserDetailsServiceImpl(UsuarioRepositorio repository) {
		this.repo = repository;
	}

	//Metodo que construye el userdetail o devuelve una Exception en caso de que no se encuentre un usaurio con el email dado
	@Override
	public UserDetails loadUserByUsername(String email) {
		Usuario usuario = repo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));
		return org.springframework.security.core.userdetails.User.builder()
			.username(usuario.getEmail())
			.password(usuario.getClave())
			.authorities(usuario.getAuthorities())
			.build();
	}
}
