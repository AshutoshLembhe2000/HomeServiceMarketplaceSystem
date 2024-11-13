package com.example.demo.CustomerService;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.DAORepo.CustomerRepository;
//import com.example.demo.Model.Customer.*;
import com.example.demo.Model.Customer.Customer;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository)
    {
        this.customerRepository = customerRepository;
    }

    //Logic to check if User already exists or not.
    public int addCustomer(Customer customer) {
        List<Map<String, Object>> response=customerRepository.findcustomer(customer.getName());
        if(!response.isEmpty())
        {
            return 0;
        }
        else
        {
            return customerRepository.addcustomer(customer);
        }
    }
}