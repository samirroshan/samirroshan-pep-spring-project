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
    //find a way to set the password 
    public Account createAccount(Account a) {
        a.setPassword(a.getPassword()); 
        return accountRepository.save(a);
    }
    //verify the users username and their password 
    //should return an authenticated account if the verification was a success
    public Optional<Account> authenticateUser(String username, String password) {
        Optional<Account> accountOpt = accountRepository.findByUsername(username);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            //checking if the passwords are same 
            if (account.getPassword().equals(password)) {
                return Optional.of(account);
            }
        }
        return Optional.empty();
    }

    //check if the accout exists by id 
    public boolean existsById(Integer id) {
        return accountRepository.existsById(id);
    }
    //need to create a way to find the account by username
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }
    
    
}
