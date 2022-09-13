package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.Models.Loan;

public class LoanApplicationDTO {
    private long id;
    private double amount;
    private Integer payment;
    private String numberOriginAccount;

    public LoanApplicationDTO() {
    }

    public LoanApplicationDTO(long id, double amount, Integer payment, String numberOriginAccount) {
        this.id = id;
        this.amount = amount;
        this.payment = payment;
        this.numberOriginAccount = numberOriginAccount;
    }

    public long getId() {
        return id;
    }

    public double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public String getNumberOriginAccount() {
        return numberOriginAccount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public void setNumberOriginAccount(String numberOriginAccount) {
        this.numberOriginAccount = numberOriginAccount;
    }
}
