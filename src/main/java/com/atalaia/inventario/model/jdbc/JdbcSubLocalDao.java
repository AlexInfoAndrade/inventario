/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.model.jdbc;

import com.atalaia.inventario.model.jdbc.JdbcLocalDao;
import com.atalaia.inventario.model.jdbc.ConnectionFactory;
import com.atalaia.inventario.model.dao.SubLocalDao;
import com.atalaia.inventario.model.SubLocal;
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
public class JdbcSubLocalDao implements SubLocalDao {
  
  public static final String TABLE_SUBLOCAL = "inv_sublocal";
  
  public static final String COLUMN_ID = "slc_id";
  public static final String COLUMN_NOME = "slc_nome";
  public static final String COLUMN_LOCAL_ID = "slc_local_id";
  
  private static final String CREATE_TB_SUBLOCAL = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_SUBLOCAL
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_NOME + " TEXT NOT NULL, "
      + COLUMN_LOCAL_ID + " INTEGER"
    + ");"
  ;
  
  public JdbcSubLocalDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
          PreparedStatement statement = con.prepareStatement(CREATE_TB_SUBLOCAL
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
  public List<SubLocal> lista() throws SQLException, FileNotFoundException, IOException {
    Connection con = null;
    List<SubLocal> subLocais = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_NOME
              + "\n, "+COLUMN_LOCAL_ID
              + "\n From "+TABLE_SUBLOCAL
      );
      
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        subLocais.add(instanciar(rs));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return subLocais;
  }
  
  public List<SubLocal> lista(long codigoLocal) throws SQLException, FileNotFoundException, IOException {
    Connection con = null;
    List<SubLocal> subLocais = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_NOME
              + "\n, "+COLUMN_LOCAL_ID
              + "\n From "+TABLE_SUBLOCAL
              + "\n Where "+COLUMN_LOCAL_ID+" = ?"
      );
      statement.setLong(1, codigoLocal);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        subLocais.add(instanciar(rs));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return subLocais;
  }

  @Override
  public SubLocal busca(long codigo) throws SQLException, FileNotFoundException, IOException {
    Connection con = null;
    SubLocal subLocal;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_NOME
              + "\n, "+COLUMN_LOCAL_ID
              + "\n From "+TABLE_SUBLOCAL
              + "\n Where "+COLUMN_ID+" = ?"
      );
      
      statement.setLong(1, codigo);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        subLocal = instanciar(rs);
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

    return subLocal;
  }
  
  private SubLocal instanciar(ResultSet rs) throws Exception {
    SubLocal l = new SubLocal();
    
    l.setId(rs.getLong(COLUMN_ID));
    l.setNome(rs.getString(COLUMN_NOME));
    l.setLocal(new JdbcLocalDao().busca(rs.getLong(COLUMN_LOCAL_ID)));
    
    return l;
  }

  @Override
  public boolean adiciona(final SubLocal subLocal) throws SQLException, FileNotFoundException, IOException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
        PreparedStatement statement = con.prepareStatement(
          "Insert Into "+TABLE_SUBLOCAL
          + "\n(`"+COLUMN_NOME+"`, `"+COLUMN_LOCAL_ID+"`) Values (?,?)"
        );

        statement.setString(1, subLocal.getNome());
        statement.setLong(2, subLocal.getLocal().getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean altera(final SubLocal subLocal) throws SQLException, FileNotFoundException, IOException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
        PreparedStatement statement = con.prepareStatement(
          "Update "+TABLE_SUBLOCAL+" Set "+COLUMN_NOME+" = ?, "+COLUMN_LOCAL_ID+" = ? \n"
          + "Where "+COLUMN_ID+" = ?"
        );

        statement.setString(1, subLocal.getNome());
        statement.setLong(2, subLocal.getLocal().getId());
        statement.setLong(3, subLocal.getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean remove(final SubLocal subLocal) throws SQLException, FileNotFoundException, IOException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException, FileNotFoundException, IOException {
        PreparedStatement statement = con.prepareStatement(
          "Delete From "+TABLE_SUBLOCAL
          + "\n Where "+COLUMN_ID+" = ?"
        );

        statement.setLong(1, subLocal.getId());

        statement.execute();
        statement.close();
      }
	});
  }
  
}
