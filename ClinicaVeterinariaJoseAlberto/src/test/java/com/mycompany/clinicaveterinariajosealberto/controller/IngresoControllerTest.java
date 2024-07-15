package com.mycompany.clinicaveterinariajosealberto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.clinicaveterinariajosealberto.config.JwtTokenUtil;
import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngreso;
import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngresoRegistro;
import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngresoUpdate;
import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.IngresoServiceImpl;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.JwtUserDetailsService;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.MascotaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Collections;
import java.sql.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

@WebMvcTest(IngresoController.class)
class IngresoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngresoServiceImpl ingresoService;

    @MockBean
    private MascotaServiceImpl mascotaService;

    @InjectMocks
    private IngresoController ingresoController;
    
    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;
    
    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ingresoController).build();
    }
    
      private String getJwtToken() {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyMTA1MTUwNCwiZXhwIjoxNzIxMDUyNTA0fQ.9MeOenoJtJQqELQc1U0DQjMOk3YpyxNBjT9YRk-H7Lk";
    }

    @Test
    void testListAllIngresos() throws Exception {
        DtoIngreso dtoIngreso = new DtoIngreso(); 
        when(ingresoService.trasformToIngresoDTO(any())).thenReturn(Collections.singletonList(dtoIngreso));
        when(ingresoService.listAllIngresos()).thenReturn(Collections.singletonList(new Ingreso()));

        mockMvc.perform(get("/hospitalclinicovet-api/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
    
    @Test
    void testListAllIngresos_NoIngresos() throws Exception {
        when(ingresoService.listAllIngresos()).thenReturn(Collections.emptyList());
        when(ingresoService.trasformToIngresoDTO(Collections.emptyList())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/hospitalclinicovet-api/ingreso")
                .header("Authorization", getJwtToken())
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateIngreso_Success() throws Exception {
        DtoIngresoRegistro ingresoRegistro = new DtoIngresoRegistro();
        ingresoRegistro.setId_mascota(1);
        ingresoRegistro.setDniPersona("12345678A");
        ingresoRegistro.setFecha_ingreso(new Date(2024, 07, 10)); 

        Mascota mascota = new Mascota();
        mascota.setDni_responsable("12345678A");
        mascota.setInactive(false);

        Ingreso createdIngreso = new Ingreso();
        createdIngreso.setId_ingreso(1);

        when(mascotaService.listById(1)).thenReturn(mascota);
        when(ingresoService.addIngreso(any(Ingreso.class))).thenReturn(createdIngreso);

        mockMvc.perform(post("/hospitalclinicovet-api/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingresoRegistro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id_ingreso").value(1));
    }

    @Test
    void testCreateIngreso_DniMismatch() throws Exception {
        DtoIngresoRegistro ingresoRegistro = new DtoIngresoRegistro();
        ingresoRegistro.setId_mascota(1);
        ingresoRegistro.setDniPersona("12345678B");
        ingresoRegistro.setFecha_ingreso(new Date(2024, 07, 10));

        Mascota mascota = new Mascota();
        mascota.setDni_responsable("12345678A");
        mascota.setInactive(false);

        when(mascotaService.listById(1)).thenReturn(mascota);

        mockMvc.perform(post("/hospitalclinicovet-api/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingresoRegistro)))
                .andExpect(status().isConflict());
    }

    @Test
    void testCreateIngreso_MascotaInactive() throws Exception {
        DtoIngresoRegistro ingresoRegistro = new DtoIngresoRegistro();
        ingresoRegistro.setId_mascota(1);
        ingresoRegistro.setDniPersona("12345678A");
        ingresoRegistro.setFecha_ingreso(new Date(2024, 07, 10));

        Mascota mascota = new Mascota();
        mascota.setDni_responsable("12345678A");
        mascota.setInactive(true);

        when(mascotaService.listById(1)).thenReturn(mascota);

        mockMvc.perform(post("/hospitalclinicovet-api/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingresoRegistro)))
                .andExpect(status().isConflict());
    }

    @Test
    void testCreateIngreso_MascotaNotFound() throws Exception {
        DtoIngresoRegistro ingresoRegistro = new DtoIngresoRegistro();
        ingresoRegistro.setId_mascota(1);
        ingresoRegistro.setDniPersona("12345678A");
        ingresoRegistro.setFecha_ingreso(new Date(2024, 07, 10));

        when(mascotaService.listById(1)).thenReturn(null);

        mockMvc.perform(post("/hospitalclinicovet-api/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingresoRegistro)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateIngreso_Success() throws Exception {
        DtoIngresoUpdate ingresoUpdate = new DtoIngresoUpdate();
        ingresoUpdate.setEstado(Ingreso.Estado.FINALIZADO);
        ingresoUpdate.setFecha_finalizacion(new Date(2024, 07, 10));

        Mascota existingMascota = new Mascota();
        existingMascota.setId_mascota(1);

        Ingreso existingIngreso = new Ingreso();
        existingIngreso.setId_ingreso(1);
        existingIngreso.setMascota(existingMascota);
        existingIngreso.setFecha_finalizacion(null);

        when(mascotaService.listById(1)).thenReturn(existingMascota);
        when(ingresoService.listById(1)).thenReturn(existingIngreso);

        when(ingresoService.updateIngreso(any(Ingreso.class))).thenReturn(existingIngreso);

        mockMvc.perform(put("/hospitalclinicovet-api/ingreso/1/1")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingresoUpdate)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateIngreso_NotFound() throws Exception {

        DtoIngresoUpdate ingresoUpdate = new DtoIngresoUpdate();
        ingresoUpdate.setEstado(Ingreso.Estado.FINALIZADO);
        ingresoUpdate.setFecha_finalizacion(new Date(2024, 07, 10));

        when(mascotaService.listById(1)).thenReturn(null);
        when(ingresoService.listById(1)).thenReturn(null);

        mockMvc.perform(put("/hospitalclinicovet-api/ingreso/1/1")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingresoUpdate)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateIngreso_InvalidStateAndDate() throws Exception {
        DtoIngresoUpdate ingresoUpdate = new DtoIngresoUpdate();
        ingresoUpdate.setEstado(Ingreso.Estado.FINALIZADO);
        ingresoUpdate.setFecha_finalizacion(null);

        
        Mascota existingMascota = new Mascota();
        existingMascota.setId_mascota(1);
        
        Ingreso existingIngreso = new Ingreso();
        existingIngreso.setId_ingreso(1);
        existingIngreso.setFecha_finalizacion(null);
        existingIngreso.setMascota(existingMascota);

       

        when(mascotaService.listById(1)).thenReturn(existingMascota);
        when(ingresoService.listById(1)).thenReturn(existingIngreso);

        mockMvc.perform(put("/hospitalclinicovet-api/ingreso/1/1")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(ingresoUpdate)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteIngreso_Success() throws Exception {

        Ingreso existingIngreso = new Ingreso();
        existingIngreso.setId_ingreso(1);
        existingIngreso.setEstado(Ingreso.Estado.ALTA);

        when(ingresoService.listById(1)).thenReturn(existingIngreso);

        mockMvc.perform(delete("/hospitalclinicovet-api/ingreso/1")
                .header("Authorization", getJwtToken()))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteIngreso_NotFound() throws Exception {
        when(ingresoService.listById(1)).thenReturn(null);

        mockMvc.perform(delete("/hospitalclinicovet-api/ingreso/1")
                .header("Authorization", getJwtToken()))
                .andExpect(status().isNotFound());
    }
}
