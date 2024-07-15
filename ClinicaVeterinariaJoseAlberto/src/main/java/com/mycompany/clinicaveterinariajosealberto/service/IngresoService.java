
package com.mycompany.clinicaveterinariajosealberto.service;

import com.mycompany.clinicaveterinariajosealberto.dto.DtoIngreso;
import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import java.util.List;
import java.util.Optional;

public interface IngresoService {
    
    Ingreso addIngreso(Ingreso ingreso);
    Ingreso updateIngreso(Ingreso ingreso);
    Ingreso deleteIngreso(Ingreso ingreso);
    List<Ingreso> listAllIngresos();
    Ingreso listById(int id);
    List<DtoIngreso> trasformToIngresoDTO(List<Ingreso> ingresos);
    
    
}
