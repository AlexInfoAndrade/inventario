/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.model;

/**
 *
 * @author Alex
 */
public class Categoria {
  private Long id;
  private String codigo;
  private String nome;

  /**
   * @return the id
   */
  public Long getId() {
    return id;
  }

  /**
   * @param id the id to set
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @return the codigo
   */
  public String getCodigo() {
    return codigo;
  }

  /**
   * @param codigo the codigo to set
   */
  public void setCodigo(String codigo) throws Exception {
    if (codigo.length() == 0) {
      throw new Exception("Código da categoria não preenchido.");
    }
    
    this.codigo = codigo;
  }

  /**
   * @return the nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * @param nome the nome to set
   */
  public void setNome(String nome) {
    this.nome = nome;
  }
  
}