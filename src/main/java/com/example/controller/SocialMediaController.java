package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody Account account) {
        Optional<Account> existingUser = accountService.findByUsername(account.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(409).body("Username already exists");
        }
        if (account.getUsername().isBlank() || account.getPassword().length() < 4) {
            return ResponseEntity.badRequest().body("Invalid username or password");
        }
        Account newAccount = accountService.createAccount(account);
        return ResponseEntity.ok(newAccount);
    }

    @PostMapping("login")
    public ResponseEntity<?> loginUser(@RequestBody Account account) {
        Optional<Account> authenticatedAccount = accountService.authenticateUser(account.getUsername(), account.getPassword());
        return authenticatedAccount.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(401).build());

    }
    @PostMapping("messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().body("Invalid message text");
        }
        if (!accountService.existsById(message.getPostedBy())) {
            return ResponseEntity.badRequest().body("User does not exist");
        }
        Message savedMessage = messageService.createMessage(message);
        return ResponseEntity.ok(savedMessage);
    }

    @GetMapping("messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable Integer accountId) {
        return ResponseEntity.ok(messageService.getMessagesForUser(accountId));
    }

    @GetMapping("messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.isPresent() ? ResponseEntity.ok(message.get()) : ResponseEntity.ok().body("");  // Ensure 200 for missing message
    }
    


    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable Integer messageId, @RequestBody Message updatedMessage) {
        if (updatedMessage.getMessageText().isBlank() || updatedMessage.getMessageText().length() > 255) {
            return ResponseEntity.badRequest().body("Invalid message text");
        }
        int rowsUpdated = messageService.updateMessage(messageId, updatedMessage.getMessageText());
        return rowsUpdated > 0 ? ResponseEntity.ok(rowsUpdated) : ResponseEntity.badRequest().body("Message not found");
    }
    @DeleteMapping("messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable Integer messageId) {
        int rowsDeleted = messageService.deleteMessage(messageId);
        return rowsDeleted > 0 ? ResponseEntity.ok(rowsDeleted) : ResponseEntity.ok().body("");
    }
}
