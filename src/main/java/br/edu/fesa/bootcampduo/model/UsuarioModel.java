package br.edu.fesa.bootcampduo.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author Kaiki
 */
import br.edu.fesa.bootcampduo.Enum.UsuarioRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern; // Importação para @Pattern
import jakarta.validation.constraints.Size;    // Importação para @Size (opcional, pode ser parte da regex)
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
@Table(name = "users") // Renomeei para "users" que é um nome comum para tabelas de usuário com Spring Security
public class UsuarioModel implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email; // Será usado como username

    @Column(nullable = false)
    // @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.") // Verificação básica de tamanho (a regex também cobre isso)
    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?~]).{8,}$",
        message = "A senha deve ter no mínimo 8 caracteres, incluindo uma letra maiúscula, uma minúscula, um número e um caractere especial."
    )
    private String senha;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false) // Nome da coluna explícito em maiúsculo
    private UsuarioRole role;

    // Construtor padrão (necessário para JPA)
    public UsuarioModel() {
    }

    // Construtor para facilitar a criação
    public UsuarioModel(String nome, String email, String senha, UsuarioRole role) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.role = role;
    }

    // Getters e Setters
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UsuarioRole getRole() {
        return role;
    }

    public void setRole(UsuarioRole role) {
        this.role = role;
    }

    // Implementação dos métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // O Spring Security espera que as roles comecem com "ROLE_"
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.email; // Usando email como username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Pode adicionar lógica se necessário
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Pode adicionar lógica se necessário
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Pode adicionar lógica se necessário
    }

    @Override
    public boolean isEnabled() {
        return true; // Pode adicionar lógica se necessário
    }
}