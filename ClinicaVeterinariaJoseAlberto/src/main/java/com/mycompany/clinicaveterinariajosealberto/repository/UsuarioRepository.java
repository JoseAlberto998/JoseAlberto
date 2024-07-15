
package com.mycompany.clinicaveterinariajosealberto.repository;

import com.mycompany.clinicaveterinariajosealberto.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Jose Alberto
 */
public interface UsuarioRepository extends JpaRepository <Usuario, Integer>{
    Usuario findBynombre(String nombre);
}
