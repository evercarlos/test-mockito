package com.tecsofec.appmockito.ejemplos.service;

import com.tecsofec.appmockito.ejemplos.model.Examen;

import java.util.Arrays;
import java.util.List;

/**
 * @author EVER CARLOS ROJAS
 */
public class Datos {

    public final static List<Examen> EXAMENES = Arrays.asList(
            new Examen(5L, "Matematicas"),
            new Examen(6L, "Lenguaje")
            , new Examen(7L, "Historia")
    );

    public final static List<String> PREGUNTAS = Arrays.asList("aritmetica", "integrales", "direvadas", "trigonometria",
            "geometria");

   // public final  static Examen EXAMEN = new Examen(8L, "Fisica");
    public final  static Examen EXAMEN = new Examen(null, "Fisica");
}
