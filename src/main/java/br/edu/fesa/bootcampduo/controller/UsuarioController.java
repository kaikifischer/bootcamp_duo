
package br.edu.fesa.bootcampduo.controller;

import br.edu.fesa.bootcampduo.model.UsuarioModel;
import br.edu.fesa.bootcampduo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;


@Controller
public class UsuarioController {

	
	@Autowired
	private UsuarioRepository ur;
	
	//Cadastrausuario
    	
		@GetMapping("/cadastrarUsuario")
		@RequestMapping(value = "/cadastrarUsuario",method = RequestMethod.GET)
		public String cadastro() {
			return "login/cadastro";
		}

		@RequestMapping(value = "/cadastrarUsuario",method = RequestMethod.POST)
		public String form(@Valid UsuarioModel usuario, BindingResult result, RedirectAttributes attributes) {
			
			if(result.hasErrors()) {
				attributes.addFlashAttribute("Mensagem","Verifique os campos:...");
				return "redirect:/cadastrarUsuario";
			}
			
			ur.save(usuario);
			attributes.addFlashAttribute("Mensagem","Vaga Cadastrada");
			return "redirect:/login";
		}
		
		
	
}
