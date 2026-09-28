package cput.ac.za.controller;

import cput.ac.za.domain.Message;
import cput.ac.za.domain.User;
import cput.ac.za.dto.SendMessageRequest;
import cput.ac.za.security.CurrentUser;
import cput.ac.za.service.MessageService;
import cput.ac.za.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;
    private final UserService userService;

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    // GET /api/messages — every message involving the current user, newest
    // first. The Angular MessagingService.getConversations() uses this for
    // the inbox/conversation list.
    @GetMapping
    public List<Message> getMine(Authentication authentication) {
        return messageService.getAllForUser(CurrentUser.id(authentication));
    }

    @GetMapping("/with/{otherUserId}")
    public List<Message> getConversation(Authentication authentication, @PathVariable Long otherUserId) {
        return messageService.getConversation(CurrentUser.id(authentication), otherUserId);
    }

    @PostMapping
    public Message send(Authentication authentication, @RequestBody SendMessageRequest request) {
        User sender = userService.read(CurrentUser.id(authentication));
        User receiver = userService.read(request.getReceiverId());

        if (receiver == null) {
            throw new RuntimeException("Recipient not found");
        }

        Message message = new Message.Builder()
                .setSender(sender)
                .setReceiver(receiver)
                .setContent(request.getContent())
                .build();

        return messageService.create(message);
    }

    @DeleteMapping("/delete/{messageId}")
    public boolean delete(@PathVariable Long messageId) {
        return messageService.delete(messageId);
    }
}
