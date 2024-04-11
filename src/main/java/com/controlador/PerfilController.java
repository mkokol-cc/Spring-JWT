package com.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.modelo.Perfil;
import com.services.PerfilService;

import jakarta.validation.Valid;

@RestController
@CrossOrigin("*")
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	private PerfilService perfilService;
	
	@PostMapping("/save")
	public ResponseEntity<Perfil> guardarPerfil(@Valid @RequestBody Perfil perfil){
		return ResponseEntity.ok(perfilService.guardarPerfil(perfil));
	}
	
	@GetMapping("")
	public ResponseEntity<List<Perfil>> listarPerfiles(){
		return ResponseEntity.ok(perfilService.listarPerfiles()); 
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Void> eliminarPerfil(@RequestBody Perfil perfil) throws Exception{
		perfilService.eliminarPerfil(perfil);
		return ResponseEntity.status(HttpStatus.ACCEPTED).build();
	}
	
	/*
	public ResponseEntity<Perfil> editarPerfil(@RequestBody Perfil perfil){
		return ResponseEntity.ok(perfilRepo.save(perfil));
	}
	*/
}
