/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.controller;

import br.edu.fesa.bootcampduo.model.FuncionarioModel;
import br.edu.fesa.bootcampduo.repository.FuncionarioRepository;
import br.edu.fesa.bootcampduo.service.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Kaiki
 */

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioRepository fr;
	
	@GetMapping("/cadastrarFuncionario")
	public String cadastrarFuncionario(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
		model.addAttribute("nome", CookieService.getCookie(request, "nomeUsuario"));
		model.addAttribute("funcionario", new FuncionarioModel()); // Adicione esta linha para garantir que o formulário tenha um objeto Funcionario
		return "funcionarios/cadastrarFuncionario";
	}

	@RequestMapping(value = "/cadastrarFuncionario", method = RequestMethod.POST)
	public String form(@Valid FuncionarioModel funcionario, BindingResult result, RedirectAttributes attributes) {
		
		if (result.hasErrors()) {
			attributes.addFlashAttribute("Mensagem", "Verifique os campos:...");
			return "redirect:/cadastrarFuncionario";
		}
		
		fr.save(funcionario);
		attributes.addFlashAttribute("Mensagem", "Funcionario Cadastrado");
		return "redirect:/cadastrarFuncionario";
	}
	
    @GetMapping("/listarFuncionarios")
    public String listarFuncionarios(Model model, HttpServletRequest request) throws UnsupportedEncodingException {
        List<FuncionarioModel> funcionarios = fr.lista();
        model.addAttribute("nome", CookieService.getCookie(request, "nomeUsuario"));
        model.addAttribute("funcionarios", funcionarios);
        return "funcionarios/listarFuncionarios";
    }
}
