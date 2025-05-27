
package br.edu.fesa.bootcampduo.controller;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;


@Controller
public class DashboardController {


	@GetMapping("/")
	public String index(Model model,HttpServletRequest request ) throws UnsupportedEncodingException {
		model.addAttribute("nome",CookieService.getCookie(request, "nomeUsuario"));
		return "index";
	}
	
	@GetMapping("/configuracoes")
	public String configuracoes(Model model,HttpServletRequest request ) throws UnsupportedEncodingException {
		model.addAttribute("nome",CookieService.getCookie(request, "nomeUsuario"));
		return "usuario/configuracoesUsuario";
	}
	
	@GetMapping("/controlePessoa")
	public String controlePessoa(Model model,HttpServletRequest request ) throws UnsupportedEncodingException {
		model.addAttribute("nome",CookieService.getCookie(request, "nomeUsuario"));
		return "controlePessoa";
	}
	
	

	
	
	
	
}
