package com.chengxusheji.domain;

import java.sql.Timestamp;
public class Teacher {
    /*��ʦ���*/
    private String teacherNo;
    public String getTeacherNo() {
        return teacherNo;
    }
    public void setTeacherNo(String teacherNo) {
        this.teacherNo = teacherNo;
    }

    /*��¼����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*����*/
    private int age;
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    /*��ְ����*/
    private Timestamp comeDate;
    public Timestamp getComeDate() {
        return comeDate;
    }
    public void setComeDate(Timestamp comeDate) {
        this.comeDate = comeDate;
    }

    /*ְ��*/
    private String postName;
    public String getPostName() {
        return postName;
    }
    public void setPostName(String postName) {
        this.postName = postName;
    }

    /*��ϵ�绰*/
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*��ʦ���*/
    private String teacherDesc;
    public String getTeacherDesc() {
        return teacherDesc;
    }
    public void setTeacherDesc(String teacherDesc) {
        this.teacherDesc = teacherDesc;
    }

}