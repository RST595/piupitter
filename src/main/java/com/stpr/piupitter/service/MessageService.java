package com.stpr.piupitter.service;

import com.stpr.piupitter.data.model.Message;
import com.stpr.piupitter.data.model.dto.MessageDto;
import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.repository.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepo messageRepo;

    public Page<MessageDto> messageList(Pageable pageable, String filter, AppUser user){
        if(filter != null && !filter.isEmpty()){
            return messageRepo.findMessageByTag(filter, pageable, user);
        } else {
            return messageRepo.findAll(pageable, user);
        }
    }

    public Object messageListForUser(Pageable pageable, AppUser currentUser, AppUser author) {
        return messageRepo.findByUser(pageable, author, currentUser);
    }
}
