package com.example.today;


public class ListData1 {
    private int id;
    private String title;
    private String shortdesc;
    private String time;
    private String contactus;

    public ListData1(int id, String title, String shortdesc, String time, String contactus) {
        this.id = id;
        this.title = title;
        this.shortdesc = shortdesc;

        this.time = time;
        this.contactus = contactus;
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

    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String  time) {
        this.time = time;
    }

    public String getContactus() {
        return contactus;
    }

    public void setContactus(String contactus) {
        this.contactus = contactus;
    }

}