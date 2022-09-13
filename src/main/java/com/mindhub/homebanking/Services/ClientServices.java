package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.Models.Client;

import java.util.List;

public interface ClientServices { //la interfaz contiene los metodos que urilice el cliente
    public List<Client> getClients(); //metodo que devuelve una lista de clientes

    public Client getClientById(long id); //metodo que devuelve un cliente

    public Client findByEmail(String email); //metodo que devuelve un cliente

    public void saveClient(Client client); //metodo que guarda un cliente

}
