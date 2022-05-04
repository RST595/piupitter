package com.stpr.piupitter.data.model;


import com.stpr.piupitter.data.model.user.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@Entity
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please fill the message") //this message returns to controller in Model
    @Length(max = 2048, message = "Message too long (more than 2kB)")
    private String text;
    @Length(max = 255, message = "Tag too long")
    private String tag;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private AppUser author;

    private String filename;

    public Message(String text, String tag, AppUser author) {
        this.text = text;
        this.tag = tag;
        this.author = author;
    }

    public String getAuthorName(){
        return author != null ? author.getUsername() : "<none>";
    }
}
