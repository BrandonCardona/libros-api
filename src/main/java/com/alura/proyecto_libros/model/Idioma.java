package com.alura.proyecto_libros.model;

public enum Idioma {

    ESPANIOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("pt"),
    OTROIDIOMA("otro");

    private String lenguaje;

    Idioma (String lenguaje){
        this.lenguaje = lenguaje;
    }

    public static Idioma fromString(String text){
        for (Idioma idioma: Idioma.values()){
            if(idioma.lenguaje.equalsIgnoreCase(text)){
                return idioma;
            }
        }
        return OTROIDIOMA;
    }

}
