package com.sid.ebankingbackend.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sid.ebankingbackend.dtos.CustomerDTO;
import com.sid.ebankingbackend.entities.AccountOperation;
import com.sid.ebankingbackend.entities.BankAccount;
import com.sid.ebankingbackend.entities.CurrentAccount;
import com.sid.ebankingbackend.entities.Customer;
import com.sid.ebankingbackend.entities.SavingAccount;
import com.sid.ebankingbackend.enums.OperationType;
import com.sid.ebankingbackend.exception.BalanceNotSufficentException;
import com.sid.ebankingbackend.exception.BankAccountNotFoundException;
import com.sid.ebankingbackend.exception.CustomerNotFoundException;
import com.sid.ebankingbackend.mappers.BankAccountMapperImpl;
import com.sid.ebankingbackend.repositories.AccountOperationRepository;
import com.sid.ebankingbackend.repositories.BankAccountRepository;
import com.sid.ebankingbackend.repositories.CustomerRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Service
@Transactional 
@AllArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

	private CustomerRepository customerRepository;
	private BankAccountRepository bankAccountRepository;
	private AccountOperationRepository accountOperationRepository;
	private BankAccountMapperImpl dtoMapper;
	//Logger log =LoggerFactory.getLogger(getClass().getName());
	
	
	@Override
	public CustomerDTO saveCustomer(CustomerDTO customerDTO) {

		log.info("Saving a new Customer");
		Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
		
		Customer savedCustomer=customerRepository.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}
	@Override
	public CustomerDTO updateCustomer(CustomerDTO customerDTO) {

		log.info("Saving a new Customer");
		Customer customer=dtoMapper.fromCustomerDTO(customerDTO);
		
		Customer savedCustomer=customerRepository.save(customer);
		return dtoMapper.fromCustomer(savedCustomer);
	}
	
	@Override
	public void deleteCustomer(Long customerId) {
		customerRepository.deleteById(customerId);
		
		
	}
	

	@Override
	public List<CustomerDTO> ListCustomers() {
		
		List<Customer> customers=customerRepository.findAll();
		List<CustomerDTO> customerDTOS=customers.stream()
				.map(cust->dtoMapper.fromCustomer(cust))
				.collect(Collectors.toList());
		return customerDTOS;
	}

	@Override
	public BankAccount getBankAccount(String accountId) throws BankAccountNotFoundException {
		BankAccount bankAccount=bankAccountRepository.findById(accountId).orElseThrow(()->new BankAccountNotFoundException(""));
		
		return bankAccount;
	}

	@Override
	public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficentException {
		// TODO Auto-generated method stub
		BankAccount bankAccount=getBankAccount(accountId);
		if(bankAccount.getBalance()<amount ) {
			throw new BalanceNotSufficentException("Solde insuffisant");
		}
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.DEBIT);
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date());
		accountOperation.setBankAccount(bankAccount);
		accountOperationRepository.save(accountOperation);
		bankAccount.setBalance(bankAccount.getBalance()-amount);
		bankAccountRepository.save(bankAccount);
		
		
	}

	@Override
	public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException {
		BankAccount bankAccount=getBankAccount(accountId);
		
		AccountOperation accountOperation=new AccountOperation();
		accountOperation.setType(OperationType.CREDIT);
		accountOperation.setAmount(amount);
		accountOperation.setDescription(description);
		accountOperation.setOperationDate(new Date());
		accountOperation.setBankAccount(bankAccount);
		accountOperationRepository.save(accountOperation);
		bankAccount.setBalance(bankAccount.getBalance()+amount);
		bankAccountRepository.save(bankAccount);
		
	}

	@Override
	public void transfer(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficentException {
		
		debit(accountIdSource, amount, "Transfer to "+accountIdDestination);
		credit(accountIdDestination, amount, "Transfer from"+accountIdSource);
		
		
	}
	@Override
	
	 public List<BankAccount> bankAccountList(){
		return bankAccountRepository.findAll();
	}
	
	@Override
	public CustomerDTO getCustomer(Long cuustomerId) throws CustomerNotFoundException {
		 Customer customer=customerRepository.findById(cuustomerId)
			.orElseThrow(()->new CustomerNotFoundException("Customer Not Found"));
		return dtoMapper.fromCustomer(customer);
	}


	@Override
	public CurrentAccount saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId)
			throws CustomerNotFoundException {
//Save Current BankAccount  . 
		
		Customer customer=customerRepository.findById(customerId).orElse(null);
		if(customer==null)
			throw new CustomerNotFoundException("customer not found");
		CurrentAccount currentAccount=new CurrentAccount();
		currentAccount.setId(UUID.randomUUID().toString());
		currentAccount.setCreatedAt(new Date());
		currentAccount.setBalance(initialBalance);
		currentAccount.setCustomer(customer);
		currentAccount.setOverDraft(overDraft);
		//CurrentAccount savedBankAccount=bankAccountRepository.save(currentAccount);

		CurrentAccount savedBankAccount=bankAccountRepository.save(currentAccount);
		return savedBankAccount;
	}


	@Override
	public SavingAccount saveSavingBankAccount(double initialBalance, double interestRate, Long customerId)
			throws CustomerNotFoundException {
		Customer customer=customerRepository.findById(customerId).orElse(null);
		if(customer==null)
			throw new CustomerNotFoundException("customer not found");
		SavingAccount savingAccount=new SavingAccount();
		savingAccount.setId(UUID.randomUUID().toString());
		savingAccount.setCreatedAt(new Date());
		savingAccount.setBalance(initialBalance);
		savingAccount.setCustomer(customer);
		savingAccount.setInterestRate(interestRate);
		
		SavingAccount savedBankAccount=bankAccountRepository.save(savingAccount);
		return savedBankAccount;
	}

	

}
