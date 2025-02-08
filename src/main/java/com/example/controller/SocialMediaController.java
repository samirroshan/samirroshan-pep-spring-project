package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/")
public class SocialMediaController {
    @Autowired 
    private AccountService accountService;

    @Autowired 
    private MessageService messageService;
    
    //need to register a new user 
    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        Optional<Account> existingUser = accountService.findByUsername(account.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }
      
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Username cannot be empty.");
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().body("Error: Password must be at least 4 characters long.");
        }
        Account newAccount = accountService.createAccount(account);
        return ResponseEntity.ok(newAccount);
    }

    //need to authenticate an user attempting to log in
    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        if (account.getUsername() == null || account.getUsername().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Username is required.");
        }
        if (account.getPassword() == null || account.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Password is required.");
        }
        Optional<Account> authenticatedAccount = accountService.authenticateUser(account.getUsername(), account.getPassword());
        return authenticatedAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());

    }





    
    //need to create a mthothed that creates a new message posted by user
    

    @PostMapping("messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        
        if (message.getMessageText() == null || message.getMessageText().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Message text cannot be empty.");
        }
        if (message.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().body("Error: Message text cannot exceed 255 characters.");
        }

        
        if (message.getPostedBy() == null) {
            return ResponseEntity.badRequest().body("Error: posted_by field is required.");
        }
        if (!accountService.existsById(message.getPostedBy())) {
            return ResponseEntity.badRequest().body("Error: User does not exist.");
        }

        
        Message savedMessage = messageService.createMessage(message);
        return ResponseEntity.ok(savedMessage); // Ensure 200 OK is returned
    }


    //find a way to get all the messages 
    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        
        
        return ResponseEntity.ok(messages); 
    }

    //find a wat to get messages posted by a particular user 
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        if (!accountService.existsById(accountId)) {
            return ResponseEntity.badRequest().body(Collections.emptyList()); 
        }
    
        List<Message> messages = messageService.getMessagesForUser(accountId);
        return ResponseEntity.ok(messages); 
    
        
    }

    //find a way to get a specifi message using the message id that we have 
    @GetMapping("messages/{messageId}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer messageId) {
        Message m = messageService.getMessageById(messageId);
        return ResponseEntity.ok(m);
    }







    

    //find a way to update the text of an existing message 
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message updatedMessage) {
        if (updatedMessage.getMessageText() == null || updatedMessage.getMessageText().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Error: Message text cannot be empty.");
        }
        if (updatedMessage.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().body("Error: Message text cannot exceed 255 characters.");
        }
    
        int result = messageService.updateMessage(messageId, updatedMessage.getMessageText());
    
        if (result == 1) {
            return ResponseEntity.ok(1); 
        }
        
        return ResponseEntity.badRequest().body("Error: Message not found or could not be updated."); 
        /* 
        int result = messageService.updateMessage(messageId, updatedMessage.getMessageText());

        if (result == 1) {
            return ResponseEntity.ok(1); 
        }
        if (result == 0) {
            return ResponseEntity.badRequest().body("Message not found"); 
        }
        return ResponseEntity.badRequest().body("Invalid message text"); 
        */

    }








    //find a way to delete a message by its ID
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
        int result = messageService.deleteMessage(messageId);
        if (result == 1) {
            return ResponseEntity.ok(1);
        }
        return ResponseEntity.ok().build();
    }


        





}
