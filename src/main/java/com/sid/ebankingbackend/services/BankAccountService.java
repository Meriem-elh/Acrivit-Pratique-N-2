package com.sid.ebankingbackend.services;

import java.util.List;

import com.sid.ebankingbackend.dtos.CustomerDTO;
import com.sid.ebankingbackend.entities.BankAccount;
import com.sid.ebankingbackend.entities.CurrentAccount;
import com.sid.ebankingbackend.entities.Customer;
import com.sid.ebankingbackend.entities.SavingAccount;
import com.sid.ebankingbackend.exception.BalanceNotSufficentException;
import com.sid.ebankingbackend.exception.BankAccountNotFoundException;
import com.sid.ebankingbackend.exception.CustomerNotFoundException;

public interface BankAccountService {
	
	 CustomerDTO saveCustomer(CustomerDTO customerDTO);
	 CurrentAccount saveCurrentBankAccount(double initialBalance,double overDraft,Long customerId) throws CustomerNotFoundException;
	 SavingAccount saveSavingBankAccount(double initialBalance,double interestRate,Long customerId) throws CustomerNotFoundException;

	 List<CustomerDTO> ListCustomers();
	 BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException;
	 void debit(String accountId,double amount,String description) throws BankAccountNotFoundException, BalanceNotSufficentException;
	 
	 void credit(String accountId,double amount,String description) throws BankAccountNotFoundException;

	 void transfer(String accountIdSource,String accountIdDestination,double amount) throws BankAccountNotFoundException, BalanceNotSufficentException;
	List<BankAccount> bankAccountList();
	CustomerDTO getCustomer(Long cuustomerId) throws CustomerNotFoundException;
	CustomerDTO updateCustomer(CustomerDTO customerDTO);
	void deleteCustomer(Long customerId);


}
