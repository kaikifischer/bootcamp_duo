
package br.edu.fesa.bootcampduo.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal; // Para injetar o usuário logado
import org.springframework.security.core.userdetails.UserDetails; // Interface UserDetails
import br.edu.fesa.bootcampduo.model.UsuarioModel; // Sua classe de usuário

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List; // Para o exemplo de dados do gráfico

@Controller
public class DashboardController {

    @GetMapping("/")
    public String index(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // O nome do usuário para o painel (th:replace="painel :: navbar")
        // será pego automaticamente pelo Thymeleaf Security Extras: sec:authentication="principal.nome"
        // Não é mais necessário adicionar "nome" ao model aqui se for apenas para o painel.

        // Se você precisar do nome do usuário ou outros detalhes no controller:
        if (userDetails instanceof UsuarioModel) {
            UsuarioModel usuarioAutenticado = (UsuarioModel) userDetails;
            // model.addAttribute("nomeDoUsuarioNoController", usuarioAutenticado.getNome());
            // Você pode usar usuarioAutenticado.getEmail(), usuarioAutenticado.getRole(), etc.
        }
        
        // Lógica para os dados do gráfico:
        // Esta parte pode precisar ser ajustada se os dados dependiam do usuário
        // ou de alguma outra lógica que foi removida.
        // Por enquanto, estou mantendo um exemplo estático.
        model.addAttribute("dados", List.of(List.of(10000, 20000, 15000, 30000, 25000, 40000)));
        return "index";
    }

    @GetMapping("/configuracoes")
    public String configuracoes(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        // O painel já exibe o nome. Se precisar de dados específicos do usuário aqui:
        if (userDetails instanceof UsuarioModel) {
            // UsuarioModel usuario = (UsuarioModel) userDetails;
            // model.addAttribute("emailUsuario", usuario.getEmail());
        }
        return "usuario/configuracoesUsuario";
    }

    @GetMapping("/controlePessoa")
    public String controlePessoa(Model model) {
        // O nome do usuário já é gerenciado pelo painel.
        // Não é necessário adicionar explicitamente aqui.
        return "controlePessoa";
    }
}