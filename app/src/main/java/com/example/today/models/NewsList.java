package com.example.today.models;

import java.util.Date;

public class NewsList {
    private int id;
    private String title;
    private String news_by;
    private String info;
    private String date;
    private String image;

    public NewsList(int id, String title, String news_by, String info, String date, String image) {
        this.id = id;
        this.title = title;
        this.news_by = news_by;

        this.info = info;
        this.date = date;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNews_by() {
        return news_by;
    }

    public void setNews_by(String news_by) {
        this.news_by = news_by;
    }

    public String getInfo() {
        return info;
    }


    public void setInfo(String info) {
        this.info = info;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

