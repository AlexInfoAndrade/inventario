/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.controller;

import com.atalaia.inventario.jdbc.JdbcLocalDao;
import com.atalaia.inventario.model.Local;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alex
 */
@ManagedBean(name="LocalMB")
public class LocalManagedBean {
  private Local local = new Local();
  
  public void cadastrarLocal() throws SQLException {
    try{
    if (new JdbcLocalDao().adiciona(getLocal())) {
      FacesContext.getCurrentInstance().addMessage(
          null, new FacesMessage(FacesMessage.SEVERITY_INFO,
          "Sucesso!", "Local cadastrado com sucesso!")
      );
    } else {
      FacesContext.getCurrentInstance().addMessage(
          null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erro!", 
          "Erro no cadastr de local!"));
    }
    } catch (Exception e) {
      throw new SQLException(e); //Usar pagina de tratamento de erro
    }
  }
  
  public List<Local> getLocais () throws SQLException {
    try{
      return new JdbcLocalDao().lista();
    } catch (Exception e) {
      throw new SQLException(e);
    }
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
