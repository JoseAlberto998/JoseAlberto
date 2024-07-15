
package com.mycompany.clinicaveterinariajosealberto.dto;

import com.mycompany.clinicaveterinariajosealberto.model.Ingreso.Estado;
import com.sun.istack.NotNull;
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
public class DtoIngresoUpdate {
    
    private Estado estado;
    private Date fecha_finalizacion;
}
