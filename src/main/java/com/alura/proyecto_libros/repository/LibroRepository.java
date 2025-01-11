package com.alura.proyecto_libros.repository;

import com.alura.proyecto_libros.model.Idioma;
import com.alura.proyecto_libros.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro,Long> {

    List<Libro> findByIdioma(Idioma idioma);

}
