package com.mobileserver.domain;

public class Homework {
    /*��ҵid*/
    private int homeworkId;
    public int getHomeworkId() {
        return homeworkId;
    }
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    /*�γ�*/
    private String courseObj;
    public String getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(String courseObj) {
        this.courseObj = courseObj;
    }

    /*��ҵ����*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*��ҵ����*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*��ҵҪ��*/
    private String hwRequire;
    public String getHwRequire() {
        return hwRequire;
    }
    public void setHwRequire(String hwRequire) {
        this.hwRequire = hwRequire;
    }

    /*��������*/
    private java.sql.Timestamp publishDate;
    public java.sql.Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(java.sql.Timestamp publishDate) {
        this.publishDate = publishDate;
    }

}