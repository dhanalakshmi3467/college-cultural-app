package com.example.today.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EventBookResponse {

    @JsonProperty(value = "booked")
    private boolean booked;
    @JsonProperty(value = "message")
    private String message;

    public boolean isBooked() {
        return booked;
    }

    public void setBooked(boolean booked) {
        this.booked = booked;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
