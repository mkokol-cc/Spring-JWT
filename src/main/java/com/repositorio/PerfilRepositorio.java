package com.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.modelo.Perfil;

@Repository
public interface PerfilRepositorio extends JpaRepository<Perfil,Long>{

}
