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
public enum Status{
  NAO_COLETADO("Não coletado"), 
  ESTRAVIADO("Estraviado"),
  COLETADO("Coletado"),
  IMPRESSO("Impresso"),
  AUDITADO("Auditado");
  
  public String status;
  
  Status (String status) {
    this.status = status;
  }
}
