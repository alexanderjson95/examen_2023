package com.example.backend.service;

import com.example.backend.model.Chat.Message;
import com.example.backend.model.Chat.MessageRequest;
import com.example.backend.model.Chat.UserMessages;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserMessageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMessageService {

    private final UserMessageRepository userMessageRepository;
    private final MessageRepository messageRepository;
    private final CryptoService crypto;
    private final UserService uService;

    @Autowired
    public UserMessageService(UserMessageRepository userMessageRepository, MessageRepository messageRepository, CryptoService crypto, UserService uService) {
        this.userMessageRepository = userMessageRepository;
        this.messageRepository = messageRepository;
        this.crypto = crypto;
        this.uService = uService;
    }


    /* READ */

    // Hämtar alla användare i projekt, transactional då vi har lazy loading på entity, så vi måste kunna hämta både projects, users, och userprojects utan att db connection stängs.
    public List<UserMessages> getSenderMessages(MessageRequest req){
        // Null check sker i service
        Users user = uService.findUserById(req.getSenderId());
        return userMessageRepository.findBySender_Id(user.getId());
    }

    // Hämtar alla användare i projekt, transactional då vi har lazy loading på entity, så vi måste kunna hämta både projects, users, och userprojects utan att db connection stängs.
    public List<UserMessages> getRecipientMessages(MessageRequest req){
        // Null check sker i service
        Users user = uService.findUserById(req.getRecipientId());
        return userMessageRepository.findByRecipient_Id(user.getId());
    }

    public List<UserMessages> getAllUsersMessages(Long userId){
        return userMessageRepository.findAllUsersMessages(userId);
    }

    //lägg på "room" så man kan konversera innanför utrymme
    @Transactional
    public void sendMessage(MessageRequest req){
        Users user = uService.findUserById(req.getSenderId());
        Users recipient = uService.findUserById(req.getRecipientId());

        Message msg = new Message();
        msg.setEncryptedValue(req.getEncryptedValue());
        messageRepository.save(msg);
        UserMessages uMsg = new UserMessages();
        uMsg.setMessage(msg);
        uMsg.setSender(user);
        uMsg.setRecipient(recipient);
        // Sparar projektet
        userMessageRepository.save(uMsg);
    }

}
