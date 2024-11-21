package com.example.demo.Model.Payment;

public class Payment extends BasePayment {
	
    public Payment(float amount, String status) {
        super(amount, status);
    }

    @Override
    public int processPayment() {
        this.setStatus("COMPLETED");
        return (int) getAmount();
    }
}