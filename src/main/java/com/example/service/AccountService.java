package com.example.service;
import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AccountService {
    @Autowired 
    private AccountRepository accountRepository;
    //public Account createAccount(Account account) {
      //  return accountRepository.save(account);
    //}
    public Account createAccount(Account a) {
        a.setPassword(hashPassword(a.getPassword())); // Hash password before saving
        return accountRepository.save(a);
    }
    public Optional<Account> authenticateUser(String username, String password) {
        Optional<Account> accountOpt = accountRepository.findByUsername(username);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            if (account.getPassword().equals(password)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    
    public boolean existsById(Integer id) {
        return accountRepository.existsById(id);
    }
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encodedHash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }
}
