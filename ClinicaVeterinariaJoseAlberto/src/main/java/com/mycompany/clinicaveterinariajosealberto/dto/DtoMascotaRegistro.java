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
public class DtoMascotaRegistro {

    @NotNull
    @NotBlank
    private String especie;
    @NotNull
    @NotBlank
    private String raza;
    @NotNull
    @NotBlank
    private String edad;
    @NotNull
    @NotBlank
    private String codigo_identificacion;
    @NotNull
    @NotBlank
    private String dni_responsable;

}
