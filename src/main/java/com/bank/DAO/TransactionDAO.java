package com.bank.DAO;

import java.util.List;

import com.bank.model.TransactionDetails;

public interface TransactionDAO {
	
	void insertTrasactionDetails(TransactionDetails transactionDetails);
	List<TransactionDetails> getTransactionDetailsByUsingAccountNumber(int accountnumber);

}
