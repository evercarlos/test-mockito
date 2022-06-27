package com.tecsofec.appmockito.ejemplos.repository;

import com.tecsofec.appmockito.ejemplos.model.Examen;

import java.util.List;

/**
 * @author EVER C.R
 */
public interface ExamenRepository {

    Examen guardar(Examen examen);

    List<Examen> findAll();

}
