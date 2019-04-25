/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.model.jdbc;

import com.atalaia.inventario.model.jdbc.ConnectionFactory;
import com.atalaia.inventario.model.dao.LocalDao;
import com.atalaia.inventario.model.Local;
import java.io.FileNotFoundException;
import java.io.IOException;
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
public class JdbcLocalDao implements LocalDao {
  
  public static final String TABLE_LOCAL = "inv_local";
  
  public static final String COLUMN_ID = "lcl_id";
  public static final String COLUMN_NOME = "lcl_nome";
  
  private static final String CREATE_TB_LOCAL = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_LOCAL
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_NOME + " TEXT NOT NULL "
    + ");"
  ;
  
  public JdbcLocalDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
          PreparedStatement statement = con.prepareStatement(
              CREATE_TB_LOCAL
          );
          
          statement.execute();
          statement.close();
        }
      });
    } catch (SQLException | IOException ex) {
      System.out.println("Erro ao criar tabela:\n"+ex.getMessage());
      ex.printStackTrace();
    }
  }

  @Override
  public List<Local> lista() throws SQLException, FileNotFoundException, IOException {
    Connection con = null;
    List<Local> locais = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_NOME
              + "\n From "+TABLE_LOCAL);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        locais.add(instanciar(rs));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return locais;
  }

  @Override
  public Local busca(long codigo) throws SQLException, FileNotFoundException, IOException {
    Connection con = null;
    Local local;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_NOME
              + "\n From "+TABLE_LOCAL
              + "\n Where "+COLUMN_ID+" = ?"
      );
      
      statement.setLong(1, codigo);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        local = instanciar(rs);
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

    return local;
  }
  
  private Local instanciar(ResultSet rs) throws Exception {
    Local l = new Local();
    
    l.setId(rs.getLong(COLUMN_ID));
    l.setNome(rs.getString(COLUMN_NOME));
    
    return l;
  }

  @Override
  public boolean adiciona(final Local local) throws SQLException, FileNotFoundException, IOException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
        PreparedStatement statement = con.prepareStatement(
          "Insert Into "+TABLE_LOCAL
          + "\n(`"+COLUMN_NOME+"`) Values (?)"
        );

        statement.setString(1, local.getNome());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean altera(final Local local) throws SQLException, FileNotFoundException, IOException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
        PreparedStatement statement = con.prepareStatement(
          "Update "+TABLE_LOCAL+" Set "+COLUMN_NOME+" = ? \n"
          + "Where "+COLUMN_ID+" = ?"
        );

        statement.setString(1, local.getNome());
        statement.setLong(2, local.getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean remove(final Local local) throws SQLException, FileNotFoundException, IOException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
        PreparedStatement statement = con.prepareStatement(
          "Delete From "+TABLE_LOCAL
          + "\n Where "+COLUMN_ID+" = ?"
        );

        statement.setLong(1, local.getId());

        statement.execute();
        statement.close();
      }
	});
  }
  
}
