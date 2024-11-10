package com.example.demo.Service;

import java.sql.SQLException;

import org.springframework.stereotype.Service;

import com.example.demo.DAORepo.CustomerRepository;
import com.example.demo.Model.Customer.Customer;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
    
    public void findUser() throws SQLException{
    	
    }
    public void createCustomer(Customer customer) throws SQLException{
    	
    }
}