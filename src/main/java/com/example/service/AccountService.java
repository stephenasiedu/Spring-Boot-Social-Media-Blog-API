package com.example.service;


import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AccountService {
    AccountRepository accountRepository;
    MessageRepository messageRepository;

//    The registration will be successful if and only if the username is not blank,
//    the password is at least 4 characters long,
//    and an Account with that username does not already exist.
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
}

