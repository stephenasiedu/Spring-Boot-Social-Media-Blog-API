package com.example.controller;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.service.AccountService;
import com.example.service.MessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * SocialMediaController provides REST endpoints for account registration, authentication,
 * and message management within the social media application.
 * Endpoints include user registration, login, message creation, retrieval, update, and deletion.
 */

@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;

    /**
     * Registers a new account.
     * - Returns the created account as JSON with HTTP 200 on success.
     * - Returns HTTP 409 (Conflict) if the username already exists.
     * - Returns HTTP 400 (Bad Request) for other registration failures.
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        try {
            Account saveAccount = accountService.registerAccount(account);
            if (saveAccount != null) {
                return ResponseEntity.status(200).body(saveAccount);
            }
        } catch (DuplicateUsernameException e) {
            return ResponseEntity.status(409).body("Duplicate username");
        }
        return ResponseEntity.status(400).body("Registration failed");
    }

    /**
     * Authenticates a user.
     * - Returns the account as JSON with HTTP 200 on successful login.
     * - Returns HTTP 401 (Unauthorized) if authentication fails.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account login) {
        Account account = accountService.login(login);
        if (account == null) {
            return ResponseEntity.status(401).body("");
        }
        return ResponseEntity.status(200).body(account);
    }

    /**
     * Creates a new message.
     * - Returns the created message as JSON with HTTP 200 on success.
     * - Returns HTTP 400 (Bad Request) if message creation fails.
     */
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message, @RequestParam Integer userId) {
        Message newMessage = messageService.createMessage(message, userId);
        if (newMessage == null) {
            return ResponseEntity.status(400).body("Client Error");
        }
        return ResponseEntity.status(200).body(newMessage);
    }

    /**
     * Retrieves all messages.
     * - Returns a list of messages (possibly empty) as JSON with HTTP 200.
     */
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages); 
    }

    /**
     * Retrieves a message by its ID.
     * - Returns the message as JSON with HTTP 200 if found.
     * - Returns an empty response with HTTP 200 if not found.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(message);
    }

    /**
     * Deletes a message by its ID.
     * - Returns 1 with HTTP 200 if the message was deleted.
     * - Returns an empty response with HTTP 200 if the message does not exist.
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable Integer messageId) {
        Message deletedMessage = messageService.deleteMessageById(messageId);
        if (deletedMessage != null) {
            return ResponseEntity.status(200).body(1);
        }else {
            return ResponseEntity.status(200).body("");
        }
    }

    /**
     * Updates a message by its ID.
     * - Returns 1 with HTTP 200 if the update is successful.
     * - Returns 0 with HTTP 400 if the update fails.
     */
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> UpdateMessageById(@PathVariable Integer messageId, @RequestBody String newMessageText) {
        Message message = messageService.updateMessageById(messageId, newMessageText);
        if (message == null) {
            return ResponseEntity.status(400).body(0);
        }
        return ResponseEntity.status(200).body(1);
    }
    
    /**
     * Retrieves all messages posted by a specific user.
     * - Returns a list of messages (possibly empty) as JSON with HTTP 200.
     */
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(messages);
    }
}