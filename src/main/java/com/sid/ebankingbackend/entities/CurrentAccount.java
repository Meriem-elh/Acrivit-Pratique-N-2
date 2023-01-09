package com.sid.ebankingbackend.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DiscriminatorValue("CA")
@Data @NoArgsConstructor @AllArgsConstructor

public class CurrentAccount  extends BankAccount{
	private double overDraft;
	

}
