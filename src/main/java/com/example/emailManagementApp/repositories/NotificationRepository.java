package com.example.emailManagementApp.repositories;

import com.example.emailManagementApp.models.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository  extends MongoRepository<Notification,String> {
Notification findNotificationByUserName(String userName);
}
