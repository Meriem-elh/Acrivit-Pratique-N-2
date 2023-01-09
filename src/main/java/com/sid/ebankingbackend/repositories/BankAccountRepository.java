package com.sid.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.ebankingbackend.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
