package com.stpr.piupitter.data.util;

import com.stpr.piupitter.data.model.user.AppUser;

public abstract class MessageHelper {
    public static String getAuthorName(AppUser author){
        return author != null ? author.getUsername() : "<none>";
    }
}
