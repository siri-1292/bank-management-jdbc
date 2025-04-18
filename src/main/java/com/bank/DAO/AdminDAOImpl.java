package com.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAOImpl implements AdminDAO {
	
	private static final String admin_login=
	"select * from admin_detail where Email_id=? and password=?";
	private static final String url=
	"jdbc:mysql://localhost:3306/teca_66_advance_java_project?user=root&password=root";
	@Override
public boolean selectAdminDetailsByUsingEmailidAndPassword(String emailid,String password) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement= 
				connection.prepareStatement(admin_login);
			preparedStatement.setString(1, emailid);
			preparedStatement.setString(2, password);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

}