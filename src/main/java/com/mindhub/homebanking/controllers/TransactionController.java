package com.mindhub.homebanking.controllers;


import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mindhub.homebanking.DTO.PaymentsDTO;
import com.mindhub.homebanking.DTO.PdfDTO;
import com.mindhub.homebanking.DTO.TransactionDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.Services.CardServices;
import com.mindhub.homebanking.Services.ClientServices;
import com.mindhub.homebanking.Services.TransactionServices;
import com.mindhub.homebanking.Utils.PDFMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Stream;

import static com.mindhub.homebanking.Models.TransactionType.CREDIT;
import static com.mindhub.homebanking.Models.TransactionType.DEBIT;


@RestController
public class TransactionController {

    @Autowired
    private ClientServices clientServices;

    @Autowired
    private AccountServices accountServices;

    @Autowired
    private TransactionServices transactionServices;

    @Autowired
    private CardServices cardServices;

    @Transactional

    @PostMapping("/api/transactions")
    public ResponseEntity<Object> newTransaction(@RequestParam Double amount,
                                                 @RequestParam String description,
                                                 @RequestParam String accountOrigin,
                                                 @RequestParam String accountDestiny,
                                                 Authentication authentication) {

        Client clientAuthentication = clientServices.findByEmail(authentication.getName());
        Account newOriginAccount = accountServices.findByNumber(accountOrigin);
        Account newDestinyAccount = accountServices.findByNumber(accountDestiny);

        if (clientAuthentication == null)
            return new ResponseEntity<>("error", HttpStatus.FORBIDDEN);

        if (amount <= 0) {
            return new ResponseEntity<>("Invalid amount", HttpStatus.FORBIDDEN);
        }

        if (description.isEmpty() || accountOrigin.isEmpty() || accountDestiny.isEmpty()) {
            return new ResponseEntity<>("Missing data", HttpStatus.FORBIDDEN);//FORBIDDEN es un codigo de error que indica que la peticion no se puede realizar.
        }

        if (accountOrigin.equals(accountDestiny)) {
            return new ResponseEntity<>("Account origin and destiny are the same", HttpStatus.FORBIDDEN);
        }

        if (newOriginAccount == null) {
            return new ResponseEntity<>("Origin account doesn't exist", HttpStatus.FORBIDDEN);
        }
        if (newDestinyAccount == null) {
            return new ResponseEntity<>("Destiny account doesn't exist", HttpStatus.FORBIDDEN);
        }

        if (newOriginAccount.getBalance() < amount) {
            return new ResponseEntity<>("Not enough money", HttpStatus.FORBIDDEN);
        }

        Transaction debitTransaction = new Transaction(newOriginAccount, DEBIT, amount, description + " go to " + newDestinyAccount.getNumber(), LocalDateTime.now());

        Transaction creditTransaction = new Transaction(newDestinyAccount, CREDIT, amount, description + " from " + newOriginAccount.getNumber(), LocalDateTime.now());

        transactionServices.saveTransaction(debitTransaction);//save es un metodo que se usa para guardar un objeto en la base de datos.
        transactionServices.saveTransaction(creditTransaction);

        newOriginAccount.setBalance(newOriginAccount.getBalance() - amount);
        newDestinyAccount.setBalance(newDestinyAccount.getBalance() + amount);

        accountServices.saveAccount(newOriginAccount);
        accountServices.saveAccount(newDestinyAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/api/transactions/pdf")
    public ResponseEntity<Object> getPdfTransactions(@RequestBody PdfDTO pdfDTO,
                                                     Authentication authentication) throws Exception {

        Client clientAuthentication = clientServices.findByEmail(authentication.getName());
        Account account = accountServices.findByNumber(pdfDTO.getAccountNumber());

        if (clientAuthentication == null)
            return new ResponseEntity<>("error", HttpStatus.FORBIDDEN);

        if (account == null)
            return new ResponseEntity<>("Account doesn't exist", HttpStatus.FORBIDDEN);

        if ( pdfDTO.getDateFrom() == null || pdfDTO.getDateTo() == null)
            return new ResponseEntity<>("Invalid dates", HttpStatus.FORBIDDEN);

        if (pdfDTO.getDateFrom().isAfter(pdfDTO.getDateTo()))//isAfter es un metodo que se usa para comparar fechas.
            return new ResponseEntity<>("Invalid dates", HttpStatus.FORBIDDEN);

       Set<Transaction> transactions = transactionServices.getTransactionByDate(pdfDTO.getDateFrom(), pdfDTO.getDateTo());


                PDFMethod.createPDF(transactions);

                return new ResponseEntity<>("PDF created", HttpStatus.CREATED);

    }


    @PostMapping("/api/clients/current/transactions/payments")
    public ResponseEntity<Object> newPayments(Authentication authentication, @RequestBody PaymentsDTO paymentsDTO){
        Client client = clientServices.findByEmail(authentication.getName());
        Card card = cardServices.getCardById(paymentsDTO.getId());
        Account accountOrigin = card.getAccount();
        Card cardNumber = cardServices.findByNumber(paymentsDTO.getNumber());

        if (client == null){
            return new ResponseEntity<>("client does not exist", HttpStatus.FORBIDDEN);
        }
        if (card == null){
            return new ResponseEntity<>("card does not exist", HttpStatus.FORBIDDEN);
        }

        if(cardNumber == null){
            return new ResponseEntity<>("card number does not exist", HttpStatus.FORBIDDEN);
        }

        if (paymentsDTO.getAmount() <= 0 ){
            return new ResponseEntity<>("invalid amount", HttpStatus.FORBIDDEN);
        }

        if(accountOrigin == null){
            return new ResponseEntity<>("account does not exist", HttpStatus.FORBIDDEN);
        }
        if (paymentsDTO.getAmount() > accountOrigin.getBalance()){
            return new ResponseEntity<>("Amount is invalid", HttpStatus.FORBIDDEN);
        }

        transactionServices.saveTransaction(new Transaction(accountOrigin,TransactionType.DEBIT,- paymentsDTO.getAmount(), paymentsDTO.getDescription(),LocalDateTime.now()));
        accountOrigin.setBalance(accountOrigin.getBalance() - paymentsDTO.getAmount());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
