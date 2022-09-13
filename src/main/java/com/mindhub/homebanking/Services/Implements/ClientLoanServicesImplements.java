package com.mindhub.homebanking.Services.Implements;


import com.mindhub.homebanking.Models.ClientLoan;
import com.mindhub.homebanking.Services.ClientLoanServices;
import com.mindhub.homebanking.repositories.ClientLoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientLoanServicesImplements implements ClientLoanServices {
    @Autowired
    private ClientLoanRepository clientLoanRepository;

    @Override
    public void saveClientLoan(ClientLoan clientLoan) {
        clientLoanRepository.save(clientLoan);
    }

}
