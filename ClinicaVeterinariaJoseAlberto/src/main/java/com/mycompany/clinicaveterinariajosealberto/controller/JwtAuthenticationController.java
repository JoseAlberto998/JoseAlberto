package com.mycompany.clinicaveterinariajosealberto.controller;




import com.mycompany.clinicaveterinariajosealberto.config.JwtTokenUtil;
import com.mycompany.clinicaveterinariajosealberto.model.JwtRequest;
import com.mycompany.clinicaveterinariajosealberto.model.JwtResponse;
import com.mycompany.clinicaveterinariajosealberto.model.Usuario;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.JwtUserDetailsService;
import com.mycompany.clinicaveterinariajosealberto.serviceImpl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.http.HttpStatus;


@RestController
@RequestMapping("hospitalclinicovet-api")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired

    private UsuarioServiceImpl repo;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
        
        if(authenticationRequest.getUsername() == null || authenticationRequest.getPassword()== null)
            return new ResponseEntity<>("Campos username o password no existentes", HttpStatus.CONFLICT);
        String token;
        UserDetails userDetails;
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        } catch (DisabledException | BadCredentialsException e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.UNAUTHORIZED);
        }
       
            userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
            token = jwtTokenUtil.generateToken(userDetails);
        
        return ResponseEntity.ok(new JwtResponse(token));
    }
}
