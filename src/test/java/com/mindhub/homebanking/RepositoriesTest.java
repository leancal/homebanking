package com.mindhub.homebanking;

import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import java.util.List;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;




@SpringBootTest
@AutoConfigureTestDatabase(replace = NONE)
public class RepositoriesTest {
    @Autowired
    LoanRepository loanRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TransactionRepository transactionRepository;

    @Test
    public void existLoans(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans,is(not(empty())));
    }

    @Test
    public void existPersonalLoan(){
        List<Loan> loans = loanRepository.findAll();
        assertThat(loans, hasItem(hasProperty("name", is("Personal"))));
    }

    @Test
    public void existAccounts(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, is(not(empty())));
    }

    @Test
    public void existAccount(){
        List<Account> accounts = accountRepository.findAll();
        assertThat(accounts, hasItem(hasProperty("number", startsWith("VIN"))));
    }

    @Test
    public void existClients(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, is(not(empty())));
    }

    @Test
    public void existClient(){
        List<Client> clients = clientRepository.findAll();
        assertThat(clients, hasItem(hasProperty("nombre", is("Melba"))));
    }

    @Test
    public void existCards(){
        List<Card> cards = cardRepository.findAll();
        assertThat(cards, is(not(empty())));
    }

//    @Test
//    public void existDebitCard(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, hasItem(hasProperty("number", is("1234-5678-9126-5325"))));
//    }
//
//
//    @Test
//    public void existCreditCard(){
//        List<Card> cards = cardRepository.findAll();
//        assertThat(cards, hasItem(hasProperty("number", is("9876-5432-1515-4951"))));
//    }

    @Test
    public void existTransactions(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, is(not(empty())));
    }

    @Test
    public void existTransaction(){
        List<Transaction> transactions = transactionRepository.findAll();
        assertThat(transactions, hasItem(hasProperty("description", is("Services"))));
    }

}
