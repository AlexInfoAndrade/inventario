/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.jdbc;

import com.atalaia.inventario.dao.SubLocalDao;
import com.atalaia.inventario.model.SubLocal;
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
      + COLUMN_LOCAL_ID + " INTEGER, "
      + "FOREIGN KEY ("+COLUMN_LOCAL_ID+") REFERENCES "
        + JdbcLocalDao.TABLE_LOCAL + "("+JdbcLocalDao.COLUMN_ID+")"
    + ");"
  ;
  
  public JdbcSubLocalDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException {
          PreparedStatement statement = con.prepareStatement(CREATE_TB_SUBLOCAL
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
  public List<SubLocal> lista() throws SQLException {
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
  
  public List<SubLocal> lista(long codigoLocal) throws SQLException {
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
  public SubLocal busca(long codigo) throws SQLException {
    Connection con = null;
    SubLocal subLocal = new SubLocal();

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
    SubLocal sl = new SubLocal();
    
    sl.setId(rs.getLong(COLUMN_ID));
    sl.setNome(rs.getString(COLUMN_NOME));
    sl.setLocal(new JdbcLocalDao().busca(rs.getLong(COLUMN_LOCAL_ID)));
    
    return sl;
  }

  @Override
  public boolean adiciona(final SubLocal subLocal) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
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
  public boolean altera(final SubLocal subLocal) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
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
  public boolean remove(final SubLocal subLocal) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
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
