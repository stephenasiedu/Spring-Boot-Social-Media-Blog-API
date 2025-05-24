package com.example.entity;

import javax.persistence.*;

/**
 * Entity representing a user account in the system.
 * Includes fields for account ID, username, and password.
 * Provides constructors, getters, setters, and standard overrides.
 */

@Entity
@Table(name="account")
public class Account {
    /**
     * Unique identifier for the account.
     */
    @Column(name="accountId")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer accountId;
    /**
     * Unique username for the account. Cannot be blank.
     */
    private String username;
    /**
     * Password for the account. Must be longer than 4 characters.
     */
    private String password;
    /**
     * Default no-argument constructor required for JPA and serialization.
     */
    public Account(){

    }
    /**
     * Constructor for creating a new account without an ID.
     * @param username the username
     * @param password the password
     */
    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }
    /**
     * Constructor for creating an account with all fields.
     * @param accountId the account ID
     * @param username the username
     * @param password the password
     */
    public Account(Integer accountId, String username, String password) {
        this.accountId = accountId;
        this.username = username;
        this.password = password;
    }
    /**
     * Gets the account ID.
     * @return accountId
     */
    public Integer getAccountId() {
        return accountId;
    }
    /**
     * Sets the account ID.
     * @param accountId the account ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }
    /**
     * Gets the username.
     * @return username
     */
    public String getUsername() {
        return username;
    }
    /**
     * Sets the username.
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
     * Gets the password.
     * @return password
     */
    public String getPassword() {
        return password;
    }
    /**
     * Sets the password.
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Checks equality based on account fields.
     * @param obj the other object
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (accountId == null) {
            if (other.accountId != null)
                return false;
        } else if (!accountId.equals(other.accountId))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    /**
     * Returns a string representation of the account.
     * @return string representation
     */
    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}