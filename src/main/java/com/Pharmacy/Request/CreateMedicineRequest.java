package com.Pharmacy.Request;

import java.util.List;

import com.Pharmacy.model.Category;
import com.Pharmacy.model.DossageLevel;

import lombok.Data;

@Data
public class CreateMedicineRequest {
	
	private String name;
	
	private String description;
	
	private Long price;
	
	private Category category;
	
	private Long pharmacyId;
	
	private boolean vegan;
	 
	private boolean seasonal;
	
	 private List<String> images; 
	 
	 private List<DossageLevel> dossage;

}
