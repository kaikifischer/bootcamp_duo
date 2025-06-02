package br.edu.fesa.bootcampduo.Enum;



/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author Kaiki
 */
public enum UsuarioRole {
    ADMIN("admin"),
    DEVOPS("devops"),
    INTEL("intel");
    //USER("user");
    
    
    private String role;
    
    UsuarioRole(String role){
        this.role = role;
    }
    public String getRole(){
        return role;
    }
}
