package com.example.demo.Model.Customer;
import org.springframework.stereotype.Component;

import com.example.demo.Model.User.User;

@Component
public class Customer extends User {
    @Override
    public String getUserType() {
        return "Customer";
    }
}
