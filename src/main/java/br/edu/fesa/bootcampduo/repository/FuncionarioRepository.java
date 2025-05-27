/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.edu.fesa.bootcampduo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.edu.fesa.bootcampduo.model.FuncionarioModel;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
/**
 *
 * @author Kaiki
 */
public interface FuncionarioRepository extends JpaRepository<FuncionarioModel, String> {
    UserDetails findByLogin(String id);
    
    @Query("SELECT f FROM FuncionarioModel f")
    public List<FuncionarioModel> lista(); 
}
