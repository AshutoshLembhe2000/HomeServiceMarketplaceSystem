package com.example.demo.Controller;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Model.User.User;
import com.example.demo.Service.Customer.CustomerService;
import com.example.demo.Service.SearchService.SearchServiceService;
import com.example.demo.Model.Booking.ServiceProviderBookingDTO;
import com.example.demo.Model.Customer.Customer;
import com.example.demo.Model.SearchServices.SearchService;

@Controller
@RequestMapping("/Customer")
public class CustomerController {
	
	public String globalCustomername;
	
	public String getGlobalCustomername() {
		return globalCustomername;
	}

	public void setGlobalCustomername(String globalCustomername) {
		this.globalCustomername = globalCustomername;
	}
	
    private final CustomerService customerService;
    private final IUserFactory userFactory;
    @Autowired 
    private SearchServiceService searchServiceService;
    
    @Autowired
    public CustomerController(CustomerService customerService,IUserFactory userFactory) {
        this.customerService = customerService;
        this.userFactory=userFactory;
    }
    
    /*---------------Customer Registration---------------------------*/
    @GetMapping("/customerRegistrationForm")
    public String checkUser(Model model){
    	User customer = userFactory.createUser("Customer");
    	model.addAttribute("customer",customer);
    	return "CustomerRegistration";
	}
    
   
    @PostMapping("/customerRegistrationSuccess")
    @ResponseBody
    public String createCustomer(Customer customer) throws SQLException {
    	int response= customerService.addCustomer(customer);
    	if(response==0) {
    		return "User Already Exists go to login";
    	}
    	else
    	{
    		return "User Created Successfully";
    	}
    }
    /*---------------Customer Registration---------------------------*/
    
    /*---------------Customer Login---------------------------*/
    @GetMapping("/customerLogin")
    public String customerLogin(Model model) {
    	return "CustomerLogin";
    }
    
    @PostMapping("/customerLoginSuccess")
    //@ResponseBody
    public String authenticateCustomer(@RequestParam("username") String name, @RequestParam String password,Model model) {
    	int response=customerService.checkCustomerLogin(name,password);
    	if(response==1) {
    		this.setGlobalCustomername(name);
            return "CustomerWelcomeScreen";
    	}
    	else {
    		//model.addAttribute("error", "Customer does not exist, please create a new user and login again."); 
    		User customer = userFactory.createUser("Customer");
        	model.addAttribute("customer",customer);
        	return "CustomerRegistration";
    	}
    }
    /*---------------Customer Login---------------------------*/
    
    /*---------------Search Service---------------------------*/
    @GetMapping("/allservices")
    public String getAllservices(Model model) {
    	List<SearchService> services = searchServiceService.getAllServices();
		Customer customer=customerService.getCustomerByName(this.getGlobalCustomername());
		this.setGlobalCustomername(customer.getName());
		model.addAttribute("customer",customer);
        model.addAttribute("services", services);
        return "all-services";
    }
    
    @GetMapping("/filterservice")
    public String listFilteredServices(
            @RequestParam(required = false) String skill,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String status,
            Model model) {

        // Fetch the filtered services for the customer
        List<SearchService> services = customerService.searchService(skill, city, status);
        Customer customer=customerService.getCustomerByName(this.getGlobalCustomername());
		model.addAttribute("customer",customer);
        model.addAttribute("services", services);
        return "filter-services"; // Refers to the Thymeleaf view
    }
    /*---------------Search Service---------------------------*/
    
    @GetMapping("/book")
    //@ResponseBody
    public String bookservice(@RequestParam String serviceId, @RequestParam String customerCity, @RequestParam String serviceCity,
    		@RequestParam String serviceStatus,@RequestParam String servicePrice, @RequestParam String serviceSkill, 
    		@RequestParam String serviceRating, @RequestParam String serviceProviderName, @RequestParam String serviceCategory,Model model) {
    	String response=customerService.checkServiceParameters(customerCity,serviceCity,serviceStatus);
    	if(response=="success")
    	{
    		model.addAttribute("serviceId",serviceId);
    		model.addAttribute("customerCity", customerCity); 
    		model.addAttribute("serviceCity", serviceCity); 
    		model.addAttribute("serviceStatus", serviceStatus);
    		model.addAttribute("servicePrice", servicePrice); 
    		model.addAttribute("serviceSkill", serviceSkill); 
    		model.addAttribute("serviceRating", serviceRating);
    		model.addAttribute("serviceProviderName", serviceProviderName);
    		model.addAttribute("serviceCategory", serviceCategory);
    		return "bookingscreen";
    	}
    	else
    	{
    		return response;
    	}
    }
    
    @GetMapping("/CustomerCurrentBookedServices")
    public String getCustomerCurrentBooking(Model model) {
    	List<ServiceProviderBookingDTO> booking = customerService.getAllCurrentBooking(this.getGlobalCustomername());
		model.addAttribute("ServiceProviderBookingDTO",booking);
        return "CustomerCurrentBookedServices";
    }
    
    @PostMapping("/CancelBooking/{bookingId}/{bookingStatus}")
    @ResponseBody
    public RedirectView deleteService(@PathVariable("bookingId") String bookingId,@PathVariable("bookingStatus") String bookingStatus,RedirectAttributes redirectAttributes) {
    	int res = customerService.cancelSelectedBooking(bookingId,bookingStatus);
    	if(res!=0) {
    		redirectAttributes.addFlashAttribute("message", "Booking Status Rejected Successfully!!");
    		return new RedirectView("/Customer/CustomerCurrentBookedServices");
    	}
    	redirectAttributes.addFlashAttribute("message", "Booking status cannot be rejected");
    	return new RedirectView("/Customer/CustomerCurrentBookedServices");
    }
    
}
