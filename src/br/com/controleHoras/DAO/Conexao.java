package br.com.controleHoras.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

	public static Connection con = null;
	
	public Connection getConnection() throws ClassNotFoundException{
		
		Class.forName("com.mysql.jdbc.Driver");
		try {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/controle", "root", "admin");
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
}
