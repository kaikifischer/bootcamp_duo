package br.edu.fesa.bootcampduo.controller;

import br.edu.fesa.bootcampduo.Enum.UsuarioRole; // Importar o Enum
import br.edu.fesa.bootcampduo.model.UsuarioModel;
import br.edu.fesa.bootcampduo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder; // Importar PasswordEncoder
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping; // Usar PostMapping
// import org.springframework.web.bind.annotation.RequestMapping; // Não mais estritamente necessário se usar Get/PostMapping
// import org.springframework.web.bind.annotation.RequestMethod; // Não mais estritamente necessário
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;

    @Autowired // Injetar o PasswordEncoder
    private PasswordEncoder passwordEncoder;

    @GetMapping("/cadastrarUsuario")
    public String formCadastro(UsuarioModel usuarioModel) { // Adicionar UsuarioModel para data-binding no GET também
        return "login/cadastro";
    }

    @PostMapping("/cadastrarUsuario") // Simplificado para @PostMapping
    public String cadastrarUsuario(@Valid UsuarioModel usuario, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios.");
            // Adicionar o objeto 'usuario' de volta para repopular o formulário em caso de erro
            attributes.addFlashAttribute("usuarioModel", usuario);
            return "redirect:/cadastrarUsuario";
        }

        // Verificar se o e-mail já existe (opcional, mas recomendado)
        if (ur.findByEmail(usuario.getEmail()) != null) {
            attributes.addFlashAttribute("mensagem", "Este e-mail já está cadastrado.");
            attributes.addFlashAttribute("usuarioModel", usuario);
            return "redirect:/cadastrarUsuario";
        }

        // Codificar a senha antes de salvar
        String senhaCodificada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);

        // Definir uma role para novos usuários
        UsuarioRole role = usuario.getRole();
        usuario.setRole(role);

        ur.save(usuario);
        attributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso!");
        return "redirect:/login"; // Redirecionar para a página de login após o cadastro
    }
}