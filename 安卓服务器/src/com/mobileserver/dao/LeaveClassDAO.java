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
	/* ������������󣬽�������������ҵ�� */
	public String AddLeaveClass(LeaveClass leaveClass) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����������� */
			String sqlString = "insert into LeaveClass(leaveClassName) values (";
			sqlString += "'" + leaveClass.getLeaveClassName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ�������� */
	public String DeleteLeaveClass(int leaveClassId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LeaveClass where leaveClassId=" + leaveClassId;
			db.executeUpdate(sqlString);
			result = "������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����������id��ȡ�������� */
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
	/* ���������� */
	public String UpdateLeaveClass(LeaveClass leaveClass) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LeaveClass set ";
			sql += "leaveClassName='" + leaveClass.getLeaveClassName() + "'";
			sql += " where leaveClassId=" + leaveClass.getLeaveClassId();
			db.executeUpdate(sql);
			result = "��������³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
