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
public class Inventario {
  private Long id;
  private Integer indice;
  private String ean;
  private Lote lote;
  private Float qntOperador;
  private Float qntAuditor;
  private String analisar;
  
  public static final String SIM = "S";
  public static final String NAO = "N";
  
  public Inventario () {
    this.analisar = NAO;
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
   * @return the indice
   */
  public Integer getIndice() {
    return indice;
  }

  /**
   * @param indice the indice to set
   */
  public void setIndice(Integer indice) {
    this.indice = indice;
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
   * @return the lote
   */
  public Lote getLote() {
    return lote;
  }

  /**
   * @param lote the lote to set
   */
  public void setLote(Lote lote) {
    this.lote = lote;
  }

  /**
   * @return the qntOperador
   */
  public Float getQntOperador() {
    return qntOperador;
  }

  /**
   * @param qntOperador the qntOperador to set
   */
  public void setQntOperador(Float qntOperador) {
    this.qntOperador = qntOperador;
  }

  /**
   * @return the qntAuditor
   */
  public Float getQntAuditor() {
    return qntAuditor;
  }

  /**
   * @param qntAuditor the qntAuditor to set
   */
  public void setQntAuditor(Float qntAuditor) {
    this.qntAuditor = qntAuditor;
  }

  /**
   * @return the analisar
   */
  public String getAnalisar() {
    return analisar;
  }

  /**
   * @param analisar the analisar to set
   */
  public void setAnalisar(String analisar) {
    this.analisar = analisar;
  }
  
  
}
