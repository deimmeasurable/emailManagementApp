package com.example.emailManagementApp.services;


import com.example.emailManagementApp.dtos.request.MessageRequest;
import com.example.emailManagementApp.dtos.response.MailBoxesDto;
import com.example.emailManagementApp.exceptions.UserDidNotLogInException;
import com.example.emailManagementApp.exceptions.UserDoesNotExistException;
import com.example.emailManagementApp.models.MailBox;
import com.example.emailManagementApp.models.MailBoxes;
import com.example.emailManagementApp.models.MailboxType;
import com.example.emailManagementApp.models.User;
import com.example.emailManagementApp.repositories.MailBoxesRepository;
import com.example.emailManagementApp.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.emailManagementApp.models.MailboxType.INBOX;
import static com.example.emailManagementApp.models.MailboxType.SENT;

@Service
public class MailBoxesServiceImpl implements MailBoxesService{
//    @Autowired
    MailBoxesRepository mailBoxesRepository;

    private ModelMapper mapper;

//    @Autowired
//  private  MessageService messageService;

  @Autowired
  private UserRepository userRepository;



    public MailBoxesServiceImpl(MailBoxesRepository mailBoxesRepository){
        this.mailBoxesRepository = mailBoxesRepository;
//        this.messageService = messageService;
    }

    @Override
    public MailBoxes createMailBoxes(String email)  {
        MailBoxes newMailBoxes = new MailBoxes();
        newMailBoxes.setMailBox(new ArrayList<>());
        newMailBoxes.setUserName(email);

        MailBox receiveMailBox= new MailBox();
        receiveMailBox.setUserName(email);
        receiveMailBox.setMailboxType(INBOX);


        newMailBoxes.getMailBox().add(receiveMailBox);

        MailBox sentMailBox = new MailBox();
        sentMailBox.setMailboxType(SENT);
        sentMailBox.setUserName(email);
        newMailBoxes.getMailBox().add(sentMailBox);

        mailBoxesRepository.save(newMailBoxes);

        MailBoxesDto mailBoxesDto = new MailBoxesDto();
        mailBoxesDto.setUserName(email);
        mailBoxesDto.setMessage(email+""+"mailboxes created for user");



        return  newMailBoxes;
    }

    @Override
    public MailBoxes findMailBoxesByUserName(String userName) {
//        User user = new User();
     return mailBoxesRepository.findMailBoxesByUserName(userName).get();


    }

    @Override
    public List<MailBox> userCanViewAllInbox(MessageRequest messageRequest) {
        User foundUser = userRepository.findUserByEmail(messageRequest.getReceiver()).orElseThrow(() -> new UserDoesNotExistException("user don't exist"));

        if(!foundUser.isLogInStatus()){
            foundUser.setLogInStatus(true);

            MailBoxes mailBoxes = mailBoxesRepository.findMailBoxesByUserName(foundUser.getEmail()).get();

      return     mailBoxes.getMailBox()
              .stream()
              .parallel()
              .filter(mailBox -> mailBox.getMailboxType()== MailboxType.INBOX).collect(Collectors.toList());
        }
        throw new UserDidNotLogInException("mail");
    }
}
