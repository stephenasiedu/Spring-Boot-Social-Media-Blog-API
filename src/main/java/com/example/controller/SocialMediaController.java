package com.example.controller;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.service.AccountService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {
    private AccountService accountService;


// the response body should contain a JSON of the Account, including its accountId
//  If the registration is not successful due to a duplicate username, the response status should be 409. (Conflict)
//- If the registration is not successful for some other reason, the response status should be 400. (Client error)
@PostMapping("/register")
public ResponseEntity<?> register(@RequestBody Account account) {
    try {
        Account registerNewAccount = accountService.registerAccount(account);// Attempting to register a new account
        // If the account is successfully registered, return it with a 200 status
        if (registerNewAccount != null) {
            return ResponseEntity.status(200)
                    .body(registerNewAccount.getAccountId()
                            + registerNewAccount.getUsername());
        }
    } catch (DuplicateUsernameException e) {
        return ResponseEntity.status(409).body("Unsuccessful registration: Username already exists.");
    }
    return ResponseEntity.status(400).body("Client Error");
}

}
