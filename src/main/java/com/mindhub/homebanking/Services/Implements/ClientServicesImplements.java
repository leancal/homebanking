package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service//la anotacion @Service es una anotacion que se usa para indicar que una clase es un servicio. y un servicio es una clase que se encarga de realizar operaciones sobre los datos.
public class ClientServicesImplements implements ClientServices {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client getClientById(long id) {
        return clientRepository.findById(id).get();
    }
    @Override
    public Client findByEmail(String email) {
        return clientRepository.findByEmail(email);
    }

    @Override
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}


