package com.bank.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.TransactionDetails;

public class TransactionDAOImpl implements TransactionDAO {
	
	//transaction_id, transaction_type, transaction_date, transaction_time, balance_amount, transaction_amount, account_number
	private static final String insert = "insert into transaction_details(transaction_type, transaction_date, transaction_time,"
			+ " balance_amount, transaction_amount, account_number) values(?,?,?,?,?,?)";
	private static final String url = "jdbc:mysql://localhost:3306/teca_66_advance_java_project?user=root&password=root";
	private static final String get_transaction_Details_By_Using_Account_number = "select * from transaction_details where account_number=?";

	@Override
	public void insertTrasactionDetails(TransactionDetails transactionDetails) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection.prepareStatement(insert);
			preparedStatement.setString(1, transactionDetails.getTransactiontype());
			preparedStatement.setDate(2, Date.valueOf(transactionDetails.getTransactiondate()));
			preparedStatement.setTime(3, Time.valueOf(transactionDetails.getTransactiontime()));
			preparedStatement.setDouble(4, transactionDetails.getBalanceamount());
			preparedStatement.setDouble(5, transactionDetails.getTransactionamount());
			preparedStatement.setInt(6, transactionDetails.getAccountnumber());
			int result = preparedStatement.executeUpdate();
			if (result > 0) {
				System.out.println(transactionDetails);
			} else {
				System.out.println("Not Inserted");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public List<TransactionDetails> getTransactionDetailsByUsingAccountNumber(int accountnumber) {
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = connection
					.prepareStatement(get_transaction_Details_By_Using_Account_number);
			preparedStatement.setInt(1, accountnumber);
			ResultSet resultSet = preparedStatement.executeQuery();
			List<TransactionDetails> listoftransactiondetails = new ArrayList<TransactionDetails>();
			if (resultSet.isBeforeFirst()) {
				while (resultSet.next()) {
					Time time = resultSet.getTime("transaction_time");
					LocalTime localTime = LocalTime.parse(time + "");
					Date date = resultSet.getDate("transaction_date");
					LocalDate localDate = LocalDate.parse(date + "");
					
					TransactionDetails transactionDetails = 
							new TransactionDetails(resultSet.getInt("transaction_id"),
							resultSet.getString("transaction_type"), 
							localDate, 
							localTime
							,resultSet.getDouble("balance_amount"), 
							resultSet.getDouble("transaction_amount"),
							resultSet.getInt("account_number"));
					
					listoftransactiondetails.add(transactionDetails);
				}
				return listoftransactiondetails;
			} else {
				return null;

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			return null;
		}

	}

}