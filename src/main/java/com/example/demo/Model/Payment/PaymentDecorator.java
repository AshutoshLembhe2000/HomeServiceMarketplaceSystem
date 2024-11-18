package com.example.demo.Model.Payment;

public abstract class PaymentDecorator extends Payment {
	protected Payment decoratedPayment;

    public PaymentDecorator(Payment payment) {
        super(payment.getAmount(), payment.getStatus(), payment.getTimestamp());
        this.decoratedPayment = payment;
    }

    @Override
    public int processPayment() {
        return decoratedPayment.processPayment();
    }
}
