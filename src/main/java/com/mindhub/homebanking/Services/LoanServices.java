package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.Models.Loan;

import java.util.List;

public interface LoanServices {

    public List<Loan> getLoans();

    public Loan getLoanByName(String name);

    public Loan getLoanById(long id);

    public void saveLoan(Loan loan);
}
