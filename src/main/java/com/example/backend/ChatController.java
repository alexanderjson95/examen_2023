package com.example.backend;


import com.example.backend.model.Chat.MessageRequest;
import com.example.backend.model.Chat.MessageResponse;
import com.example.backend.model.Users.Users;
import com.example.backend.service.UserMessageService;
import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class ChatController {
    private final UserMessageService service;
    private final UserService userService;

    @GetMapping("/sent")
    public ResponseEntity<List<MessageResponse>> getSent(Principal principal){
        String username = principal.getName();
        Users user = userService.findUserByUsername(username);
        return ResponseEntity.ok(service.getSenderMessages(user.getId()));
    }


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


}
