package com.example.messageservice.controller;
import com.example.messageservice.model.Message;
import com.example.messageservice.service.KafkaProducerService;
import com.example.messageservice.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public MessageController(MessageService messageService, KafkaProducerService kafkaProducerService) {
        this.messageService = messageService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<Message> sendMessage(@RequestBody Message message) {
        Message savedMessage = messageService.save(message);
        kafkaProducerService.sendMessage(savedMessage);
        return new ResponseEntity<>(savedMessage, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Message>> getMessages(@RequestParam(required = false) String sender) {
        List<Message> messages;
        if (sender != null) {
            messages = messageService.findBySender(sender);
        } else {
            messages = messageService.findAll();
            if (messages.size() > 10) {
                messages = messages.subList(messages.size() - 10, messages.size());
            }
        }
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
