package com.tecsofec.appmockito.ejemplos.service;

import com.tecsofec.appmockito.ejemplos.model.Examen;
import com.tecsofec.appmockito.ejemplos.repository.ExamenRepository;
import com.tecsofec.appmockito.ejemplos.repository.ExamenRepositoryOtro;
import com.tecsofec.appmockito.ejemplos.repository.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

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

    /*@Test
    void GuardarExmamen(){
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);// Si se comentta no pasa

        when(repository.guardar(any(Examen.class))).thenReturn(Datos.EXAMEN);// any: Cualquier tipo de examen
        Examen  examen = service.guardar(newExamen);
        assertNotNull(examen.getId());
        assertEquals(8L,examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }*/
    @Test
    void GuardarExmamen(){
        // Given
        Examen newExamen = Datos.EXAMEN;
        newExamen.setPreguntas(Datos.PREGUNTAS);// Si se comentta no pasa

        when(repository.guardar(any(Examen.class))).then(new Answer<Examen>(){

            Long secuencia = 8L;

            @Override
            public Examen answer(InvocationOnMock invocation) throws Throwable {
               Examen examen = invocation.getArgument(0);
               examen.setId(secuencia++);
               return  examen;
            }
        });

        Examen  examen = service.guardar(newExamen);
        assertNotNull(examen.getId());
        assertEquals(8L,examen.getId());
        assertEquals("Fisica", examen.getNombre());

        verify(repository).guardar(any(Examen.class));
        verify(preguntaRepository).guardarVarias(anyList());
    }

    @Test
    void testManejoExeception() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenThrow(IllegalArgumentException.class);
        Exception exception= assertThrows(IllegalArgumentException.class, () ->{
            service.findExamenPorNombreConPreguntas("Matematicas");
            //service.findExamenPorNombre("Matematicas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
    }

    @Test
    void testManejoExeception1() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
        when(preguntaRepository.findPreguntasPorExamenId(isNull())).thenThrow(IllegalArgumentException.class);
        Exception exception= assertThrows(IllegalArgumentException.class, () ->{
            service.findExamenPorNombreConPreguntas("Matematicas");
            //service.findExamenPorNombre("Matematicas");
        });
        assertEquals(IllegalArgumentException.class, exception.getClass());

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(isNull());
    }

    // VALIDANDO ARGUMENTO
    @Test
    void testArgumentMatchers() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        //verify(preguntaRepository).findPreguntasPorExamenId(ArgumentMatchers.argThat(arg-> arg.equals(6L)));
        //verify(preguntaRepository).findPreguntasPorExamenId(ArgumentMatchers.argThat(arg-> arg!=null && arg.equals(5L)));
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg-> arg!=null && arg >= 5L));

    }

    @Test
    void testArgumentMatchers2() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));

    }


    @Test
    void testArgumentMatchers3() {
        when(repository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
        when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
        service.findExamenPorNombreConPreguntas("Matematicas");

        verify(repository).findAll();
        verify(preguntaRepository).findPreguntasPorExamenId(argThat((argument)->argument!=null && argument >0));// no muestra el mensaje personalizado

    }



    // VALIDAR ARGUMENTO
    public static class MiArgsMatchers implements ArgumentMatcher<Long> {

        private Long argument;

        @Override
        public boolean matches(Long argument) {
            this.argument = argument;
            return argument!=null && argument >0;
        }

        @Override
        public String toString() {
            return "Es por el mensaje personalizado de error que imprime mockito en caso de que falle el test" +
                    " "+this.argument +" debe ser un entero positivo";
        }
    }

    // CAPTURAR ARGUMENTO

    @Test
    void testArgumentoCaptor() {

    }

}
