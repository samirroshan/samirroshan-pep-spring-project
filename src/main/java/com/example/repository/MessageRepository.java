package com.example.repository;

import com.example.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository 
public interface MessageRepository extends JpaRepository<Message, Integer> {
    List<Message> findByPostedBy(Integer postedBy);

    @Modifying
    @Transactional
    @Query("UPDATE Message m SET m.messageText = ?2 WHERE m.messageId = ?1")
    int updateMessageTextById(Integer messageId, String newText);

    @Modifying
    @Transactional
    int deleteByMessageId(Integer messageId);
}
