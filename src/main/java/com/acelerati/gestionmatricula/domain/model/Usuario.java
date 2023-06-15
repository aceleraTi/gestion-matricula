package com.acelerati.gestionmatricula.domain.model;



public class Usuario {
   private Long id;
   private String nombre;
   private String tipoUsuario;

   public Usuario() {
   }

   public Usuario(Long id, String nombre, String tipoUsuario) {
      this.id = id;
      this.nombre = nombre;
      this.tipoUsuario = tipoUsuario;
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getTipoUsuario() {
      return tipoUsuario;
   }

   public void setTipoUsuario(String tipoUsuario) {
      this.tipoUsuario = tipoUsuario;
   }
}
