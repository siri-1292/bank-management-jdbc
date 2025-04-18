package com.bank.exception;

public class BankCustomerException extends RuntimeException {

	private String exceptionMsg;
	
	public BankCustomerException() {
		// TODO Auto-generated constructor stub
	}

	public BankCustomerException(String exceptionMsg) {
		super();
		this.exceptionMsg = exceptionMsg;
	}

	public String getExceptionMsg() {
		return exceptionMsg;
	}

	public void setExceptionMsg(String exceptionMsg) {
		this.exceptionMsg = exceptionMsg;
	}
	
	
}