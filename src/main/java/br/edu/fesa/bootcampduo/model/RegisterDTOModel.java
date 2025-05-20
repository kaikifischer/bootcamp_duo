/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.model;

import br.edu.fesa.bootcampduo.Enum.UsuarioRole;

/**
 *
 * @author Kaiki
 */
public record RegisterDTOModel (String login, String password, String email, UsuarioRole role) {
    
}
