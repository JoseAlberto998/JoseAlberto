
package com.mycompany.clinicaveterinariajosealberto.dto;

import java.sql.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class DtoIngresoRegistro {
    
    @NotNull
    private Integer id_mascota;
    @NotNull
    private Date fecha_ingreso;
    @NotNull
    @NotBlank
    private String dniPersona;
    
}
