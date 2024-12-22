package com.demo.bank_app.service.impl;

import com.demo.bank_app.dto.AccountDto;
import com.demo.bank_app.entity.Account;
import com.demo.bank_app.mapper.AccountMapper;
import com.demo.bank_app.repository.AccountRepository;
import com.demo.bank_app.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {
    private AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.
                findById(id).orElseThrow(()-> new RuntimeException("Id not found."));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto depositMoney(Long id, double amount) {
        Account account = accountRepository.
                findById(id).orElseThrow(()-> new RuntimeException("Id not found."));
        double deposit = account.getBalance()+amount;
        account.setBalance(deposit);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdrawMoney(Long id, double amount) {
        Account account = accountRepository.
                findById(id).orElseThrow(()-> new RuntimeException("Id not found."));
        double withdraw = 0.0;
        if(account.getBalance()>amount){
            withdraw = account.getBalance()-amount;
        }else{
            throw new RuntimeException("Insufficient Balance");
        }
        account.setBalance(withdraw);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accountList = accountRepository.findAll();
        List<AccountDto> accountDtos = accountList.stream()
                .map(account -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
        return accountDtos;
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.
                findById(id).orElseThrow(()-> new RuntimeException("Id not found."));

        accountRepository.delete(account);

    }
}
