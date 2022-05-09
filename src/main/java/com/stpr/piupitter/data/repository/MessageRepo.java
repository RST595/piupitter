package com.stpr.piupitter.data.repository;

import com.stpr.piupitter.data.model.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message, Long> {
    Page<Message> findMessageByTag(String tag, Pageable pageable);
    Page<Message> findAll(Pageable pageable);
}
