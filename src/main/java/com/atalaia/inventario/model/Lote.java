/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class Lote {
  private Long id;
  private Integer lote;
  private String operador;
  private String auditor;
  private Local local;
  private SubLocal subLocal;
  private Status status;
  private List<Categoria> categorias;
  
  public Lote () {
    categorias = new ArrayList<>();
  }

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
   * @return the lote
   */
  public Integer getLote() {
    return lote;
  }

  /**
   * @param lote the lote to set
   */
  public void setLote(Integer lote) {
    this.lote = lote;
  }

  /**
   * @return the operador
   */
  public String getOperador() {
    return operador;
  }

  /**
   * @param operador the operador to set
   */
  public void setOperador(String operador) {
    this.operador = operador;
  }

  /**
   * @return the auditor
   */
  public String getAuditor() {
    return auditor;
  }

  /**
   * @param auditor the auditor to set
   */
  public void setAuditor(String auditor) {
    this.auditor = auditor;
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

  /**
   * @return the subLocal
   */
  public SubLocal getSubLocal() {
    return subLocal;
  }

  /**
   * @param subLocal the subLocal to set
   */
  public void setSubLocal(SubLocal subLocal) {
    this.subLocal = subLocal;
  }

  /**
   * @return the status
   */
  public Status getStatus() {
    return status;
  }

  /**
   * @param status the status to set
   */
  public void setStatus(Status status) {
    this.status = status;
  }

  /**
   * @return the categorias
   */
  public List<Categoria> getCategorias() {
    return categorias;
  }

  /**
   * @param categorias the categorias to set
   */
  public void setCategorias(List<Categoria> categorias) {
    this.categorias = categorias;
  }
  
}
