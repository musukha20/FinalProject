package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.project.dto.AddToCartStatus;

import com.project.dto.CartDto;

import com.project.dto.Login;
import com.project.dto.LoginStatus;

import com.project.dto.PlacedOrder;
import com.project.dto.Status;
import com.project.entity.Cart;
import com.project.entity.Temp;
import com.project.entity.User;
import com.project.exception.CustomerServiceException;
import com.project.service.CustomerService;

@CrossOrigin
@RestController
public class CustomerController {

	@Autowired
	private CustomerService customerService;
	
	@PostMapping(path="/userRegistration")
	public Status register(@RequestBody User user) {
		try {
			int id=customerService.register(user);
			Status status=new Status();
			status.setStatus(true);
			status.setStatusMessage("Registration Successfull");
			return status;
			
		}
		catch(CustomerServiceException e) {
			Status status = new Status();
			status.setStatus(false);
			status.setStatusMessage(e.getMessage());
			return status;
		}
	} 
	
	@PostMapping("/login")
    //@PostMapping(path="/login", consumes ="application/json")
    public LoginStatus login(@RequestBody Login login) {
        try {
        	//System.out.println(loginDto.getEmail());
            User user = customerService.login(login.getEmail(), login.getPassword());
            LoginStatus loginStatus = new LoginStatus();
            loginStatus.setStatus(true);
            loginStatus.setStatusMessage("Login Sucessful");
            loginStatus.setCustomerId(user.getId());
            loginStatus.setCustomerName(user.getName());
            return loginStatus;
            
        }
        catch(CustomerServiceException e) {
            LoginStatus loginStatus = new LoginStatus();
            loginStatus.setStatusMessage(e.getMessage());
            return loginStatus;
        }
        
    }
  

	@GetMapping(path = "/addToMyCart")
	public Status addToMyCart(@RequestParam("userId") int userId, @RequestParam("productId") int productId) {
		try {
			boolean ok = customerService.addToCart(userId, productId);
			Status s = new Status();
			s.setStatus(true);
			if (ok == true) {
				s.setStatusMessage("Product Added To Cart Successfully");

			} else {
				s.setStatusMessage("Cannot Add Product to Cart");

			}
			return s;

		} catch (CustomerServiceException e) {
			AddToCartStatus s = new AddToCartStatus();
			s.setStatus(false);
			s.setStatusMessage(e.getMessage());
			return s;
		}
	}
	
	@GetMapping(path = "/updateMyCart") //passed
	public String updateMyCart(@RequestParam("cartId") int cartId, @RequestParam("addOrMinus") int addOrMinus)
	{
		boolean ok = this.customerService.updateCart(cartId,addOrMinus);
		if(ok==true)
			return "Cart Updated Successful";
		return "Cannot Update Cart";
	}
	
	@DeleteMapping(path = "/deleteMyCart") //passed
	public String deleteMyCart(@RequestParam("cartId")int cId)
	{
		try
		{
			boolean ok = this.customerService.deleteCart(cId);
			return "Cart Deleted";
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}



	@PostMapping(path = "/placeOrder")
	public String placeOrder(@RequestBody List<Cart> carts,@RequestParam("payType") String payType) {
		try {
		boolean ok = customerService.placeOrder(carts, payType);
		if (ok == true)
			return "Order Place Successfully";
		return "Order place failed";
		}catch(CustomerServiceException e){
		return "Everything Failed";
		}
	} 
	
	@GetMapping(path = "/cart") //passed
	public List<CartDto> getMyCart(@RequestParam("userId") int userId)
	{
		return this.customerService.getCartValues(userId);
	}
	
	@GetMapping(path="/getMyPlacedOrders")
	public List<PlacedOrder> showOrderDetails(@RequestParam ("uId") int uId){
		return customerService.getMyPlacedOrders(uId);
		
	} 
}