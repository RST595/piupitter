package com.stpr.piupitter.web_controller;

import com.stpr.piupitter.data.model.Message;
import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.repository.MessageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MessageRepo messageRepo;

    @Value("${upload.path}")
    private String uploadPath;
    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @PostMapping("/main")
    public String add(@AuthenticationPrincipal AppUser author,
                      @Valid Message message, //@Valid check message entity for @NotBlank, @Length and so on...
                      BindingResult bindingResult, //list of fields and errors of validation, always should be right before Model argument
                     Model model,
                        @RequestParam("file")MultipartFile file) throws IOException { //MultipartFile file to upload images

        message.setAuthor(author);

        if (bindingResult.hasErrors()) {

            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult); //creating map for Model from bindingResult

            model.mergeAttributes(errorsMap);
            model.addAttribute("message", message);
        } else {
            //if picture was chosen
            if (file != null && !file.getOriginalFilename().isEmpty()) {
                File uploadDir = new File(uploadPath);
                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString(); //make random filename part
                String resultFilename = uuidFile + "." + file.getOriginalFilename();
                file.transferTo(new File(uploadPath + "/" + resultFilename));
                message.setFilename(resultFilename);
            }
            model.addAttribute("message", null); //clear input message form after successful adding
            messageRepo.save(message);
        }

        model.addAttribute("messages", messageRepo.findAll());

        return "main";
    }
    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "")String filter, Model model) {
        Iterable<Message> messages;
        //if filter was set
        if(filter != null && !filter.isEmpty()){
            messages = messageRepo.findMessageByTag(filter);
        } else {
            messages = messageRepo.findAll();
        }
        model.addAttribute("messages", messages);
        model.addAttribute("filter", filter);
        return "main";
    }



}
