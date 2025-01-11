package com.alura.proyecto_libros.model;

import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name="autor")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String nombre;
    private Long fechaNacimiento;
    private Long fechaFallecimiento;
    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public Autor(){

    }

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Long getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Long fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Long fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        if (this.libros == null) {
            this.libros = new ArrayList<>();
        }
        libros.forEach(libro -> libro.setAutor(this));
        this.libros.addAll(libros);
    }


    @Override
    public String toString() {

        String librosTítulos = libros.stream()
                .map(l -> l.getTitulo())
                .collect(Collectors.joining(", "));
        return
                "\nAutor: " + nombre + '\n' +
                "Fecha de nacimiento: " + fechaNacimiento + '\n' +
                "Fecha de fallecimiento: " + fechaFallecimiento + '\n' +
                "Libros: " + librosTítulos + '\n';
    }
}



