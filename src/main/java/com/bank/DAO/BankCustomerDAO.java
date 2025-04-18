package com.bank.DAO;

import java.util.List;

import com.bank.model.BankCustomerDetails;

public interface BankCustomerDAO {
	
void insertBankCutomerDetails(BankCustomerDetails bankCustomerDetails);
	
	List<BankCustomerDetails> getAllCustomerDetails();
	
	void updateAccountNumberAndPinByUsingId(BankCustomerDetails bankCustomerDetails);
	
	void deleteCustomerDetailsByUsingId(int id);
	
	BankCustomerDetails selectCustomerDetailsByUsingEmailisAndPin(String emailid,int pin);
	
	boolean updateBalanceAmountByUsingAccountNumber(double amount,int accountnumber);
	
	BankCustomerDetails checkByUsingAccountNumber(int accountnumber);
	
   BankCustomerDetails checkAmountByUsingAccountNumber(int accountnumber);
   
   boolean updatePasswordByUsingEmailId(String password,String emailid);

   BankCustomerDetails selectCustomerDetailsByUsingEmail(String emailid);
   
   BankCustomerDetails checkByUsingMobilenumber(long mobilenumber);
   
   //boolean deleteCustomerDetailsByUsingId(int id);


}
