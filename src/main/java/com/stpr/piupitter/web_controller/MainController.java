package com.stpr.piupitter.web_controller;

import com.stpr.piupitter.data.model.Message;
import com.stpr.piupitter.data.model.user.AppUser;
import com.stpr.piupitter.data.repository.MessageRepo;
import freemarker.template.utility.StringUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
            saveFile(message, file);
            model.addAttribute("message", null); //clear input message form after successful adding
            messageRepo.save(message);
        }

        model.addAttribute("messages", messageRepo.findAll());

        return "main";
    }

    private void saveFile(Message message, MultipartFile file) throws IOException {
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
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "")String filter,
                       Model model,
                       @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Message> page;
        //if filter was set
        if(filter != null && !filter.isEmpty()){
            page = messageRepo.findMessageByTag(filter, pageable);
        } else {
            page = messageRepo.findAll(pageable);
        }
        model.addAttribute("page", page);
        model.addAttribute("url", "/main");
        model.addAttribute("filter", filter);
        return "main";
    }

    @GetMapping("/user-messages/{user}")
    public String userMessages(@AuthenticationPrincipal AppUser currentUser,
                               @PathVariable AppUser user,
                               Model model,
                               @RequestParam(required = false) Message message){
        model.addAttribute("userChannel", user);
        model.addAttribute("subscriptionsCount", user.getSubscriptions().size());
        model.addAttribute("subscribersCount", user.getSubscribers().size());
        model.addAttribute("isSubscriber", user.getSubscribers().contains(currentUser));
        model.addAttribute("messages", user.getMessages());
        model.addAttribute("message", message);
        model.addAttribute("isCurrentUser", currentUser.equals(user));
        return "user_messages";
    }

    @PostMapping("/user-messages/{user}")
    public String updateMessage(@AuthenticationPrincipal AppUser currentUser,
                                @PathVariable Long user,
                                @RequestParam("id") Message message,
                                @RequestParam("text") String text,
                                @RequestParam("tag") String tag,
                                @RequestParam("file")MultipartFile file) throws IOException {
        if(message.getAuthor().equals(currentUser)){
            if(!text.isEmpty()){
                message.setText(text);
            }
            if(!tag.isEmpty()){
                message.setTag(tag);
            }

            saveFile(message, file);
            messageRepo.save(message);
        }
        return "redirect:/user-messages/" + user;
    }



}
