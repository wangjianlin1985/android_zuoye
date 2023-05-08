package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.LeaveInfo;
import com.mobileserver.util.DB;

public class LeaveInfoDAO {

	public List<LeaveInfo> QueryLeaveInfo(int leaveClassObj,String reason,String studentObj,String addTime,String leaveState) {
		List<LeaveInfo> leaveInfoList = new ArrayList<LeaveInfo>();
		DB db = new DB();
		String sql = "select * from LeaveInfo where 1=1";
		if (leaveClassObj != 0)
			sql += " and leaveClassObj=" + leaveClassObj;
		if (!reason.equals(""))
			sql += " and reason like '%" + reason + "%'";
		if (!studentObj.equals(""))
			sql += " and studentObj = '" + studentObj + "'";
		if (!addTime.equals(""))
			sql += " and addTime like '%" + addTime + "%'";
		if (!leaveState.equals(""))
			sql += " and leaveState like '%" + leaveState + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				LeaveInfo leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(rs.getInt("leaveId"));
				leaveInfo.setLeaveClassObj(rs.getInt("leaveClassObj"));
				leaveInfo.setReason(rs.getString("reason"));
				leaveInfo.setContent(rs.getString("content"));
				leaveInfo.setStudentObj(rs.getString("studentObj"));
				leaveInfo.setAddTime(rs.getString("addTime"));
				leaveInfo.setLeaveState(rs.getString("leaveState"));
				leaveInfoList.add(leaveInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveInfoList;
	}
	/* ������ٶ��󣬽�����ٵ����ҵ�� */
	public String AddLeaveInfo(LeaveInfo leaveInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�������� */
			String sqlString = "insert into LeaveInfo(leaveClassObj,reason,content,studentObj,addTime,leaveState) values (";
			sqlString += leaveInfo.getLeaveClassObj() + ",";
			sqlString += "'" + leaveInfo.getReason() + "',";
			sqlString += "'" + leaveInfo.getContent() + "',";
			sqlString += "'" + leaveInfo.getStudentObj() + "',";
			sqlString += "'" + leaveInfo.getAddTime() + "',";
			sqlString += "'" + leaveInfo.getLeaveState() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "�����ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����� */
	public String DeleteLeaveInfo(int leaveId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from LeaveInfo where leaveId=" + leaveId;
			db.executeUpdate(sqlString);
			result = "���ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "���ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* �������id��ȡ����� */
	public LeaveInfo GetLeaveInfo(int leaveId) {
		LeaveInfo leaveInfo = null;
		DB db = new DB();
		String sql = "select * from LeaveInfo where leaveId=" + leaveId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				leaveInfo = new LeaveInfo();
				leaveInfo.setLeaveId(rs.getInt("leaveId"));
				leaveInfo.setLeaveClassObj(rs.getInt("leaveClassObj"));
				leaveInfo.setReason(rs.getString("reason"));
				leaveInfo.setContent(rs.getString("content"));
				leaveInfo.setStudentObj(rs.getString("studentObj"));
				leaveInfo.setAddTime(rs.getString("addTime"));
				leaveInfo.setLeaveState(rs.getString("leaveState"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return leaveInfo;
	}
	/* ������� */
	public String UpdateLeaveInfo(LeaveInfo leaveInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update LeaveInfo set ";
			sql += "leaveClassObj=" + leaveInfo.getLeaveClassObj() + ",";
			sql += "reason='" + leaveInfo.getReason() + "',";
			sql += "content='" + leaveInfo.getContent() + "',";
			sql += "studentObj='" + leaveInfo.getStudentObj() + "',";
			sql += "addTime='" + leaveInfo.getAddTime() + "',";
			sql += "leaveState='" + leaveInfo.getLeaveState() + "'";
			sql += " where leaveId=" + leaveInfo.getLeaveId();
			db.executeUpdate(sql);
			result = "��ٸ��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ٸ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
