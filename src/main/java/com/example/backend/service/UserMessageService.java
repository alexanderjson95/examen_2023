package com.example.backend.service;

import com.example.backend.model.Chat.Message;
import com.example.backend.model.Chat.MessageRequest;
import com.example.backend.model.Users.Users;
import com.example.backend.repository.MessageRepository;
import com.example.backend.repository.UserMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class UserMessageService {

    private final UserMessageRepository userMessageRepository;
    private final MessageRepository messageRepository;
    private final CryptoService crypto;

    @Autowired
    public UserMessageService(UserMessageRepository userMessageRepository, MessageRepository messageRepository, CryptoService crypto) {
        this.userMessageRepository = userMessageRepository;
        this.messageRepository = messageRepository;
        this.crypto = crypto;
    }


    public void sendMessage(MessageRequest req){

        String encryptedMsg = crypto.encryptMessage(req.getContent())


    }

}
