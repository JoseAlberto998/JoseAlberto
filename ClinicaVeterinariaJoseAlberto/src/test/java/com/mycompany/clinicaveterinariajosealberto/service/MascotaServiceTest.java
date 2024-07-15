
package com.mycompany.clinicaveterinariajosealberto.service;

import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import com.mycompany.clinicaveterinariajosealberto.repository.MascotaRepository;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.MascotaServiceImpl;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author Jose Alberto
 */
public class MascotaServiceTest {
    @Mock
    private MascotaRepository mascotaRepository;

    @InjectMocks
    private MascotaServiceImpl mascotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMascota() {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);

        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota);

        Mascota result = mascotaService.addMascota(mascota);

        assertNotNull(result);
        assertEquals(1, result.getId_mascota());
        verify(mascotaRepository, times(1)).save(mascota);
    }

    @Test
    void testUpdateMascota() {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);

        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota);

        Mascota result = mascotaService.updateMascota(mascota);

        assertNotNull(result);
        assertEquals(1, result.getId_mascota());
        verify(mascotaRepository, times(1)).save(mascota);
    }

    @Test
    void testDeleteMascota() {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);
        mascota.setInactive(false);

        when(mascotaRepository.save(any(Mascota.class))).thenReturn(mascota);

        Mascota result = mascotaService.deleteMascota(mascota);

        assertNotNull(result);
        assertEquals(true, result.isInactive());
        verify(mascotaRepository, times(1)).save(mascota);
    }

    @Test
    void testListById() {
        Mascota mascota = new Mascota();
        mascota.setId_mascota(1);

        when(mascotaRepository.findById(1)).thenReturn(Optional.of(mascota));

        Mascota result = mascotaService.listById(1);

        assertNotNull(result);
        assertEquals(1, result.getId_mascota());
        verify(mascotaRepository, times(1)).findById(1);
    }
}
