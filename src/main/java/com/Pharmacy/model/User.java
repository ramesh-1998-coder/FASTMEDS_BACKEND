package com.Pharmacy.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import com.Pharmacy.Dto.PharmacyDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Long id;
	
	@Column(name = "full_name")
	private String fullName;
	
	private String email;
	
	@JsonProperty(access= JsonProperty.Access.WRITE_ONLY)
	private String password;
	
	private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;
	
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy="customer")
	private List<Order> orders= new ArrayList<>();
	
	@ElementCollection
	private List<PharmacyDto> favorites = new ArrayList<>();


	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Address> address = new ArrayList<>();


//	public Long getId() {
//		return id;
//	}
//
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//
//	public String getFullName() {
//		return fullName;
//	}
//
//
//	public void setFullName(String fullName) {
//		this.fullName = fullName;
//	}
//
//
//	public String getEmail() {
//		return email;
//	}
//
//
//	public void setEmail(String email) {
//		this.email = email;
//	}
//
//
//	public String getPassword() {
//		return password;
//	}
//
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//
//	public USER_ROLE getRole() {
//		return role;
//	}
//
//
//	public void setRole(USER_ROLE role) {
//		this.role = role;
//	}
//
//
//	public List<Order> getOrders() {
//		return orders;
//	}
//
//
//	public void setOrders(List<Order> orders) {
//		this.orders = orders;
//	}
//
//
//	public List<PharmacyDto> getFavorites() {
//		return favorites;
//	}
//
//
//	public void setFavorites(List<PharmacyDto> favorites) {
//		this.favorites = favorites;
//	}
//
//
//	public List<Address> getAddress() {
//		return address;
//	}
//
//
//	public void setAddress(List<Address> address) {
//		this.address = address;
//	}
//
//
//	@Override
//	public String toString() {
//		return "User [id=" + id + ", fullName=" + fullName + ", email=" + email + ", password=" + password + ", role="
//				+ role + ", orders=" + orders + ", favorites=" + favorites + ", address=" + address + "]";
//	}
//
//
//	
//	
//	
//	
//	
//	
	

}
