package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;
import java.util.List;

/**
 * Repository interface for Message entity.
 * Provides methods for CRUD operations and custom queries related to messages.
 */
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByPostedBy(Integer postedBy);
}