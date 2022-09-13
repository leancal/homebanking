package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.AccountType;
import com.mindhub.homebanking.Models.Transaction;


import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {
    private String number;
    private LocalDateTime creationDate;
    private double balance;
    private long id;

    private Set<TransactionDTO> transactions;
    private boolean active;
    private AccountType accountType;

    public AccountDTO() {}

    public AccountDTO(Account account) {
        this.number = account.getNumber();//account.getNumber() es el nombre de la variable que esta en el mismo objeto y getNumber es el nombre de la variable de la clase Account.
        this.creationDate = account.getCreationDate();//
        this.balance = account.getBalance();
        this.id = account.getId();
        this.transactions = account.getTransactions().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
        this.active = account.isActive();
        this.accountType = account.getAccountType();

    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public long getId() {
        return id;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }

    public boolean isAccountState() {
        return active;
    }

    public AccountType getAccountType() {
        return accountType;
    }
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
