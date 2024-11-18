package com.example.demo.Model.Payment;

public class Payment {
    private String bookingId;  // Will be set after successful payment
    private float amount;
    private String status;
    private String timestamp;

    public Payment() {
    }

    public Payment(float amount, String status, String timestamp) {
        this.amount = amount;
        this.status = status;
        this.timestamp = timestamp;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int processPayment() {
        this.status = "COMPLETED";
        return (int) amount;
    }
}
