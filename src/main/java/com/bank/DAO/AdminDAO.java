package com.bank.DAO;

public interface AdminDAO {
	
	boolean selectAdminDetailsByUsingEmailidAndPassword(String emailid,String password);

}