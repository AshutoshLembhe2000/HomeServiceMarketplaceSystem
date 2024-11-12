package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Service.CustomerService;

@Controller
@RestController("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }
    
    @GetMapping("/test")
    public String test ()
    {
    	System.out.println("Working");
    	return "Working";
    }

    //@GetMapping("/register")

    //@PostMapping("/register")
}
