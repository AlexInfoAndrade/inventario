/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.dao;

import com.atalaia.inventario.model.Produto;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alex
 */
public interface ProdutoDao {
  
  /**
   * Lista com os dados do produto do banco.
   * @return List recuperado do banco.
   * @throws SQLException
   */
  List<Produto> lista() throws SQLException;
  
  /**
   * Busca um produto com o código informado.
   * @param codigo Código do objeto a recuperar do banco.
   * @return objeto se encontrado.
   * @throws SQLException
   */
  Produto busca(long codigo) throws SQLException;
  
  boolean adiciona(Produto produto) throws SQLException;
  boolean altera(Produto produto) throws SQLException;
  boolean remove(Produto produto) throws SQLException;
}
