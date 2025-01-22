package com.abhiroid.cocsit_network.model;

public class NotiSugMdoel {

    private int profileImage;
    private String name;

    public NotiSugMdoel(int postImage, String ghost) {
        this.profileImage = postImage;
        this.name = ghost;
    }

    public int getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(int profileImage) {
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
