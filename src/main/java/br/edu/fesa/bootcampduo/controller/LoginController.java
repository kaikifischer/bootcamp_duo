/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.controller;

import br.edu.fesa.bootcampduo.model.UsuarioModel;
import br.edu.fesa.bootcampduo.repository.UsuarioRepository;
import br.edu.fesa.bootcampduo.service.CookieService;
import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 *
 * @author Kaiki
 */
@Controller
public class LoginController {
	
	@Autowired
	private UsuarioRepository ur;
	

	@GetMapping("/login")
	public String cadastro() {
		return "login/login";
	}
	
	
	@PostMapping("/logar")
	public String form(UsuarioModel usuario, Model model, HttpServletResponse response) throws UnsupportedEncodingException {
		UsuarioModel user = (UsuarioModel) this.ur.login(usuario.getEmail(), usuario.getSenha());
		if(user != null) {
			CookieService.setCookie(response, "usuarioId", String.valueOf(user.getId()),10000);
			CookieService.setCookie(response, "nomeUsuario", String.valueOf(user.getNome()),10000);

			return "redirect:/";
		}
		
		model.addAttribute("erro","Usuario ou senha invalidos");
		return "login/login";
	}
	
	@GetMapping("/sair")
	public String sair(HttpServletResponse response) throws UnsupportedEncodingException {
		CookieService.setCookie(response, "usuarioId", "",0);
		return "login/login";
	}
	
		
		
	
}
