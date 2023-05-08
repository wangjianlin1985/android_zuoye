package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.LeaveClass;
import com.mobileserver.util.DB;

public class LeaveClassDAO {

	public List<LeaveClass> QueryLeaveClass() {
		List<LeaveClass> leaveClassList = new ArrayList<LeaveClass>();
		DB db = new DB();
		String sql = "select * from LeaveClass where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				LeaveClass leaveClass = new LeaveClass();
				leaveClass.setLeaveClassId(rs.getInt("leaveClassId"));
				leaveClass.setLeaveClassName(rs.getString("leaveClassName"));
				leaveClassList.add(leaveClass);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveClassList;
	}
	/* 传入请假类别对象，进行请假类别的添加业务 */
	public String AddLeaveClass(LeaveClass leaveClass) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新请假类别 */
			String sqlString = "insert into LeaveClass(leaveClassName) values (";
			sqlString += "'" + leaveClass.getLeaveClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "请假类别添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "请假类别添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除请假类别 */
	public String DeleteLeaveClass(int leaveClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LeaveClass where leaveClassId=" + leaveClassId;
			db.executeUpdate(sqlString);
			result = "请假类别删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "请假类别删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据请假类别id获取到请假类别 */
	public LeaveClass GetLeaveClass(int leaveClassId) {
		LeaveClass leaveClass = null;
		DB db = new DB();
		String sql = "select * from LeaveClass where leaveClassId=" + leaveClassId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				leaveClass = new LeaveClass();
				leaveClass.setLeaveClassId(rs.getInt("leaveClassId"));
				leaveClass.setLeaveClassName(rs.getString("leaveClassName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveClass;
	}
	/* 更新请假类别 */
	public String UpdateLeaveClass(LeaveClass leaveClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LeaveClass set ";
			sql += "leaveClassName='" + leaveClass.getLeaveClassName() + "'";
			sql += " where leaveClassId=" + leaveClass.getLeaveClassId();
			db.executeUpdate(sql);
			result = "请假类别更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "请假类别更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
