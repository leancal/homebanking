package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Models.Account;
import com.mindhub.homebanking.Services.AccountServices;
import com.mindhub.homebanking.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServicesImplements implements AccountServices {
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Override
    public Account getAccountById(long id) {
        return accountRepository.findById(id).get();
    }

    @Override
    public void saveAccount(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByNumber(String number) {
        return accountRepository.findByNumber(number);
    }

    @Override
    public Account getAccountType(String accountType) {
        return accountRepository.findByAccountType(accountType);
    }

    //delete
    @Override
    public void deleteAccount(long id) {
        accountRepository.deleteById(id);
    }
}
