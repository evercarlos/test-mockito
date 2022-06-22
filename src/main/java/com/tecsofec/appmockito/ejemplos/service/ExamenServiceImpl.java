package com.tecsofec.appmockito.ejemplos.service;

import com.tecsofec.appmockito.ejemplos.model.Examen;
import com.tecsofec.appmockito.ejemplos.repository.ExamenRepository;

/**
 * @author EVER C.R
 */
public class ExamenServiceImpl implements ExamenService {

    private ExamenRepository examenRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository) {
        this.examenRepository = examenRepository;
    }

    @Override
    public Examen findExamenPorNombre(String nombre) {
        examenRepository.findAll().stream().filter(e -> e.getNombre().contains(nombre));
        return null;
    }
}
