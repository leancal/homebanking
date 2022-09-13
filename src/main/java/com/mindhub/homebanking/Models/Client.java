package com.mindhub.homebanking.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class Client {

    @Id // clave primaria de la tabla cliente en la base de datos
    @GeneratedValue(strategy = GenerationType.AUTO)
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    private Set<Account> accounts = new HashSet<>(); //accounts es una propiedad de cliente, pero no es una tabla, es una coleccion de objetos de tipo account
                                                    //Set<Account> es una coleccion de objetos de tipo account


    private String nombre;
    private String apellido;
    private String email;

   @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans = new HashSet<>();

   @OneToMany(mappedBy = "client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    private String password;



    public Client() { } //linea 38 (tengo un metodo constructor) y 40 (tengo otro metodo constructor) con el mismo nombre que trabajan de manera difeneren segun los parametros que
                        //recibe el metodo. Esto es POLIMORFISMO.
    public Client(String nombre, String apellido, String email, String password ) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.password = password;
    }

    public long getId() {
        return id;
    }
    public String getNombre() {return nombre;}

    public void setNombre(String nombre) {this.nombre = nombre;}


    public String getApellido() {return apellido;}

    public void setApellido(String apellido) {this.apellido = apellido;}


    public String getEmail() {return email;}

    public void setEmail(String email) {this.email = email;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void addAccount(Account account) {
        account.setClient(this);
        accounts.add(account);
    }
    public String toString() {
        return nombre + " " + apellido;
    }
    @JsonIgnore
    public List<Loan> getLoans() {
        return clientLoans.stream().map(clientLoans -> clientLoans.getLoan()).collect(Collectors.toList());
    }
    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }


}
