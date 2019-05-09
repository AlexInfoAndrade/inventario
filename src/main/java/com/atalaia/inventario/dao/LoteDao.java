/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.dao;

import com.atalaia.inventario.model.Inventario;
import com.atalaia.inventario.model.Lote;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alex
 */
public interface LoteDao {
  /**
   * Lista com os dados do lote do banco.
   * @return List recuperado do banco.
   * @throws SQLException
   */
  List<Lote> lista() throws SQLException;
  
  /**
   * Busca um lote com o código informado.
   * @param codigo Código do objeto a recuperar do banco.
   * @return objeto se encontrado.
   * @throws SQLException
   */
  Lote busca(long codigo) throws SQLException;
  
  boolean adiciona(Lote lote) throws SQLException;
  boolean altera(Lote lote) throws SQLException;
  boolean remove(Lote lote) throws SQLException;
}
