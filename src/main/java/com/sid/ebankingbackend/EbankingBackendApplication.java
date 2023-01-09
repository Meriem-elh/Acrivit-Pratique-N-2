package com.sid.ebankingbackend;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.sid.ebankingbackend.dtos.CustomerDTO;
import com.sid.ebankingbackend.entities.AccountOperation;
import com.sid.ebankingbackend.entities.BankAccount;
import com.sid.ebankingbackend.entities.CurrentAccount;
import com.sid.ebankingbackend.entities.Customer;
import com.sid.ebankingbackend.entities.SavingAccount;
import com.sid.ebankingbackend.enums.AccountStatus;
import com.sid.ebankingbackend.enums.OperationType;
import com.sid.ebankingbackend.exception.BalanceNotSufficentException;
import com.sid.ebankingbackend.exception.BankAccountNotFoundException;
import com.sid.ebankingbackend.exception.CustomerNotFoundException;
import com.sid.ebankingbackend.repositories.AccountOperationRepository;
import com.sid.ebankingbackend.repositories.BankAccountRepository;
import com.sid.ebankingbackend.repositories.CustomerRepository;
import com.sid.ebankingbackend.services.BankAccountService;

@SpringBootApplication
public class EbankingBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
		return args ->{
			Stream.of("Hassan","iman","Mohammed").forEach(name->{
				CustomerDTO customer=new CustomerDTO();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				bankAccountService.saveCustomer(customer);
			});
			bankAccountService.ListCustomers().forEach(customer->{
				try {
					bankAccountService.saveCurrentBankAccount(Math.random(), 9000, customer.getId());
					bankAccountService.saveSavingBankAccount(Math.random(), 9000, customer.getId());
					List<BankAccount> bankAccounts=bankAccountService.bankAccountList();
					
					for(BankAccount bankAccount:bankAccounts) {
						for(int i=0;i<10;i++) {
							bankAccountService.credit(bankAccount.getId(), 10000+Math.random()*120000, "Credit");
							bankAccountService.debit(bankAccount.getId(), 1000+Math.random()*9000, "Debit");
						}
					}
				} catch (CustomerNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BankAccountNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (BalanceNotSufficentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			});
		};
	}
	
	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository,
							BankAccountRepository bankAccountRepository,
							AccountOperationRepository  accountOperationRepository ) {
		return args ->{
			Stream.of("Hassan","Yassine","Aicha").forEach(name->{
				Customer customer=new Customer();
				customer.setName(name);
				customer.setEmail(name+"@gmail.com");
				customerRepository.save(customer);
				
			});
			customerRepository.findAll().forEach(cust->{
				CurrentAccount currentAccount=new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random()*9000);
				currentAccount.setCreatedAt(new Date() );
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);
				
				SavingAccount savingAccount=new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random()*9000);
				savingAccount.setCreatedAt(new Date() );
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestRate(5.5);
				bankAccountRepository.save(savingAccount);
				
			});
			bankAccountRepository.findAll().forEach(acc->{
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation=new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*12000);
					accountOperation.setType(Math.random()>0.5?OperationType.DEBIT:OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);

					
				}
			});
		};
	}
}
