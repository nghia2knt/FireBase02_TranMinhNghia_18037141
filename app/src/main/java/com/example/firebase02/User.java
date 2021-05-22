package com.example.firebase02;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class User {
    public String username;
    public String email;
    public int bored;
    public int angry;
    public int smile;


    public User(String username, String email, int bored, int angry, int smile) {
        this.username = username;
        this.email = email;
        this.bored = bored;
        this.angry = angry;
        this.smile = smile;
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getBored() {
        return bored;
    }

    public void setBored(int bored) {
        this.bored = bored;
    }

    public int getAngry() {
        return angry;
    }

    public void setAngry(int angry) {
        this.angry = angry;
    }

    public int getSmile() {
        return smile;
    }

    public void setSmile(int smile) {
        this.smile = smile;
    }
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("username", username);
        result.put("email", email);
        result.put("bored", bored);
        result.put("angry", angry);
        result.put("smile", smile);
        return result;
    }
    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", bored=" + bored +
                ", angry=" + angry +
                ", smile=" + smile +
                '}';
    }
}
