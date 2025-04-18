package com.bank.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import com.bank.DAO.BankCustomerDAO;
import com.bank.DAO.BankCustomerDAOImpl;
import com.bank.DAO.TransactionDAO;
import com.bank.DAO.TransactionDAOImpl;
import com.bank.exception.BankCustomerException;
import com.bank.model.BankCustomerDetails;
import com.bank.model.TransactionDetails;

public class BankCustomerServiceImpl implements BankCustomerService {

	Scanner scanner = new Scanner(System.in);
	BankCustomerDAO bankCustomerDAO = new BankCustomerDAOImpl();
	TransactionDAO transactionDAO = new TransactionDAOImpl();
	private BankCustomerDetails loginPersonDetails;

	@Override
	public void bankCustomerDetails() {
		List<BankCustomerDetails> allCustomerDetails = bankCustomerDAO.getAllCustomerDetails();
		BankCustomerDetails bankCustomerDetails = new BankCustomerDetails();
		
		System.out.println("Enter Customer Name");
		String name = scanner.next();
		bankCustomerDetails.setName(name);

		boolean emailistatus = true;
		while (emailistatus) {
			System.out.println("Enter Customer Emailid");
			String emailid = scanner.next();
			int emailidcount = 0;
			try {
				if (allCustomerDetails != null) {
					for (BankCustomerDetails bankCustomerDetails2 : allCustomerDetails) {
						if (bankCustomerDetails2 != null 
							&& bankCustomerDetails2.getEmailid() != null 
							&& bankCustomerDetails2.getEmailid().equals(emailid)) {
							emailidcount++;
						}
					}
				}

				if (emailidcount > 0) {
					throw new BankCustomerException("Already Emailid Existed");
				} else {
					bankCustomerDetails.setEmailid(emailid);
					emailistatus = false;
				}
			} catch (BankCustomerException bankCustomerException) {
				System.out.println(bankCustomerException.getExceptionMsg());
			}
		}

		System.out.println("Enter Customer Mobile Number");
		long mobilenumber = scanner.nextLong();
		bankCustomerDetails.setMobilenumber(mobilenumber);

		System.out.println("Enter Customer Aadhar Number");
		long aadharnumber = scanner.nextLong();
		bankCustomerDetails.setAadharnumber(aadharnumber);

		System.out.println("Enter Customer PAN Card (ABCDE1234Y)");
		String pannumber = scanner.next();
		bankCustomerDetails.setPannumber(pannumber);

		System.out.println("Enter Customer DATE OF BIRTH (YYYY-MM-DD)");
		String dob = scanner.next();
		bankCustomerDetails.setDateofbirth(Date.valueOf(dob));

		System.out.println("Enter Customer Address");
		String address = scanner.next();
		bankCustomerDetails.setAddress(address);

		System.out.println("Enter Customer Gender");
		String gender = scanner.next();
		bankCustomerDetails.setGender(gender);

		System.out.println("Enter Customer Age");
		int age = scanner.nextInt();
		bankCustomerDetails.setAge(age);

		System.out.println("Enter Amount");
		double amount = scanner.nextDouble();
		bankCustomerDetails.setAmount(amount);

		bankCustomerDAO.insertBankCutomerDetails(bankCustomerDetails);
	}


	@Override
	public void customerLogIn() {
		System.out.println("Enter Customer Emailid");
		String emailid = scanner.next();
		System.out.println("Enter Customer PIN");
		int pin = scanner.nextInt();
		loginPersonDetails = bankCustomerDAO.selectCustomerDetailsByUsingEmailisAndPin(emailid, pin);
		if (loginPersonDetails != null) {
			Boolean status = true;
			while (status) {
				Random random = new Random();
				String capt = "";
				String a[] = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
						"S", "T", "U", "V", "W", "X", "Y", "Z", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k",
						"l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4",
						"5", "6", "7", "8", "9" };
				for (int i = 0; i < 6; i++) {
					// 61
					int ind = random.nextInt(a.length);
					String cap = a[ind];
					capt = capt + cap;
				}
				System.out.println("Your CapCha :" + capt);
				System.out.println("Enter Capcha");
				String usercapcha = scanner.next();
				if (capt.equals(usercapcha)) {
					status = false;
					if (loginPersonDetails.getGender().equalsIgnoreCase("Male")) {
						System.out.println("Hello MR's :" + loginPersonDetails.getName());
						bankCustomerOperations();

					}
					if (loginPersonDetails.getGender().equalsIgnoreCase("Female")) {
						System.out.println("Hello Miss :" + loginPersonDetails.getName());
						bankCustomerOperations();

					}
				} else {

					System.out.println("Invalid Capcha");
				}
			}
		} else {
			System.out.println("Invalid Emailid Or Pin");
		}

	}

	@Override
	public void bankCustomerOperations() {
	    // Defensive check
	    if (loginPersonDetails == null) {
	        System.out.println("User not logged in. Please log in first.");
	        return;
	    }

	    System.out.println("Choose Operation: ");
	    System.out.println("1. Credit");
	    System.out.println("2. Debit");
	    System.out.println("3. Check Balance");
	    System.out.println("4. Check Statement");
	    System.out.println("5. Update Password");
	    System.out.println("6. Mobile to Mobile Transaction");

	    int choice = scanner.nextInt();

	    switch (choice) {
	        case 1:
	            credit();
	            break;
	        case 2:
	            debit();
	            break;
	        case 3:
	            checkbalance();
	            break;
	        case 4:
	            int accountnumber = loginPersonDetails.getAccountnumber();
	            List<TransactionDetails> details = transactionDAO.getTransactionDetailsByUsingAccountNumber(accountnumber);
	            if (details != null && !details.isEmpty()) {
	                System.out.println("Name: " + loginPersonDetails.getName());
	                System.out.println("Account Number: " + accountnumber);
	                for (TransactionDetails td : details) {
	                    System.out.println("Type: " + td.getTransactiontype());
	                    System.out.println("Date: " + td.getTransactiondate());
	                    System.out.println("Time: " + td.getTransactiontime());
	                    System.out.println("Amount: " + td.getTransactionamount());
	                    System.out.println("Balance: " + td.getBalanceamount());
	                    System.out.println("--------");
	                }
	            } else {
	                System.out.println("No transaction records found.");
	            }
	            break;
	        case 5:
	        	updatepassword();
	            //System.out.println("Update Password feature coming soon...");
	            break;
	        case 6:
	        	mobileTomobiletransaction();
	            //System.out.println("Mobile to Mobile Transaction feature coming soon...");
	            break;
	        default:
	            System.out.println("Invalid choice.");
	    }
	}

	@Override
	public void debit() {
		System.out.println("Enter Amount");
		double useramount = scanner.nextDouble();
		if (useramount >= 0) {
			double databaseamount = loginPersonDetails.getAmount();
			int accountnumber = loginPersonDetails.getAccountnumber();
			if (useramount <= databaseamount) {
				double balanceamount = databaseamount - useramount;
				System.out.println(balanceamount);
				if (bankCustomerDAO.updateBalanceAmountByUsingAccountNumber(balanceamount, accountnumber)) {
					TransactionDetails transactionDetails = new TransactionDetails();
					transactionDetails.setTransactiontype("Debit");
					transactionDetails.setTransactiondate(LocalDate.now());
					transactionDetails.setTransactiontime(LocalTime.now());
					transactionDetails.setAccountnumber(accountnumber);
					transactionDetails.setBalanceamount(balanceamount);
					transactionDetails.setTransactionamount(useramount);

					transactionDAO.insertTrasactionDetails(transactionDetails);
					System.out.println("Amount Debited");
				} else {
					System.out.println("Server 404");
				}
			} else {
				System.out.println("Insufficenet Balance");
			}
		} else {
			System.out.println("Invalid Amount");
		}

	}

	@Override
	public void credit() {
		System.out.println("Enter Amount");
		double amount = scanner.nextDouble();
		if (amount >= 0) {
			System.out.println("Enter Account Number");
			int accountnumber = scanner.nextInt();
			BankCustomerDetails bankCustomerDetails = bankCustomerDAO.checkByUsingAccountNumber(accountnumber);
			if (bankCustomerDetails != null) {
				int ownaccount = loginPersonDetails.getAccountnumber();
				int otheraccount = bankCustomerDetails.getAccountnumber();
				if (ownaccount == otheraccount) {

					double dbamount = loginPersonDetails.getAmount();
					double add = dbamount + amount;
					bankCustomerDAO.updateBalanceAmountByUsingAccountNumber(add, ownaccount);

					System.out.println("Added");

				} else {
					double owndbamount = loginPersonDetails.getAmount();
					double sub = owndbamount - amount;
					double add = bankCustomerDetails.getAmount() + amount;
					bankCustomerDAO.updateBalanceAmountByUsingAccountNumber(sub, ownaccount);
					bankCustomerDAO.updateBalanceAmountByUsingAccountNumber(add, otheraccount);
					System.out.println("Sent");

				}

			} else {
				System.out.println("Invalid Acount Number");

			}
		} else {
			System.out.println("Invalid Amount");

		}

	}


	@Override
	public void checkbalance() {
	    System.out.print("Enter Account Number: ");
	    int accountNumber = scanner.nextInt();

	    // Assuming the DAO returns null if account not found
	    BankCustomerDetails bankCustomerDetails = bankCustomerDAO.checkAmountByUsingAccountNumber(accountNumber);

	    if (bankCustomerDetails != null) {
	        System.out.println("Your Balance is: " + bankCustomerDetails.getAmount());
	    } else {
	        System.out.println("Account not found. Please check the account number.");
	    }
	}


	@Override
	public void updatepassword() {
	    System.out.println("Enter Email Id:");
	    String emailid = scanner.next();

	    // Check if customer exists with the provided email
	    BankCustomerDetails customer = bankCustomerDAO.selectCustomerDetailsByUsingEmail(emailid);

	    if (customer != null) {
	        System.out.println("Enter New Password: ");
	        String newPassword = scanner.next();
	        System.out.println("Confirm New Password: ");
	        String confirmPassword = scanner.next();

	        if (newPassword.equals(confirmPassword)) {
	            boolean isUpdated = bankCustomerDAO.updatePasswordByUsingEmailId(newPassword,emailid);
	            if (isUpdated) {
	                System.out.println("Password updated successfully!");
	            } else {
	                System.out.println("Failed to update password. Please try again.");
	            }
	        } else {
	            System.out.println("Passwords do not match.");
	        }
	    } else {
	        System.out.println("No user found with the given email.");
	    }
	}


	@Override
	public void mobileTomobiletransaction() {
		System.out.println("Enter Receiver's Mobile Number:");
		long receiverMobile = scanner.nextLong();
		
		BankCustomerDetails receiver=bankCustomerDAO.checkByUsingMobilenumber(receiverMobile);
		
		if(receiver==null)
		{
			System.out.println("Receiver not found!");
			return;
		}
		
		System.out.println("Enter Amount to send:");
		double amount=scanner.nextDouble();
		
		if(amount<=0)
		{
			System.out.println("Invalid amount.");
			return;
		}
		
		double senderBalance=loginPersonDetails.getAmount();
		int senderAccount=loginPersonDetails.getAccountnumber();
		int receiverAccount=receiver.getAccountnumber();
		
		if(amount>senderBalance)
		{
			System.out.println("Insufficient balance.");
			return;
		}
		
		double senderNewBalance = senderBalance - amount;
	    double receiverNewBalance = receiver.getAmount() + amount;

	    // Update balances
	    boolean senderUpdated = bankCustomerDAO.updateBalanceAmountByUsingAccountNumber(senderNewBalance, senderAccount);
	    boolean receiverUpdated = bankCustomerDAO.updateBalanceAmountByUsingAccountNumber(receiverNewBalance, receiverAccount);

	    if (senderUpdated && receiverUpdated) {
	        // Update sender's login object
	        loginPersonDetails.setAmount(senderNewBalance);

	        // Record transactions for both sender and receiver
	        TransactionDetails senderTx = new TransactionDetails();
	        senderTx.setTransactiontype("Mobile Transfer Sent");
	        senderTx.setTransactionamount(amount);
	        senderTx.setTransactiondate(LocalDate.now());
	        senderTx.setTransactiontime(LocalTime.now());
	        senderTx.setAccountnumber(senderAccount);
	        senderTx.setBalanceamount(senderNewBalance);
	        transactionDAO.insertTrasactionDetails(senderTx);

	        TransactionDetails receiverTx = new TransactionDetails();
	        receiverTx.setTransactiontype("Mobile Transfer Received");
	        receiverTx.setTransactionamount(amount);
	        receiverTx.setTransactiondate(LocalDate.now());
	        receiverTx.setTransactiontime(LocalTime.now());
	        receiverTx.setAccountnumber(receiverAccount);
	        receiverTx.setBalanceamount(receiverNewBalance);
	        transactionDAO.insertTrasactionDetails(receiverTx);

	        System.out.println("Amount sent successfully!");
	    } else {
	        System.out.println("Transaction failed. Please try again.");
	    }
		
		
	}

}