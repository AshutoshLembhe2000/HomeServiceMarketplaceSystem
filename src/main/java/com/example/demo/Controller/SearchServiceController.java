package com.example.demo.Controller;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Model.Customer.Customer;
import com.example.demo.Model.SearchService.SearchService;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Model.User.User;
import com.example.demo.Service.SearchServiceService;
import com.example.demo.Service.ServiceProvider_Service;

@Controller
@RequestMapping("/serviceProvider")
public class SearchServiceController {

	
   //private  final SearchService searchService;
    private final SearchServiceService searchServiceService;
    
    public SearchServiceController(SearchServiceService searchServiceService) {
        this.searchServiceService = searchServiceService;
       // this.searchService=searchService;
    }
    

    @GetMapping("/serviceProviderWelcomeScreen")
    public String serviceProviderForm(Model model){
    	return "ServiceProviderWelcomeScreen";
	}
    
    @GetMapping("/addServiceForm")
    //@ResponseBody
    public String addServiceForm(Model model){
    	model.addAttribute("SearchService",new SearchService());
    	return "addServiceForm";
    }
    
    @PostMapping("/addServiceItem")
    @ResponseBody
    public String addServiceItem(SearchService SearchService) throws SQLException {
    	
    	System.out.print("Done dona Done");
    	return "sswfds";
    }

}
