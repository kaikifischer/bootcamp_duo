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
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy; // Será removido para stateful
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private AuthorizationService authorizationService; // Nosso UserDetailsService

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")) // Permite CSRF para o H2 console
                        .disable() // Vamos manter desabilitado por enquanto para simplificar, mas ATENÇÃO: Habilitar em produção!
                )
                // .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // REMOVER PARA LOGIN STATEFUL
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                AntPathRequestMatcher.antMatcher("/h2-console/**"),
                                AntPathRequestMatcher.antMatcher("/css/**"),
                                AntPathRequestMatcher.antMatcher("/js/**"),
                                AntPathRequestMatcher.antMatcher("/images/**"),
                                AntPathRequestMatcher.antMatcher("/assets/**"),
                                AntPathRequestMatcher.antMatcher("/libs/**"),
                                AntPathRequestMatcher.antMatcher("/static/**"),
                                AntPathRequestMatcher.antMatcher("/bootstrap/**"), // Adicionado para seus arquivos bootstrap
                                AntPathRequestMatcher.antMatcher("/login"),
                                AntPathRequestMatcher.antMatcher("/logar"), // Permitir a URL de processamento de login
                                AntPathRequestMatcher.antMatcher("/cadastrarUsuario"),
                                // Rotas públicas do template "bootcamp"
                                AntPathRequestMatcher.antMatcher("/bootcamp-duo/**"),
                                AntPathRequestMatcher.antMatcher("/footer.html"),
                                AntPathRequestMatcher.antMatcher("/header.html")
                        ).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/cadastrarUsuario")).permitAll() // Permitir POST para cadastro
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/")).hasAnyRole("USER", "ADMIN") // Permitir acesso à dashboard para USER e ADMIN
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/configuracoes/**")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/controlePessoa/**")).hasAnyRole("USER", "ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/cadastrarFuncionario/**")).hasRole("ADMIN") // Somente ADMIN cadastra funcionário
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/listarFuncionarios/**")).hasAnyRole("USER", "ADMIN")
                        // .requestMatchers("/admin/**").hasRole("ADMIN") // Exemplo se você tiver rotas específicas para admin
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/logar") // Spring Security vai interceptar esta URL
                        .usernameParameter("email") // Define que o parâmetro do username no form é "email"
                        .passwordParameter("senha") // Define que o parâmetro da senha no form é "senha"
                        .defaultSuccessUrl("/", true)
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/sair") // URL para acionar o logout (pode ser GET ou POST por padrão)
                        .logoutSuccessUrl("/login?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID") // Remove o cookie de sessão do Spring
                        .permitAll()
                )
                // Necessário para o H2 Console funcionar em frames
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

    // Configuração para o AuthenticationManagerBuilder (usado para configurar o UserDetailsService e PasswordEncoder)
    // Este método é chamado pelo Spring para construir o AuthenticationManager global
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    //    auth.userDetailsService(authorizationService)
    //        .passwordEncoder(passwordEncoder());
    //}
}