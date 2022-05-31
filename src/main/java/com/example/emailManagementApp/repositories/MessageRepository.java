package com.example.emailManagementApp.repositories;

import com.example.emailManagementApp.models.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message,String> {
    Message findMessageByUserName(String id);
    Message findMessageBySender(String sender);


}
