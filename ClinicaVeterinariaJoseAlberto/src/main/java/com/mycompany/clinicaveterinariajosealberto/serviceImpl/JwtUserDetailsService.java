package com.mycompany.clinicaveterinariajosealberto.serviceImpl;


import com.mycompany.clinicaveterinariajosealberto.model.Usuario;
import com.mycompany.clinicaveterinariajosealberto.repository.UsuarioRepository;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    @Autowired
    private  UsuarioRepository usuarioRepository;

   
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario user = usuarioRepository.findBynombre(username);
        if (user != null) {
            String encodedPassword = user.getContraseña();
            return new org.springframework.security.core.userdetails.User(user.getNombre(), encodedPassword, new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("Usuario no encontrado: " + username);
        }
    }
}