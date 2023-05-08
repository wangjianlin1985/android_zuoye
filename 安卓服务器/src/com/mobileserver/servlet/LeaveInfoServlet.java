package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.LeaveInfoDAO;
import com.mobileserver.domain.LeaveInfo;

import org.json.JSONStringer;

public class LeaveInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�������ҵ������*/
	private LeaveInfoDAO leaveInfoDAO = new LeaveInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public LeaveInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ��ٵĲ�����Ϣ*/
			int leaveClassObj = 0;
			if (request.getParameter("leaveClassObj") != null)
				leaveClassObj = Integer.parseInt(request.getParameter("leaveClassObj"));
			String reason = request.getParameter("reason");
			reason = reason == null ? "" : new String(request.getParameter(
					"reason").getBytes("iso-8859-1"), "UTF-8");
			String studentObj = "";
			if (request.getParameter("studentObj") != null)
				studentObj = request.getParameter("studentObj");
			String addTime = request.getParameter("addTime");
			addTime = addTime == null ? "" : new String(request.getParameter(
					"addTime").getBytes("iso-8859-1"), "UTF-8");
			String leaveState = request.getParameter("leaveState");
			leaveState = leaveState == null ? "" : new String(request.getParameter(
					"leaveState").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ����ٲ�ѯ*/
			List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfo(leaveClassObj,reason,studentObj,addTime,leaveState);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<LeaveInfos>").append("\r\n");
			for (int i = 0; i < leaveInfoList.size(); i++) {
				sb.append("	<LeaveInfo>").append("\r\n")
				.append("		<leaveId>")
				.append(leaveInfoList.get(i).getLeaveId())
				.append("</leaveId>").append("\r\n")
				.append("		<leaveClassObj>")
				.append(leaveInfoList.get(i).getLeaveClassObj())
				.append("</leaveClassObj>").append("\r\n")
				.append("		<reason>")
				.append(leaveInfoList.get(i).getReason())
				.append("</reason>").append("\r\n")
				.append("		<content>")
				.append(leaveInfoList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<studentObj>")
				.append(leaveInfoList.get(i).getStudentObj())
				.append("</studentObj>").append("\r\n")
				.append("		<addTime>")
				.append(leaveInfoList.get(i).getAddTime())
				.append("</addTime>").append("\r\n")
				.append("		<leaveState>")
				.append(leaveInfoList.get(i).getLeaveState())
				.append("</leaveState>").append("\r\n")
				.append("	</LeaveInfo>").append("\r\n");
			}
			sb.append("</LeaveInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(LeaveInfo leaveInfo: leaveInfoList) {
				  stringer.object();
			  stringer.key("leaveId").value(leaveInfo.getLeaveId());
			  stringer.key("leaveClassObj").value(leaveInfo.getLeaveClassObj());
			  stringer.key("reason").value(leaveInfo.getReason());
			  stringer.key("content").value(leaveInfo.getContent());
			  stringer.key("studentObj").value(leaveInfo.getStudentObj());
			  stringer.key("addTime").value(leaveInfo.getAddTime());
			  stringer.key("leaveState").value(leaveInfo.getLeaveState());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����٣���ȡ��ٲ������������浽�½�����ٶ��� */ 
			LeaveInfo leaveInfo = new LeaveInfo();
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			leaveInfo.setLeaveId(leaveId);
			int leaveClassObj = Integer.parseInt(request.getParameter("leaveClassObj"));
			leaveInfo.setLeaveClassObj(leaveClassObj);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setReason(reason);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setContent(content);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setStudentObj(studentObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setAddTime(addTime);
			String leaveState = new String(request.getParameter("leaveState").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setLeaveState(leaveState);

			/* ����ҵ���ִ����Ӳ��� */
			String result = leaveInfoDAO.AddLeaveInfo(leaveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����٣���ȡ��ٵ����id*/
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = leaveInfoDAO.DeleteLeaveInfo(leaveId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*�������֮ǰ�ȸ���leaveId��ѯĳ�����*/
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfo(leaveId);

			// �ͻ��˲�ѯ����ٶ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("leaveId").value(leaveInfo.getLeaveId());
			  stringer.key("leaveClassObj").value(leaveInfo.getLeaveClassObj());
			  stringer.key("reason").value(leaveInfo.getReason());
			  stringer.key("content").value(leaveInfo.getContent());
			  stringer.key("studentObj").value(leaveInfo.getStudentObj());
			  stringer.key("addTime").value(leaveInfo.getAddTime());
			  stringer.key("leaveState").value(leaveInfo.getLeaveState());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������٣���ȡ��ٲ������������浽�½�����ٶ��� */ 
			LeaveInfo leaveInfo = new LeaveInfo();
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			leaveInfo.setLeaveId(leaveId);
			int leaveClassObj = Integer.parseInt(request.getParameter("leaveClassObj"));
			leaveInfo.setLeaveClassObj(leaveClassObj);
			String reason = new String(request.getParameter("reason").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setReason(reason);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setContent(content);
			String studentObj = new String(request.getParameter("studentObj").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setStudentObj(studentObj);
			String addTime = new String(request.getParameter("addTime").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setAddTime(addTime);
			String leaveState = new String(request.getParameter("leaveState").getBytes("iso-8859-1"), "UTF-8");
			leaveInfo.setLeaveState(leaveState);

			/* ����ҵ���ִ�и��²��� */
			String result = leaveInfoDAO.UpdateLeaveInfo(leaveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
