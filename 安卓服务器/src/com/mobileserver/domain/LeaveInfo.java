package com.mobileserver.domain;

public class LeaveInfo {
    /*���id*/
    private int leaveId;
    public int getLeaveId() {
        return leaveId;
    }
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }

    /*������*/
    private int leaveClassObj;
    public int getLeaveClassObj() {
        return leaveClassObj;
    }
    public void setLeaveClassObj(int leaveClassObj) {
        this.leaveClassObj = leaveClassObj;
    }

    /*���ԭ��*/
    private String reason;
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }

    /*�������*/
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*���ѧ��*/
    private String studentObj;
    public String getStudentObj() {
        return studentObj;
    }
    public void setStudentObj(String studentObj) {
        this.studentObj = studentObj;
    }

    /*���ʱ��*/
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    /*���״̬*/
    private String leaveState;
    public String getLeaveState() {
        return leaveState;
    }
    public void setLeaveState(String leaveState) {
        this.leaveState = leaveState;
    }

}