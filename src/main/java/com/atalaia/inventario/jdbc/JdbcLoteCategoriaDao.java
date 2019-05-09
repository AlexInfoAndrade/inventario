/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.jdbc;

import com.atalaia.inventario.dao.LoteCategoriaDao;
import com.atalaia.inventario.model.Categoria;
import com.atalaia.inventario.model.Lote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class JdbcLoteCategoriaDao implements LoteCategoriaDao {
  
  public static final String TABLE_LOTE_CATEGORIA = "inv_lote_categoria";
  
  public static final String COLUMN_ID = "loc_id";
  public static final String COLUMN_LOTE_ID = "loc_lote_id";
  public static final String COLUMN_CATEGORIA_ID = "loc_categoria_id";
  
  private static final String CREATE_TB_LOTE_CATEGORIA = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_LOTE_CATEGORIA
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_LOTE_ID + " INTEGER NOT NULL, "
      + COLUMN_CATEGORIA_ID + " INTEGER NOT NULL, "
      + "FOREIGN KEY ("+COLUMN_LOTE_ID+") REFERENCES "
        + JdbcLoteDao.TABLE_LOTE + "("+JdbcLoteDao.COLUMN_ID+"),"
      + "FOREIGN KEY ("+COLUMN_CATEGORIA_ID+") REFERENCES "
        + JdbcCategoriaDao.TABLE_CATEGORIA + "("+JdbcCategoriaDao.COLUMN_ID+")"
    + ");"
  ;
  
  public JdbcLoteCategoriaDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException {
          PreparedStatement statement = con.prepareStatement(
              CREATE_TB_LOTE_CATEGORIA
          );
          
          statement.execute();
          statement.close();
        }
      });
    } catch (SQLException ex) {
      System.out.println("Erro ao criar tabela:\n"+ex.getMessage());
      ex.printStackTrace();
    }
  }

  @Override
  public List<Lote> buscaPorCategoria(long codCategoria) throws SQLException {
    Connection con = null;
    List<Lote> lotes = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_LOTE_ID
              + "\n, "+COLUMN_CATEGORIA_ID
              + "\n From "+TABLE_LOTE_CATEGORIA
              + "\n Where "+COLUMN_CATEGORIA_ID+" = ?"
          );
      statement.setLong(1, codCategoria);
      
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        lotes.add(new JdbcLoteDao().busca(rs.getLong(COLUMN_LOTE_ID)));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return lotes;
  }

  @Override
  public List<Categoria> buscaPorLote(long codLote) throws SQLException {
    Connection con = null;
    List<Categoria> categorias = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_LOTE_ID
              + "\n, "+COLUMN_CATEGORIA_ID
              + "\n From "+TABLE_LOTE_CATEGORIA
              + "\n Where "+COLUMN_LOTE_ID+" = ?"
          );
      statement.setLong(1, codLote);
      
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        categorias.add(new JdbcCategoriaDao().busca(rs.getLong(COLUMN_CATEGORIA_ID)));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return categorias;
  }

  @Override
  public boolean adiciona(final Lote lote, final Categoria categoria) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Insert Into "+TABLE_LOTE_CATEGORIA
          + "\n ( "
          + "\n  `"+COLUMN_LOTE_ID+"`"
          + "\n, `"+COLUMN_CATEGORIA_ID+"`"
          + "\n ) "
          + "Values (?,?)"
        );

        statement.setLong(1, lote.getId());
        statement.setLong(2, categoria.getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean remove(final Lote lote, final Categoria categoria) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Delete From "+TABLE_LOTE_CATEGORIA
          + "\n Where "+COLUMN_LOTE_ID+" = ?"
          + "\n And "+COLUMN_CATEGORIA_ID+" = ?"
        );

        statement.setLong(1, lote.getId());
        statement.setLong(2, categoria.getId());

        statement.execute();
        statement.close();
      }
	});
  }
  
}
