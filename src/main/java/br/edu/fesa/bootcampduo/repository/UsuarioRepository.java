/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fesa.bootcampduo.model.UsuarioModel;
import org.springframework.security.core.userdetails.UserDetails;
/**
 *
 * @author Kaiki
 */
public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {
    UserDetails findByLogin(String login);
}
