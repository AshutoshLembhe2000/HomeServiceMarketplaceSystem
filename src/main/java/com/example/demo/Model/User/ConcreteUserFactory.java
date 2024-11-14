package com.example.demo.Model.User;
import com.example.demo.Model.ServiceProvider.ServiceProvider;
import org.springframework.stereotype.Component;

import com.example.demo.Model.Admin.Admin;
import com.example.demo.Model.Customer.Customer;

@Component
public class ConcreteUserFactory implements IUserFactory {

 @Override
 public User createUser(String userType) {
     switch (userType) {
         case "Customer":
        	 System.out.println("Inside customer factory");
             return new Customer();
         case "Admin":
        	 System.out.println("Inside Admin factory");
             return new Admin();
         case "ServiceProvider":
             System.out.println("Inside ServiceProvider factory");
             return new ServiceProvider();
     }
	 //test
	return null;
 }
}

