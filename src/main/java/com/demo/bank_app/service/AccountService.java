package com.demo.bank_app.service;

import com.demo.bank_app.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto account);
    AccountDto getAccountById(Long id);
    AccountDto depositMoney(Long id, double amount);
    AccountDto withdrawMoney(Long id, double amount);
    List<AccountDto> getAllAccounts();
    void deleteAccount(Long id);
}
