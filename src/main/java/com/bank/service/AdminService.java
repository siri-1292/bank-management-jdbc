package com.bank.service;

import com.bank.model.BankCustomerDetails;

public interface AdminService {
	
	void adminLogin();
	void allUserDetails();
	void acceptPendingDetails(BankCustomerDetails bankCustomerDetails);
	void deletePendingDetails(int id);
	void allPendingDetails();

}
