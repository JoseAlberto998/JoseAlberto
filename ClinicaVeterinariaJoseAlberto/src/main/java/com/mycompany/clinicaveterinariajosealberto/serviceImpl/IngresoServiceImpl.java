
package com.mycompany.clinicaveterinariajosealberto.serviceImpl;

import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngreso;
import com.mycompany.clinicaveterinariajosealberto.dto.DtoMascota;
import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import com.mycompany.clinicaveterinariajosealberto.repository.IngresoRepository;
import com.mycompany.clinicaveterinariajosealberto.service.IngresoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jose Alberto
 */
@Service
public class IngresoServiceImpl implements IngresoService{

    @Autowired
    IngresoRepository ingresorepository;
    
    @Override
    public Ingreso addIngreso(Ingreso ingreso) {
        return ingresorepository.save(ingreso);
    }

    @Override
    public Ingreso updateIngreso(Ingreso ingreso) {
        return ingresorepository.save(ingreso);
    }

    @Override
    public Ingreso deleteIngreso(Ingreso ingreso) {
        ingreso.setEstado(Ingreso.Estado.ANULADO);
        return ingresorepository.save(ingreso);
    }

    @Override
    public List<Ingreso> listAllIngresos() {
        return ingresorepository.findAll();
    }

    @Override
    public Ingreso listById(int id) {
        return ingresorepository.findById(id).orElse(null);
    }

    @Override
    public List<DtoIngreso> trasformToIngresoDTO(List<Ingreso> ingresos) {
        List<DtoIngreso> ingresosDto = new ArrayList<>();
        for(Ingreso ingreso : ingresos)
        {
            DtoIngreso ingresoDto = new DtoIngreso();
            ingresoDto.setFecha_alta(ingreso.getFecha_alta());
            ingresoDto.setFecha_finalizacion(ingreso.getFecha_finalizacion());
            DtoMascota mascota = new DtoMascota();
            mascota.setCodigo_identificacion(ingreso.getMascota().getCodigo_identificacion());
            mascota.setDni_responsable(ingreso.getMascota().getDni_responsable());
            mascota.setEdad(ingreso.getMascota().getEdad());
            mascota.setRaza(ingreso.getMascota().getRaza());
            mascota.setEspecie(ingreso.getMascota().getEspecie());
            mascota.setId_mascota(ingreso.getMascota().getId_mascota());
            ingresoDto.setMascota(mascota);
            ingresoDto.setDniPersona(ingreso.getDniPersona());
            ingresoDto.setEstado(ingreso.getEstado());
            
            ingresosDto.add(ingresoDto);
        }
        return ingresosDto;
    }
    
}
