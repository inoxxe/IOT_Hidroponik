package com.example.ino.iot_hidroponik.Model;



public class User {

    private String username,name,password,id;

    public User(String username, String name, String password, String id) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
