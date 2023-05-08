package com.mobileclient.domain;

import java.io.Serializable;

public class LeaveClass implements Serializable {
    /*请假类别id*/
    private int leaveClassId;
    public int getLeaveClassId() {
        return leaveClassId;
    }
    public void setLeaveClassId(int leaveClassId) {
        this.leaveClassId = leaveClassId;
    }

    /*请假类别名称*/
    private String leaveClassName;
    public String getLeaveClassName() {
        return leaveClassName;
    }
    public void setLeaveClassName(String leaveClassName) {
        this.leaveClassName = leaveClassName;
    }

}