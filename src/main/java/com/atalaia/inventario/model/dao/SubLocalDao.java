/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.model.dao;

import com.atalaia.inventario.model.Local;
import com.atalaia.inventario.model.SubLocal;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alex
 */
public interface SubLocalDao {
  /**
   * Lista com os dados de sub-locais do banco.
   * @return List recuperado do banco.
   * @throws SQLException
   * @throws FileNotFoundException
   * @throws IOException 
   */
  List<SubLocal> lista() throws SQLException, FileNotFoundException, IOException;
  
  /**
   * Busca um sub-local com o código informado.
   * @param codigo Código do objeto a recuperar do banco.
   * @return objeto se encontrado.
   * @throws SQLException
   * @throws FileNotFoundException
   * @throws IOException 
   */
  SubLocal busca(long codigo) throws SQLException, FileNotFoundException, IOException;
  
  boolean adiciona(SubLocal subLocal) throws SQLException, FileNotFoundException, IOException;
  boolean altera(SubLocal subLocal) throws SQLException, FileNotFoundException, IOException;
  boolean remove(SubLocal subLocal) throws SQLException, FileNotFoundException, IOException;
}
