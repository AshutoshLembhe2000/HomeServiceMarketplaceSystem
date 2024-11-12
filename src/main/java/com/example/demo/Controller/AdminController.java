package com.example.demo.Controller;

import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Admin.Service.*;
import com.example.demo.Model.Admin.Admin;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Model.User.User;

@Controller
@RequestMapping("/Admin")
public class AdminController {
	private final AdminService adminservice;
    private final IUserFactory userFactory;
    
    public AdminController(AdminService adminservice,IUserFactory userFactory) {
        this.adminservice = adminservice;
        this.userFactory=userFactory;
    }
    

    @GetMapping("/adminRegistrationForm")
    public String checkUser(Model model){
    	User admin = userFactory.createUser("Admin");
    	model.addAttribute("admin",admin);
    	return "AdminRegistration";
	}
    
   
    @PostMapping("/adminRegistrationSuccess")
    @ResponseBody
    public String createAdmin(Admin admin) throws SQLException {
    	int response= adminservice.addAdmin(admin);
    	if(response==0) {
    		return "Admin Already Exists";
    	}
    	else
    	{
    		return "Admin Created Successfully";
    	}
    }

}
