package com.chengxusheji.domain;

import java.sql.Timestamp;
public class LeaveInfo {
    /*«ÎºŸid*/
    private int leaveId;
    public int getLeaveId() {
        return leaveId;
    }
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    /*«ÎºŸ¿‡±*/
    private LeaveClass leaveClassObj;
    public LeaveClass getLeaveClassObj() {
        return leaveClassObj;
    }
    public void setLeaveClassObj(LeaveClass leaveClassObj) {
        this.leaveClassObj = leaveClassObj;
    }

    /*«ÎºŸ‘≠“Ú*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*«ÎºŸœÍ«È*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*«ÎºŸ—ß…˙*/
    private Student studentObj;
    public Student getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }

    /*«ÎºŸ ±º‰*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*…Û∫À◊¥Ã¨*/
    private String leaveState;
    public String getLeaveState() {
        return leaveState;
    }
    public void setLeaveState(String leaveState) {
        this.leaveState = leaveState;
    }

}