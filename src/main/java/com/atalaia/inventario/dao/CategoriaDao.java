/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.dao;

import com.atalaia.inventario.model.Categoria;
import java.io.FileNotFoundException;
import java.io.IOException;
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
   * @throws FileNotFoundException
   * @throws IOException 
   */
  List<Categoria> lista() throws SQLException, FileNotFoundException, IOException;
  
  /**
   * Busca uma categoria com o código informado.
   * @param codigo Código do objeto a recuperar do banco.
   * @return objeto se encontrado.
   * @throws SQLException
   * @throws FileNotFoundException
   * @throws IOException 
   */
  Categoria busca(long codigo) throws SQLException, FileNotFoundException, IOException;
  
  boolean adiciona(Categoria categoria) throws SQLException, FileNotFoundException, IOException;
  boolean altera(Categoria categoria) throws SQLException, FileNotFoundException, IOException;
  boolean remove(Categoria categoria) throws SQLException, FileNotFoundException, IOException;
}
