package com.project.service;
import java.util.List;

import com.project.dto.CartDto;
import com.project.dto.PlacedOrder;
import com.project.dto.WishListDto;
import com.project.entity.Cart;
import com.project.entity.User;
import com.project.exception.CartException;
import com.project.exception.WishlistException;
import com.project.repository.CartDao;



public interface CustomerService {

	public int register(User user);
	public boolean addToCart(int userId, int productId);
	public boolean placeOrder(List<CartDto> carts, String payType);
	public List<PlacedOrder> getMyPlacedOrders(int uId);
	public boolean updateCart(int cId, int addOrMinus);
	public boolean deleteCart(int cartId) throws CartException;

	public List<CartDto> getCartValues(int userId);

	public User login(String email, String password);
	
	public List<WishListDto> getWishlistValues(int uId);
	public boolean addToWishlist(int uId, int pId);
	public boolean deleteWishlist(int wId) throws WishlistException;
	public int generateOTP();

	
}
