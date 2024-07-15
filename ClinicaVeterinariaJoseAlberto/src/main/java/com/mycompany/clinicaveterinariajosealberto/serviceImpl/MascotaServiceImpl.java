
package com.mycompany.clinicaveterinariajosealberto.serviceImpl;

import com.mycompany.clinicaveterinariajosealberto.dto.DtoMascotaRegistro;
import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import com.mycompany.clinicaveterinariajosealberto.repository.MascotaRepository;
import com.mycompany.clinicaveterinariajosealberto.service.MascotaService;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jose Alberto
 */
@Service
public class MascotaServiceImpl implements MascotaService{

    @Autowired
    MascotaRepository mascotaRepository;
    
    @Override
    public Mascota addMascota(Mascota mascota) {
       return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota updateMascota(Mascota mascota) {
        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota deleteMascota(Mascota mascota) {
        mascota.setInactive(true);
        return mascotaRepository.save(mascota);
    }

    @Override
    public Mascota listById(int id) {
        return mascotaRepository.findById(id).orElse(null);
    }

    @Override
    public Mascota transformDtoToMascota(DtoMascotaRegistro mascotadto) {
        Mascota mascota = new Mascota();
        mascota.setEspecie(mascotadto.getEspecie());
        mascota.setRaza(mascotadto.getRaza());
        mascota.setEdad(mascotadto.getEdad());
        mascota.setDni_responsable(mascotadto.getDni_responsable());
        mascota.setCodigo_identificacion(mascotadto.getCodigo_identificacion());
        return mascota;
    }
    
}
