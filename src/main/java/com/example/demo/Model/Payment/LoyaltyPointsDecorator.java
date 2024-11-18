package com.example.demo.Model.Payment;

public class LoyaltyPointsDecorator extends PaymentDecorator {

    public LoyaltyPointsDecorator(Payment payment) {
        super(payment);
    }

    @Override
    public int processPayment() {
        float loyaltyDiscountedAmount = decoratedPayment.getAmount() * 0.90f; // 10% discount
        decoratedPayment.setAmount(loyaltyDiscountedAmount);
        System.out.println("inside loyalty decorator, 10% Loyalty Discount Applied.");
        return decoratedPayment.processPayment();
    }
}
