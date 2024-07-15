
package com.mycompany.clinicaveterinariajosealberto.serviceImpl;

import com.mycompany.clinicaveterinariajosealberto.model.Usuario;
import com.mycompany.clinicaveterinariajosealberto.repository.UsuarioRepository;
import com.mycompany.clinicaveterinariajosealberto.service.UsuarioService;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author Jose Alberto
 */
@Service
public class UsuarioServiceImpl  implements UsuarioService{
     @Autowired
    UsuarioRepository usuariorepository;
    
    
     @Autowired
	private PasswordEncoder bcryptEncoder;
    
    @Override
    public Usuario crearUsuario(Usuario usuario) {
        
        usuario.setContraseña(bcryptEncoder.encode(usuario.getContraseña()));

        return usuariorepository.save(usuario);
    }

    @Override
    public Usuario updateUsuario(Usuario usuario) {
        return usuariorepository.save(usuario);
    }

    @Override
    public void eliminarUsuario(int id) {
        usuariorepository.deleteById(id);
    }

    @Override
    public ArrayList<Usuario> usuariosAll() {
        return (ArrayList<Usuario>) usuariorepository.findAll();
    }

    @Override
    public Usuario usuarioById(int id) {
        Optional<Usuario> op = usuariorepository.findById(id);
        return op.isPresent() ? op.get() : new Usuario();
    }

    @Override
    public Usuario usuarioByName(String username) {
        return usuariorepository.findBynombre(username);
    }
    
}
