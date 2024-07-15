
package com.mycompany.clinicaveterinariajosealberto.service;

import com.mycompany.clinicaveterinariajosealberto.model.Usuario;
import java.util.ArrayList;

/**
 *
 * @author Jose alberto
 */
public interface UsuarioService {
    Usuario crearUsuario(Usuario usuario);
    Usuario updateUsuario(Usuario usuario); 
    void eliminarUsuario(int id);
    ArrayList<Usuario> usuariosAll(); 
    Usuario usuarioById(int id);
    Usuario usuarioByName(String username);
}
