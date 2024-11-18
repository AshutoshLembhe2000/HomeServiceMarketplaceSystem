package com.example.demo.Model.Payment;

public class DiscountDecorator extends PaymentDecorator {

    public DiscountDecorator(Payment payment) {
        super(payment);
    }

    @Override
    public int processPayment() {
        float discountedAmount = decoratedPayment.getAmount() * 0.95f; // 5% discount
        decoratedPayment.setAmount(discountedAmount);
        System.out.println("inside discount decorator, 5% Discount Applied");
        return decoratedPayment.processPayment();
    }
}
