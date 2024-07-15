package com.mycompany.clinicaveterinariajosealberto.model;

import java.io.Serializable;
import java.sql.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Jose Alberto
 * @Version 1.0
 */

@Entity
@Table(name = "ingreso")
@Getter
@Setter
public class Ingreso implements Serializable {

    
    /**
    * Enum que determina las diferentes opciones del estado de un ingreso.
    */
    
    public enum Estado {
        ALTA,
        ANULADO,
        HOSPITALIZACION,
        FINALIZADO
    }
    /**
    * Id del ingreso.
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_ingreso")
    private Integer id_ingreso;
    @Column(name = "fecha_alta")
    @Basic(optional = false)
    /**
    * Fecha de alta del ingreso.
    */
    private Date fecha_alta;
    @Column(name = "fecha_finalizacion")
    /**
    * Fecha de finalización del ingreso.
    */
    private Date fecha_finalizacion;
    @Column(name = "estado")
    @Enumerated(EnumType.STRING)
    /**
    * Estado del ingreso.
    */
    private Estado estado;
    @JoinColumn(name = "id_mascota", referencedColumnName = "id_mascota")
    @ManyToOne
    /**
    * Mascota que realiza un ingreso.
    */
    private Mascota mascota;
   
    /**
    * DNI de la persona que realiza el ingreso de la mascota.
    */
    @Column(name = "dni_persona")
    private String dniPersona;

    public Ingreso() {
    }

    public Ingreso(Integer id_ingreso, Date fecha_alta, Date fecha_finalizacion, Estado estado, Mascota mascota, String dniPersona) {
        this.id_ingreso = id_ingreso;
        this.fecha_alta = fecha_alta;
        this.fecha_finalizacion = fecha_finalizacion;
        this.estado = estado;
        this.mascota = mascota;
        this.dniPersona = dniPersona;
    }

    
    public Ingreso(Date fecha_alta, String dniPersona, Mascota mascota) {
        this.fecha_alta = fecha_alta;
        this.dniPersona = dniPersona;
        this.mascota = mascota;
        this.estado = estado.ALTA;

    }

}
