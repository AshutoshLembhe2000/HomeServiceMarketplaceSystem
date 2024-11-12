package com.example.demo.Controller;


import com.example.demo.DAORepo.ServiceProvider_Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path="/user")
public class ServiceProvider_Controller {
    @Autowired
    ServiceProvider_Repository Service_Repo;

    @ResponseBody
    @GetMapping
    public String check()
    {
        return "Welcome to the server";
    }

    // Endpoint to register a new service provider
    @RequestMapping(path = "/register")
    @ResponseBody
    public String registerUser(@RequestParam long id, @RequestParam String name,
                               @RequestParam String email, @RequestParam String password,
                               @RequestParam String phone, @RequestParam String city) {
        return Service_Repo.setServiceProviderDetails(id, name, email, password, phone, city);
    }

    // Endpoint to login a service provider
    @RequestMapping(path = "/login")
    @ResponseBody
    public String loginUser(@RequestParam String email, @RequestParam String password) {
        boolean isValidUser = Service_Repo.validateLogin(email, password);
        if (isValidUser) {
            return "Login Successful!";
        } else {
            return "Invalid email or password!";
        }
    }
}
