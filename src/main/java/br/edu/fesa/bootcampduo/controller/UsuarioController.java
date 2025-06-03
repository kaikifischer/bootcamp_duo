package br.edu.fesa.bootcampduo.controller;

import br.edu.fesa.bootcampduo.Enum.UsuarioRole;
import br.edu.fesa.bootcampduo.model.UsuarioModel;
import br.edu.fesa.bootcampduo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // Importado
import org.springframework.security.core.context.SecurityContextHolder; // Importado
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // --- FLUXO DE AUTO-REGISTRO PÚBLICO ---
    @GetMapping("/cadastrarUsuario")
    public String formCadastroPublico(UsuarioModel usuarioModel, Model model) {
        List<UsuarioRole> rolesPublicasDisponiveis = Arrays.stream(UsuarioRole.values())
                .filter(role -> role == UsuarioRole.DEVOPS || role == UsuarioRole.INTEL)
                .collect(Collectors.toList());
        model.addAttribute("rolesDisponiveis", rolesPublicasDisponiveis);
        return "login/cadastro";
    }

    @PostMapping("/cadastrarUsuario")
    public String cadastrarUsuarioPublico(@Valid @ModelAttribute("usuarioModel") UsuarioModel usuario,
                                        BindingResult result,
                                        RedirectAttributes attributes,
                                        Model model) {

        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos obrigatórios.");
            List<UsuarioRole> rolesPublicasDisponiveis = Arrays.stream(UsuarioRole.values())
                .filter(role -> role == UsuarioRole.DEVOPS || role == UsuarioRole.INTEL)
                .collect(Collectors.toList());
            attributes.addFlashAttribute("usuarioModel", usuario);
            attributes.addFlashAttribute("rolesDisponiveis", rolesPublicasDisponiveis);
            return "redirect:/cadastrarUsuario";
        }

        if (ur.findByEmail(usuario.getEmail()) != null) {
            attributes.addFlashAttribute("mensagem", "Este e-mail já está cadastrado.");
            attributes.addFlashAttribute("usuarioModel", usuario);
             List<UsuarioRole> rolesPublicasDisponiveis = Arrays.stream(UsuarioRole.values())
                .filter(role -> role == UsuarioRole.DEVOPS || role == UsuarioRole.INTEL)
                .collect(Collectors.toList());
            attributes.addFlashAttribute("rolesDisponiveis", rolesPublicasDisponiveis);
            return "redirect:/cadastrarUsuario";
        }

        if (usuario.getRole() == null || usuario.getRole() == UsuarioRole.ADMIN) {
            usuario.setRole(UsuarioRole.DEVOPS);
        } else if (usuario.getRole() != UsuarioRole.DEVOPS && usuario.getRole() != UsuarioRole.INTEL) {
             usuario.setRole(UsuarioRole.DEVOPS);
        }

        String senhaCodificada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);

        ur.save(usuario);
        attributes.addFlashAttribute("mensagemSucesso", "Usuário cadastrado com sucesso! Faça o login.");
        return "redirect:/login";
    }

    // --- NOVOS Endpoints para ADMIN criar usuários ---
    @GetMapping("/admin/usuarios/novo")
    public String exibirFormularioAdminCriarUsuario(Model model) {
        model.addAttribute("usuario", new UsuarioModel());
        model.addAttribute("roles", UsuarioRole.values());
        return "usuario/admincadastrousuario";
    }

    @PostMapping("/admin/usuarios/salvar")
    public String salvarNovoUsuarioAdmin(@Valid @ModelAttribute("usuario") UsuarioModel usuario,
                                         BindingResult result,
                                         RedirectAttributes attributes,
                                         Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", UsuarioRole.values());
            return "usuario/admincadastrousuario";
        }

        if (ur.findByEmail(usuario.getEmail()) != null) {
            model.addAttribute("mensagemErro", "Este e-mail já está cadastrado.");
            model.addAttribute("roles", UsuarioRole.values());
            return "usuario/admincadastrousuario";
        }
        
        if (usuario.getRole() == null) {
             model.addAttribute("mensagemErro", "O papel do usuário é obrigatório.");
             model.addAttribute("roles", UsuarioRole.values());
             return "usuario/admincadastrousuario";
        }

        String senhaCodificada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCodificada);

        ur.save(usuario);
        attributes.addFlashAttribute("mensagemSucesso", "Novo usuário cadastrado pelo Admin com sucesso!");
        return "redirect:/gerenciarUsuarios";
    }

    // --- Funcionalidades de Gerenciamento de Usuários (APENAS PARA ADMIN) ---
    @GetMapping("/gerenciarUsuarios")
    public String listarUsuarios(Model model) {
        List<UsuarioModel> users = ur.findAll();
        model.addAttribute("users", users);
        return "usuario/gerenciarUsuarios";
    }

    @GetMapping("/gerenciarUsuarios/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") String id, Model model, RedirectAttributes attributes) {
        try {
            UUID userId = UUID.fromString(id);
            Optional<UsuarioModel> usuarioOptional = ur.findById(userId);
            if (usuarioOptional.isPresent()) {
                model.addAttribute("usuario", usuarioOptional.get());
                model.addAttribute("roles", UsuarioRole.values());
                return "usuario/editarUsuario";
            } else {
                attributes.addFlashAttribute("mensagemErro", "Usuário não encontrado.");
                return "redirect:/gerenciarUsuarios";
            }
        } catch (IllegalArgumentException e) {
            attributes.addFlashAttribute("mensagemErro", "ID de usuário inválido.");
            return "redirect:/gerenciarUsuarios";
        }
    }

    @PostMapping("/gerenciarUsuarios/atualizar")
    public String atualizarUsuario(@Valid @ModelAttribute("usuario") UsuarioModel usuario,
                                 BindingResult result,
                                 RedirectAttributes attributes,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("roles", UsuarioRole.values());
            return "usuario/editarUsuario";
        }
    
        Optional<UsuarioModel> existingUserOptional = ur.findById(usuario.getId());
        if (existingUserOptional.isEmpty()) {
            attributes.addFlashAttribute("mensagemErro", "Usuário não encontrado para atualização.");
            return "redirect:/gerenciarUsuarios";
        }
        
        UsuarioModel existingUser = existingUserOptional.get();
        existingUser.setNome(usuario.getNome());
        existingUser.setEmail(usuario.getEmail());
        existingUser.setRole(usuario.getRole());
        
        if (usuario.getSenha() != null && !usuario.getSenha().isEmpty()) {
           existingUser.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
        
        ur.save(existingUser);
        attributes.addFlashAttribute("mensagemSucesso", "Usuário atualizado com sucesso!");
        return "redirect:/gerenciarUsuarios";
    }

    @PostMapping("/gerenciarUsuarios/remover/{id}")
    public String removerUsuario(@PathVariable("id") UUID id, RedirectAttributes attributes) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); // Obtém o usuário logado
        String currentUsername = authentication.getName(); // Email do usuário logado

        Optional<UsuarioModel> userToDeleteOptional = ur.findById(id);

        if (userToDeleteOptional.isPresent()) {
            UsuarioModel userToDelete = userToDeleteOptional.get();
            // Verifica se o email do usuário a ser deletado é o mesmo do usuário logado
            if (userToDelete.getEmail().equals(currentUsername)) {
                attributes.addFlashAttribute("mensagemErro", "Você não pode remover o seu próprio usuário enquanto estiver logado.");
                return "redirect:/gerenciarUsuarios";
            }

            try {
                ur.deleteById(id);
                attributes.addFlashAttribute("mensagemSucesso", "Usuário removido com sucesso!");
            } catch (Exception e) {
                attributes.addFlashAttribute("mensagemErro", "Erro ao remover usuário: " + e.getMessage());
            }
        } else {
            attributes.addFlashAttribute("mensagemErro", "Usuário não encontrado para remoção.");
        }
        return "redirect:/gerenciarUsuarios";
    }
}