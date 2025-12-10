package com.fashion.site.model;

public class Document {
    private String id;
    private String title;
    private String major;
    private String school;
    private String format;
    private String filePath;
    private int downloadCount;

    public Document(String id, String title, String major, String school, String format, String filePath) {
        this.id = id;
        this.title = title;
        this.major = major;
        this.school = school;
        this.format = format;
        this.filePath = filePath;
        this.downloadCount = 0;
    }

    public Document() {
    }

    // getters & setters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getMajor() { return major; }
    public String getSchool() { return school; }
    public String getFormat() { return format; }
    public String getFilePath() { return filePath; }
    public int getDownloadCount() { return downloadCount; }
    public void increaseDownloadCount() { this.downloadCount++; }
}
