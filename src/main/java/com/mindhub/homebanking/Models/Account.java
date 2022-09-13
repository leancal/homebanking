package com.mindhub.homebanking.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Account{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")//generator = "native" la generacion del id va a estar en manos de la base de datos.
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    private Set<Transaction> transactions = new HashSet<>();//new HashSet<> indicamos  que nos guarde un espacio en la base de datos
                                                            //set de transacciones es una lista desordenada que no repite datos
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;//propiedad de tipo client, objeto definido

    @OneToMany(mappedBy="account", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private String number;
    private LocalDateTime creationDate;
    private Double balance;
    private boolean active = true;
    private AccountType accountType;


    public Account() {
    }

    public Account(String number, LocalDateTime creationDate, Double balance, Client client, AccountType accountType, boolean active) {
        this.number = number;
        this.creationDate = creationDate;//this. hace referencia a la variable que esta en el mismo objeto. creationDate es el nombre de la variable.
        this.balance = balance;
        this.client = client;
        this.accountType = accountType;
        this.active = active;

    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this); //this hace referencia al objeto que quiero crear con esta clase
        transactions.add(transaction);//transactions es el nombre de la propiedad de tipo set que esta en el objeto account
    }


    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
