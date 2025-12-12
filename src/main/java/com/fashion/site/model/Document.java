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

    // getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getMajor() { return major; }
    public String getSchool() { return school; }
    public String getFormat() { return format; }
    public String getFilePath() { return filePath; }
    public int getDownloadCount() { return downloadCount; }

    // setters
    public void setId(String id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setMajor(String major) { this.major = major; }
    public void setSchool(String school) { this.school = school; }
    public void setFormat(String format) { this.format = format; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    public void setDownloadCount(int downloadCount) { this.downloadCount = downloadCount; }

    // tăng lượt tải
    public void increaseDownloadCount() { this.downloadCount++; }
}
