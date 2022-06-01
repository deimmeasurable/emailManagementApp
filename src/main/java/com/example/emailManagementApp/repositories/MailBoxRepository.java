package com.example.emailManagementApp.repositories;

import com.example.emailManagementApp.models.MailBox;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

@Service
public interface MailBoxRepository extends MongoRepository<MailBox,String> {
}
