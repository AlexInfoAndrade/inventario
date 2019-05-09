/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.jdbc;

import com.atalaia.inventario.dao.LoteDao;
import com.atalaia.inventario.model.Lote;
import com.atalaia.inventario.model.Status;
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
public class JdbcLoteDao implements LoteDao {
  
  public static final String TABLE_LOTE = "inv_lote";
  
  public static final String COLUMN_ID = "lot_id";
  public static final String COLUMN_LOTE = "lot_lote";
  public static final String COLUMN_OPERADOR = "lot_operador";
  public static final String COLUMN_AUDITOR = "lot_auditor";
  public static final String COLUMN_LOCAL_ID = "lot_local_id";
  public static final String COLUMN_SUB_LOCAL_ID = "lot_sub_local_id";
  public static final String COLUMN_STATUS = "lot_status";
  
  public static final String FK_TABLE_LOTE_CATEGORIA = JdbcLoteCategoriaDao.TABLE_LOTE_CATEGORIA;
  
  public static final String FK_COLUMN_ID = JdbcLoteCategoriaDao.COLUMN_ID;
  public static final String FK_COLUMN_LOTE_ID = JdbcLoteCategoriaDao.COLUMN_LOTE_ID;
  public static final String FK_COLUMN_CATEGORIA_ID = JdbcLoteCategoriaDao.COLUMN_CATEGORIA_ID;
  
  private static final String CREATE_TB_LOTE = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_LOTE
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_LOTE + " INTEGER NOT NULL UNIQUE, "
      + COLUMN_OPERADOR + " TEXT, "
      + COLUMN_AUDITOR + " TEXT, "
      + COLUMN_LOCAL_ID + " INTEGER, "
      + COLUMN_SUB_LOCAL_ID + " INTEGER, "
      + COLUMN_STATUS + " TEXT NOT NULL, "
      + "FOREIGN KEY ("+COLUMN_LOCAL_ID+") REFERENCES "
        + JdbcLocalDao.TABLE_LOCAL + "("+JdbcLocalDao.COLUMN_ID+"),"
      + "FOREIGN KEY ("+COLUMN_SUB_LOCAL_ID+") REFERENCES "
        + JdbcSubLocalDao.TABLE_SUBLOCAL + "("+JdbcSubLocalDao.COLUMN_ID+")"
    + ");"
  ;
  
  public JdbcLoteDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException {
          PreparedStatement statement = con.prepareStatement(
              CREATE_TB_LOTE
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
  public List<Lote> lista() throws SQLException {
    Connection con = null;
    List<Lote> lotes = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_LOTE
              + "\n, "+COLUMN_OPERADOR
              + "\n, "+COLUMN_AUDITOR
              + "\n, "+COLUMN_LOCAL_ID
              + "\n, "+COLUMN_SUB_LOCAL_ID
              + "\n, "+COLUMN_STATUS
              + "\n From "+TABLE_LOTE
      );
      
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        lotes.add(instanciar(rs));
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
  public Lote busca(long codigo) throws SQLException {
    Connection con = null;
    Lote lote;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_LOTE
              + "\n, "+COLUMN_OPERADOR
              + "\n, "+COLUMN_AUDITOR
              + "\n, "+COLUMN_LOCAL_ID
              + "\n, "+COLUMN_SUB_LOCAL_ID
              + "\n, "+COLUMN_STATUS
              + "\n From "+TABLE_LOTE
              + "\n Where "+COLUMN_ID+" = ?"
          );
      statement.setLong(1, codigo);
      
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        lote = instanciar(rs);
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

    return lote;
  }
  
  public Lote busca(int codLote) throws SQLException {
    Connection con = null;
    Lote lote;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_LOTE
              + "\n, "+COLUMN_OPERADOR
              + "\n, "+COLUMN_AUDITOR
              + "\n, "+COLUMN_LOCAL_ID
              + "\n, "+COLUMN_SUB_LOCAL_ID
              + "\n, "+COLUMN_STATUS
              + "\n From "+TABLE_LOTE
              + "\n Where "+COLUMN_LOTE+" = ?"
          );
      statement.setInt(1, codLote);
      
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        lote = instanciar(rs);
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

    return lote;
  }
  
  private Lote instanciar(ResultSet rs) throws Exception {
    Lote l = new Lote();
    
    l.setId(rs.getLong(COLUMN_ID));
    l.setLote(rs.getInt(COLUMN_LOTE));
    l.setOperador(rs.getString(COLUMN_OPERADOR));
    l.setAuditor(rs.getString(COLUMN_AUDITOR));
    l.setLocal(new JdbcLocalDao().busca(rs.getLong(COLUMN_LOCAL_ID)));
    l.setSubLocal(new JdbcSubLocalDao().busca(rs.getLong(COLUMN_SUB_LOCAL_ID)));
    l.setStatus(Status.valueOf(rs.getString(COLUMN_STATUS)));
    
    return l;
  }

  @Override
  public boolean adiciona(final Lote lote) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Insert Into "+TABLE_LOTE
          + "\n ( "
          + "\n  `"+COLUMN_LOTE+"`"
          + "\n, `"+COLUMN_OPERADOR+"`"
          + "\n, `"+COLUMN_AUDITOR+"`"
          + "\n, `"+COLUMN_LOCAL_ID+"`"
          + "\n, `"+COLUMN_SUB_LOCAL_ID+"`"
          + "\n, `"+COLUMN_STATUS+"`"
          + "\n ) "
          + "Values (?,?,?,?,?,?)"
        );

        statement.setInt(1, lote.getLote());
        statement.setString(2, lote.getOperador());
        statement.setString(3, lote.getAuditor());
        statement.setLong(4, lote.getLocal().getId());
        statement.setLong(5, lote.getSubLocal().getId());
        statement.setString(6, lote.getStatus().name());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean altera(final Lote lote) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Update "+TABLE_LOTE
          + "\n Set "
          + "\n  "+COLUMN_LOTE         +" = ?"
          + "\n, "+COLUMN_OPERADOR     +" = ?"
          + "\n, "+COLUMN_AUDITOR      +" = ?"
          + "\n, "+COLUMN_LOCAL_ID     +" = ?"
          + "\n, "+COLUMN_SUB_LOCAL_ID +" = ?"
          + "\n, "+COLUMN_STATUS       +" = ?"
          + "\n Where "+COLUMN_ID      +" = ?"
        );

        statement.setInt(1, lote.getLote());
        statement.setString(2, lote.getOperador());
        statement.setString(3, lote.getAuditor());
        statement.setLong(4, lote.getLocal().getId());
        statement.setLong(5, lote.getSubLocal().getId());
        statement.setString(6, lote.getStatus().name());
        statement.setLong(7, lote.getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean remove(final Lote lote) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Delete From "+FK_TABLE_LOTE_CATEGORIA
          + "\n Where "+FK_COLUMN_LOTE_ID+" = ?;"
                  
          + "\n Delete From "+TABLE_LOTE
          + "\n Where "+COLUMN_ID+" = ?;"
        );

        statement.setLong(1, lote.getId());
        statement.setLong(2, lote.getId());

        statement.execute();
        statement.close();
      }
    });
  }
  
}
