package com.example.backend.service;

import com.example.backend.model.Chat.Message;
import com.example.backend.model.Chat.MessageRequest;
import com.example.backend.model.Chat.MessageResponse;
import com.example.backend.model.Chat.UserMessages;
import com.example.backend.model.Users.UserResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserMessageRepository;
import com.example.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.bouncycastle.util.encoders.UrlBase64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.Random;


@Service
public class UserMessageService {

    private final UserMessageRepository userMessageRepository;
    private final MessageRepository messageRepository;
    private final CryptoService crypto;
    private final UserService uService;
    private final UserRepository uRepo;

    @Autowired
    public UserMessageService(UserMessageRepository userMessageRepository, MessageRepository messageRepository, CryptoService crypto, UserService uService, UserRepository uRepo) {
        this.userMessageRepository = userMessageRepository;
        this.messageRepository = messageRepository;
        this.crypto = crypto;
        this.uService = uService;
        this.uRepo = uRepo;
    }


    /* READ */

    public List<MessageResponse> getConversation(Long userId, Long recipientId){
        return userMessageRepository.getConvo(userId, recipientId)
                .stream().map(MessageResponse::fromMessageResponse).toList();
    }

    public List<UserResponse> getContacts(Long userId){
        List<Long> contactIds = userMessageRepository.getAllContacts(userId);
        return uRepo.findAllById(contactIds).stream()
                .map(UserResponse::returnUser)
                .toList();
    }

    public List<MessageResponse> getSenderMessages(Long userId){
        return userMessageRepository.findAllUsersMessages(userId)
                .stream().map(MessageResponse::fromMessageResponse).toList();
    }

    public List<MessageResponse> getRecipientMessages(Long recipientId){
        return userMessageRepository.findByRecipient_Id(recipientId)
                .stream().map(MessageResponse::fromMessageResponse).toList();
    }

    public List<MessageResponse> getAllUsersMessages(Long userId){
        return userMessageRepository.findAllUsersMessages(userId)
                .stream().map(MessageResponse::fromMessageResponse).toList();
    }

    @Transactional
    public void sendMessage(Users user, MessageRequest req){
        System.out.println(" Reciever: " + req.getRecipientId() +  " Message: " + req.getEncryptedValue() + "Convo Key: ");

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
