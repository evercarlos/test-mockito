package com.tecsofec.appmockito.ejemplos.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author EVER C.R
 */
public class Examen {

    private Long id;
    private String nombre;
    private List<String>preguntas;

    public Examen(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.preguntas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
