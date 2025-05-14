package br.edu.fesa.bootcampduo.controller;
        
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bootcamp-duo")
public class BootcampduoController {
    @GetMapping("")
    public String index(){
        return "/index";
    }

    @GetMapping("/games")
    public String games(){
        return "bootcamp/games";
    }

    @GetMapping("/mobile-android")
    public String mobileAndroid() {
        return "/bootcamp/mobile-android";
    }

    @GetMapping("/mobile-ios")
    public String mobileIos() {
        return "/bootcamp/mobile-ios";
    }

    @GetMapping("/devops")
    public String devops() {
        return "/bootcamp/devops";
    }
    
    @GetMapping("/website-html")
    public String websiteHtml() {
        return "/bootcamp/website-html";
    }

    @GetMapping("/website-css")
    public String websiteCss() {
        return "/bootcamp/website-css";
    }
    
    @GetMapping("/website-javascript")
    public String websiteJavascript() {
        return "/bootcamp/website-javascript";
    }

    @GetMapping("/inteligencia-artificial")
    public String inteligenciaArtificial() {
        return "/bootcamp/inteligencia-artificial";
    }
    
    @GetMapping("/login")
    public String login() {
        return "/bootcamp/login";
    }
    
    @GetMapping("/registrar")
    public String registrar() {
        return "/bootcamp/registrar";
    }
}


