package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.User;

public class UserDAO {

	private Connection con;

	public UserDAO() {
		try {
			con = DriverManager.getConnection("jdbc:h2:~/chat", "admin", "a");
		} catch (SQLException e) {
			System.err.println("La conexión no se pudo establecer. " + e.getMessage());
		}
	}

	public boolean addUser(User u) { //falta verificar que no hayan nombres repetidos
			boolean success = false;
		try {
			Statement statementOb = con.createStatement();

			String sqlString = "INSERT INTO USERS (USERNAME, ONLINE) values " + "('" + u.getUsername() + "', "
					+ u.isOnline() + ")";

			statementOb.executeUpdate(sqlString);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return success;
	}

	public ArrayList<User> listUsers(User u) {
		ArrayList<User> lista = new ArrayList<User>();
		try {
			Statement statementOb = con.createStatement();

			ResultSet r = statementOb.executeQuery("SELECT * FROM USERS WHERE USERNAME = " + u.getUsername());
			
			while(r.next()){
				String nombre = r.getString("USERNAME");
				boolean online = r.getBoolean("ONLINE");
				User temp = new User(nombre);
				temp.setOnline(online);
                lista.add(temp);
            }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
		return lista;
	}

	public void disconnectUser(User u) {
		try {
			Statement statementOb = con.createStatement();

			String sqlString = "UPDATE USERS SET 'ONLINE' = FALSE";

			statementOb.executeUpdate(sqlString);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				con.close();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
			}
		}
	}
}