/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.atalaia.inventario.jdbc;

import com.atalaia.inventario.dao.ProdutoDao;
import com.atalaia.inventario.model.Produto;
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
public class JdbcProdutoDao implements ProdutoDao {
  
  public static final String TABLE_PRODUTO = "inv_produto";
  
  public static final String COLUMN_ID = "prd_id";
  public static final String COLUMN_EAN = "prd_ean";
  public static final String COLUMN_DESCRICAO = "prd_descricao";
  public static final String COLUMN_VALOR_CUSTO = "prd_valor_custo";
  public static final String COLUMN_VALOR_VENDA = "prd_valor_venda";
  public static final String COLUMN_ESTOQUE = "prd_estoque";
  public static final String COLUMN_CATEGORIA_ID = "prd_categoria_id";
  
  private static final String CREATE_TB_PRODUTO = 
    "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUTO
    + "(" 
      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
      + COLUMN_EAN + " TEXT NOT NULL, "
      + COLUMN_DESCRICAO + " TEXT NOT NULL, "
      + COLUMN_VALOR_CUSTO + " REAL, "
      + COLUMN_VALOR_VENDA + " REAL, "
      + COLUMN_ESTOQUE + " REAL, "
      + COLUMN_CATEGORIA_ID + " INTEGER "
    + ");"
  ;
  
  public JdbcProdutoDao () {
    iniciaTabela();
  }
  
  private void iniciaTabela () {
    try {
      ConnectionFactory.executaTransacao(new Transacao(){
        @Override
        public void executar(Connection con) throws SQLException {
          PreparedStatement statement = con.prepareStatement(
              CREATE_TB_PRODUTO
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
  public List<Produto> lista() throws SQLException {
    Connection con = null;
    List<Produto> produtos = new ArrayList<>();

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_EAN
              + "\n, "+COLUMN_DESCRICAO
              + "\n, "+COLUMN_VALOR_CUSTO
              + "\n, "+COLUMN_VALOR_VENDA
              + "\n, "+COLUMN_ESTOQUE
              + "\n, "+COLUMN_CATEGORIA_ID
              + "\n From "+TABLE_PRODUTO);
      ResultSet rs = statement.executeQuery();

      while (rs.next()) {
        produtos.add(instanciar(rs));
      }

      rs.close();
      statement.close();

    } catch (Exception e) {
      throw new SQLException(e);
    } finally {
      con.close();
    }

    return produtos;
  }

  @Override
  public Produto busca(long codigo) throws SQLException {
    Connection con = null;
    Produto produto;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_EAN
              + "\n, "+COLUMN_DESCRICAO
              + "\n, "+COLUMN_VALOR_CUSTO
              + "\n, "+COLUMN_VALOR_VENDA
              + "\n, "+COLUMN_ESTOQUE
              + "\n, "+COLUMN_CATEGORIA_ID
              + "\n From "+TABLE_PRODUTO
              + "\n Where "+COLUMN_ID+" = ?"
          );
      statement.setLong(1, codigo);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        produto = instanciar(rs);
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

    return produto;
  }
  
  public Produto busca(String ean) throws SQLException {
    Connection con = null;
    Produto produto;

    try {
      con = ConnectionFactory.getConnection();
      PreparedStatement statement =
          con.prepareStatement(
              "Select "+COLUMN_ID
              + "\n, "+COLUMN_EAN
              + "\n, "+COLUMN_DESCRICAO
              + "\n, "+COLUMN_VALOR_CUSTO
              + "\n, "+COLUMN_VALOR_VENDA
              + "\n, "+COLUMN_ESTOQUE
              + "\n, "+COLUMN_CATEGORIA_ID
              + "\n From "+TABLE_PRODUTO
              + "\n Where "+COLUMN_EAN+" = ?"
          );
      statement.setString(1, ean);
      ResultSet rs = statement.executeQuery();

      if (rs.next()) {
        produto = instanciar(rs);
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

    return produto;
  }
  
  private Produto instanciar(ResultSet rs) throws Exception {
    Produto p = new Produto();
    
    p.setId(rs.getLong(COLUMN_ID));
    p.setEan(rs.getString(COLUMN_EAN));
    p.setDescricao(rs.getString(COLUMN_DESCRICAO));
    p.setValorCusto(rs.getFloat(COLUMN_VALOR_CUSTO));
    p.setValorVenda(rs.getFloat(COLUMN_VALOR_VENDA));
    p.setEstoque(rs.getFloat(COLUMN_ESTOQUE));
    p.setCategoria(
        new JdbcCategoriaDao().busca(rs.getString(COLUMN_CATEGORIA_ID))
    );
    
    return p;
  }

  @Override
  public boolean adiciona(final Produto produto) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Insert Into "+TABLE_PRODUTO
          + "\n ( "
          + "\n  `"+COLUMN_EAN+"`"
          + "\n, `"+COLUMN_DESCRICAO+"`"
          + "\n, `"+COLUMN_VALOR_CUSTO+"`"
          + "\n, `"+COLUMN_VALOR_VENDA+"`"
          + "\n, `"+COLUMN_ESTOQUE+"`"
          + "\n, `"+COLUMN_CATEGORIA_ID+"`"
          + "\n ) "
          + "Values (?,?,?,?,?,?)"
        );

        statement.setString(1, produto.getEan());
        statement.setString(2, produto.getDescricao());
        statement.setFloat(3, produto.getValorCusto());
        statement.setFloat(4, produto.getValorVenda());
        statement.setFloat(5, produto.getEstoque());
        statement.setLong(6, produto.getCategoria().getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean altera(final Produto produto) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Update "+TABLE_PRODUTO
          + "\n Set "
          + "\n  "+COLUMN_EAN          +" = ?"
          + "\n, "+COLUMN_DESCRICAO    +" = ?"
          + "\n, "+COLUMN_VALOR_CUSTO  +" = ?"
          + "\n, "+COLUMN_VALOR_VENDA  +" = ?"
          + "\n, "+COLUMN_ESTOQUE      +" = ?"
          + "\n, "+COLUMN_CATEGORIA_ID +" = ?"
          + "\n Where "+COLUMN_ID      +" = ?"
        );

        statement.setString(1, produto.getEan());
        statement.setString(2, produto.getDescricao());
        statement.setFloat(3, produto.getValorCusto());
        statement.setFloat(4, produto.getValorVenda());
        statement.setFloat(5, produto.getEstoque());
        statement.setLong(6, produto.getCategoria().getId());
        statement.setLong(7, produto.getId());

        statement.execute();
        statement.close();
      }
	});
  }

  @Override
  public boolean remove(final Produto produto) throws SQLException {
    return ConnectionFactory.executaTransacao(new Transacao(){
      @Override
      public void executar(Connection con) throws SQLException {
        PreparedStatement statement = con.prepareStatement(
          "Delete From "+TABLE_PRODUTO
          + "\n Where "+COLUMN_ID+" = ?"
        );

        statement.setLong(1, produto.getId());

        statement.execute();
        statement.close();
      }
	});
  }
  
}
