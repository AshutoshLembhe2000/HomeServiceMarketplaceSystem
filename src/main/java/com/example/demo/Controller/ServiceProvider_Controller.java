package com.example.demo.Controller;

import com.example.demo.Model.Booking.ServiceProviderBookingDTO;
import com.example.demo.Model.Customer.Customer;
import com.example.demo.Service.ServiceProvider.GlobalContext;
import com.example.demo.Model.SearchServices.SearchService;
import com.example.demo.Model.ServiceProvider.ServiceProvider;
import com.example.demo.Model.ServiceProvider.ServiceProviderStateManager;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Model.User.User;
import com.example.demo.Service.Customer.CustomerService;
import com.example.demo.Service.ServiceProvider.ServiceProviderService;

import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    private final ServiceProviderStateManager serviceProviderStateManager;
    
    private final CustomerService customerService;
    
    private final IUserFactory userFactory;
    
	private  final GlobalContext globalcontext; 

    public ServiceProvider_Controller(ServiceProviderService serviceproviderservice, IUserFactory userFactory,GlobalContext globalcontext, ServiceProviderStateManager serviceProviderStateManager, CustomerService customerService) {
        this.serviceproviderservice = serviceproviderservice;
		this.customerService = customerService;
        this.userFactory = userFactory;
        this.globalcontext=globalcontext;
        this.serviceProviderStateManager=serviceProviderStateManager;
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
        if (s == -1) {
            return "You can only add one service per ServiceProvider."; // Or redirect to an appropriate page with a message
        }
    	return "Service Added Sucesfully!";
    }

    @GetMapping("/ViewBooking")
    public String viewBooking(Model model) {
        List<ServiceProviderBookingDTO> bookings = serviceproviderservice.getBookedServices();
        model.addAttribute("ServiceProviderBookingDTO", bookings);
        return "ServiceProviderBookedServices";
    }



    @GetMapping("/PastBookings")
    public String viewPastBookings(Model model) {
        List<ServiceProviderBookingDTO> pastBookings = serviceproviderservice.getPastBookings();
        model.addAttribute("pastBookings", pastBookings);
        return "PastBooking";
    }

    @GetMapping("/ListServices")
    //@ResponseBody
    public String listServices(Model model){
    	System.out.println(globalcontext.getServiceProviderId());
    	model.addAttribute("SearchService",serviceproviderservice.getAllServiceProviderServices());
    	return "ListServices";
    }
    
    @PostMapping("/DeleteService/{providerName}/{serviceId}")
    public RedirectView deleteService(@PathVariable("providerName") String providerName,@PathVariable("serviceId") String ServiceId, Model model) {
    	int res = serviceproviderservice.deleteSelectedService(providerName,ServiceId);
        	return new RedirectView("/ServiceProvider/ListServices");
    }

    // Endpoint to modify a service
    @PostMapping("/ModifyService/{providerName}/{serviceId}")
    public String modifyService(@PathVariable("providerName") String providerName,@PathVariable("serviceId") String serviceId , Model model) {
        // Fetch the service details and populate the modification form
        List<SearchService> res = serviceproviderservice.getServiceForModify(providerName,serviceId);
        //SearchService service = res.get(0);
        model.addAttribute("SearchService", res.get(0));
        System.out.println(res.get(0));
        return "ModifyService"; // Name of the Thymeleaf template for modification
    }
    
    @PostMapping("/SaveModifiedService")
    public RedirectView saveModifiedService(@ModelAttribute SearchService searchService, Model model) {
    	serviceproviderservice.updateService(searchService);
        return new RedirectView("/ServiceProvider/ListServices");
    }

    @PostMapping("/UpdateBookingStatus")
    public RedirectView updateBookingStatus(@RequestParam String bookingId, @RequestParam String status, RedirectAttributes redirectAttributes) {
        try {
            serviceproviderservice.updateBookingStatus(bookingId, status);

            List<ServiceProvider> res = serviceproviderservice.getServiceProviderByServiceId();
            List<Customer> customers = customerService.getAllCustomersByCity(res.get(0).getCity());

            serviceProviderStateManager.changeState(res.get(0), status, customers);

            redirectAttributes.addFlashAttribute("message", "Booking status updated successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Failed to update booking status.");
        }
        return new RedirectView("/ServiceProvider/ViewBooking");
    }









}