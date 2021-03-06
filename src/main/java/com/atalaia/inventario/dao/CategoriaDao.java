/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.dao;

import com.atalaia.inventario.model.Categoria;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alex
 */
public interface CategoriaDao {
  
  /**
   * Lista com os dados da categoria do banco.
   * @return List recuperado do banco.
   * @throws SQLException
   */
  List<Categoria> lista() throws SQLException;
  
  /**
   * Busca uma categoria com o código informado.
   * @param codigo Código do objeto a recuperar do banco.
   * @return objeto se encontrado.
   * @throws SQLException
   */
  Categoria busca(long codigo) throws SQLException;
  
  boolean adiciona(Categoria categoria) throws SQLException;
  boolean altera(Categoria categoria) throws SQLException;
  boolean remove(Categoria categoria) throws SQLException;
}
