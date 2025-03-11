package com.Pharmacy.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private User customer;
	
	private Long Total;
	
	
	@OneToMany(mappedBy="cart", cascade= CascadeType.ALL, orphanRemoval= true)
	private List<CartItems> item= new ArrayList<>();


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getCustomer() {
		return customer;
	}


	public void setCustomer(User customer) {
		this.customer = customer;
	}


	public Long getTotal() {
		return Total;
	}


	public void setTotal(Long total) {
		Total = total;
	}


	public List<CartItems> getItem() {
		return item;
	}


	public void setItem(List<CartItems> item) {
		this.item = item;
	}


	@Override
	public String toString() {
		return "Cart [id=" + id + ", customer=" + customer + ", Total=" + Total + ", item=" + item + "]";
	}
	
}
