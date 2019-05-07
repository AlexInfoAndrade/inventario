/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.jdbc;

import com.atalaia.inventario.dao.CategoriaDao;
import com.atalaia.inventario.model.Categoria;
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
public class JdbcCategoriaDao implements CategoriaDao {
  
  public static final String TABLE_CATEGORIA = "inv_categoria";
  
  public static final String COLUMN_ID = "cat_id";
  public static final String COLUMN_CODIGO = "cat_codigo";
  public static final String COLUMN_NOME = "cat_nome";
  
  public static final String FK_TABLE_LOTE_CATEGORIA = JdbcLoteCategoriaDao.TABLE_LOTE_CATEGORIA;
  
  public static final String FK_COLUMN_ID = JdbcLoteCategoriaDao.COLUMN_ID;
  public static final String FK_COLUMN_LOTE_ID = JdbcLoteCategoriaDao.COLUMN_LOTE_ID;
  public static final String FK_COLUMN_CATEGORIA_ID = JdbcLoteCategoriaDao.COLUMN_CATEGORIA_ID;
  
  private static final String CREATE_TB_CATEGORIA = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIA
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_CODIGO + " INTEGER NOT NULL UNIQUE, "
      + COLUMN_NOME + " TEXT "
    + ");"
  ;
  
  public JdbcCategoriaDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException {
          PreparedStatement statement = con.prepareStatement(
              CREATE_TB_CATEGORIA
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
  public List<Categoria> lista() throws SQLException {
    Connection con = null;
    List<Categoria> categorias = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_CODIGO
              + "\n, "+COLUMN_NOME
              + "\n From "+TABLE_CATEGORIA);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        categorias.add(instanciar(rs));
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
  public Categoria busca(long codigo) throws SQLException {
    Connection con = null;
    Categoria categoria;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_CODIGO
              + "\n, "+COLUMN_NOME
              + "\n From "+TABLE_CATEGORIA
              + "\n Where "+COLUMN_ID+" = ?"
      );
      
      statement.setLong(1, codigo);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        categoria = instanciar(rs);
      } else {
        throw new SQLException("Nenhum registro encontrado.");
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return categoria;
  }
  
  public Categoria busca(String codCategoria) throws SQLException {
    Connection con = null;
    Categoria categoria = new Categoria();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_CODIGO
              + "\n, "+COLUMN_NOME
              + "\n From "+TABLE_CATEGORIA
              + "\n Where "+COLUMN_CODIGO+" = ?"
      );
      
      statement.setString(1, codCategoria);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        categoria = instanciar(rs);
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return categoria;
  }
  
  private Categoria instanciar(ResultSet rs) throws Exception {
    Categoria c = new Categoria();
    
    c.setId(rs.getLong(COLUMN_ID));
    c.setCodigo(rs.getString(COLUMN_CODIGO));
    c.setNome(rs.getString(COLUMN_NOME));
    
    return c;
  }

  @Override
  public boolean adiciona(final Categoria categoria) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Insert Into "+TABLE_CATEGORIA
          + "\n(`"+COLUMN_CODIGO+"`, `"+COLUMN_NOME+"`) Values (?,?)"
        );

        statement.setString(1, categoria.getCodigo());
        statement.setString(2, categoria.getNome());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean altera(final Categoria categoria) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Update "+TABLE_CATEGORIA
          +" Set "+COLUMN_CODIGO+" = ?, "+COLUMN_NOME+" = ?"
          + "\n Where "+COLUMN_ID+" = ?"
        );

        statement.setString(1, categoria.getCodigo());
        statement.setString(2, categoria.getNome());
        statement.setLong(3, categoria.getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean remove(final Categoria categoria) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Delete From "+FK_TABLE_LOTE_CATEGORIA
          + "\n Where "+FK_COLUMN_CATEGORIA_ID+" = ?;"
                  
          + "\n Delete From "+TABLE_CATEGORIA
          + "\n Where "+COLUMN_ID+" = ?;"
        );

        statement.setLong(1, categoria.getId());
        statement.setLong(2, categoria.getId());

        statement.execute();
        statement.close();
      }
    });
  }
  
}
