package com.mycompany.clinicaveterinariajosealberto.service;

import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import com.mycompany.clinicaveterinariajosealberto.repository.IngresoRepository;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.IngresoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


/**
 *
 * @author Jose Alberto
 */

class IngresoServiceTest {

    @Mock
    private IngresoRepository ingresoRepository;

    @InjectMocks
    private IngresoServiceImpl ingresoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddIngreso() {
        Ingreso ingreso = new Ingreso();
        ingreso.setId_ingreso(1);

        when(ingresoRepository.save(any(Ingreso.class))).thenReturn(ingreso);

        Ingreso result = ingresoService.addIngreso(ingreso);

        assertNotNull(result);
        assertEquals(1, result.getId_ingreso());
        verify(ingresoRepository, times(1)).save(ingreso);
    }

    @Test
    void testUpdateIngreso() {
        Ingreso ingreso = new Ingreso();
        ingreso.setId_ingreso(1);

        when(ingresoRepository.save(any(Ingreso.class))).thenReturn(ingreso);

        Ingreso result = ingresoService.updateIngreso(ingreso);

        assertNotNull(result);
        assertEquals(1, result.getId_ingreso());
        verify(ingresoRepository, times(1)).save(ingreso);
    }

    @Test
    void testDeleteIngreso() {
        Ingreso ingreso = new Ingreso();
        ingreso.setId_ingreso(1);
        ingreso.setEstado(Ingreso.Estado.ALTA);

        when(ingresoRepository.save(any(Ingreso.class))).thenReturn(ingreso);

        Ingreso result = ingresoService.deleteIngreso(ingreso);

        assertNotNull(result);
        assertEquals(Ingreso.Estado.ANULADO, result.getEstado());
        verify(ingresoRepository, times(1)).save(ingreso);
    }

    @Test
    void testListAllIngresos() {
        Ingreso ingreso1 = new Ingreso();
        ingreso1.setId_ingreso(1);

        Ingreso ingreso2 = new Ingreso();
        ingreso2.setId_ingreso(2);

        List<Ingreso> ingresos = Arrays.asList(ingreso1, ingreso2);

        when(ingresoRepository.findAll()).thenReturn(ingresos);

        List<Ingreso> result = ingresoService.listAllIngresos();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(ingresoRepository, times(1)).findAll();
    }

    @Test
    void testListById() {
        Ingreso ingreso = new Ingreso();
        ingreso.setId_ingreso(1);

        when(ingresoRepository.findById(1)).thenReturn(Optional.of(ingreso));

        Ingreso result = ingresoService.listById(1);

        assertNotNull(result);
        assertEquals(1, result.getId_ingreso());
        verify(ingresoRepository, times(1)).findById(1);
    }

}