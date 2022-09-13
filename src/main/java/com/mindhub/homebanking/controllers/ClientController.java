package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.AccountType;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.configurations.WebAuthentication;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@RestController
public class ClientController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ClientServices clientServices;

    @GetMapping("/api/clients") //@RequestMaping es una peticion asociada a una ruta
    public List<ClientDTO> getClients() {
        return clientServices.getClients().stream().map(ClientDTO::new).collect(toList());
        //stream es una funcion que se encarga de recorrer una lista y devuelve una nueva lista con los datos que se le pasa
    }

    @GetMapping("/api/clients/{id}") //@RequestMaping es una peticion asociada a una ruta
    public ClientDTO getClient(@PathVariable long id) {
        return new ClientDTO(clientServices.getClientById(id));

    }

    @RestController //@RestController es una anotacion que se usa para indicar que una clase es un controlador REST.

    public class AppController {




        @PostMapping("/api/clients")//@PostMapping es una peticion asociada a una ruta

        public ResponseEntity<Object> register(

                @RequestParam String firstName, @RequestParam String lastName, //@RequestParam es una anotacion que se usa para indicar que una variable es un parametro de una peticion.

                @RequestParam String email, @RequestParam String password) {


            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);
            }

            if (clientServices.findByEmail(email) != null) {
                return new ResponseEntity<>("Email already in use", HttpStatus.FORBIDDEN);
            }


            Client newClient = new Client(firstName, lastName, email, passwordEncoder.encode(password));

            String randomNumber = "VIN-" + getRandomNumber(0, 99999999);
            String randomNumber2 = "VIN-" + getRandomNumber(0, 99999999);
            clientServices.saveClient(newClient);//guarda el cliente en la base de datos
            //accountServices.saveAccount(new Account(randomNumber, LocalDateTime.now(), 0.0, newClient, true));//crea una cuenta para el cliente
            accountServices.saveAccount(new Account(randomNumber, LocalDateTime.now(), 0.0, newClient, AccountType.SAVINGS, true));//crea una cuenta para el cliente
            accountServices.saveAccount(new Account(randomNumber2, LocalDateTime.now(), 0.0, newClient, AccountType.CURRENT, true));//crea una cuenta para el cliente


            return new ResponseEntity<>(HttpStatus.CREATED);

        }


    }

    @Autowired
    private WebAuthentication webAuthentication;

    @GetMapping("/api/clients/current")//@GetMapping es una peticion asociada a una ruta
    public ClientDTO getAll(Authentication authentication) {
        return new ClientDTO(clientServices.findByEmail(authentication.getName()));

    }

    public int getRandomNumber(int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }//funcion que devuelve un numero aleatorio entre un minimo y un maximo
}








