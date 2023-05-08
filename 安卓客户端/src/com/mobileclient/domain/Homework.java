package com.mobileclient.domain;

import java.io.Serializable;

public class Homework implements Serializable {
    /*作业id*/
    private int homeworkId;
    public int getHomeworkId() {
        return homeworkId;
    }
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    /*课程*/
    private String courseObj;
    public String getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(String courseObj) {
        this.courseObj = courseObj;
    }

    /*作业标题*/
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*作业内容*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*作业要求*/
    private String hwRequire;
    public String getHwRequire() {
        return hwRequire;
    }
    public void setHwRequire(String hwRequire) {
        this.hwRequire = hwRequire;
    }

    /*发布日期*/
    private java.sql.Timestamp publishDate;
    public java.sql.Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(java.sql.Timestamp publishDate) {
        this.publishDate = publishDate;
    }

}