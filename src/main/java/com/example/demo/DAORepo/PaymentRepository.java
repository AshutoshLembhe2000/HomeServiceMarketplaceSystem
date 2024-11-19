package com.example.demo.DAORepo;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PaymentRepository {

    @Autowired
    private JdbcTemplate queryTemplate;

    public void savePayment(String bookingId, float amount, String status, String timestamp) {
        String sql = "INSERT INTO payment (booking_id, amount, status, timestamp) VALUES (?, ?, ?, ?)";
        queryTemplate.update(sql, bookingId, amount, status, timestamp);
    }

    public List<Map<String, Object>> getBookingCountByCustomerId(int customerId) {
        String sql = "SELECT COUNT(*) FROM booking WHERE customer_id = ?";
        return queryTemplate.queryForList(sql, new Object[] {customerId});
    }

    public String createBooking(int customerId, String serviceId) {
        String bookingId = "BOOK" + System.currentTimeMillis(); // Generate unique booking ID
        String sql = "INSERT INTO booking (booking_id, customer_id, service_id, booking_date, status, payment_status) VALUES (?, ?, ?, NOW(), ?, ?)";
        queryTemplate.update(sql, bookingId, customerId, serviceId, "PENDING", "UNPAID");
        return bookingId;
    }

    public void updateBookingPaymentStatus(String bookingId) {
        String sql = "UPDATE booking SET payment_status = 'PAID' WHERE booking_id = ?";
        queryTemplate.update(sql, bookingId);
    }

}
