package com.sid.ebankingbackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sid.ebankingbackend.entities.AccountOperation;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

}
