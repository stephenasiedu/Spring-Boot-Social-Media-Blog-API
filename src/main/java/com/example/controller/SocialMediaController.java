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
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private MessageService messageService;


    /**
     * Registers a new account.
     * - If successful, returns the created account as JSON with status 200.
     * - If the username is a duplicate, returns status 409 (Conflict).
     * - If registration fails for other reasons, returns status 400 (Bad Request).
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
     * Logs in a user.
     * - If successful, returns the account as JSON with status 200.
     * - If login fails, returns status 401 (Unauthorized).
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
     * - If successful, returns the created message as JSON with status 200.
     * - If creation fails, returns status 400 (Bad Request).
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
     * - Always returns a list of messages (can be empty) as JSON with status 200.
     */
    @GetMapping("/messages")
    public ResponseEntity<?> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(messages); 
    }

    /**
     * Retrieves a message by its ID.
     * - If the message exists, returns it as JSON with status 200.
     * - If the message does not exist, returns an empty response with status 200.
     */
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable Integer messageId) {
        Message message = messageService.getMessageById(messageId);
        return ResponseEntity.status(200).body(message);
    }

    /**
     * Deletes a message by its ID.
     * - If the message exists, deletes it and returns 1 with status 200.
     * - If the message does not exist, returns 0 with status 200.
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
     * - If the update is successful, returns 1 with status 200.
     * - If the update fails, returns 0 with status 400.
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
     * - Always returns a list of messages (can be empty) as JSON with status 200.
     */
    @GetMapping("accounts/{accountId}/messages")
    public ResponseEntity<?> getMessagesByAccountId(@PathVariable Integer accountId) {
        List<Message> messages = messageService.getMessagesByAccountId(accountId);
        return ResponseEntity.status(200).body(messages);
    }
}