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
  NAO_COLETADO(1), 
  ESTRAVIADO(0),
  COLETADO(2),
  IMPRESSO(3),
  AUDITADO(4);
  
  public int status;
  
  Status (int status) {
    this.status = status;
  }
}
