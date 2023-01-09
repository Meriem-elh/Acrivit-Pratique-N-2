package com.sid.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.ebankingbackend.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
	

}
