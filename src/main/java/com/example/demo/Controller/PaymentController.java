package com.example.demo.Controller;

import com.example.demo.Model.Customer.Customer;
import com.example.demo.Model.Payment.Payment;
import com.example.demo.Model.User.IUserFactory;
import com.example.demo.Service.Customer.CustomerService;
import com.example.demo.Service.OTP.OTPService;
import com.example.demo.Service.Payment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private final CustomerService customerServices;
    private final OTPService otpService;

	public PaymentController(CustomerService customerServices, OTPService otpService) {
        this.customerServices = customerServices;
        this.otpService = otpService;
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


    // Handles GET request to show OTP form
    @GetMapping("/VerifyOTP")
    public String showOTPForm(@RequestParam("bookingId") String bookingId, Model model) {
        model.addAttribute("bookingId", bookingId);
        return "VerifyOTP"; // Thymeleaf template for OTP form
    }




    @PostMapping("/VerifyOTP")
    public RedirectView verifyOTP(@RequestParam("bookingId") String bookingId,
                                  @RequestParam("otpCode") String otpCode,
                                  RedirectAttributes redirectAttributes) {
        boolean isVerified = otpService.verifyOTP(otpCode, bookingId);
        if (isVerified) {
            otpService.updateBookingStatusToCompleted(bookingId);

            redirectAttributes.addFlashAttribute("message", "OTP Confirmed Successfully!");
            return new RedirectView("/ServiceProvider/ServiceProviderWelcomeScreen");
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid OTP. Please try again.");
            return new RedirectView("/ServiceProvider/VerifyOTP?bookingId=" + bookingId);
        }
    }
}
