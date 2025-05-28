    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.controller;

/**
 *
 * @author Kaiki
 */

import br.edu.fesa.bootcampduo.model.FuncionarioModel;
import br.edu.fesa.bootcampduo.repository.FuncionarioRepository;
// import br.edu.fesa.bootcampduo.service.CookieService; // Não mais necessário para nome de usuário
// import jakarta.servlet.http.HttpServletRequest; // Não mais necessário para nome de usuário
import jakarta.validation.Valid;
// import java.io.UnsupportedEncodingException; // Não mais necessário
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Adicionado para consistência se necessário
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Usar PostMapping
// import org.springframework.web.bind.annotation.RequestMapping; // Pode ser removido
// import org.springframework.web.bind.annotation.RequestMethod; // Pode ser removido
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FuncionarioController {

    @Autowired
    private FuncionarioRepository fr;

    // Opcional: Se você for salvar senhas para FuncionárioModel também, injete.
    // @Autowired
    // private PasswordEncoder passwordEncoder;

    @GetMapping("/cadastrarFuncionario")
    public String cadastrarFuncionarioForm(Model model, FuncionarioModel funcionarioModel) { // Adicionado FuncionarioModel para data-binding
        // O nome do usuário logado será exibido pelo painel.
        // model.addAttribute("nome", CookieService.getCookie(request, "nomeUsuario")); // REMOVIDO
        model.addAttribute("funcionario", funcionarioModel); // Garante que o formulário tenha o objeto
        return "funcionarios/cadastrarFuncionario";
    }

    @PostMapping("/cadastrarFuncionario") // Usar @PostMapping
    public String cadastrarFuncionarioSubmit(@Valid FuncionarioModel funcionario, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios.");
            // Adicionar o objeto 'funcionario' de volta para repopular o formulário em caso de erro
            attributes.addFlashAttribute("funcionarioModel", funcionario);
            return "redirect:/cadastrarFuncionario";
        }

        // Se FuncionarioModel também tiver senha e precisar ser codificada:
        // if (funcionario.getSenha() != null && !funcionario.getSenha().isEmpty()) {
        // funcionario.setSenha(passwordEncoder.encode(funcionario.getSenha()));
        // }

        fr.save(funcionario);
        attributes.addFlashAttribute("mensagemSucesso", "Funcionário cadastrado com sucesso!");
        // return "redirect:/cadastrarFuncionario"; // Redireciona para o form novamente
        return "redirect:/listarFuncionarios"; // Ou para a lista após o cadastro
    }

    @GetMapping("/listarFuncionarios")
    public String listarFuncionarios(Model model) {
        List<FuncionarioModel> funcionarios = fr.lista();
        // model.addAttribute("nome", CookieService.getCookie(request, "nomeUsuario")); // REMOVIDO
        model.addAttribute("funcionarios", funcionarios);
        return "funcionarios/listarFuncionarios";
    }
}