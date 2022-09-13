package com.mindhub.homebanking.Models;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.GenericGenerators;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id")
    private Account account;

    private String cardHolder;
    private String number;
    private int cvv;
    private LocalDateTime thruDate;
    private LocalDateTime fromDate;

    private CardType cardType;
    private CardColor cardColor;

    private boolean active;

    public Card() {//linea 29 (tengo un metodo constructor) y 33 (tengo otro metodo constructor) con el mismo nombre que trabajan de manera difeneren segun los parametros que
                    //recibe el metodo. Esto es POLIMORFISMO.
    }

    public Card(Client client, String cardHolder, String number, int cvv, LocalDateTime thruDate, LocalDateTime fromDate, CardType cardType, CardColor cardColor, boolean active) {
        this.client = client;
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.active = active;
    }

    public Card(Client client, Account account, String cardHolder, String number, int cvv, LocalDateTime thruDate, LocalDateTime fromDate, CardType cardType, CardColor cardColor, boolean active) {
        this.client = client;
        this.account = account;
        this.cardHolder = cardHolder;
        this.number = number;
        this.cvv = cvv;
        this.thruDate = thruDate;
        this.fromDate = fromDate;
        this.cardType = cardType;
        this.cardColor = cardColor;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public LocalDateTime getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDateTime thruDate) {
        this.thruDate = thruDate;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
