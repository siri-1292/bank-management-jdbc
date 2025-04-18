package com.bank;

import java.util.Scanner;

import com.bank.service.AdminService;
import com.bank.service.AdminServiceImpl;
import com.bank.service.BankCustomerService;
import com.bank.service.BankCustomerServiceImpl;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		BankCustomerService bankCustomerService = new BankCustomerServiceImpl();
		AdminService adminService = new AdminServiceImpl();
		boolean status = true;
		while (status) {
			System.out.println("Enter \n 1. For Customer Registration "
					               + "\n 2. For Customer Login "
					               + "\n 3. For Admin Login");
			switch (scanner.nextInt()) {
			case 1:
				System.out.println("Customer Registration");
				bankCustomerService.bankCustomerDetails();
				break;
			case 2:
				System.out.println("Customer Login");
				bankCustomerService.customerLogIn();
				break;
			case 3:
				System.out.println("Admin Login");
				adminService.adminLogin();
				break;
			default:
				System.out.println("Invalid Request");
				break;
			}
			System.out.println("Do You want To Continue \n YES \n NO");
			if (scanner.next().equalsIgnoreCase("YES")) {

			} else {

				System.out.println("Thank You...");
				status = false;

			}
		}

	}
}