package com.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.modelo.Perfil;
import com.repositorio.PerfilRepositorio;

@Service
public class PerfilService {
	
	@Autowired
	private UsuarioService usuarioService;
	
	private final PerfilRepositorio repository;

	public PerfilService(PerfilRepositorio repository) {
		this.repository = repository;
	}

	public Perfil guardarPerfil(Perfil perfil) {
		return repository.save(perfil);
	}

	public List<Perfil> listarPerfiles() {
		return repository.findAll();
	}

	public void eliminarPerfil(Perfil perfil) throws Exception {
		Perfil p = repository.findById(perfil.getId()).orElseThrow(() -> new Exception("Perfil no encontrado con el id:"+perfil.getId()));
		p.getUsuarios().forEach(u->{
			u.setPerfil(null);
			usuarioService.guardarUsuario(u);
		});
		repository.delete(perfil);
	}

	
}
