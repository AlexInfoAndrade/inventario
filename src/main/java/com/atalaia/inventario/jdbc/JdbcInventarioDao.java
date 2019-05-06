/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.jdbc;

import com.atalaia.inventario.dao.InventarioDao;
import com.atalaia.inventario.model.Inventario;
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
public class JdbcInventarioDao implements InventarioDao {
  
  public static final String TABLE_INVENTARIO = "inv_inventario";
  
  public static final String COLUMN_ID = "iin_id";
  public static final String COLUMN_INDICE = "iin_indice";
  public static final String COLUMN_EAN = "iin_ean";
  public static final String COLUMN_LOTE_ID = "iin_lote_id";
  public static final String COLUMN_QNT_OPERADOR = "iin_qnt_operador";
  public static final String COLUMN_QNT_AUDITOR = "iin_qnt_auditor";
  public static final String COLUMN_ANALISAR = "iin_analisar";
  
  
  private static final String CREATE_TB_INVENTARIO = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_INVENTARIO
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_INDICE + " INTEGER NOT NULL,"
      + COLUMN_EAN + " TEXT NOT NULL, "
      + COLUMN_LOTE_ID + " INTEGER NOT NULL, "
      + COLUMN_QNT_OPERADOR + " REAL NOT NULL, "
      + COLUMN_QNT_AUDITOR + " REAL, "
      + COLUMN_ANALISAR + " TEXT NOT NULL "
    + ");"
  ;
  
  public JdbcInventarioDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException {
          PreparedStatement statement = con.prepareStatement(
              CREATE_TB_INVENTARIO
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
  public List<Inventario> lista() throws SQLException {
    Connection con = null;
    List<Inventario> inventarios = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n , "+COLUMN_INDICE
              + "\n, "+COLUMN_EAN
              + "\n, "+COLUMN_LOTE_ID
              + "\n, "+COLUMN_QNT_OPERADOR
              + "\n, "+COLUMN_QNT_AUDITOR
              + "\n, "+COLUMN_ANALISAR
              + "\n From "+TABLE_INVENTARIO
      );
      
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        inventarios.add(instanciar(rs));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return inventarios;
  }
  
  public List<Inventario> lista(Lote lote) throws SQLException {
    Connection con = null;
    List<Inventario> inventarios = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n , "+COLUMN_INDICE
              + "\n, "+COLUMN_EAN
              + "\n, "+COLUMN_LOTE_ID
              + "\n, "+COLUMN_QNT_OPERADOR
              + "\n, "+COLUMN_QNT_AUDITOR
              + "\n, "+COLUMN_ANALISAR
              + "\n From "+TABLE_INVENTARIO
              + "\n Where "+COLUMN_LOTE_ID+" = ?"
          );
      statement.setLong(1, lote.getId());
      
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        inventarios.add(instanciar(rs));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return inventarios;
  }

  @Override
  public Inventario busca(long codigo) throws SQLException {
    Connection con = null;
    Inventario inventario;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n , "+COLUMN_INDICE
              + "\n, "+COLUMN_EAN
              + "\n, "+COLUMN_LOTE_ID
              + "\n, "+COLUMN_QNT_OPERADOR
              + "\n, "+COLUMN_QNT_AUDITOR
              + "\n, "+COLUMN_ANALISAR
              + "\n From "+TABLE_INVENTARIO
              + "\n Where "+COLUMN_ID+" = ?"
          );
      statement.setLong(1, codigo);
      
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        inventario = instanciar(rs);
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

    return inventario;
  }
  
  private Inventario instanciar(ResultSet rs) throws Exception {
    Inventario i = new Inventario();
    
    i.setId(rs.getLong(COLUMN_ID));
    i.setIndice(rs.getInt(COLUMN_INDICE));
    i.setEan(rs.getString(COLUMN_EAN));
    i.setLote(new JdbcLoteDao().busca(rs.getLong(COLUMN_LOTE_ID)));
    i.setQntOperador(rs.getFloat(COLUMN_QNT_OPERADOR));
    i.setQntAuditor(rs.getFloat(COLUMN_QNT_AUDITOR));
    i.setAnalisar(rs.getString(COLUMN_ANALISAR));
    
    return i;
  }

  @Override
  public boolean adiciona(final Inventario inventario) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Insert Into "+TABLE_INVENTARIO
          + "\n ( "
          + "\n  `"+COLUMN_INDICE+"`"
          + "\n, `"+COLUMN_EAN+"`"
          + "\n, `"+COLUMN_LOTE_ID+"`"
          + "\n, `"+COLUMN_QNT_OPERADOR+"`"
          + "\n, `"+COLUMN_QNT_AUDITOR+"`"
          + "\n, `"+COLUMN_ANALISAR+"`"
          + "\n ) "
          + "Values (?,?,?,?,?,?)"
        );

        statement.setInt(1, inventario.getIndice());
        statement.setString(2, inventario.getEan());
        statement.setLong(3, inventario.getLote().getId());
        statement.setFloat(4, inventario.getQntOperador());
        statement.setFloat(5, inventario.getQntAuditor());
        statement.setString(6, inventario.getAnalisar());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean altera(final Inventario inventario) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Update "+TABLE_INVENTARIO
          + "\n Set "
          + "\n  "+COLUMN_INDICE          +" = ?"
          + "\n, "+COLUMN_EAN    +" = ?"
          + "\n, "+COLUMN_LOTE_ID  +" = ?"
          + "\n, "+COLUMN_QNT_OPERADOR  +" = ?"
          + "\n, "+COLUMN_QNT_AUDITOR      +" = ?"
          + "\n, "+COLUMN_ANALISAR +" = ?"
          + "\n Where "+COLUMN_ID      +" = ?"
        );

        statement.setInt(1, inventario.getIndice());
        statement.setString(2, inventario.getEan());
        statement.setLong(3, inventario.getLote().getId());
        statement.setFloat(4, inventario.getQntOperador());
        statement.setFloat(5, inventario.getQntAuditor());
        statement.setString(6, inventario.getAnalisar());
        statement.setLong(7, inventario.getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean remove(final Inventario inventario) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Delete From "+TABLE_INVENTARIO
          + "\n Where "+COLUMN_ID+" = ?"
        );

        statement.setLong(1, inventario.getId());

        statement.execute();
        statement.close();
      }
	});
  }
  
}
