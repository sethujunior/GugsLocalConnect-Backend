package cput.ac.za.service;

import cput.ac.za.domain.Message;
import cput.ac.za.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IService<Message,Long> {

    private MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message create(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Message read(Long Id) {
        return messageRepository.findById(Id).orElse(null);
    }

    @Override
    public Message update(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public boolean delete(Long Id) {
        if (messageRepository.existsById(Id)) {
            messageRepository.deleteById(Id);
            return true;
        }
        return false;
    }

    @Override
    public List<Message> getAll() {
        return messageRepository.findAll();
    }
}
