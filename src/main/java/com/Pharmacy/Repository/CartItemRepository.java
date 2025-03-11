package com.Pharmacy.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Pharmacy.model.Cart;
import com.Pharmacy.model.CartItems;


public interface CartItemRepository extends JpaRepository<CartItems , Long>{

	
}
