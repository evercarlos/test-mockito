package com.tecsofec.appmockito.ejemplos.repository;

import java.util.List;

/**
 * @author EVER C.R
 */
public interface PreguntaRepository {

    List<String> findPreguntasPorExamenId(Long id);
}
