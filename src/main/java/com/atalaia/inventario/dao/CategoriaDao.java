/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.dao;

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
   * Lista com os dados de locais do banco.
   * @return List recuperado do banco.
   * @throws SQLException
   * @throws FileNotFoundException
   * @throws IOException 
   */
  List<Categoria> lista() throws SQLException, FileNotFoundException, IOException;
  
  /**
   * Busca um local com o código informado.
   * @param codigo Código do objeto a recuperar do banco.
   * @return objeto se encontrado.
   * @throws SQLException
   * @throws FileNotFoundException
   * @throws IOException 
   */
  Local busca(long codigo) throws SQLException, FileNotFoundException, IOException;
  
  boolean adiciona(Local local) throws SQLException, FileNotFoundException, IOException;
  boolean altera(Local local) throws SQLException, FileNotFoundException, IOException;
  boolean remove(Local local) throws SQLException, FileNotFoundException, IOException;
}
