package com.tecsofec.appmockito.ejemplos.service;

import com.tecsofec.appmockito.ejemplos.model.Examen;
import com.tecsofec.appmockito.ejemplos.repository.ExamenRepository;
import com.tecsofec.appmockito.ejemplos.repository.ExamenRepositoryOtro;
import com.tecsofec.appmockito.ejemplos.repository.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @author EVER C.R
 */
@ExtendWith(MockitoExtension.class) // Para inyeccíon.
class ExamenServiceImplTest {

    @Mock
    ExamenRepository repository;

    //@Mock
    //ExamenService service;

    @Mock
    PreguntaRepository preguntaRepository;

    @InjectMocks
    ExamenServiceImpl service;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);
        //this.repository = mock(ExamenRepositoryOtro.class);
        //preguntaRepository = mock(PreguntaRepository.class);
       // this.service = new ExamenServiceImpl(repository, preguntaRepository);
    }

    @Test
    void findExamenPorNombre() {
        //ExamenRepository repository = mock(ExamenRepository.class);
        /*List<Examen> datos = Arrays.asList(new Examen(5L, "Matematicas"), new Examen(6L, "Lenguaje")
                , new Examen(7L, "Historia"));*/
        // mock when
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        // mockito
        //assertNotNull(examen);
        assertTrue(examen.isPresent());
        assertEquals(5L, examen.orElseThrow(null).getId());
        assertEquals("Matematicas", examen.orElseThrow(null).getNombre());
    }

    @Test
    void findExamenPorNombreListVacia() {
        List<Examen> datos = Collections.emptyList();
        // mock when
        when(repository.findAll()).thenReturn(datos);
        Optional<Examen> examen = service.findExamenPorNombre("Matematicas");
        // mockito
        //assertNotNull(examen);
        assertFalse(examen.isPresent());
    }

    @Test
    void testPreguntasExamen(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);// when: Cuando
        //when(preguntaRepository.findPreguntasPorExamenId(7L)).thenReturn(Datos.PREGUNTAS);
        // anyLong: Para cualquier id
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));
    }

    @Test
    void testPreguntasExamenVerify(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);// when: Cuando
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertEquals(5, examen.getPreguntas().size());
        assertTrue(examen.getPreguntas().contains("aritmetica"));
        // new
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void testNoExisteExamenVerify(){
        when(repository.findAll()).thenReturn(Datos.EXAMENES);// when: Cuando
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        Examen examen = service.findExamenPorNombreConPreguntas("Matematicas");
        assertNotNull(examen);
        //assertEquals(5, examen.getPreguntas().size());
        //assertTrue(examen.getPreguntas().contains("aritmetica"));
        // new
        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(5L);
    }

    @Test
    void GuardarExmamen(){
        when(repository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);// any: Cualquier tipo de examen
        Examen  examen = service.guardar(Datos.EXAMEN);
    }
}