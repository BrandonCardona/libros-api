package com.alura.proyecto_libros.repository;

import com.alura.proyecto_libros.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor,Long> {

    @Query("SELECT a FROM Autor a WHERE :anio BETWEEN a.fechaNacimiento AND a.fechaFallecimiento")
    List<Autor> findAutoresVivosEnAnio(@Param("anio") Long anio);

}
