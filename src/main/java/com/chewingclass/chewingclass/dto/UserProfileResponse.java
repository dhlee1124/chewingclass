package com.chewingclass.chewingclass.dto;

import com.chewingclass.chewingclass.entity.User;
import lombok.Getter;

@Getter
public class UserProfileResponse {
    private Long id;
    private String email;
    private String name;
    private String phoneNumber;
    private String profileImageUrl;

    public UserProfileResponse(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.phoneNumber = user.getPhoneNumber();
        this.profileImageUrl = user.getProfileImageUrl();
    }
}