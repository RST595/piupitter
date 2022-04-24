package com.stpr.piupitter.data.repository;

import com.stpr.piupitter.data.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message, Long> {
    List<Message> findMessageByTag(String tag);
}
