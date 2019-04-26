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
public class Produto {
  private Long id;
  private String ean;
  private String descricao;
  private Float valorCusto;
  private Float valorVenda;
  private Float estoque;
  private Categoria categoria;
  
  public Produto () {
    this.categoria = new Categoria();
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
   * @return the ean
   */
  public String getEan() {
    return ean;
  }

  /**
   * @param ean the ean to set
   */
  public void setEan(String ean) {
    this.ean = ean;
  }

  /**
   * @return the descricao
   */
  public String getDescricao() {
    return descricao;
  }

  /**
   * @param descricao the descricao to set
   */
  public void setDescricao(String descricao) {
    this.descricao = descricao;
  }

  /**
   * @return the valorCusto
   */
  public Float getValorCusto() {
    return valorCusto;
  }

  /**
   * @param valorCusto the valorCusto to set
   */
  public void setValorCusto(Float valorCusto) {
    this.valorCusto = valorCusto;
  }

  /**
   * @return the valorVenda
   */
  public Float getValorVenda() {
    return valorVenda;
  }

  /**
   * @param valorVenda the valorVenda to set
   */
  public void setValorVenda(Float valorVenda) {
    this.valorVenda = valorVenda;
  }

  /**
   * @return the estoque
   */
  public Float getEstoque() {
    return estoque;
  }

  /**
   * @param estoque the estoque to set
   */
  public void setEstoque(Float estoque) {
    this.estoque = estoque;
  }

  /**
   * @return the categoria
   */
  public Categoria getCategoria() {
    return categoria;
  }

  /**
   * @param categoria the categoria to set
   */
  public void setCategoria(Categoria categoria) {
    this.categoria = categoria;
  }
  
  public boolean temCategoria () {
    return !categoria.isVazia();
  }
}
