/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.controller;

/**
 *
 * @author Kaiki
 */
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class CustomErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("jakarta.servlet.error.status_code");
        // Para Spring Boot 2.x seria: javax.servlet.error.status_code

        String errorMsg = "";
        Integer statusCode = null;

        if (status != null) {
            statusCode = Integer.valueOf(status.toString());
            logger.info("Erro HTTP {} interceptado pelo CustomErrorController.", statusCode);

            if (statusCode == 403) {
                return "error/acessonegado"; // Seu template para 403 (acesso negado)
            } else {
                return "error/error_geral"; // Ou uma página de erro genérica
            }
            // Adicione mais 'else if' para outros códigos de status, se necessário
        }

        // Fallback para erros não especificados ou se o status_code não estiver presente
        model.addAttribute("errorCode", statusCode != null ? statusCode.toString() : "Desconhecido");
        model.addAttribute("errorMessage", "Ocorreu um erro inesperado.");
        logger.warn("Erro não mapeado especificamente ou status code ausente. Status: {}", status);
        return "error/error_geral"; // Um template genérico error/error_geral.html
    }

}