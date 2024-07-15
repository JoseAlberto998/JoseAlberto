
package com.mycompany.clinicaveterinariajosealberto.dto;

import com.sun.istack.NotNull;
import javax.validation.constraints.NotBlank;
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
public class DtoMascota {
   
    private Integer id_mascota;
    private String especie;
    private String raza;
    private String edad;
    private String codigo_identificacion;
    private String dni_responsable;

}
