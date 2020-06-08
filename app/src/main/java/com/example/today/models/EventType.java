package com.example.today.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventType {

    @JsonProperty(value = "id")
    private String id;
    @JsonProperty(value = "type")
    private String type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public static class MostViewedHelperClass {
        int image;
        String title, desc;
        public MostViewedHelperClass(int image, String title,String desc)
        {
            this.title = title;
            this.image = image;
            this.desc = desc;
        }

        public int getImage() {
            return image;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }
    }
}
