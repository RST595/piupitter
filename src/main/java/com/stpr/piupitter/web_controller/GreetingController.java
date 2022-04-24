package com.stpr.piupitter.web_controller;

import com.stpr.piupitter.data.model.Message;
import com.stpr.piupitter.data.repository.MessageRepo;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class GreetingController {
    private final MessageRepo messageRepo;
    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name,
                           Map<String, Object> model) {
        model.put("name", name);
        return "greeting";
    }

    @PostMapping()
    public String add(@RequestParam String text, @RequestParam String tag, Map<String, Object> model) {
        messageRepo.save(new Message(text, tag));
        model.put("messages", messageRepo.findAll());
        return "main";
    }
    @GetMapping()
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
