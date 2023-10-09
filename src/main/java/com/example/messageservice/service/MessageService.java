package com.example.messageservice.service;
import com.example.messageservice.model.Message;
import com.example.messageservice.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> findBySender(String sender) {
        return messageRepository.findBySender(sender);
    }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }
}
