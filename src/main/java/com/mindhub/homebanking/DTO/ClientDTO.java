//el DTO (data transfer objets) evita que me aparezcan solamente los datos del cliente, sin sus cuentas y sus detalles

package com.mindhub.homebanking.DTO;


import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.ClientLoan;

import java.util.Set;

import java.util.stream.Collectors;



public class ClientDTO {
    private String nombre;
    private String apellido;
    private String email;
    private Set<AccountDTO> accounts;
    private long id;
    private Set<ClientLoanDTO> loans;
    private Set<CardDTO> cards;


    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        this.nombre = client.getNombre();
        this.apellido = client.getApellido();
        this.email = client.getEmail();
        this.id = client.getId();
        this.accounts = client.getAccounts().stream().map(AccountDTO::new).collect(Collectors.toSet());
        this.loans = client.getClientLoans().stream().map(ClientLoanDTO::new).collect(Collectors.toSet());
        this.cards = client.getCards().stream().map(CardDTO::new).collect(Collectors.toSet());
    }

    public String getNombre() {
        return nombre;
    }



    public String getApellido() {
        return apellido;
    }



    public String getEmail() {
        return email;
    }



    public Set<AccountDTO> getAccounts() {
        return accounts;
    }



    public long getId() {
        return id;
    }



    public Set<ClientLoanDTO> getLoans() {
        return loans;
    }

    public Set<CardDTO> getCards() {
        return cards;
    }
}
