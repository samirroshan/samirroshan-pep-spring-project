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

    public Message createMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
    public List<Message> getMessagesForUser(Integer userId) {
        return messageRepository.findByPostedBy(userId);
    }
    /* 
    public ResponseEntity<?> getMessageById(Integer id) {
        Optional<Message> message = messageRepository.findById(id);
        if (message.isPresent()) {
            return ResponseEntity.ok(message.get());
        } else {
            return ResponseEntity.status(404).body("Message not found"); // Directly handling the error
        }
    }*/
    public Optional<Message> getMessageById(Integer id) {
        return messageRepository.findById(id);
    }
    
    
    

        public int updateMessage(Integer id, String newText) {
            if (!messageRepository.existsById(id)) {
                return 0; 
            }
            if (newText == null || newText.trim().isEmpty() || newText.length() > 255) {
                return -1; 
            }
            messageRepository.updateMessageTextById(id, newText);
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
