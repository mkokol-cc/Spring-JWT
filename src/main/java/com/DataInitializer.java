package com;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.modelo.Perfil;
import com.modelo.Rol;
import com.modelo.Usuario;
import com.repositorio.PerfilRepositorio;
import com.repositorio.RolRepositorio;
import com.repositorio.UsuarioRepositorio;
import com.services.PerfilService;
import com.services.UsuarioService;

@Component
public class DataInitializer implements ApplicationRunner {
	
    @Autowired
    private RolRepositorio rolRepository;

    @Autowired
    private PerfilRepositorio perfilRepository;

    @Autowired
    private UsuarioService usuarioService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
    	/*
        // Crear Roles
        Rol rol1 = new Rol("PERFIL_EDITOR", "Podes Agergar, Editar y Borrar Perfiles");
        Rol rol2 = new Rol("PERFIL_VIEWER", "Podes Ver Los Perfiles");
        Rol rol3 = new Rol("USUARIO_EDITOR", "Podes Agergar, Editar y Borrar Usuarios");
        Rol rol4 = new Rol("USUARIO_VIEWER", "Podes Ver Los Usuarios");
        
        Rol rol1DB = rolRepository.save(rol1);
        Rol rol2DB = rolRepository.save(rol2);
        Rol rol3DB = rolRepository.save(rol3);
        Rol rol4DB = rolRepository.save(rol4);

        // Crear Perfil con todos los roles
        Perfil perfil = new Perfil();
        perfil.setNombre("ADMINISTRADOR");
        perfil.addRol(rol1DB);
        perfil.addRol(rol2DB);
        perfil.addRol(rol3DB);
        perfil.addRol(rol4DB);
        Perfil perfilDB = perfilRepository.save(perfil);
        
        Perfil p1 = new Perfil();
        p1.setNombre("PERFIL_EDITOR");
        p1.addRol(rol1DB);
        perfilRepository.save(p1);
        Perfil p2 = new Perfil();
        p2.setNombre("PERFIL_VIEWER");
        p2.addRol(rol2DB);
        perfilRepository.save(p2);
        Perfil p3 = new Perfil();
        p3.setNombre("USUARIO_EDITOR");
        p3.addRol(rol3DB);
        perfilRepository.save(p3);
        Perfil p4 = new Perfil();
        p4.setNombre("USUARIO_VIEWER");
        p4.addRol(rol4DB);
        perfilRepository.save(p4);

        // Crear Usuario con el perfil
        Usuario superUsuario = new Usuario("admin@gmail.com", "password");
        superUsuario.setPerfil(perfilDB);
        usuarioService.encriptarYGuardarUsuario(superUsuario);*/
    }
}