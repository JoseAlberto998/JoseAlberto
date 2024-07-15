package com.mycompany.clinicaveterinariajosealberto.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jose Alberto
 * @Version 1.0
 */

@Entity
@Table(name = "mascota")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Mascota implements Serializable {

    /**
    * Id de la mascota, se genera automaticamente.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_mascota")
    private Integer id_mascota;
    /**
    * Especie de la mascota.
    */
    @Column(name = "Especie")
    @Basic(optional = false)
    private String especie;
    /**
    * Raza de la mascota.
    */
    @Column(name = "raza")
    @Basic(optional = false)
    private String raza;
    /**
    * Edad de la mascota.
    */
    @Column(name = "edad")
    @Basic(optional = false)
    private String edad;
    /**
    * Código de identificación de la mascota.
    */
    @Column(name = "codigo_identificacion")
    @Basic(optional = false)
    private String codigo_identificacion;
    /**
    * DNI del responsable de la mascota.
    */
    @Column(name = "dni_responsable")
    @Basic(optional = false)
    private String dni_responsable;
    /**
    * Boolean inactive para desactivar la mascota.
    */
    @Column(name = "inactive")
    @Basic(optional = true)
    private boolean inactive = false;
    /**
    * Lista de ingresos de la mascota.
    */
    @OneToMany(mappedBy = "mascota")
    @JsonIgnore
    private List<Ingreso> ingresosList;

    /**
    * Getter que devuelve si esta inactiva la mascota.
    * @return boolean
    */
    public boolean isInactive() {
        return inactive;
    }
}
