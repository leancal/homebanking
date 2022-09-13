package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Models.Loan;
import com.mindhub.homebanking.Services.LoanServices;
import com.mindhub.homebanking.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServicesImplements implements LoanServices {

    @Autowired
    private LoanRepository loanRepository;

    @Override
    public List<Loan> getLoans() {
        return loanRepository.findAll();
    }
    @Override
    public Loan getLoanByName(String name) {
        return loanRepository.findByName(name);
    }

    @Override
    public Loan getLoanById(long id)
    {
        return loanRepository.findById(id);
    }

    @Override
    public void saveLoan(Loan loan) {
        loanRepository.save(loan);
    }
}
