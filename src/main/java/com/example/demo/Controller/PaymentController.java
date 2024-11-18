package com.example.demo.Controller;

import com.example.demo.Model.Customer.Customer;
import com.example.demo.Model.Payment.Payment;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Service.Customer.CustomerService;
import com.example.demo.Service.Payment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private final CustomerService customerServices;
	public PaymentController(CustomerService customerServices) {
        this.customerServices = customerServices;
    }
	
	@Autowired
	private CustomerController customerControllergetter;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/makePayment")
    public String showPaymentPage(@RequestParam String serviceId,Model model) {
    	model.addAttribute("serviceId",serviceId);
        model.addAttribute("payment", new Payment());
        return "paymentForm"; // Thymeleaf template for payment form
    }

    @PostMapping("/processPayment")
    public String processPayment(@ModelAttribute Payment payment,
                                 @RequestParam String serviceId,
                                 Model model) {
    	String cusName=customerControllergetter.getGlobalCustomername();
    	Customer customer=customerServices.getCustomerByName(cusName);
    	int customerId=customer.getId();
        String message = paymentService.processFinalPayment(payment, customerId, serviceId);
        model.addAttribute("message", message);
        return "paymentResult"; // Thymeleaf template to display the result
    }
}
