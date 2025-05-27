/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.controller;


import br.edu.fesa.bootcampduo.model.UsuarioModel;
import br.edu.fesa.bootcampduo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


/**
 *
 * @author Kaiki
 */
//@RestController
//@RequestMapping("auth")
public class AuthenticationController {
//    @Autowired
//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private UsuarioRepository repository;
//    @Autowired
//    //private TokenService tokenService;
    
//    @PostMapping("/login")
//    public ResponseEntity login(@RequestBody @Valid AuthenticationDTOModel data) {
    //try {
//        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(), data.password());
//        var auth = this.authenticationManager.authenticate(usernamePassword);

        //UsuarioModel user = (UsuarioModel) auth.getPrincipal();
        //String token = tokenService.generateToken(user);

//        return ResponseEntity.ok().build();
        //return ResponseEntity.ok(new LoginResponseDTO(token, user.getLogin(), user.getRole().name()));
    //} catch (AuthenticationException e) {
        //return ResponseEntity.status(401).body("Login ou senha inválidos");
    //}
//}
    
//     @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Valid RegisterDTOModel data){
//        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();
        
//        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
//        UsuarioModel newUser = new UsuarioModel(data.login(), encryptedPassword, data.email(), data.role());
          
//        this.repository.save(newUser);
        
//        return ResponseEntity.ok().build();
//    }
}
