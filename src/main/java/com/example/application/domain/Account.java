package com.example.application.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicLong;

@Getter
@Setter
@ToString
@EqualsAndHashCode(of = "accountId", callSuper = false)
@Entity
public class Account implements Cloneable {
    @Id
    private Long accountId;
    private String firstName;
    private LocalDate openingDate;
    private String accountType;
    private Double balance;
    private Boolean active;

    private static final AtomicLong nextId = new AtomicLong(1000);
    private static final String[] accounttypes = {"Checking", "Savings", "Fixed Deposit", "Business", "Joint"};

    public Account( String firstName, LocalDate openingDate, String accountType, Double balance, Boolean active) {
        setAccountId();
        setFirstName(firstName);
        setOpeningDate(openingDate);
        setAccountType(accountType);
        setBalance(balance);
        setActive(active);
    }

    public Account(Long accountId, String firstName, LocalDate openingDate, String accountType, Double balance, Boolean active) {
        setAccountId(accountId);
        setFirstName(firstName);
        setOpeningDate(openingDate);
        setAccountType(accountType);
        setBalance(balance);
        setActive(active);
    }

    public Account() {
        setAccountId();
        setFirstName("Unknown");
        setOpeningDate(LocalDate.now());
        setAccountType("Checking");
        setBalance(0.0);
        setActive(true);
    }

    public void setAccountId() {
        this.accountId = nextId.getAndIncrement();
    }

    public void setBalance(Double balance) {
        if(balance.doubleValue() < -1000)
            throw new AccountException("Balance must be greater than or equal to 1000");
        if(balance.doubleValue() > 100_000)
            throw new AccountException("Balance must be less than or equal to 100_000");
        this.balance = balance;
    }

    public void setAccountType(String accountType) {
        if(!Arrays.asList(accounttypes).contains(accountType))
            throw new AccountException("Account type must be one of " + Arrays.toString(accounttypes));
        this.accountType = accountType;
    }


    @Override
    public Account clone() {
        return new Account(accountId, firstName, openingDate, accountType, balance, active);
    }
}

