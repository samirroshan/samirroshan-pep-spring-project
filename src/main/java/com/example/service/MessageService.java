package com.example.service;
import com.example.entity.Message;
import com.example.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
@Service
public class MessageService {
    @Autowired 
    private MessageRepository messageRepository;


    //create a message and then you should be able to save it here
    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }
    //find a way to get all the message
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    //find a way to get the messages from the user
    public List<Message> getMessagesForUser(Integer userId) {
        return messageRepository.findByPostedBy(userId);
    }
    //find a way to get message by id
    //fix this something isn't working right 
    /* 
    public ResponseEntity<?> getMessageById(Integer id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());
        } else {
            return ResponseEntity.status(404).body("Message not found"); 
        }
    }*/
    public Message getMessageById(Integer id) {
        return messageRepository.findById(id).orElse(null);
    }
    
    
    
    //onces you find the id update the message 
    public int updateMessage(Integer id, String message) {
        if (!messageRepository.existsById(id)) {
            return 0; 
        }
        if (((message == null) || (message.trim().isEmpty())) || (message.length() > 255)) {
            return -1; 
        }
        messageRepository.updateMessageTextById(id, message);
        return 1; 
    }
        
        
    
    public int deleteMessage(Integer id) {
        if (!messageRepository.existsById(id)) {
           return 0; 
        }
        messageRepository.deleteByMessageId(id);
        return 1; 
    }
        
    
    
}
