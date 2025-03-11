package com.Pharmacy.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Pharmacy.model.Cart;
import com.Pharmacy.model.CartItems;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long>{

	public Cart findByCustomerId(Long userId);
}
