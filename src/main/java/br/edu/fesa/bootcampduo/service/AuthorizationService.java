/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.service;

import br.edu.fesa.bootcampduo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author Kaiki
 */
@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository repository;
            
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //forma correta -> return repository.findByLogin(username);
        return org.springframework.security.core.userdetails.User
            .withUsername(username)
            .password("{noop}dummy") // {noop} indica senha sem codificação
            .roles("USER")
            .build();
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
