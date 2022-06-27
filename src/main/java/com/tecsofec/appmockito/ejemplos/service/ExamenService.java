package com.tecsofec.appmockito.ejemplos.service;

import com.tecsofec.appmockito.ejemplos.model.Examen;

import java.util.Optional;

/**
 * @author EVER C.R
 */
public interface ExamenService {

    Optional<Examen> findExamenPorNombre(String nombre);

    Examen findExamenPorNombreConPreguntas(String nombre);

    Examen guardar(Examen examen);

}
