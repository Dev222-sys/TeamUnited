package com.example.teamunited.Modal;

public class ScreenItem {

    String Title,Title2,Description;

    int ScreenImg;

    public ScreenItem(String title, String title2, String description, int screenImg) {
        Title = title;
        Title2 = title2;
        Description = description;
        ScreenImg = screenImg;
    }

    public String getTitle2() {
        return Title2;
    }

    public void setTitle2(String title2) {
        Title2 = title2;
    }
/*
    public ScreenItem(String title, String description, int screenImg) {
        Title = title;
        Description = description;
        ScreenImg = screenImg;
    }*/

    public void setTitle(String title) {
        Title = title;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setScreenImg(int screenImg) {
        ScreenImg = screenImg;
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public int getScreenImg() {
        return ScreenImg;
    }
}
