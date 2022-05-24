package com.example.emailManagementApp.repositories;


import com.example.emailManagementApp.models.MailBoxes;
//import com.example.emailManagementApp.models.Mailboxes;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MailBoxesRepository extends MongoRepository<MailBoxes,String> {
       Optional<MailBoxes> findMailBoxesByUserName(String userName);
}
