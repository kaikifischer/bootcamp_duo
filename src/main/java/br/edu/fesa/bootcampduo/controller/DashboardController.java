package br.edu.fesa.bootcampduo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class DashboardController {

    @GetMapping("/admin")
    public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("dados", List.of(List.of(10000, 20000, 15000, 30000, 25000, 40000)));
        return "admin";
    }

    @GetMapping("/devops")
    public String devopsPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        return "devops";
    }

    @GetMapping("/intel")
    public String intelPage(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        return "intel";
    }

    @GetMapping("/configuracoes")
    public String configuracoes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        return "usuario/configuracoesUsuario";
    }

    @GetMapping("/controlePessoa")
    public String controlePessoa(Model model) {
        return "controlePessoa";
    }
}