/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.jdbc;

import com.atalaia.inventario.dao.LoteDao;
import com.atalaia.inventario.model.Lote;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
  
  private static final String CREATE_TB_LOTE = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_LOTE
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_LOTE + " INTEGER NOT NULL,"
      + COLUMN_OPERADOR + " TEXT, "
      + COLUMN_AUDITOR + " TEXT, "
      + COLUMN_LOCAL_ID + " INTEGER, "
      + COLUMN_SUB_LOCAL_ID + " INTEGER, "
      + COLUMN_STATUS + " INTEGER NOT NULL "
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
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public Lote busca(long codigo) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean adiciona(Lote lote) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean altera(Lote lote) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }

  @Override
  public boolean remove(Lote lote) throws SQLException {
    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
  }
  
}
