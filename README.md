---

# Proyecto de Gestión de Libros y Autores con Gutendex API

Este proyecto utiliza la API Gutendex para gestionar un catálogo de libros y autores. Permite consultar información sobre libros, autores, autores vivos en un determinado año y libros por idioma. Los datos de la API provienen de la base de datos de Project Gutenberg, que contiene más de 70.000 libros gratuitos en línea.

## Tecnologías Utilizadas

- **Java**: Lenguaje principal para el desarrollo del proyecto.
- **Spring Boot**: Para la creación de una aplicación web robusta.
- **Gutendex API**: API para obtener metadatos de libros de Project Gutenberg.

## Funcionalidades

El sistema permite las siguientes operaciones:

1. **Registrar libros y autores**: Al utilizar la API Gutendex, el sistema registra libros y sus respectivos autores en la base de datos.
2. **Consultar libros**: Permite buscar libros por su título.
3. **Listar libros registrados**: Muestra todos los libros que han sido registrados en la base de datos.
4. **Listar autores registrados**: Muestra todos los autores registrados en la base de datos.
5. **Listar autores vivos en un año determinado**: Permite consultar qué autores estaban vivos en un año específico.
6. **Listar libros por idioma**: Permite consultar los libros registrados que están disponibles en un idioma específico (español, inglés, francés, portugués, entre otros).

## Instalación

1. Clona este repositorio:

    ```bash
    git clone https://github.com/BrandonCardona/libros-api.git
    ```

2. Navega al directorio del proyecto:

    ```bash
    cd proyecto-libros
    ```

3. Construye y ejecuta el proyecto con Maven:

    ```bash
    mvn spring-boot:run
    ```
