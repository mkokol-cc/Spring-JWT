package com.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.modelo.Rol;
import com.repositorio.RolRepositorio;

@Service
public class RolService {
	
	private final RolRepositorio repository;

	public RolService(RolRepositorio repository) {
		this.repository = repository;
	}
	
	public List<Rol> listarRoles(){
		return repository.findAll();
	}

}
