package com.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.modelo.Usuario;
import com.repositorio.UsuarioRepositorio;

@Service
@Transactional(readOnly = true)
public class UsuarioService {

	@Autowired
	private UsuarioRepositorio repository;
	
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void signup(Usuario request) {
		String email = request.getEmail();
		Optional<Usuario> existingUser = repository.findByEmail(email);
		if (existingUser.isPresent()) {
			throw new DuplicateKeyException(String.format("El usuario con el email '%s' ya esta registrado.", email));
		}
		String claveEncriptada = passwordEncoder.encode(request.getClave());
		Usuario user = new Usuario(email, claveEncriptada);
		repository.save(user);
	}
	
	public List<Usuario> listarUsuarios(){
		return repository.findAll();
	}
	
	public Usuario editarUsuario(Usuario u) {
		Usuario guardado = getById(u.getId());
		guardado.editar(u);
		return guardarUsuario(guardado);
	}
	
	@Transactional
	public Usuario encriptarYGuardarUsuario(Usuario u) {
		String claveEncriptada = passwordEncoder.encode(u.getClave());
		u.setClave(claveEncriptada);
		return repository.save(u);
		//return null;
	}
	
	public Usuario guardarUsuario(Usuario u) {
		return repository.save(u);
	}
	
	@Transactional
	public void eliminarUsuario(Usuario u) {
		repository.delete(u);
	}
	@Transactional
	public void eliminarUsuario(long id) {
		repository.deleteById(id);
	}
	
	public Usuario getById(long id) {
		return repository.findById(id).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el id: " + id));
	}
	public Usuario getByEmail(String email) {
		return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el email: " + email));
	}
}
