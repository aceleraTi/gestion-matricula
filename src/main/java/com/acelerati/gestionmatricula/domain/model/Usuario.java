package com.acelerati.gestionmatricula.domain.model;



public class Usuario {
   private Long usuarioId;
   private String nombre;
   private String apellido;
   private String email;
   private String codigo;
   private Long tipoUsuario;

   public Usuario() {

   }

   public Usuario(Long usuarioId, String nombre, String apellido, String email, String codigo, Long tipoUsuario) {
      this.usuarioId = usuarioId;
      this.nombre = nombre;
      this.apellido = apellido;
      this.email = email;
      this.codigo = codigo;
      this.tipoUsuario = tipoUsuario;
   }

   public Long getUsuarioId() {
      return usuarioId;
   }

   public void setUsuarioId(Long usuarioId) {
      this.usuarioId = usuarioId;
   }

   public String getNombre() {
      return nombre;
   }

   public void setNombre(String nombre) {
      this.nombre = nombre;
   }

   public String getApellido() {
      return apellido;
   }

   public void setApellido(String apellido) {
      this.apellido = apellido;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getCodigo() {
      return codigo;
   }

   public void setCodigo(String codigo) {
      this.codigo = codigo;
   }

   public Long getTipoUsuario() {
      return tipoUsuario;
   }

   public void setTipoUsuario(Long tipoUsuario) {
      this.tipoUsuario = tipoUsuario;
   }

}
