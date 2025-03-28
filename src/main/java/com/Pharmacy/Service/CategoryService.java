package com.Pharmacy.Service;

import java.util.List;

import com.Pharmacy.model.Category;

public interface CategoryService {
	
	public Category createCategory(String name, Long userId) throws Exception;
	
	public List<Category> findCategoryByPharmacy(Long id)throws Exception;
	
	public Category findCategoryById(Long id)throws Exception;

}
