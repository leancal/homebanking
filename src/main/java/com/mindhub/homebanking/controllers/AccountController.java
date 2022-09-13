package com.mindhub.homebanking.controllers;
import com.mindhub.homebanking.DTO.AccountDTO;
import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Models.AccountType;
import com.mindhub.homebanking.Models.Client;
import com.mindhub.homebanking.Models.Transaction;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.mindhub.homebanking.Models.TransactionType.CREDIT;
import static com.mindhub.homebanking.Models.TransactionType.DEBIT;
import static java.util.stream.Collectors.toList;

@RestController
public class AccountController {
    @Autowired
    private AccountServices accountServices;

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/api/accounts")
//microservicio que devuelve una lista de cuentas. lo que generamos en el DTO lo instanciamos y evitamos la recursividad
    public List<AccountDTO> getAccounts() { //serv let es un metodo que se ejecuta cuando se hace una peticion a un servidor
        return accountServices.getAccounts().stream().map(account -> new AccountDTO(account)).collect(toList());
    }

    @GetMapping("/api/accounts/{id}")
    public AccountDTO getAccount(@PathVariable long id) {
        return new AccountDTO(accountServices.getAccountById(id));//null pointer exception si no encuentra la cuenta
    }                                                                                                   //si encuentra la cuenta, la instancia en el DTO

    @PostMapping("/api/clients/current/accounts")//@RequestMapping es una peticion asociada a una ruta
    public ResponseEntity<Object> createNewCurrentAccount(@RequestParam String accountType, Authentication authentication) {

        String randomNumber = "VIN-" + getRandomNumber(10000000, 99999999); //llamamos al metodo getRandomNumber para generar un numero aleatorio y con string concatenamos el numero con el prefijo VIN-

        Client newCurrentClient = clientServices.findByEmail(authentication.getName());//obtenemos el cliente que esta autenticado, variable de tipo client que se encuentra en la clase ClientRepository

        if(accountType.isEmpty()){
            return new ResponseEntity<>("Missing account type", HttpStatus.FORBIDDEN);
        }
//        if (newCurrentClient.getAccounts().size() >= 3) {
//            return new ResponseEntity<>("You can´t create more accounts", HttpStatus.FORBIDDEN);
//        }

        if (newCurrentClient.getAccounts().stream().filter(account -> account.isActive() == true).count() >= 3){
            return new ResponseEntity<>("Just 3 accounts", HttpStatus.FORBIDDEN);

         }


    Account savingAccount = new Account(randomNumber, LocalDateTime.now(), 0.0, newCurrentClient, AccountType.valueOf(accountType), true);

            accountServices.saveAccount(savingAccount);


            return new ResponseEntity<>("Account created",HttpStatus.CREATED);
}





    public int getRandomNumber (int min, int max) {
        return (int) (Math.random() * (max - min) + min);
    }

    @PatchMapping("/api/clients/current/accounts/accountState")
        public ResponseEntity<Object> updateAccountState (Authentication authentication, @RequestParam  String number) {

        Client clientAccount = clientServices.findByEmail(authentication.getName());
        Account accountNumber = accountServices.findByNumber(number);

        if(clientAccount == null){
            return new ResponseEntity<>("Client not found", HttpStatus.NOT_FOUND);
        }
        if (number.isEmpty()){
            return new ResponseEntity<>("Account number is empty", HttpStatus.BAD_REQUEST);
        }
        if (!clientAccount.getAccounts().contains(accountNumber)) {
            return new ResponseEntity<>("You can't update this account", HttpStatus.FORBIDDEN);
        }

        if (accountNumber.getBalance() > 0) {
            return new ResponseEntity<>("Account balance is bigger than $0", HttpStatus.FORBIDDEN);
        }



        accountNumber.setActive(false);
        accountServices.saveAccount(accountNumber);
        return new ResponseEntity<>("Account updated", HttpStatus.OK);
    }
    @PatchMapping("/clients/current/accounts/delete/{id}")
    public ResponseEntity<Object> smartDelete(@PathVariable Long id) {
        Account account = accountRepository.findById(id).orElse(null);


        if (account.getBalance() > 0) {
            return new ResponseEntity<>("Account balance is bigger than $0", HttpStatus.FORBIDDEN);
        }

        account.setActive(false);
        accountRepository.save(account);

        return new ResponseEntity<>("Account updated", HttpStatus.OK);
    }

}

