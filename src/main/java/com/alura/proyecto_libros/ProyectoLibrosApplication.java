package com.alura.proyecto_libros;

import com.alura.proyecto_libros.principal.Principal;
import com.alura.proyecto_libros.repository.AutorRepository;
import com.alura.proyecto_libros.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProyectoLibrosApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository repository;
	@Autowired
	private AutorRepository repositorioAutor;

	public static void main(String[] args) {
		SpringApplication.run(ProyectoLibrosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(repository, repositorioAutor);
		principal.muestraElMenu();
	}

}
