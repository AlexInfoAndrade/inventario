package com.atalaia.inventario.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

public interface Transacao {
  void executar(Connection connection) throws SQLException;
}
