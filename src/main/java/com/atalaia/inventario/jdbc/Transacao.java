package com.atalaia.inventario.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public interface Transacao {
  void executar(Connection connection) throws SQLException, FileNotFoundException, IOException;
}
