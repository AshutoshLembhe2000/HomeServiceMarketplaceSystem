package com.example.demo.Service.Payment;

import com.example.demo.Model.Payment.*;
import com.example.demo.DAORepo.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    private int getcount = 0;
    private String finalmessage="";
	public String processFinalPayment(Payment payment, int customerId, String serviceId) {
        // Check booking count for customer to decide on discounts
    	Map<String,Object> count;
    	
    	List<Map<String, Object>> bookingCount = paymentRepository.getBookingCountByCustomerId(customerId);
    	if (!bookingCount.isEmpty())
    	{
	        count=bookingCount.get(0);
	        getcount=((Number) count.get("COUNT(*)")).intValue();
    	}
        System.out.println(getcount);
        
        if (getcount > 4) {
            payment = new LoyaltyPointsDecorator(payment);
            
        } else if (getcount >=2 && getcount<=4) {
        	System.out.println("inside discount decorator");
            payment = new DiscountDecorator(payment);
        }

        // Process payment
        int finalamount = payment.processPayment();

        // Generate a timestamp for the payment
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        payment.setTimestamp(timestamp);

        // Create a new booking after successful payment
        String bookingId = paymentRepository.createBooking(customerId, serviceId);
        payment.setBookingId(bookingId);

        // Save payment in the database
        paymentRepository.savePayment(bookingId, finalamount, "COMPLETED", payment.getTimestamp());

        // Update booking payment status
        paymentRepository.updateBookingPaymentStatus(bookingId);
        
        //Letting customer know if they have got any discount or not
        if (getcount > 4) {
            finalmessage="Final amount after 10% loyalty discount: " + finalamount + " Booking ID: " + bookingId;
        } else if (getcount >=2 && 4<=getcount) {
        	finalmessage="Final amount after 5% discount: " + finalamount + " Booking ID: " + bookingId;
        }
        else
        {
        	finalmessage="Final amount: " + finalamount + " Booking ID: " + bookingId;
        }
        return finalmessage;
    }
}
