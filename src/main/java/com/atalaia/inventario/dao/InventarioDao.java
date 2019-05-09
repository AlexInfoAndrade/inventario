/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.dao;

import com.atalaia.inventario.model.Inventario;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alex
 */
public interface InventarioDao {
  /**
   * Lista com os dados do inventário do banco.
   * @return List recuperado do banco.
   * @throws SQLException
   */
  List<Inventario> lista() throws SQLException;
  
  /**
   * Busca um inventário com o código informado.
   * @param codigo Código do objeto a recuperar do banco.
   * @return objeto se encontrado.
   * @throws SQLException
   */
  Inventario busca(long codigo) throws SQLException;
  
  boolean adiciona(Inventario inventario) throws SQLException;
  boolean altera(Inventario inventario) throws SQLException;
  boolean remove(Inventario inventario) throws SQLException;
}
