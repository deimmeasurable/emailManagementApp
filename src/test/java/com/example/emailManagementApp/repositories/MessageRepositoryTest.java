package com.example.emailManagementApp.repositories;

import com.example.emailManagementApp.models.Message;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class MessageRepositoryTest {

    @Autowired
    MessageRepository messageRepository;


    @BeforeEach
    void setUp() {
    }

    @Test
    void testFindBySender(){
        Message message = new Message();
        message.setSender("newemail@gmail.com");
        Message message1 = new Message();
        messageRepository.save(message);
        messageRepository.save(message1);
        Message foundMessage = messageRepository.findMessageBySender("newemail@gmail.com");
        log.info("found message-->{}", foundMessage);

        assertThat(messageRepository.findAll().size()).isGreaterThan(1);
        log.info("total msgs-->{}", messageRepository.findAll());
        assertNotNull(foundMessage);

    }
}