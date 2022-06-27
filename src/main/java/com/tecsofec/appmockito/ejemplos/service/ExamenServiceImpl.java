package com.tecsofec.appmockito.ejemplos.service;

import com.tecsofec.appmockito.ejemplos.model.Examen;
import com.tecsofec.appmockito.ejemplos.repository.ExamenRepository;
import com.tecsofec.appmockito.ejemplos.repository.PreguntaRepository;

import java.util.List;
import java.util.Optional;

/**
 * @author EVER C.R
 */
public class ExamenServiceImpl implements ExamenService {

    private ExamenRepository examenRepository;
    private PreguntaRepository preguntaRepository;

    public ExamenServiceImpl(ExamenRepository examenRepository, PreguntaRepository preguntaRepository) {
        this.examenRepository = examenRepository;
        this.preguntaRepository = preguntaRepository;
    }

    @Override
    public Optional<Examen> findExamenPorNombre(String nombre) {
     Optional<Examen> examenOptional= examenRepository.findAll().stream().filter(e -> e.getNombre().contains(nombre))
                .findFirst();

        return examenOptional;
    }

    @Override
    public Examen findExamenPorNombreConPreguntas(String nombre) {

        Optional<Examen> examenOptional = findExamenPorNombre(nombre);
        Examen examen = null;
        if(examenOptional.isPresent()){
            examen = examenOptional.orElseThrow(null);
            /*List<String> preguntas = preguntaRepository.findPreguntasPorExamenId(examen.getId());
            examen.setPreguntas(preguntas);*/
        }
        return examen;
    }
}
