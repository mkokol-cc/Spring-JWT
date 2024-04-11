package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class JwtUsuarioRolesComponentApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtUsuarioRolesComponentApplication.class, args);
		System.out.println("Spring Framework version: " + SpringVersion.getVersion());
	}

}
