/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.dao;

import com.atalaia.inventario.model.Categoria;
import com.atalaia.inventario.model.Lote;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alex
 */
public interface LoteCategoriaDao {
  /**
   * Busca uma List de Lotes que estejam referenciados ao código
   * da categoria informado.
   * @param codCategoria Código da categoria para recuperar os lotes.
   * @return objeto se encontrado.
   * @throws SQLException
   */
  List<Lote> buscaPorCategoria(long codCategoria) throws SQLException;
  /**
   * Busca um lote com o código informado.
   * @param codLote Código do lote para recuperar as categorias.
   * @return objeto se encontrado.
   * @throws SQLException
   */
  List<Categoria> buscaPorLote(long codLote) throws SQLException;
  
  boolean adiciona(Lote lote, Categoria categoria) throws SQLException;
  boolean remove(Lote lote, Categoria categoria) throws SQLException;
}
