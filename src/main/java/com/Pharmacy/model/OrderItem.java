package com.Pharmacy.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Medicine medicine;
	
	private int quantity;
	
	private Long totalPrice;
	
	private List<String> dossage;
	
	private List<String> dossageLevel;
	
	
	
}
