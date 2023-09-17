package usermanagement.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import usermanagement.model.User;

public class UserDAO {
	private String URL = "jdbc:mysql://localhost:3306/test?allowPublicKeyRetrieval=true&useSSL=false";
	private String username = "root";
	private String password = "Admin1234";

	private static final String INSERT_USERS_SQL = "INSERT INTO register"
			+ " (name, email, country) VALUES " + " (?,?,?);";
	private static final String SELECT_USERS_BY_ID_SQL = "SELECT * FROM register WHERE id=?;";
	private static final String SELECT_ALL_USERS_SQL = "SELECT * FROM register";
	private static final String DELETE_USERS_SQL = "DELETE FROM register WHERE id=?;";
	private static final String UPDATE_USERS_SQL = "UPDATE register SET name=?,email=?,sex=?,country=? where id=?;";

	protected Connection getConnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL, username, password);
		} catch (Exception e) {
			System.out.println(e);
		}
		return con;
	}

	public List<User> getAllUsers() {
		List<User> users = new ArrayList<User>();

		try {
			Connection con = getConnection();
			Statement st = con.createStatement();
			// PreparedStatement ps = con.prepareStatement(SELECT_ALL_USERS_SQL);
			ResultSet rs = st.executeQuery(SELECT_ALL_USERS_SQL);
			while (rs.next()) {
				User u = new User();
				u.setId(rs.getInt("id"));
				u.setName(rs.getString("name"));
				u.setPassword(rs.getString("password"));
				u.setEmail(rs.getString("email"));
				u.setSex(rs.getString("sex"));
				u.setCountry(rs.getString("country"));
				users.add(u);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return users;
	}

	public User getUserByID(int id) {
		User user = null;

		try {
			Connection con = getConnection();
			PreparedStatement ps = con.prepareStatement(SELECT_USERS_BY_ID_SQL);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmail(rs.getString("email"));
				user.setSex(rs.getString("sex"));
				user.setCountry(rs.getString("country"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return user;
	}

	public boolean updateUser(User user) throws SQLException {
		boolean rowUpdated;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_USERS_SQL);) {
			statement.setString(1, user.getName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getSex());
			statement.setString(4, user.getCountry());
			statement.setInt(5, user.getId());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}

	public void insertUser(User user) throws SQLException {
		System.out.println(INSERT_USERS_SQL);

		try (Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USERS_SQL)) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getCountry());
			System.out.println(preparedStatement);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			printSQLException(e);
		}
	}

	public boolean deleteUser(int id) throws SQLException {
		boolean rowDeleted;
		try (Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_USERS_SQL);) {
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}
}
