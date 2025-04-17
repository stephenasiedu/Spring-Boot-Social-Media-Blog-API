package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Service class for managing accounts.
 * Provides methods for account registration and login.
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountService {

    private AccountRepository accountRepository;
    /**
     * Registers a new account.
     * 
     * Registration will be successful if:
     * - The username is not blank.
     * - The password is at least 4 characters long.
     * - An account with the same username does not already exist.
     * 
     * @param account the account to register
     * @return the registered Account entity if successful, otherwise null
     * @throws DuplicateUsernameException if the username already exists
     */
    @Transactional
    public Account registerAccount(Account account) {
        if (account.getUsername() == null || account.getUsername().isBlank()) {
            return null;
        }
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        Account existing = accountRepository.findByUsername(account.getUsername());
        if (existing != null) {
            throw new DuplicateUsernameException("Username already exists");
        }
        return accountRepository.save(account);
    }

    /**
     * Logs in a user.
     * 
     * Login will be successful if:
     * - The username and password provided match an existing account in the database.
     * 
     * @param username the username of the account
     * @param password the password of the account
     * @return the Account entity if login is successful, otherwise null
     */
    @Transactional
    public Account login(String username, String password) {
        Account account = accountRepository.findByUsername(username);
        if (account != null && account.getPassword().equals(password)) {
            return account;
        }
        return null;
    }
}