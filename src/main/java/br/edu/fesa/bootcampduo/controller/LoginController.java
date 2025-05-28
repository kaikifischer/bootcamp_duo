/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.controller;

/**
 *
 * @author Kaiki
 */

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam; // Para capturar parâmetros de erro/logout

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(Model model, 
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout) {
        
        if (error != null) {
            model.addAttribute("errorMessage", "E-mail ou senha inválidos.");
        }
        if (logout != null) {
            model.addAttribute("logoutMessage", "Você foi desconectado com sucesso.");
        }
        return "login/login"; // Retorna a view login/login.html
    }
}