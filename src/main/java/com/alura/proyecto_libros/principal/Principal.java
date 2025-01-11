package com.alura.proyecto_libros.principal;

import com.alura.proyecto_libros.model.*;
import com.alura.proyecto_libros.repository.AutorRepository;
import com.alura.proyecto_libros.repository.LibroRepository;
import com.alura.proyecto_libros.service.ConsumoAPI;
import com.alura.proyecto_libros.service.ConvierteDatos;

import java.util.*;

public class Principal {

    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books?search=";
    private ConvierteDatos conversor = new ConvierteDatos();


    private LibroRepository repositorio;
    private AutorRepository repositorioAutor;
    private List<Autor> autores;
    private List<Libro> libros;

    public Principal(LibroRepository repositorio, AutorRepository repositorioAutor){
        this.repositorio = repositorio;
        this.repositorioAutor = repositorioAutor;
        this.autores = repositorioAutor.findAll();
        this.libros = repositorio.findAll();
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    --------------------------------------
                    Eliga la opcion a traves de su numero:
                    1 - Buscar libro por titulo
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                            
                    0 - Salir
                    """;
            System.out.println(menu);

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Por favor, ingrese un número.");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivosAnio();
                    break;
                case 5:
                    listarLibrosPorIdioma();
                    break;

                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }


    private DatosLibro getDatosLibro() {
        System.out.println("Ingresa el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));

        try {
            ResponseApi response = conversor.obtenerDatos(json, ResponseApi.class);

            if (response.getResults() != null && !response.getResults().isEmpty()) {
                DatosLibro datos = response.getResults().get(0);
                return datos;
            } else {
                System.out.println("No se encontraron resultados");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private void buscarLibroPorTitulo() {

        DatosLibro datos = getDatosLibro();
        if (datos == null) {
            return;
        }

        DatosAutor datosAutor = datos.autor().get(0);

        Autor autor = autores.stream()
                .filter(a -> a.getNombre().equals(datosAutor.nombre())
                        && a.getFechaNacimiento().equals(datosAutor.fechaNacimiento())
                        && a.getFechaFallecimiento().equals(datosAutor.fechaFallecimiento()))
                .findFirst()
                .orElseGet(() -> {
                    Autor nuevoAutor = new Autor(datosAutor);
                    repositorioAutor.save(nuevoAutor);
                    autores.add(nuevoAutor);
                    return nuevoAutor;
                });

        boolean libroExiste = libros.stream()
                .anyMatch(l -> l.getTitulo().equals(datos.titulo())
                        && l.getAutor().getNombre().equals(autor.getNombre()));

        if (libroExiste) {
            System.out.println("El libro con el título '" + datos.titulo() + "' ya ha sido registrado");
            return;
        }

        Libro libro = new Libro(datos);
        autor.setLibros(Collections.singletonList(libro));
        repositorio.save(libro);
        libros.add(libro);
        System.out.println(libro);
    }

    private void listarLibrosRegistrados() {

        System.out.println("LIBROS REGISTRADOS");
        libros = repositorio.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getIdioma))
                .forEach(System.out::println);
    }

    private void listarAutoresRegistrados() {
        System.out.println("AUTORES REGISTRADOS");
        autores = repositorioAutor.findAll();
        autores.forEach(System.out::println);
    }

    private void listarAutoresVivosAnio() {

        System.out.println("Ingrese el año que desea buscar");
        var anio = teclado.nextLong();
        teclado.nextLine();
        List<Autor> autoresVivos = repositorioAutor.findAutoresVivosEnAnio(anio);

        if (!autoresVivos.isEmpty()) {
            System.out.println("\nLos autores vivos en el año " + anio + " son:");
            autoresVivos.forEach(System.out::println);
        } else {
            System.out.println("\nNo se encontraron autores vivos en el año " + anio);
        }
    }

    private void listarLibrosPorIdioma() {

        System.out.println("Ingrese el idioma para buscar los libros\n"+
                "es- Español\n"+
                "en- Ingles\n"+
                "fr- Frances\n"+
                "pt- Portugues\n"+
                "otro- Otro Idioma\n");
        var opcion = teclado.nextLine();

        if (!opcion.equalsIgnoreCase("es") &&
                !opcion.equalsIgnoreCase("en") &&
                !opcion.equalsIgnoreCase("fr") &&
                !opcion.equalsIgnoreCase("pt") &&
                !opcion.equalsIgnoreCase("otro")) {
            System.out.println("Opción de idioma no valido");
            return;
        }

        var idioma = Idioma.fromString(opcion);
        List<Libro> librosPorIdioma = repositorio.findByIdioma(idioma);
        System.out.println("Los libros del idioma " + opcion + " son: ");
        librosPorIdioma.forEach(System.out::println);
    }
}
