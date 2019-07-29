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

	/*构造请假业务层对象*/
	private LeaveInfoDAO leaveInfoDAO = new LeaveInfoDAO();

	/*默认构造函数*/
	public LeaveInfoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询请假的参数信息*/
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

			/*调用业务逻辑层执行请假查询*/
			List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfo(leaveClassObj,reason,studentObj,addTime,leaveState);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加请假：获取请假参数，参数保存到新建的请假对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = leaveInfoDAO.AddLeaveInfo(leaveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除请假：获取请假的请假id*/
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			/*调用业务逻辑层执行删除操作*/
			String result = leaveInfoDAO.DeleteLeaveInfo(leaveId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新请假之前先根据leaveId查询某个请假*/
			int leaveId = Integer.parseInt(request.getParameter("leaveId"));
			LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfo(leaveId);

			// 客户端查询的请假对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新请假：获取请假参数，参数保存到新建的请假对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = leaveInfoDAO.UpdateLeaveInfo(leaveInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
