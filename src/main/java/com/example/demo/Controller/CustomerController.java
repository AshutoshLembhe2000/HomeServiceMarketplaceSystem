package com.example.demo.Controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Model.User.User;
import com.example.demo.Model.Customer.Customer;
import com.example.demo.Service.CustomerService;

@Controller
@RequestMapping("/Customer")
public class CustomerController {

    private final CustomerService customerService;
    private final IUserFactory userFactory;
    
    public CustomerController(CustomerService customerService,IUserFactory userFactory) {
        this.customerService = customerService;
        this.userFactory=userFactory;
    }
    
    @GetMapping("/customerRegistrationForm")
    public String checkUser(Model model){
    	User customer = userFactory.createUser("Customer");
    	model.addAttribute("customer",customer);
    	return "CustomerRegistration";
	}
    
   
    @PostMapping("/customerRegistrationSuccess")
    @ResponseBody
    public String createCustomer(Customer customer) throws SQLException {
    	int response= customerService.addCustomer(customer);
    	if(response==0) {
    		return "User Already Exists";
    	}
    	else
    	{
    		return "User Created Successfully";
    	}
    }
}
