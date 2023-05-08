package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Homework;
import com.mobileserver.util.DB;

public class HomeworkDAO {

	public List<Homework> QueryHomework(String courseObj,String title,Timestamp publishDate) {
		List<Homework> homeworkList = new ArrayList<Homework>();
		DB db = new DB();
		String sql = "select * from Homework where 1=1";
		if (!courseObj.equals(""))
			sql += " and courseObj = '" + courseObj + "'";
		if (!title.equals(""))
			sql += " and title like '%" + title + "%'";
		if(publishDate!=null)
			sql += " and publishDate='" + publishDate + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Homework homework = new Homework();
				homework.setHomeworkId(rs.getInt("homeworkId"));
				homework.setCourseObj(rs.getString("courseObj"));
				homework.setTitle(rs.getString("title"));
				homework.setContent(rs.getString("content"));
				homework.setHwRequire(rs.getString("hwRequire"));
				homework.setPublishDate(rs.getTimestamp("publishDate"));
				homeworkList.add(homework);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return homeworkList;
	}
	/* 传入作业对象，进行作业的添加业务 */
	public String AddHomework(Homework homework) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新作业 */
			String sqlString = "insert into Homework(courseObj,title,content,hwRequire,publishDate) values (";
			sqlString += "'" + homework.getCourseObj() + "',";
			sqlString += "'" + homework.getTitle() + "',";
			sqlString += "'" + homework.getContent() + "',";
			sqlString += "'" + homework.getHwRequire() + "',";
			sqlString += "'" + homework.getPublishDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "作业添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "作业添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除作业 */
	public String DeleteHomework(int homeworkId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Homework where homeworkId=" + homeworkId;
			db.executeUpdate(sqlString);
			result = "作业删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "作业删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据作业id获取到作业 */
	public Homework GetHomework(int homeworkId) {
		Homework homework = null;
		DB db = new DB();
		String sql = "select * from Homework where homeworkId=" + homeworkId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				homework = new Homework();
				homework.setHomeworkId(rs.getInt("homeworkId"));
				homework.setCourseObj(rs.getString("courseObj"));
				homework.setTitle(rs.getString("title"));
				homework.setContent(rs.getString("content"));
				homework.setHwRequire(rs.getString("hwRequire"));
				homework.setPublishDate(rs.getTimestamp("publishDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return homework;
	}
	/* 更新作业 */
	public String UpdateHomework(Homework homework) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Homework set ";
			sql += "courseObj='" + homework.getCourseObj() + "',";
			sql += "title='" + homework.getTitle() + "',";
			sql += "content='" + homework.getContent() + "',";
			sql += "hwRequire='" + homework.getHwRequire() + "',";
			sql += "publishDate='" + homework.getPublishDate() + "'";
			sql += " where homeworkId=" + homework.getHomeworkId();
			db.executeUpdate(sql);
			result = "作业更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "作业更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
