package com.Pharmacy.Service;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Pharmacy.Repository.AddressRepository;
import com.Pharmacy.Repository.OrderItemRepository;
import com.Pharmacy.Repository.OrderRepository;
import com.Pharmacy.Repository.UserRepository;
import com.Pharmacy.Request.OrderRequest;
import com.Pharmacy.model.Address;
import com.Pharmacy.model.Cart;
import com.Pharmacy.model.CartItems;
import com.Pharmacy.model.Order;
import com.Pharmacy.model.OrderItem;
import com.Pharmacy.model.Pharmacy;
import com.Pharmacy.model.User;

@Service
public class OrderServiceImpl implements OrderService{
	
	@Autowired
	private OrderRepository orderRespository;
	
	@Autowired
	private OrderItemRepository orderItemRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	@Autowired
	private PharmacyService pharmacyService;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CartService cartService;
	
	

	@Override
	public Order createOrder(OrderRequest order, User user) throws Exception{
		
		Address shipAddress=order.getDeliveryAddress();
		
		Address saveAddress= addressRepository.save(shipAddress);
		
		if(!user.getAddress().contains(saveAddress)) {
			user.getAddress().add(saveAddress);
			userRepository.save(user);
		}
		
		Pharmacy pharmacy=pharmacyService.findPharmacyById(order.getPharmacyId());
		
		Order createOrder=new Order();
		createOrder.setCustomer(user);
		createOrder.setCreatedAt(new Date());
		createOrder.setOrderStatus("PENDING");
		createOrder.setDeliveryAddress(saveAddress);
		createOrder.setPharmacy(pharmacy);
		
		Cart cart=cartService.findCartByUserId(user.getId());
		
		List<OrderItem> orderItems=new ArrayList<>();
		
		for(CartItems cartItem: cart.getItem()) {
			OrderItem orderItem= new OrderItem();
			orderItem.setMedicine(cartItem.getMedicine());
			orderItem.setDossage(cartItem.getDossage());
			orderItem.setDossageLevel(cartItem.getDossageLevel());
			orderItem.setQuantity(cartItem.getQuatity());
			orderItem.setTotalPrice(cartItem.getTotalPrice());
			
			OrderItem savedOrderItem=orderItemRepository.save(orderItem);
			orderItems.add(savedOrderItem);
		}
		Long totalPrice=cartService.calculateCartTotal(cart);
		
		createOrder.setItems(orderItems);
		createOrder.setTotalPrice(totalPrice);
		
		Order savedOrder=orderRespository.save(createOrder);
		pharmacy.getOrders().add(savedOrder);
		
		
		return createOrder;
	}

	@Override
	public Order updateOrder(Long orderId, String orderStatus) throws Exception {
		
		Order order= findOrderById(orderId);
		if(orderStatus.equals("DELIVERED")
				||orderStatus.equals("DELIVERED")
				||orderStatus.equals("DELIVERED")) {
			
			order.setOrderStatus(orderStatus);
			return orderRespository.save(order);
			
		}throw new Exception("Please select a valid order Status");
	}

	@Override
	public void cancelOrder(Long orderId) throws Exception {
		
		Order order= findOrderById(orderId);
		orderRespository.deleteById(orderId);
		
	}

	@Override
	public List<Order> getUserOrder(Long userId) throws Exception {
		// TODO Auto-generated method stub
		return orderRespository.findByCustomerId(userId);
	}

	@Override
	public List<Order> getPharmacyOrder(Long pharmacyId, String orderStatus) throws Exception {
		
		List<Order> orders=orderRespository.findByPharmacyId(pharmacyId);
		
		if(orderStatus!=null) {
			orders=orders.stream().
					filter(order -> order.getOrderStatus()
							.equals(orderStatus))
					.collect(Collectors.toList());
		}
		return orders;
	}

	@Override
	public Order findOrderById(Long orderId) throws Exception {
		Optional<Order> optionalOrder=orderRespository.findById(orderId);
		if(optionalOrder.isEmpty()) {
			throw new Exception("Order not found");
		}
		return optionalOrder.get();
	}

}
