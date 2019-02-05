package com.tutorialspoint;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserDao {

	static final String SUCCESS_RESULT = "<result>success</result>";
	static final String FAILURE_RESULT = "<result>failure</result>";

	public List<User> getAllUsers() {
		return getUsers();
	}

	private List<User> getUsers() {
		List<User> userList = new ArrayList<User>();
		try {
			Connection con = getDBConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id, name, age from emp");
			while (rs.next()) {
				Integer tempKey = Integer.valueOf(rs.getString("id"));
				String tempName = rs.getString("name");
				Integer tempAge = Integer.valueOf(rs.getString("age"));
				User tempUser = new User(tempName, tempAge);
				userList.add(tempUser);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userList;
	}

	public List<User> getUserByID(Integer userId) {
		List<User> userList = new ArrayList<User>();

		try {
			Connection con = getDBConnection();
			Statement stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery("select id, name, age from emp where id = " + userId);
			while (rs.next()) {
				Integer tempKey = Integer.valueOf(rs.getString("id"));
				String tempName = rs.getString("name");
				Integer tempAge = Integer.valueOf(rs.getString("age"));
				User tempUser = new User(tempName, tempAge);
				userList.add(tempUser);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		return userList;
	}
/**
 * used to add a new user.  primary key of ID is not being used as the table primary key is defined as auto-incement
 * @param pUser
 * @return
 * @throws ClassNotFoundException
 * @throws SQLException
 */
	public int addUser(User pUser) throws ClassNotFoundException, SQLException {
		
		Connection con = getDBConnection();
		String insertTableSQL = "INSERT INTO emp" + "(name, age) VALUES" + "(?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
		String tempName = pUser.getName();
		Integer tempAge = pUser.getAge();
		preparedStatement.setString(1, tempName);
		preparedStatement.setInt(2, tempAge);
		preparedStatement.executeUpdate();
		
		return 1;
	}

	public int updateUser(User pUser) throws ClassNotFoundException, SQLException {
		Connection con = getDBConnection();
		String updateStatement = "UPDATE emp set name = '"+ pUser.getName() + "' , age = " + pUser.getAge()
			 + " where id=" + pUser.getId();
		PreparedStatement preparedStatement = con.prepareStatement(updateStatement);
		return preparedStatement.executeUpdate();
	}

	public int deleteUser(int id) throws ClassNotFoundException, SQLException {

		Connection con = getDBConnection();
		String deleteSQL = "DELETE from emp WHERE id = ?";
		PreparedStatement preparedStatement = con.prepareStatement(deleteSQL);
		try {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (con != null) {
				con.close();
			}
		}
		return 1;
	}

	private void saveUserList(List<User> userList) throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo", "root", "");
		String insertTableSQL = "INSERT INTO emp" + "(id, name, age) VALUES" + "(?,?,?)";
		PreparedStatement preparedStatement = con.prepareStatement(insertTableSQL);
		try {

			// here sonoo is database name, root is username and password

			Iterator<User> userIter = userList.iterator();
			while (userIter.hasNext()) {
				User tempUser = (User) userIter.next();
				String tempName = tempUser.getName();
				Integer tempAge = tempUser.getAge();
				preparedStatement.setString(2, tempName);
				preparedStatement.setInt(3, tempAge);
				preparedStatement.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println("Caught Exception during update!" + e.getLocalizedMessage());
		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (con != null) {
				con.close();
			}

		}
	}

	private Connection getDBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/sonoo", "root", "");
		return con;
	}

}
