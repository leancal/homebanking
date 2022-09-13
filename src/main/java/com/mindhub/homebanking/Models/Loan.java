package com.mindhub.homebanking.Models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


    private String name;
    private Double maxAmount;



    @ElementCollection
    @Column(name = "payments")
    private List<Integer> payments = new ArrayList<>();

    @OneToMany(mappedBy = "loan", fetch = FetchType.EAGER) //mappedBy = "loan" indica que la relacion es de uno a muchos y que la clase que se relaciona es la Loan
    private Set<ClientLoan> clientLoans = new HashSet<>();


    public Loan() {
    }

    public Loan(String name, Double maxAmount, List<Integer> payments ) {
        this.name = name;
        this.maxAmount = maxAmount;
        this.payments = payments;

    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(Double maxAmount) {
        this.maxAmount = maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public void setPayments(List<Integer> payments) {
        this.payments = payments;
    }


    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public List<Client> getClients() {
        return clientLoans.stream().map(clientLoans -> clientLoans.getClient()).collect(Collectors.toList());
    }


}
