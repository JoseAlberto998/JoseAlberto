
package com.mycompany.clinicaveterinariajosealberto.dto;

import com.mycompany.clinicaveterinariajosealberto.model.Ingreso.Estado;
import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jose Alberto
 * @Version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoIngreso {

    private Date fecha_alta;
    private Date fecha_finalizacion;
    private Estado estado;
    private DtoMascota mascota;
    private String DniPersona;
    
}
