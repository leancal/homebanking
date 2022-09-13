package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.Models.Transaction;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

public interface TransactionServices {

    public void saveTransaction(Transaction transaction);

    public Set<Transaction> getTransactionByDate (LocalDateTime dateFrom, LocalDateTime dateTo);
}
