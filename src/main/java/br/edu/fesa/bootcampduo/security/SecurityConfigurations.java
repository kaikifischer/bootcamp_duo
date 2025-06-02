/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.security;

/**
 *
 * @author Kaiki
 */

import br.edu.fesa.bootcamp.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private AuthenticationSuccessHandler roleBasedAuthenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                        .disable()
                )
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                AntPathRequestMatcher.antMatcher("/h2-console/**"),
                                AntPathRequestMatcher.antMatcher("/css/**"),
                                AntPathRequestMatcher.antMatcher("/js/**"),
                                AntPathRequestMatcher.antMatcher("/images/**"),
                                AntPathRequestMatcher.antMatcher("/assets/**"),
                                AntPathRequestMatcher.antMatcher("/libs/**"),
                                AntPathRequestMatcher.antMatcher("/static/**"),
                                AntPathRequestMatcher.antMatcher("/bootstrap/**"),
                                AntPathRequestMatcher.antMatcher("/login"),
                                AntPathRequestMatcher.antMatcher("/logar"),
                                AntPathRequestMatcher.antMatcher("/cadastrarUsuario"),
                                AntPathRequestMatcher.antMatcher("/bootcamp-duo/**"),
                                AntPathRequestMatcher.antMatcher("/footer.html"),
                                AntPathRequestMatcher.antMatcher("/header.html")
                        ).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/cadastrarUsuario")).permitAll()
                        
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/")).hasRole("ADMIN") 
                        // .requestMatchers(AntPathRequestMatcher.antMatcher("/user_page/**")).hasRole("USER") // REGRA REMOVIDA
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/devops/**")).hasRole("DEVOPS")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/intel/**")).hasRole("INTEL") 
                        
                        // Ajuste /configuracoes se USER não deveria ter acesso
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/configuracoes/**")).hasAnyRole("ADMIN", "DEVOPS", "INTEL") 
                        // Ajuste /controlePessoa se USER não deveria ter acesso (parece ser ADMIN/USER)
                        // Se USER não existe mais, talvez /controlePessoa seja só ADMIN ou precise de nova lógica.
                        // Por agora, vou manter como estava, mas você deve revisar:
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/controlePessoa/**")).hasRole("ADMIN") // Se apenas ADMIN acessa
                        // .requestMatchers(AntPathRequestMatcher.antMatcher("/controlePessoa/**")).hasAnyRole("ADMIN", "DEVOPS", "INTEL") // Se outras roles acessam
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/cadastrarFuncionario/**")).hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/listarFuncionarios/**")).hasRole("ADMIN") // Exemplo: se apenas ADMIN pode listar
                        // .requestMatchers(AntPathRequestMatcher.antMatcher("/listarFuncionarios/**")).hasAnyRole("ADMIN", "DEVOPS", "INTEL") // Se outras roles também podem
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/logar")
                        .usernameParameter("email")
                        .passwordParameter("senha")
                        .successHandler(roleBasedAuthenticationSuccessHandler)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/sair")
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}