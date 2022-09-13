package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.DTO.ClientDTO;
import com.mindhub.homebanking.DTO.LoanApplicationDTO;
import com.mindhub.homebanking.DTO.LoanDTO;
import com.mindhub.homebanking.Models.*;
import com.mindhub.homebanking.Services.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class LoanController {
    @Autowired
    private LoanServices loanServices;
    @Autowired
    private ClientServices clientServices;
    @Autowired
    private AccountServices accountServices;
    @Autowired
    private TransactionServices transactionServices;
    @Autowired
    private ClientLoanServices clientLoanServices;
    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("api/loans")
    public List<LoanDTO> getLoanApplicationDTO() {
        return loanServices.getLoans().stream().map(LoanDTO::new).collect(Collectors.toList());
    }


    @Transactional
    @PostMapping("api/loans")
    public ResponseEntity<String> newLoans(@RequestBody LoanApplicationDTO loanApplicationDTO, Authentication authentication) {

        Client client = clientServices.findByEmail(authentication.getName());
        Loan loan = loanServices.getLoanById(loanApplicationDTO.getId());//obtengo el prestamo por el id
        Loan loanType = loanServices.getLoanByName(loan.getName());
        Account account = accountServices.findByNumber(loanApplicationDTO.getNumberOriginAccount());
        Account accountDestiny=accountServices.findByNumber(account.getNumber());


//        if (loanApplicationDTO == null) {
//            return new ResponseEntity<>("Missing Data", HttpStatus.FORBIDDEN);
//        }


        if (loanApplicationDTO.getAmount()== 0 || loanApplicationDTO.getNumberOriginAccount().isEmpty() || loanApplicationDTO.getPayment()==0 ) {
            return new ResponseEntity<>("Wrong data", HttpStatus.FORBIDDEN);
        }



        if(accountServices.findByNumber(loanApplicationDTO.getNumberOriginAccount())==null){
            return new ResponseEntity<>("Account doesn´t exist ",HttpStatus.FORBIDDEN);

        }



        if (!client.getAccounts().contains(account)){
            return new ResponseEntity<>("Unauthenticated account",HttpStatus.FORBIDDEN);

        }
        if (loanServices.getLoanById(loanApplicationDTO.getId())==null){
            return new ResponseEntity<>("Loan doesn´t exist",HttpStatus.FORBIDDEN);
        }

        if (loanApplicationDTO.getAmount()>loan.getMaxAmount() || loanApplicationDTO.getAmount() <=0){
            return new ResponseEntity<>("Amount exceeds maximum amount",HttpStatus.FORBIDDEN);
        }

        if (!loan.getPayments().contains(loanApplicationDTO.getPayment())){
            return new ResponseEntity<>("Payment not available",HttpStatus.FORBIDDEN);
        }

        if(client.getLoans().contains(loanType)){
            return new ResponseEntity<>("You already have this type of loan",HttpStatus.FORBIDDEN);
        }

        //double loanInterest=(loanApplicationDTO.getAmount());

        ClientLoan clientLoans=new ClientLoan(loanApplicationDTO.getAmount(),loanApplicationDTO.getPayment(),client,loan);
        Transaction transactionsLoanCredit=new Transaction(account, TransactionType.CREDIT, loanApplicationDTO.getAmount(),  loan.getName() + " " + "loan approved",  LocalDateTime.now());

        switch (loan.getName()){
            case "Personal":
                switch (clientLoans.getPayment()){
                    case 6: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.10));
                        break;
                    case 12: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.20));
                        break;
                    case 24: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.30));
                        break;
                }
                break;
                case "Mortgage":
                    switch (clientLoans.getPayment()){
                        case 6: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.10));
                            break;
                        case 12: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.20));
                            break;
                        case 24: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.30));
                            break;
                        case 36: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.40));
                            break;
                        case 48: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.50));
                            break;
                        case 60: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.60));
                            break;
                    }

                break;
                case "Vehicle":
                    switch (clientLoans.getPayment()){
                        case 6: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.10));
                            break;
                        case 12: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.20));
                            break;
                        case 24: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.30));
                            break;
                        case 36: clientLoans.setAmount(clientLoans.getAmount()+(clientLoans.getAmount()*0.40));
                            break;
                    }
                     break;
                 default:
                     break;

        }

        transactionServices.saveTransaction(transactionsLoanCredit);
        clientLoanServices.saveClientLoan(clientLoans);

        Double balDestiny= loanApplicationDTO.getAmount()+ account.getBalance();

        accountDestiny.setBalance(balDestiny);

        return new ResponseEntity<>("Loan created", HttpStatus.OK);
    }

    @PostMapping("/api/admin/loans")
    public ResponseEntity<String> addAdminLoan (@RequestParam String name,@RequestParam Double amount, @RequestParam List<Integer> payments, Authentication authentication){

        Client adminAuthentication = clientServices.findByEmail(authentication.getName());

        if(adminAuthentication == null){
            return new ResponseEntity<>("missing admin authentication", HttpStatus.FORBIDDEN);
        }

        if(name.isEmpty()){
            return new ResponseEntity<>("missing name of loan", HttpStatus.FORBIDDEN);
        }

        if(amount <= 0){
            return new ResponseEntity<>("missing max amount of loan", HttpStatus.FORBIDDEN);
        }

        if(loanServices.getLoans().stream().map(x -> x.getName()).collect(Collectors.toSet()).contains(name)){
            return new ResponseEntity<>("same name of previous loan", HttpStatus.FORBIDDEN);
        }

        //loanServices.saveLoan(new Loan(name, amount, payments));
        Loan loan = new Loan(name, amount, payments);
        //loanServices.saveLoan(loan);
        loanRepository.save(loan);

        return new ResponseEntity<>("Loan Created",HttpStatus.CREATED);
    }

}
