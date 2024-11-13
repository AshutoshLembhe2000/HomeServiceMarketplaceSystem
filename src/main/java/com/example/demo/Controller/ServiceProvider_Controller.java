package com.example.demo.Controller;


import com.example.demo.Model.ServiceProvider.ServiceProvider;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Model.User.User;
import com.example.demo.ServiceProvider.Service.ServiceProviderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
@RequestMapping("/ServiceProvider")
public class ServiceProvider_Controller {

    private final ServiceProviderService serviceproviderservice;
    private final IUserFactory userFactory;

    public ServiceProvider_Controller(ServiceProviderService serviceproviderservice, IUserFactory userFactory) {
        this.serviceproviderservice = serviceproviderservice;
        this.userFactory = userFactory;
    }

    // Endpoint to register a new service provider
    @GetMapping("/ServiceProviderRegistrationForm")
    public String checkServiceProvider(Model model)
    {
        User ServiceProvider = userFactory.createUser("ServiceProvider");
        model.addAttribute("ServiceProvider", ServiceProvider);
        return "ServiceProviderRegistration";
    }

    // Register a new ServiceProvider
    @PostMapping("/ServiceProviderRegistrationSuccess")
    @ResponseBody
    public String createServiceProvider(ServiceProvider serviceProvider) throws SQLException {
        int response= serviceproviderservice.VerifyifServiceProviderExist(serviceProvider);
        if(response==0) {
            return "User Already Exists";
        }
        else
        {
            return "User Created Successfully";
        }
    }

    @GetMapping("/ServiceProviderLoginForm")
    public String LoginForm(Model model)
    {
        ServiceProvider serviceProvider = new ServiceProvider();
        model.addAttribute("ServiceProvider", serviceProvider);
        return "ServiceProvider_login";
    }

    @PostMapping("/loginsucess")
    @ResponseBody
    public String loginServiceProvider(@RequestParam String email, @RequestParam String password) {
        boolean isAuthenticated = serviceproviderservice.validateLogin(email, password);
        if (isAuthenticated) {
            return "Login Successful";
//            model.addAttribute("email", email);
//            return "ServiceProviderDashboard";
        } else {
            return "Invalid email or password";
        }
    }



}
