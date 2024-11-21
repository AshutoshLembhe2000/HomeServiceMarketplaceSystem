package com.example.demo.Model.Payment;

public abstract class BasePayment {
    private String bookingId;
    private float amount;
    private String status;
    private String timestamp;

    public BasePayment(float amount, String status) {
        this.amount = amount;
        this.status = status;
    }

    // Abstract method for processing payment
    public abstract int processPayment();

    // Common methods for all payments
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
}
