package com.sid.ebankingbackend.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sid.ebankingbackend.dtos.CustomerDTO;
import com.sid.ebankingbackend.entities.Customer;
import com.sid.ebankingbackend.exception.CustomerNotFoundException;
import com.sid.ebankingbackend.services.BankAccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController

@AllArgsConstructor
@Slf4j
public class CustomerRestController {
	private BankAccountService bankAccountService;
	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return bankAccountService.ListCustomers();
		
	}
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name="id") Long customerId) throws CustomerNotFoundException {
		return bankAccountService.getCustomer(customerId);
	}
	@PostMapping("/customers")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
		return bankAccountService.saveCustomer(customerDTO);
	}
	
}
