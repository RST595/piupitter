package com.stpr.piupitter.web_controller;

import com.stpr.piupitter.data.model.Message;
import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.repository.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MessageRepo messageRepo;
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal AppUser author,
                      @RequestParam String text,
                      @RequestParam String tag, Map<String, Object> model) {
        messageRepo.save(new Message(text, tag, author));
        model.put("messages", messageRepo.findAll());
        return "main";
    }
    @GetMapping("/main")
    public String main(Map<String, Object> model) {
        model.put("messages", messageRepo.findAll());
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String filter, Map<String, Object> model) {
        Iterable<Message> messages; //iterable implements list, findMessageByTag could return null
        if(filter != null && !filter.isEmpty()){
            messages = messageRepo.findMessageByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.put("messages", messages);
        return "main";
    }

}
