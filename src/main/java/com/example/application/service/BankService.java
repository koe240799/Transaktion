package com.example.application.service;

import com.example.application.domain.Account;
import com.example.application.domain.AccountException;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Collectors;

@Service
public class BankService {
    private ArrayList<Account> accounts;

    public BankService() {
        accounts = new ArrayList<>();
        fillTestData(20);
    }

    public void fillTestData(int count){
        String[] accounttypes =  {"Checking", "Savings", "Fixed Deposit", "Business", "Joint"};
        Account a;
        Faker faker = new Faker();
        String firstname;
        LocalDate openingDate;
        String accountType;
        Double balance;
        Boolean active;

        for (int i = 0; i < count; i++) {
            firstname = faker.name().firstName();
            openingDate = LocalDate.now().minusDays((int)(Math.random()*365));
            accountType = accounttypes[(int)(Math.random()*accounttypes.length -1)];
            balance = faker.number().randomDouble(2,0,80_000);
            active = faker.bool().bool();
            a = new Account(firstname, openingDate, accountType, balance, active);
            accounts.add(a);
        }
    }

    public ArrayList<Account> findAll() {
        return new ArrayList<>(accounts);
    }

    @Override
    public String toString() {
        return accounts.stream()
                .map(s -> s.toString())
                .collect(Collectors.joining("\n"));
    }

    public void removeAllAccounts() {
        accounts.clear();
    }


    public void addAccount(Account a) {
        accounts.add(a);
    }

    public void raise() {
        accounts.forEach(a -> a.setBalance(a.getBalance()*1.1));
    }

    public void remove1(Long accountId) {
        if(accountId == null)
            throw new AccountException("No existing account");
        boolean found =accounts.removeIf(a -> a.getAccountId().equals(accountId));
        if(!found)
            throw new AccountException("Account not found");

    }

    public void add100Eur(Long accountId) {
        accounts.stream()
                .filter(a -> a.getAccountId().equals(accountId))
                .forEach(a -> a.setBalance(a.getBalance()+100));
    }
}
