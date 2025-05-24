package com.example.service;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;

/**
 * Service class for managing accounts.
 * Provides methods for account registration and login.
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    /**
     * Registers a new account.
     * Registration is successful if:
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
        if (account.getUsername() == null 
        || account.getUsername().isBlank()
        || account.getPassword() == null
        || account.getPassword().length() < 4) {
            return null;
        }

        Account existing = accountRepository.findByUsername(account.getUsername());
        if (existing != null) {
            throw new DuplicateUsernameException("Username already exists");
        }
        return accountRepository.save(account);
    }

    /**
     * Authenticates a user.
     * Login is successful if the provided username and password match an existing account.
     *
     * @param login the account credentials for authentication
     * @return the Account entity if login is successful, otherwise null
     */
    @Transactional
    public Account login(Account login) {
        Account account = accountRepository.findByUsername(login.getUsername());
        if (account != null && account.getPassword().equals(login.getPassword())) {
            return account;
        }
        return null;
    }
}