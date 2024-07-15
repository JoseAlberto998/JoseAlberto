package com.mycompany.clinicaveterinariajosealberto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.clinicaveterinariajosealberto.config.JwtTokenUtil;
import com.mycompany.clinicaveterinariajosealberto.dto.DtoMascotaRegistro;
import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.JwtUserDetailsService;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.MascotaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.when;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@WebMvcTest(MascotaController.class)
public class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaServiceImpl mascotaService;

    @InjectMocks
    private MascotaController mascotaController;
    
    @MockBean
    private JwtUserDetailsService jwtUserDetailsService;

    @MockBean
    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(mascotaController).build();
    }

    private String getJwtToken() {
        return "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTcyMTA1MTUwNCwiZXhwIjoxNzIxMDUyNTA0fQ.9MeOenoJtJQqELQc1U0DQjMOk3YpyxNBjT9YRk-H7Lk";
    }

    @Test
    void testListMascotaById_Success() throws Exception {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);

        when(mascotaService.listById(1)).thenReturn(mascota);

        mockMvc.perform(get("/hospitalclinicovet-api/mascota/1")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id_mascota").value(1));
    }

    @Test
    void testListMascotaById_NotFound() throws Exception {
        when(mascotaService.listById(1)).thenReturn(null);

        mockMvc.perform(get("/hospitalclinicovet-api/mascota/1")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListIngresosByMascotaId_Success() throws Exception {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);
        Ingreso ingreso = new Ingreso();
        List<Ingreso> ingresosList = List.of(ingreso);
        mascota.setIngresosList(ingresosList);

        when(mascotaService.listById(1)).thenReturn(mascota);

        mockMvc.perform(get("/hospitalclinicovet-api/mascota/1/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testListIngresosByMascotaId_NotFound() throws Exception {
        when(mascotaService.listById(1)).thenReturn(null);

        mockMvc.perform(get("/hospitalclinicovet-api/mascota/1/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testListIngresosByMascotaId_NoContent() throws Exception {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);
        mascota.setIngresosList(Collections.emptyList());

        when(mascotaService.listById(1)).thenReturn(mascota);

        mockMvc.perform(get("/hospitalclinicovet-api/mascota/1/ingreso")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testCreateMascota_Success() throws Exception {
        DtoMascotaRegistro mascotadto = new DtoMascotaRegistro();
        mascotadto.setEspecie("beagle");
        mascotadto.setRaza("beagle");
        mascotadto.setEdad("1 mes");
        mascotadto.setCodigo_identificacion("x123456");
        mascotadto.setDni_responsable("12345678A");
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);

        when(mascotaService.transformDtoToMascota(any(DtoMascotaRegistro.class))).thenReturn(mascota);
        when(mascotaService.addMascota(any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(post("/hospitalclinicovet-api/mascota")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mascotadto)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateMascota_notSuccess() throws Exception {
        DtoMascotaRegistro mascotadto = new DtoMascotaRegistro();
        Mascota mascota = new Mascota();

        when(mascotaService.transformDtoToMascota(any(DtoMascotaRegistro.class))).thenReturn(mascota);
        when(mascotaService.addMascota(any(Mascota.class))).thenReturn(mascota);

        mockMvc.perform(post("/hospitalclinicovet-api/mascota")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(mascotadto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testSetInactiveMascota_Success() throws Exception {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);

        when(mascotaService.listById(1)).thenReturn(mascota);

        mockMvc.perform(delete("/hospitalclinicovet-api/mascota/1")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testSetInactiveMascota_NotFound() throws Exception {
        when(mascotaService.listById(1)).thenReturn(null);

        mockMvc.perform(delete("/hospitalclinicovet-api/mascota/1")
                .header("Authorization", getJwtToken())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
