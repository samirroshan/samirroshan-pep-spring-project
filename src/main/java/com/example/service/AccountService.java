package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService {
    @Autowired 
    private AccountRepository accountRepository;
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }
    public Optional<Account> authenticateUser(String username, String password) {
        return accountRepository.findByUsernameAndPassword(username, password);
    }
    public boolean existsById(Integer id) {
        return accountRepository.existsById(id);
    }
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
}
