package br.edu.fesa.bootcampduo.controller;

import br.edu.fesa.bootcampduo.Enum.UsuarioRole;
import br.edu.fesa.bootcampduo.model.UsuarioModel;
import br.edu.fesa.bootcampduo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class RoleController {

    @Autowired
    private UsuarioRepository usuarioRepository; // UsuarioRepository 주입

    @GetMapping("/admin")
    public String adminDashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Contar usuários por role
        List<UsuarioModel> todosUsuarios = usuarioRepository.findAll();
        long devopsCount = todosUsuarios.stream()
                                        .filter(u -> u.getRole() == UsuarioRole.DEVOPS)
                                        .count();
        long intelCount = todosUsuarios.stream()
                                       .filter(u -> u.getRole() == UsuarioRole.INTEL)
                                       .count();

        // Adicionar contagens ao modelo para o dashboard
        model.addAttribute("devopsCount", devopsCount);
        model.addAttribute("intelCount", intelCount);
        
        return "admin"; // Renderiza admin.html
    }

    @GetMapping("/devops")
    public String devopsPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Lógica específica para a página DevOps, se houver
        return "devops"; // Renderiza devops.html
    }

    @GetMapping("/intel")
    public String intelPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // Lógica específica para a página Intel, se houver
        return "intel"; // Renderiza intel.html
    }
}