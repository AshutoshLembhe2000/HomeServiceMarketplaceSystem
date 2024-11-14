package com.example.demo.Controller;

import com.example.demo.Model.Booking.ServiceProviderBookingDTO;
import com.example.demo.GlobalContext;
import com.example.demo.Model.SearchService.SearchService;
import com.example.demo.Model.ServiceProvider.ServiceProvider;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Model.User.User;
import com.example.demo.ServiceProvider.Service.ServiceProviderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;
import java.util.List;
import java.sql.SQLException;

@Controller
@RequestMapping("/ServiceProvider")
public class ServiceProvider_Controller {

    private final ServiceProviderService serviceproviderservice;
    private final IUserFactory userFactory;
    
	private  final GlobalContext globalcontext; 

    public ServiceProvider_Controller(ServiceProviderService serviceproviderservice, IUserFactory userFactory,GlobalContext globalcontext) {
        this.serviceproviderservice = serviceproviderservice;
        this.userFactory = userFactory;
        this.globalcontext=globalcontext;
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
    public RedirectView createServiceProvider(ServiceProvider serviceProvider) throws SQLException {
        int response = serviceproviderservice.VerifyifServiceProviderExist(serviceProvider);
        if (response == 0) {
            return new RedirectView("/ServiceProvider/ServiceProviderLoginForm?error=UserAlreadyExists");
        } else {
            return new RedirectView("/ServiceProvider/ServiceProviderLoginForm");
        }
    }

    @GetMapping("/ViewBooking")
    public String viewBooking(Model model) {
        List<ServiceProviderBookingDTO> bookings = serviceproviderservice.getBookedServices();
        model.addAttribute("ServiceProviderBookingDTO", bookings);
        return "ServiceProviderBookedServices";
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
    public RedirectView loginServiceProvider(@RequestParam String email, @RequestParam String password,RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = serviceproviderservice.validateLogin(email, password);
        if (isAuthenticated) {
        	return new RedirectView("ServiceProviderWelcomeScreen");
            //return "Login Successful";
//            model.addAttribute("email", email);
//            return "ServiceProviderDashboard";
        } else {
            //return "Invalid email or password";
        	 redirectAttributes.addFlashAttribute("error", "Invalid email or password");
             return new RedirectView("ServiceProviderLoginForm");
        }
    }

    @GetMapping("/ServiceProviderWelcomeScreen")
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
    	int s = serviceproviderservice.verifyServiceAddition(SearchService);
    	System.out.print("Done dona Done");
    	return "sswfds";
    }

    @GetMapping("/ListServices")
    //@ResponseBody
    public String listServices(Model model){
    	System.out.println(globalcontext.getServiceProviderId());
    	model.addAttribute("SearchService",serviceproviderservice.getAllServiceProviderServices());
    	return "ListServices";
    }

}
