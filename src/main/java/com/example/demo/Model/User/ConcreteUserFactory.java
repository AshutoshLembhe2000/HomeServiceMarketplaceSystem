package com.example.demo.Model.User;
import org.springframework.stereotype.Component;
import com.example.demo.Model.Customer.Customer;

@Component
public class ConcreteUserFactory implements IUserFactory {

 @Override
 public User createUser(String userType) {
     switch (userType) {
         case "Customer":
        	 System.out.println("Inside customer factory");
             return new Customer();
     }
	return null;
 }
}

