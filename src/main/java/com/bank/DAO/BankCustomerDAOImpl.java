package com.bank.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bank.model.BankCustomerDetails;

public class BankCustomerDAOImpl implements BankCustomerDAO {

	//Id, Name, EmaiId, Mobile_Number, Aadhar_Number, Pan_Number, Account_Number, Pin, Date_Of_Birth, Address, Amount, Age, Gender, status
	private static final String insert_customer_details=
"insert into bank_customer_details (Name, EmaiId, Mobile_Number, Aadhar_Number, "
+ "Pan_Number, Date_Of_Birth, Address, Amount, Age, Gender,status)values(?,?,?,?,?,?,?,?,?,?,?)";
	
	private static final String select_All=
			"select * from bank_customer_details";
	
	private static final String url="jdbc:mysql://localhost:3306/teca_66_advance_java_project?user=root&password=root";
	
	//select * from bank_customer_details where status ='pending'
	private static final String update_pin_accountnumber=
			"update bank_customer_details set Account_Number=? ,Pin =?,status=? where Id=?";
	
	private static final String delete_Customer_Details=
			"delete from bank_customer_details where Id=?";
	
	private static final String customer_login=
			"select * from bank_customer_details where EmaiId=? And Pin=?";
	
	private static final String update_Amount=
			"update bank_customer_details set Amount=? where Account_Number=?";
	
	private static final String get_by_account_number ="select * from bank_customer_details where Account_Number=?";
	
	private static final String check_balance_by_account_number="select Amount from bank_customer_details where Account_Number=?";
	
	private static final String upadate_password_by_Email="update bank_customer_details set password=? where EmaiId=?";
	
	private static final String select_details_by_Email="select * from bank_customer_details where EmaiId=?";
	
	private static final String select_details_by_mobilenumber="select * from bank_customer_details where Mobile_Number=?";
	@Override
	public void insertBankCutomerDetails(BankCustomerDetails bankCustomerDetails) {
		try {
			Connection connection=
			DriverManager.getConnection(url);
			PreparedStatement preparedStatement = 
				connection.prepareStatement(insert_customer_details);
			preparedStatement.setString(1,bankCustomerDetails.getName());
			preparedStatement.setString(2,bankCustomerDetails.getEmailid());
			preparedStatement.setLong(3,bankCustomerDetails.getMobilenumber());
			preparedStatement.setLong(4,bankCustomerDetails.getAadharnumber());
			preparedStatement.setString(5,bankCustomerDetails.getPannumber());
			preparedStatement.setDate(6,bankCustomerDetails.getDateofbirth());
			preparedStatement.setString(7,bankCustomerDetails.getAddress());
			preparedStatement.setDouble(8,bankCustomerDetails.getAmount());
			preparedStatement.setInt(9,bankCustomerDetails.getAge());
			preparedStatement.setString(10,bankCustomerDetails.getGender());
			preparedStatement.setString(11,"Pending");
			int result = preparedStatement.executeUpdate();
			if (result>0) {
				
				System.out.println("Customer Registration Successfill...");
				
			} else {
				
				System.out.println("Invalid Data");

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// insertion

	}
	@Override
	public List<BankCustomerDetails> getAllCustomerDetails() {//In Bank customer DAO IMPL class
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement = 
					connection.prepareStatement(select_All);
		     ResultSet resultSet = preparedStatement.executeQuery();
		     List<BankCustomerDetails> listofbankCustomerDetails =
		    		 new ArrayList<BankCustomerDetails>();
		     if (resultSet.isBeforeFirst()) {
		    	 while (resultSet.next()) {
		    		 BankCustomerDetails bankCustomerDetails = new BankCustomerDetails();
		    		 bankCustomerDetails.setId(resultSet.getInt("Id"));
		    		 bankCustomerDetails.setName(resultSet.getString("Name"));
		    		 bankCustomerDetails.setEmailid(resultSet.getString("EmaiId"));
		    		 bankCustomerDetails.setAadharnumber(resultSet.getLong("Aadhar_Number"));
		    		 bankCustomerDetails.setMobilenumber(resultSet.getLong("Mobile_Number"));
		    		 bankCustomerDetails.setPannumber(resultSet.getString("Pan_Number"));
		    		 bankCustomerDetails.setStatus(resultSet.getString("status"));
		    		 listofbankCustomerDetails.add(bankCustomerDetails);	
				}
		    	 return listofbankCustomerDetails;
			} else {
				return null;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	@Override
	public void updateAccountNumberAndPinByUsingId(BankCustomerDetails bankCustomerDetails) {
		//"update bank_customer_details set Account_Number=? ,Pin =?,status=? where id=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement =
			connection.prepareStatement(update_pin_accountnumber);
			preparedStatement.setInt(1,bankCustomerDetails.getAccountnumber());
			preparedStatement.setInt(2,bankCustomerDetails.getPin());
			preparedStatement.setInt(4,bankCustomerDetails.getId());
			preparedStatement.setString(3,"Accepted");
			int result = preparedStatement.executeUpdate();
			if (result>0) {
				System.out.println("Updated");
			} else {
				System.out.println("Not Updated");
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
//	@Override
//	public void deleteCustomerDetailsByUsingId(int id) {
//		// TODO Auto-generated method stub
//		try {
//			Connection connection = DriverManager.getConnection(url);
//			PreparedStatement preparedStatement =
//			connection.prepareStatement(delete_Customer_Details);
//			preparedStatement.setInt(1,bankCustomerDetails.getId());
//			
//			int result = preparedStatement.executeUpdate();
//			if (result>0) {
//				System.out.println("Deleted");
//			} else {
//				System.out.println("Not Deleted");
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		
//	}
	
	
	@Override
	public void deleteCustomerDetailsByUsingId(int id) {
	    try {
	        Connection connection = DriverManager.getConnection(url);
	        PreparedStatement preparedStatement = connection.prepareStatement(delete_Customer_Details);
	        preparedStatement.setInt(1, id);

	        int result = preparedStatement.executeUpdate();
	        if (result > 0) {
	            System.out.println("Deleted");
	        } else {
	            System.out.println("Not Deleted");
	        }

	        connection.close(); // Good practice to close connection

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	@Override
	public BankCustomerDetails selectCustomerDetailsByUsingEmailisAndPin(String emailid, int pin) {
//			"select * from bank_customer_details where emailid=? And Pin=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement =
			connection.prepareStatement(customer_login);
			preparedStatement.setString(1, emailid);
			preparedStatement.setInt(2, pin);
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				BankCustomerDetails bankCustomerDetails = new BankCustomerDetails();
				bankCustomerDetails.setName(resultSet.getString("Name"));
				bankCustomerDetails.setGender(resultSet.getString("Gender"));
				bankCustomerDetails.setAmount(resultSet.getDouble("Amount"));
				bankCustomerDetails.setAccountnumber(resultSet.getInt("Account_Number"));
				
				return bankCustomerDetails;
			} else {
				return null;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}
	@Override
	public boolean updateBalanceAmountByUsingAccountNumber(double amount,int accountnumber) {
		//			"update bank_customer_details set Amount=? where Account_Number=?";
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement =
			connection.prepareStatement(update_Amount);
			preparedStatement.setDouble(1,amount);
			preparedStatement.setInt(2, accountnumber);
			  int result=  preparedStatement.executeUpdate();
			  if (result>0) {
				  return true;
			} else {
				  return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		
		
		
	}
	@Override
	public BankCustomerDetails checkByUsingAccountNumber(int accountnumber) {
		// TODO Auto-generated method stub
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement =
			connection.prepareStatement(get_by_account_number);
			preparedStatement.setInt(1, accountnumber);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				BankCustomerDetails bankCustomerDetails = new BankCustomerDetails();
				bankCustomerDetails.setName(resultSet.getString("Name"));
				bankCustomerDetails.setGender(resultSet.getString("Gender"));
				bankCustomerDetails.setAmount(resultSet.getDouble("Amount"));
				bankCustomerDetails.setAccountnumber(resultSet.getInt("Account_Number"));
				
				return bankCustomerDetails;
			} else {
				return null;

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		
	}
	@Override
	public BankCustomerDetails checkAmountByUsingAccountNumber(int accountnumber) {
	    try {
	        Connection connection = DriverManager.getConnection(url);
	        PreparedStatement preparedStatement = connection.prepareStatement(check_balance_by_account_number);
	        preparedStatement.setInt(1, accountnumber);
	        ResultSet resultSet = preparedStatement.executeQuery();  
	        if (resultSet.next()) {
	            BankCustomerDetails bankCustomerDetails = new BankCustomerDetails();
	            bankCustomerDetails.setAmount(resultSet.getDouble("Amount"));  
	            bankCustomerDetails.setAccountnumber(accountnumber);
	            return bankCustomerDetails;
	        } else {
	            return null;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	@Override
	public boolean updatePasswordByUsingEmailId(String password, String emailid) {
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(upadate_password_by_Email);
			preparedStatement.setString(1, password);
			preparedStatement.setString(2, emailid);
			int result=preparedStatement.executeUpdate();
			if(result!=0)
			{
				return true;
			}
			else
			{
				return false;
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return false;
	}
	
	//Id, Name, EmaiId, Mobile_Number, Aadhar_Number, Pan_Number, Account_Number, Pin, Date_Of_Birth, Address, Amount, Age, Gender, status, password
	@Override
	public BankCustomerDetails selectCustomerDetailsByUsingEmail(String emailid) {
		BankCustomerDetails bankCustomerDetails=null;
		try {
			Connection connection=DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(select_details_by_Email);
			preparedStatement.setString(1, emailid);
			ResultSet resultSet=preparedStatement.executeQuery();
			if(resultSet.next())
			{
				bankCustomerDetails = new BankCustomerDetails();
				bankCustomerDetails.setName(resultSet.getString("Name"));
				bankCustomerDetails.setEmailid(resultSet.getString("EmaiId"));
				bankCustomerDetails.setMobilenumber(resultSet.getLong("Mobile_Number"));
				bankCustomerDetails.setAadharnumber(resultSet.getLong("Aadhar_Number"));
				bankCustomerDetails.setPannumber(resultSet.getString("Pan_Number"));
				bankCustomerDetails.setAccountnumber(resultSet.getInt("Account_Number"));
				bankCustomerDetails.setPin(resultSet.getInt("Pin"));
				bankCustomerDetails.setDateofbirth(resultSet.getDate("Date_Of_Birth"));
				bankCustomerDetails.setAddress(resultSet.getString("Address"));
				
				return bankCustomerDetails;
				
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}
	@Override
	public BankCustomerDetails checkByUsingMobilenumber(long mobilenumber) {
		
		BankCustomerDetails bankCustomerDetails=null;
		
		try {
			Connection connection = DriverManager.getConnection(url);
			PreparedStatement preparedStatement=connection.prepareStatement(select_details_by_mobilenumber);
			preparedStatement.setLong(1, mobilenumber);
			ResultSet resultSet=preparedStatement.executeQuery();
			
			if(resultSet.next())
			{
				bankCustomerDetails=new BankCustomerDetails();
				bankCustomerDetails.setName(resultSet.getString("Name"));
				bankCustomerDetails.setMobilenumber(resultSet.getLong("Mobile_Number"));
				bankCustomerDetails.setAmount(resultSet.getDouble("Amount"));
				bankCustomerDetails.setAccountnumber(resultSet.getInt("Account_Number"));
				
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return bankCustomerDetails;
	}
	

}