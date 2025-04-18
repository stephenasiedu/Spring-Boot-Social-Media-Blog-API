package com.example.service;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Service class for managing messages.
 * Provides methods for creating, retrieving, updating, and deleting messages.
 */
@Service
@AllArgsConstructor
@NoArgsConstructor
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private AccountRepository accountRepository;
    /**
     * Creates a new message.
     * 
     * The creation will be successful if:
     * - The message text is not blank.
     * - The message text is not over 255 characters.
     * - The `postedBy` refers to an existing user.
     * 
     * @param messageText the text of the message
     * @param postedBy the username of the account posting the message
     * @return the created Message entity if successful, otherwise null
     */
    @Transactional
    public Message createMessage(Message message, Integer userId) {
        if (message.getMessageText() == null || message.getMessageText().isBlank() || message.getMessageText().length() > 255) {
            return null;
        }
        Account user = accountRepository.getById(userId);
        if(user == null){
            return null;
        }
        
        return messageRepository.save(message);
    }

    /**
     * Retrieves all messages from the database.
     * 
     * @return a list of all Message entities
     */
    @Transactional
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message by its ID.
     * 
     * @param messageId the ID of the message to retrieve
     * @return the Message entity if found, otherwise null
     */
    @Transactional
    public Message getMessageById(Integer messageId) {
        List<Message> messages = messageRepository.findAll();
        for (Message message : messages) {
            if (Objects.equals(message.getMessageId(), messageId)) {
                return message;
            }
        }
        return null;
    }

    /**
     * Updates a message by its ID.
     * 
     * The update will be successful if:
     * - The message ID exists.
     * - The new message text is not blank.
     * - The new message text is not over 255 characters.
     * 
     * @param messageId the ID of the message to update
     * @param messageText the new text for the message
     * @return the updated Message entity if successful, otherwise null
     */
    @Transactional
    public Message updateMessageById(Integer messageId, String messageText) {
        Message message = getMessageById(messageId);
        if (message != null && messageText != null && !messageText.isBlank() && messageText.length() <= 255) {
            message.setMessageText(messageText);
            return messageRepository.save(message);
        }
        return null;
    }

    /**
     * Retrieves all messages posted by a specific user.
     * 
     * @param accountId the ID of the account whose messages to retrieve
     * @return a list of Message entities posted by the user, or an empty list if none exist
     */
    @Transactional
    public List<Message> getMessagesByAccountId(Integer accountId) {
        List<Message> messages = messageRepository.findByPostedBy(accountId);
        return messages.isEmpty() ? new ArrayList<>() : messages;
    }

    /**
     * Deletes a message by its ID.
     * 
     * The deletion will remove the message from the database if it exists.
     * 
     * @param messageId the ID of the message to delete
     */
    @Transactional
    public void deleteMessageById(Integer messageId) { 
        Message message = getMessageById(messageId);
        if (message != null) {
            messageRepository.delete(message);
        }
    }
}