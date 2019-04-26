package com.atalaia.inventario.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
  private static final String DB_PROPERTIES = "database.properties";
  
  /**
   * Cria a conexao com o Gerenciador de banco de dados.
   *
   * @return Conexão estabelecida
   * @throws SQLException Erros de SQL
   */
  public static Connection getConnection() throws SQLException {
    try {
      InputStream inputStream = ConnectionFactory.class.getClassLoader().
          getResourceAsStream(DB_PROPERTIES);

      Properties p = new Properties();
      p.load(inputStream);

      /*
      Properties p = new Properties();
      System.out.println(DB_PROPERTIES);
      p.load(new FileInputStream(new File(DB_PROPERTIES)));
      */
      
      String driver = p.getProperty("driver");
      String url = p.getProperty("url");

      Class.forName(driver);

      return DriverManager.getConnection(url, p);
    } catch (Exception e) {
      throw new SQLException(e);
    }
  }

  /**
   * Executa uma trasação implementa da interface Transação.
   *
   * @param transacao implementação da interface Transação
   * @throws SQLException Erros de SQL
   * @return Verdadeiro se a transação for executada, caso contrário retorna falso.
   */
  public static boolean executaTransacao(Transacao transacao) throws SQLException {
    Connection con = getConnection();

    try {
      con.setAutoCommit(false);
      transacao.executar(con);
      con.commit();
      return true;
    } catch (Exception e) {
      con.rollback();
      throw new SQLException(e);
    } finally {
      con.close();
    }
  }
  
}
