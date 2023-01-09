package com.sid.ebankingbackend.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.sid.ebankingbackend.enums.OperationType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data @NoArgsConstructor @AllArgsConstructor
@Entity
public class AccountOperation {
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private Date operationDate;
	private double amount;
	@Enumerated(EnumType.STRING)
	private OperationType type;
	@ManyToOne
	private BankAccount bankAccount;
	private String description;
	

}
