package br.edu.fesa.bootcampduo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.expression.SecurityExpressionHandler; // Importante
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.FilterInvocation; // Importante
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfigurations {

    @Autowired
    private AuthenticationSuccessHandler roleBasedAuthenticationSuccessHandler;

    /**
     * Define a hierarquia de roles.
     * ADMIN herda DEVOPS e INTEL.
     */
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        // A convenção é usar o prefixo ROLE_ na definição da hierarquia
        String hierarchy = "ROLE_ADMIN > ROLE_DEVOPS \n ROLE_ADMIN > ROLE_INTEL";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    /**
     * Define um SecurityExpressionHandler customizado que usa a hierarquia de roles.
     * O Spring Security irá detectar e usar este bean para avaliar expressões de segurança
     * como hasRole() nas configurações do HttpSecurity.
     * Nomeamos como "customWebSecurityExpressionHandler" para evitar conflito com o bean padrão
     * "webSecurityExpressionHandler" (a menos que a sobrescrita de beans esteja habilitada).
     */
    @Bean
    public SecurityExpressionHandler<FilterInvocation> customWebSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy); // O roleHierarchy bean será injetado
        return expressionHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        // O Spring Security automaticamente usará o bean 'customWebSecurityExpressionHandler'
        // que definimos acima, pois ele procura por um bean do tipo SecurityExpressionHandler<FilterInvocation>.
        // Portanto, não precisamos configurar explicitamente o expressionHandler no HttpSecurity aqui.

        httpSecurity
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                        .disable()
                )
                .authorizeHttpRequests(authorize -> authorize
                        // Nenhuma chamada explícita a .expressionHandler() aqui
                        .requestMatchers(
                                AntPathRequestMatcher.antMatcher("/h2-console/**"),
                                AntPathRequestMatcher.antMatcher("/css/**"),
                                AntPathRequestMatcher.antMatcher("/js/**"),
                                AntPathRequestMatcher.antMatcher("/images/**"),
                                AntPathRequestMatcher.antMatcher("/assets/**"),
                                AntPathRequestMatcher.antMatcher("/libs/**"),
                                AntPathRequestMatcher.antMatcher("/static/**"),
                                AntPathRequestMatcher.antMatcher("/bootstrap/**"),
                                AntPathRequestMatcher.antMatcher("/"),
                                AntPathRequestMatcher.antMatcher("/login"),
                                AntPathRequestMatcher.antMatcher("/logar"),
                                AntPathRequestMatcher.antMatcher("/cadastrarUsuario"),
                                AntPathRequestMatcher.antMatcher("/bootcamp-duo/**"),
                                AntPathRequestMatcher.antMatcher("/footer.html"),
                                AntPathRequestMatcher.antMatcher("/header.html")
                        ).permitAll()
                        // .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/cadastrarUsuario")).permitAll() // Já coberto acima

                        .requestMatchers(AntPathRequestMatcher.antMatcher("/admin/**")).hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/devops/**")).hasRole("DEVOPS")
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/intel/**")).hasRole("INTEL")

                        .requestMatchers(AntPathRequestMatcher.antMatcher("/gerenciarUsuarios/**")).hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/admin/usuarios/novo")).hasRole("ADMIN")
                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST,"/admin/usuarios/salvar")).hasRole("ADMIN")
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
                .headers(headers -> headers.frameOptions(frameOptionsConfig -> frameOptionsConfig.sameOrigin()));

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