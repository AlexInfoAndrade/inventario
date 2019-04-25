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
public class SubLocal {
  private Long id;
  private String nome;
  private Local local;

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
   * @return the nome
   */
  public String getNome() {
    return nome;
  }

  /**
   * @param nome the nome to set
   */
  public void setNome(String nome) throws Exception {
    if (nome.length() == 0) {
      throw new Exception("Nome do Sub-Local não preenchido.");
    }
    
    this.nome = nome;
  }

  /**
   * @return the local
   */
  public Local getLocal() {
    return local;
  }

  /**
   * @param local the local to set
   */
  public void setLocal(Local local) {
    this.local = local;
  }
  
}
