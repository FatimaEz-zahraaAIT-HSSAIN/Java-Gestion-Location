package application;

import java.sql.*;

public class ConnexionMysql {
	public Connection cn= null;
	public static Connection connexionDB() throws SQLException{
		try {

			String url = "jdbc:mysql://localhost:3306/locationlogement";
	        String user = "root";
	        String password = "";
	        Connection cn= DriverManager.getConnection(url, user, password);
	        System.out.println("Connexion etablit");
	        return cn;
		} catch (SQLException e) {
			System.out.println("Connexion echouee");
			e.printStackTrace();
			return null;
		}
		
	}
	
}
