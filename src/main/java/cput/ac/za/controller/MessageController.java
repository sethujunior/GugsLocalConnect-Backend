package cput.ac.za.controller;

import cput.ac.za.domain.Message;
import cput.ac.za.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("messages")
public class MessageController {

    private MessageService messageService;
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/create")
    public Message create(@RequestBody Message message) {
        return messageService.create(message);
    }

    @GetMapping("/read/{messageId}")
    public Message read(@PathVariable Long messageId)   {
        return messageService.read(messageId);
    }

    @PutMapping("/update")
    public Message update(@RequestBody Message message) {
        return messageService.update(message);
    }

    @DeleteMapping("/delete/{messageId}")
    public boolean delete(@PathVariable Long messageId) {
        return messageService.delete(messageId);
    }

    @GetMapping("/getAll")
    public List<Message> getAll(){
        return messageService.getAll();
    }

}
