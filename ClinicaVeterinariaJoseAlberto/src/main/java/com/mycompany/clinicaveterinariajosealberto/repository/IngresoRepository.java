
package com.mycompany.clinicaveterinariajosealberto.repository;

import com.mycompany.clinicaveterinariajosealberto.model.Ingreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Jose Alberto
 */
@Repository
public interface  IngresoRepository extends JpaRepository <Ingreso, Integer>{
    
}
