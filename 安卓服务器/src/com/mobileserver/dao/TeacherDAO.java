package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Teacher;
import com.mobileserver.util.DB;

public class TeacherDAO {

	public List<Teacher> QueryTeacher(String teacherNo,String name,Timestamp comeDate,String postName,String telephone) {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		DB db = new DB();
		String sql = "select * from Teacher where 1=1";
		if (!teacherNo.equals(""))
			sql += " and teacherNo like '%" + teacherNo + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if(comeDate!=null)
			sql += " and comeDate='" + comeDate + "'";
		if (!postName.equals(""))
			sql += " and postName like '%" + postName + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setTeacherNo(rs.getString("teacherNo"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSex(rs.getString("sex"));
				teacher.setAge(rs.getInt("age"));
				teacher.setComeDate(rs.getTimestamp("comeDate"));
				teacher.setPostName(rs.getString("postName"));
				teacher.setTelephone(rs.getString("telephone"));
				teacher.setTeacherDesc(rs.getString("teacherDesc"));
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacherList;
	}
	/* 传入老师对象，进行老师的添加业务 */
	public String AddTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新老师 */
			String sqlString = "insert into Teacher(teacherNo,password,name,sex,age,comeDate,postName,telephone,teacherDesc) values (";
			sqlString += "'" + teacher.getTeacherNo() + "',";
			sqlString += "'" + teacher.getPassword() + "',";
			sqlString += "'" + teacher.getName() + "',";
			sqlString += "'" + teacher.getSex() + "',";
			sqlString += teacher.getAge() + ",";
			sqlString += "'" + teacher.getComeDate() + "',";
			sqlString += "'" + teacher.getPostName() + "',";
			sqlString += "'" + teacher.getTelephone() + "',";
			sqlString += "'" + teacher.getTeacherDesc() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "老师添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "老师添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除老师 */
	public String DeleteTeacher(String teacherNo) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Teacher where teacherNo='" + teacherNo + "'";
			db.executeUpdate(sqlString);
			result = "老师删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "老师删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据教师编号获取到老师 */
	public Teacher GetTeacher(String teacherNo) {
		Teacher teacher = null;
		DB db = new DB();
		String sql = "select * from Teacher where teacherNo='" + teacherNo + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				teacher = new Teacher();
				teacher.setTeacherNo(rs.getString("teacherNo"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSex(rs.getString("sex"));
				teacher.setAge(rs.getInt("age"));
				teacher.setComeDate(rs.getTimestamp("comeDate"));
				teacher.setPostName(rs.getString("postName"));
				teacher.setTelephone(rs.getString("telephone"));
				teacher.setTeacherDesc(rs.getString("teacherDesc"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacher;
	}
	/* 更新老师 */
	public String UpdateTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Teacher set ";
			sql += "password='" + teacher.getPassword() + "',";
			sql += "name='" + teacher.getName() + "',";
			sql += "sex='" + teacher.getSex() + "',";
			sql += "age=" + teacher.getAge() + ",";
			sql += "comeDate='" + teacher.getComeDate() + "',";
			sql += "postName='" + teacher.getPostName() + "',";
			sql += "telephone='" + teacher.getTelephone() + "',";
			sql += "teacherDesc='" + teacher.getTeacherDesc() + "'";
			sql += " where teacherNo='" + teacher.getTeacherNo() + "'";
			db.executeUpdate(sql);
			result = "老师更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "老师更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
