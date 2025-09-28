package com.example.backend;


import com.example.backend.model.Chat.MessageRequest;
import com.example.backend.model.Chat.MessageResponse;
import com.example.backend.model.Users.UserResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.service.UserMessageService;
import com.example.backend.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
@Slf4j
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class ChatController {
    private final UserMessageService service;
    private final UserService userService;
    @Autowired
    private ObjectMapper mapper;
    @GetMapping("/sent")
    public ResponseEntity<List<MessageResponse>> getSent(Principal principal){
        String username = principal.getName();
        Users user = userService.findUserByUsername(username);
        return ResponseEntity.ok(service.getSenderMessages(user.getId()));}


    @GetMapping("/convo/{recipientId}")
    public ResponseEntity<List<MessageResponse>> openConvo(@PathVariable Long recipientId, Principal principal){
        String username = principal.getName();
        Long user = userService.findUserByUsername(username).getId();
        return ResponseEntity.ok(service.getConversation(user,recipientId));
    }

    @GetMapping("/recieved/{userId}")
    public ResponseEntity<List<MessageResponse>> getRecieved(@PathVariable Long userId){
        return ResponseEntity.ok(service.getRecipientMessages(userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MessageResponse>> getAll(@PathVariable Long userId) {
        return ResponseEntity.ok(service.getAllUsersMessages(userId));
    }

    @PostMapping("/send")
    public ResponseEntity<Void> send(@RequestBody MessageRequest req, Principal principal) {
        String username = principal.getName();
        Users user = userService.findUserByUsername(username);
        System.out.println("Recipient: " + req.getRecipientId() + " Sender: " + user.getId());
        service.sendMessage(user, req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/contacts")
    public ResponseEntity<List<UserResponse>> getContacts(Principal principal) throws JsonProcessingException {
        String username = principal.getName();
        Long user = userService.findUserByUsername(username).getId();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User: {}", principal.getName());
        log.info("Authorities: {}", auth.getAuthorities());
        List<UserResponse> contacts = service.getContacts(user);
        return ResponseEntity.ok(service.getContacts(user));
    }
}
