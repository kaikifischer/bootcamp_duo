/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.model;


/**
 *
 * @author Kaiki
 */

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "funcionario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FuncionarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Login é obrigatório")
    @Column(nullable = false, unique = true)
    private String login;  // novo campo para login

    @NotBlank(message = "Senha é obrigatória")
    @Column(nullable = false)
    private String senha;  // novo campo para senha

    @NotBlank(message = "Nome é obrigatório")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "CPF é obrigatório")
    @Column(nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "Data de nascimento é obrigatória")
    @Column(name = "data_de_nascimento", nullable = false)
    private LocalDate dataDeNascimento;

    @NotBlank(message = "Escolaridade é obrigatória")
    @Column(nullable = false)
    private String escolaridade;

    @NotBlank(message = "Endereço é obrigatório")
    @Column(nullable = false)
    private String endereco;
}