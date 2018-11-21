/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conex;

import com.mysql.fabric.xmlrpc.base.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author Jackson Nascimento
 */
 public class BancoDados {
 private static final String STR_DRIVER = "com.mysql.jdbc.Driver";
 private static final String USER = "root";
 private static final String PASSWORD = "26031997"; 
 private static final String IP = "localhost";
 private static final String DATABASE = "automaçãoPredial";
 private static final String STR_CON =
	"jdbc:mysql://" + IP + ":3306/" + DATABASE;
 public static Connection getConexao() throws SQLException {
	Connection conn = null;
	try {
		Class.forName(STR_DRIVER);
		conn = DriverManager.getConnection(STR_CON, USER, PASSWORD);
		System.out.println("Obtendoxao...");
	return conn;
        } catch (ClassNotFoundException e) {
		String errorMsg = "Driver nao encontrado";
		throw new SQLException(errorMsg, e);
        } catch (SQLException e) {
		String errorMsg = "Erro ao obter a conexao";
		throw new SQLException(errorMsg, e);
        }
        
  
 }
 
 public static void closeAll(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
}
