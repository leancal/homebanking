package com.mindhub.homebanking.DTO;

import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Models.Loan;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientLoanDTO {

    private long id;
    private long loan_id;
    private Double amount;
    private Integer payment;
    private String name;





    public ClientLoanDTO() {
    }

    public ClientLoanDTO(ClientLoan clientLoan) {
        this.id = clientLoan.getId();
        this.amount = clientLoan.getAmount();
        this.payment = clientLoan.getPayment();
        this.loan_id = clientLoan.getLoan().getId();
        this.name = clientLoan.getLoan().getName();


    }

    public long getId() {
        return id;
    }

    public long getLoan_id() {
        return loan_id;
    }

    public Double getAmount() {
        return amount;
    }

    public Integer getPayment() {
        return payment;
    }

    public String getName() {
        return name;
    }
}
