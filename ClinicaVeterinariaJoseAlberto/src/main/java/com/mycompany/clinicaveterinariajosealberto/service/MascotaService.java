
package com.mycompany.clinicaveterinariajosealberto.service;

import com.mycompany.clinicaveterinariajosealberto.dto.DtoMascotaRegistro;
import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import java.util.Optional;

public interface MascotaService {
    
    Mascota addMascota(Mascota mascota);
    Mascota updateMascota(Mascota mascota);
    Mascota deleteMascota(Mascota mascota);
    Mascota listById(int id);
    Mascota transformDtoToMascota(DtoMascotaRegistro mascotadto);
    
}
