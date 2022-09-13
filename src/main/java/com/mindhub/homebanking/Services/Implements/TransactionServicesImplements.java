package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Services.TransactionServices;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Service
public class TransactionServicesImplements implements TransactionServices {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    @Override
    public Set<Transaction> getTransactionByDate (LocalDateTime dateFrom, LocalDateTime dateTo) {
        return transactionRepository.findByDateBetween(dateFrom, dateTo);
    }
}
