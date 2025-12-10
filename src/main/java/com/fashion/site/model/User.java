package com.fashion.site.model;

import java.util.List;

public class User {
    private String username;
    private String password;
    private String major;
    private String school;
    private List<String> preferredFormats;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getters & setters
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public List<String> getPreferredFormats() { return preferredFormats; }
    public void setPreferredFormats(List<String> preferredFormats) { this.preferredFormats = preferredFormats; }
}
