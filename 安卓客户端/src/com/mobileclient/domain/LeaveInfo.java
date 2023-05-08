package com.mobileclient.domain;

import java.io.Serializable;

public class LeaveInfo implements Serializable {
    /*请假id*/
    private int leaveId;
    public int getLeaveId() {
        return leaveId;
    }
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    /*请假类别*/
    private int leaveClassObj;
    public int getLeaveClassObj() {
        return leaveClassObj;
    }
    public void setLeaveClassObj(int leaveClassObj) {
        this.leaveClassObj = leaveClassObj;
    }

    /*请假原因*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*请假详情*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*请假学生*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*请假时间*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*审核状态*/
    private String leaveState;
    public String getLeaveState() {
        return leaveState;
    }
    public void setLeaveState(String leaveState) {
        this.leaveState = leaveState;
    }

}