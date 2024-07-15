
package com.mycompany.clinicaveterinariajosealberto.repository;

import com.mycompany.clinicaveterinariajosealberto.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MascotaRepository extends JpaRepository <Mascota, Integer>{
    
}
