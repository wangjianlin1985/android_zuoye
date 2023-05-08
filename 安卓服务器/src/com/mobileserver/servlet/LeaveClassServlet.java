package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.LeaveClassDAO;
import com.mobileserver.domain.LeaveClass;

import org.json.JSONStringer;

public class LeaveClassServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*����������ҵ������*/
	private LeaveClassDAO leaveClassDAO = new LeaveClassDAO();

	/*Ĭ�Ϲ��캯��*/
	public LeaveClassServlet() {
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
			/*��ȡ��ѯ������Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ���������ѯ*/
			List<LeaveClass> leaveClassList = leaveClassDAO.QueryLeaveClass();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<LeaveClasss>").append("\r\n");
			for (int i = 0; i < leaveClassList.size(); i++) {
				sb.append("	<LeaveClass>").append("\r\n")
				.append("		<leaveClassId>")
				.append(leaveClassList.get(i).getLeaveClassId())
				.append("</leaveClassId>").append("\r\n")
				.append("		<leaveClassName>")
				.append(leaveClassList.get(i).getLeaveClassName())
				.append("</leaveClassName>").append("\r\n")
				.append("	</LeaveClass>").append("\r\n");
			}
			sb.append("</LeaveClasss>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(LeaveClass leaveClass: leaveClassList) {
				  stringer.object();
			  stringer.key("leaveClassId").value(leaveClass.getLeaveClassId());
			  stringer.key("leaveClassName").value(leaveClass.getLeaveClassName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��������𣺻�ȡ������������������浽�½������������ */ 
			LeaveClass leaveClass = new LeaveClass();
			int leaveClassId = Integer.parseInt(request.getParameter("leaveClassId"));
			leaveClass.setLeaveClassId(leaveClassId);
			String leaveClassName = new String(request.getParameter("leaveClassName").getBytes("iso-8859-1"), "UTF-8");
			leaveClass.setLeaveClassName(leaveClassName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = leaveClassDAO.AddLeaveClass(leaveClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ�������𣺻�ȡ�������������id*/
			int leaveClassId = Integer.parseInt(request.getParameter("leaveClassId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = leaveClassDAO.DeleteLeaveClass(leaveClassId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*����������֮ǰ�ȸ���leaveClassId��ѯĳ��������*/
			int leaveClassId = Integer.parseInt(request.getParameter("leaveClassId"));
			LeaveClass leaveClass = leaveClassDAO.GetLeaveClass(leaveClassId);

			// �ͻ��˲�ѯ����������󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("leaveClassId").value(leaveClass.getLeaveClassId());
			  stringer.key("leaveClassName").value(leaveClass.getLeaveClassName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���������𣺻�ȡ������������������浽�½������������ */ 
			LeaveClass leaveClass = new LeaveClass();
			int leaveClassId = Integer.parseInt(request.getParameter("leaveClassId"));
			leaveClass.setLeaveClassId(leaveClassId);
			String leaveClassName = new String(request.getParameter("leaveClassName").getBytes("iso-8859-1"), "UTF-8");
			leaveClass.setLeaveClassName(leaveClassName);

			/* ����ҵ���ִ�и��²��� */
			String result = leaveClassDAO.UpdateLeaveClass(leaveClass);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
