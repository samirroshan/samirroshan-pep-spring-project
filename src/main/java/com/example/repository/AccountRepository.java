package com.example.repository;
import com.example.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.Optional;
import com.example.entity.Message;
import com.example.entity.Account;
import com.example.service.AccountService;
import java.util.*;


//@Service
@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>  
{
    //how to find way to get account details from the username 
    Optional< Account> findByUsername( String   username);
    
    //Optional<Account > findByUsernameAndPassword( String  username, String.  pasword);
}

 

 
