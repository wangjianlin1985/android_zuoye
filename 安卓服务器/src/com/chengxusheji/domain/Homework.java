package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Homework {
    /*作业id*/
    private int homeworkId;
    public int getHomeworkId() {
        return homeworkId;
    }
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }

    /*课程*/
    private Course courseObj;
    public Course getCourseObj() {
        return courseObj;
    }
    public void setCourseObj(Course courseObj) {
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
    private Timestamp publishDate;
    public Timestamp getPublishDate() {
        return publishDate;
    }
    public void setPublishDate(Timestamp publishDate) {
        this.publishDate = publishDate;
    }

}