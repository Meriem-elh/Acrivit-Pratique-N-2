package com.sid.ebankingbackend.entities;

import java.util.Date;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.sid.ebankingbackend.enums.AccountStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="TYPE",length=4,discriminatorType =DiscriminatorType.STRING )
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public abstract class BankAccount {
	@Id 
	private String id;
	private double balance;
	private Date createdAt;
	@Enumerated(EnumType.STRING)
	private AccountStatus status;
	@ManyToOne
	private Customer customer;
	@OneToMany(mappedBy="bankAccount",fetch= FetchType.LAZY)
	private List<AccountOperation> accountOperations;
	
	
	

}
