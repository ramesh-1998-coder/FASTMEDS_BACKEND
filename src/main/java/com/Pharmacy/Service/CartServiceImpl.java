package com.Pharmacy.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Pharmacy.Repository.CartItemRepository;
import com.Pharmacy.Repository.CartRepository;
import com.Pharmacy.Repository.MedicineRepository;
import com.Pharmacy.Request.AddCartItemRequest;
import com.Pharmacy.model.Cart;
import com.Pharmacy.model.CartItems;
import com.Pharmacy.model.Medicine;
import com.Pharmacy.model.User;

@Service
public class CartServiceImpl implements CartService{
	
	@Autowired
	private CartRepository cartRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private MedicineService medicineService;

	@Override
	public CartItems addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
		User user=userService.findUserByJwtToken(jwt);
		
		Medicine medicine=medicineService.findMedicineById(req.getMedicinceId());
		
		Cart cart=cartRepository.findByCustomerId(user.getId());
		
		for(CartItems cartItem: cart.getItem()) {
			if(cartItem.getMedicine().equals(medicine)) {
				int newQuantity=cartItem.getQuatity()+req.getQuantity();
				return updateCartItemQuantity(cartItem.getId(),newQuantity);
			}
		}
		
		CartItems newCartItem=new CartItems();
		newCartItem.setMedicine(medicine);
		newCartItem.setCart(cart);
		newCartItem.setQuatity(req.getQuantity());
		newCartItem.setDossage(req.getDossage());
		newCartItem.setTotalPrice(req.getQuantity()*medicine.getPrice());
		
		CartItems savedCartItem=cartItemRepository.save(newCartItem);
		
		cart.getItem().add(savedCartItem);
		
		return savedCartItem;
		
		
	}

	@Override
	public CartItems updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
		Optional<CartItems> cartItemOptional=cartItemRepository.findById(cartItemId);
		if(cartItemOptional.isEmpty()) {
			throw new Exception("cart item not found");
		}
		CartItems item=cartItemOptional.get();
		item.setQuatity(quantity);
		
		
		item.setTotalPrice(item.getMedicine().getPrice()*quantity);
		
		return cartItemRepository.save(item);
	}

	@Override
	public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
		User user=userService.findUserByJwtToken(jwt);
		
		
		Cart cart=cartRepository.findByCustomerId(user.getId());
		
		Optional<CartItems> cartItemOptional=cartItemRepository.findById(cartItemId);
		if(cartItemOptional.isEmpty()) {
			throw new Exception("cart item not found");
		}
		CartItems item=cartItemOptional.get();
		
		cart.getItem().remove(item);
		
		return cartRepository.save(cart);
		
	}

	@Override
	public Long calculateCartTotal(Cart cart) throws Exception {
		
		Long total=0L;
		
		for(CartItems cartItems:cart.getItem()) {
			total+=cartItems.getMedicine().getPrice()*cartItems.getQuatity();
		}
		return total;
	}

	@Override
	public Cart findCartById(Long id) throws Exception {
		Optional<Cart> optinalCart=cartRepository.findById(id);
		
		if(optinalCart.isEmpty()) {
			throw new Exception("cart not found with id"+id);
			
		}
		return optinalCart.get();
	}

	@Override
	public Cart findCartByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		Cart cart=cartRepository.findByCustomerId(userId);
		cart.setTotal(calculateCartTotal(cart));
		return cart;
	}

	@Override
	public Cart clearCart(Long userId) throws Exception {
		
		
		Cart cart=findCartByUserId(userId);
		
		cart.getItem().clear();
		
		return cartRepository.save(cart);
	}

}
