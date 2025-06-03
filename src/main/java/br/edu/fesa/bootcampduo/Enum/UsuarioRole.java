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
    ADMIN("Administrador"),
    DEVOPS("DevOps"),
    INTEL("Intel");
    //USER("user"); // Comentado no seu original
    
    private String displayName; // Para exibição no formulário
    // private String role; // Seu campo original, se precisar manter para o método getRole()
    
    UsuarioRole(String displayName){
        this.displayName = displayName;
        // this.role = role; // Se o construtor original era (String role) e você quer manter o campo 'role'
    }

    public String getDisplayName(){ // Getter para o displayName
        return displayName;
    }
    
    // Seu método getRole() original. 
    // Se 'role' era o nome do enum em minúsculo, você pode fazer:
    public String getRole(){
        return this.name().toLowerCase(); 
        // Ou return this.role; se você inicializou o campo 'role' no construtor.
    }
}
