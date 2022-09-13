package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.Models.Account;

import java.util.List;

public interface AccountServices {

    public List<Account> getAccounts();
    public Account getAccountById(long id);

    public void saveAccount(Account account);
    public Account findByNumber(String number);

    public Account getAccountType(String accountType);


    public void deleteAccount(long id);

}
