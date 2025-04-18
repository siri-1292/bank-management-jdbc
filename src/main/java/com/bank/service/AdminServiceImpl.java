package com.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.AdminDAO;
import com.bank.DAO.AdminDAOImpl;
import com.bank.DAO.BankCustomerDAO;
import com.bank.DAO.BankCustomerDAOImpl;
import com.bank.model.BankCustomerDetails;

public class AdminServiceImpl implements AdminService {
	Scanner scanner = new Scanner(System.in);
	AdminDAO adminDAO = new AdminDAOImpl();
	BankCustomerDAO bankCustomerDAO = new BankCustomerDAOImpl();

	@Override
	public void adminLogin() {
		System.out.println("Enter Admin Emailid");
		String emailid = scanner.next();
		System.out.println("Enter Admin Password");
		String password = scanner.next();
		if (adminDAO.selectAdminDetailsByUsingEmailidAndPassword(emailid, password)) {
			System.out.println("Enter \n 1.To Get All Account Request Details " + "\n 2.To get all User Details "
					+ "\n 3.To get all Account closing Request Details");
			switch (scanner.nextInt()) {
			case 1:
				System.out.println("All Account Request Details");
				allPendingDetails();
				break;
			case 2:
				System.out.println("All User Details");
				allUserDetails();

				break;
			case 3:
				System.out.println("All Account closing Request Details");
				break;

			default:
				System.out.println("Invalid Request");
				break;
			}
		}

	}

//with in adminserviceimpl
	@Override
	public void allUserDetails() {
		/* Get the method from BankCustomerDAOImpl class */
		List<BankCustomerDetails> allCustomerDetails = bankCustomerDAO.getAllCustomerDetails();
		allCustomerDetails.forEach((customerdetails) -> {
			int indexOf = allCustomerDetails.indexOf(customerdetails) + 1;
			System.out.println("S.No :" + indexOf);
			System.out.println("Customer Name :" + customerdetails.getName());
			System.out.println("Customer Emailid :" + customerdetails.getEmailid());
			long mobilenumber = customerdetails.getMobilenumber();
			String mobile = "" + mobilenumber;
			System.out.println("Customer Mobile Number :" + mobile.substring(0, 3) + "****" + mobile.substring(7, 10));
			long aadharnumber = customerdetails.getAadharnumber();
			String aadhar = "" + aadharnumber;
			System.out.println("Aadhar Number :" + aadhar.substring(0, 4) + "*****" + aadhar.substring(9, 12));
			System.out.println("***-----*******-------******");
		});

	}

	@Override
	public void allPendingDetails() {
		List<BankCustomerDetails> allCustomerDetails = bankCustomerDAO.getAllCustomerDetails();
		List<BankCustomerDetails> allpendingdetailslist = new ArrayList<BankCustomerDetails>();
		for (BankCustomerDetails bankCustomerDetails : allCustomerDetails) {
			if (bankCustomerDetails.getStatus().equalsIgnoreCase("Pending")) {
				BankCustomerDetails bankCustomerDetails2 = new BankCustomerDetails();
				bankCustomerDetails2.setName(bankCustomerDetails.getName());
				bankCustomerDetails2.setEmailid(bankCustomerDetails.getEmailid());
				bankCustomerDetails2.setId(bankCustomerDetails.getId());
				allpendingdetailslist.add(bankCustomerDetails2);
				int indexOf = allpendingdetailslist.indexOf(bankCustomerDetails2) + 1;
				System.out.println("S.No :" + indexOf);
				System.out.println("Customer Name :" + bankCustomerDetails.getName());
				System.out.println("Customer emailid :" + bankCustomerDetails.getEmailid());
				System.out.println("Customer Mobile Number :" + bankCustomerDetails.getMobilenumber());
				System.out.println("Customer Status :" + bankCustomerDetails.getStatus());
			}
		}
		System.out.println("Enter S.No To Select The Customer Details");
		BankCustomerDetails adminselectedobject = allpendingdetailslist.get(scanner.nextInt() - 1);
		System.out.println(adminselectedobject);
		System.out.println("Enter 1 To Accept \n 2 To Delete");
		switch (scanner.nextInt()) {
		case 1:
			
			acceptPendingDetails(adminselectedobject);

			break;
		case 2:
            deletePendingDetails(adminselectedobject.getId());
			break;

		default:
			break;
		}

	}

	@Override
	public void acceptPendingDetails(BankCustomerDetails bankCustomerDetails) {
		Random random = new Random();
		int accountnumber = random.nextInt(10000000);
		System.out.println(accountnumber);
		if (accountnumber < 1000000) {
			// 999999+1000000=1999999
			accountnumber += 1000000;
		}
		System.out.println(accountnumber);
		int pin = random.nextInt(10000);

		if (pin < 1000) {
			pin += 1000;
		}
		bankCustomerDetails.setAccountnumber(accountnumber);
		bankCustomerDetails.setPin(pin);
		bankCustomerDAO.updateAccountNumberAndPinByUsingId(bankCustomerDetails);

	}

	@Override
	public void deletePendingDetails(int id) {
		bankCustomerDAO.deleteCustomerDetailsByUsingId(id);
		
		
		
		
		
	}

}