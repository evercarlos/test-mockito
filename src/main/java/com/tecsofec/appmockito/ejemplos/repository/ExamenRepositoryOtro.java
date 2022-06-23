package com.tecsofec.appmockito.ejemplos.repository;

import com.tecsofec.appmockito.ejemplos.model.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author EVER C.R
 */
public class ExamenRepositoryOtro implements ExamenRepository{

    @Override
    public List<Examen> findAll() {
        try {
            System.out.println("ExamenRepositoryOtro");
            TimeUnit.SECONDS.sleep(5);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
