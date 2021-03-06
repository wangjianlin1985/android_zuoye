package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.TeacherDAO;
import com.mobileserver.domain.Teacher;

import org.json.JSONStringer;

public class TeacherServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造老师业务层对象*/
	private TeacherDAO teacherDAO = new TeacherDAO();

	/*默认构造函数*/
	public TeacherServlet() {
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
			/*获取查询老师的参数信息*/
			String teacherNo = request.getParameter("teacherNo");
			teacherNo = teacherNo == null ? "" : new String(request.getParameter(
					"teacherNo").getBytes("iso-8859-1"), "UTF-8");
			String name = request.getParameter("name");
			name = name == null ? "" : new String(request.getParameter(
					"name").getBytes("iso-8859-1"), "UTF-8");
			Timestamp comeDate = null;
			if (request.getParameter("comeDate") != null)
				comeDate = Timestamp.valueOf(request.getParameter("comeDate"));
			String postName = request.getParameter("postName");
			postName = postName == null ? "" : new String(request.getParameter(
					"postName").getBytes("iso-8859-1"), "UTF-8");
			String telephone = request.getParameter("telephone");
			telephone = telephone == null ? "" : new String(request.getParameter(
					"telephone").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行老师查询*/
			List<Teacher> teacherList = teacherDAO.QueryTeacher(teacherNo,name,comeDate,postName,telephone);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Teachers>").append("\r\n");
			for (int i = 0; i < teacherList.size(); i++) {
				sb.append("	<Teacher>").append("\r\n")
				.append("		<teacherNo>")
				.append(teacherList.get(i).getTeacherNo())
				.append("</teacherNo>").append("\r\n")
				.append("		<password>")
				.append(teacherList.get(i).getPassword())
				.append("</password>").append("\r\n")
				.append("		<name>")
				.append(teacherList.get(i).getName())
				.append("</name>").append("\r\n")
				.append("		<sex>")
				.append(teacherList.get(i).getSex())
				.append("</sex>").append("\r\n")
				.append("		<age>")
				.append(teacherList.get(i).getAge())
				.append("</age>").append("\r\n")
				.append("		<comeDate>")
				.append(teacherList.get(i).getComeDate())
				.append("</comeDate>").append("\r\n")
				.append("		<postName>")
				.append(teacherList.get(i).getPostName())
				.append("</postName>").append("\r\n")
				.append("		<telephone>")
				.append(teacherList.get(i).getTelephone())
				.append("</telephone>").append("\r\n")
				.append("		<teacherDesc>")
				.append(teacherList.get(i).getTeacherDesc())
				.append("</teacherDesc>").append("\r\n")
				.append("	</Teacher>").append("\r\n");
			}
			sb.append("</Teachers>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Teacher teacher: teacherList) {
				  stringer.object();
			  stringer.key("teacherNo").value(teacher.getTeacherNo());
			  stringer.key("password").value(teacher.getPassword());
			  stringer.key("name").value(teacher.getName());
			  stringer.key("sex").value(teacher.getSex());
			  stringer.key("age").value(teacher.getAge());
			  stringer.key("comeDate").value(teacher.getComeDate());
			  stringer.key("postName").value(teacher.getPostName());
			  stringer.key("telephone").value(teacher.getTelephone());
			  stringer.key("teacherDesc").value(teacher.getTeacherDesc());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加老师：获取老师参数，参数保存到新建的老师对象 */ 
			Teacher teacher = new Teacher();
			String teacherNo = new String(request.getParameter("teacherNo").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherNo(teacherNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			teacher.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			teacher.setSex(sex);
			int age = Integer.parseInt(request.getParameter("age"));
			teacher.setAge(age);
			Timestamp comeDate = Timestamp.valueOf(request.getParameter("comeDate"));
			teacher.setComeDate(comeDate);
			String postName = new String(request.getParameter("postName").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPostName(postName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTelephone(telephone);
			String teacherDesc = new String(request.getParameter("teacherDesc").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherDesc(teacherDesc);

			/* 调用业务层执行添加操作 */
			String result = teacherDAO.AddTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除老师：获取老师的教师编号*/
			String teacherNo = new String(request.getParameter("teacherNo").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = teacherDAO.DeleteTeacher(teacherNo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新老师之前先根据teacherNo查询某个老师*/
			String teacherNo = new String(request.getParameter("teacherNo").getBytes("iso-8859-1"), "UTF-8");
			Teacher teacher = teacherDAO.GetTeacher(teacherNo);

			// 客户端查询的老师对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("teacherNo").value(teacher.getTeacherNo());
			  stringer.key("password").value(teacher.getPassword());
			  stringer.key("name").value(teacher.getName());
			  stringer.key("sex").value(teacher.getSex());
			  stringer.key("age").value(teacher.getAge());
			  stringer.key("comeDate").value(teacher.getComeDate());
			  stringer.key("postName").value(teacher.getPostName());
			  stringer.key("telephone").value(teacher.getTelephone());
			  stringer.key("teacherDesc").value(teacher.getTeacherDesc());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新老师：获取老师参数，参数保存到新建的老师对象 */ 
			Teacher teacher = new Teacher();
			String teacherNo = new String(request.getParameter("teacherNo").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherNo(teacherNo);
			String password = new String(request.getParameter("password").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPassword(password);
			String name = new String(request.getParameter("name").getBytes("iso-8859-1"), "UTF-8");
			teacher.setName(name);
			String sex = new String(request.getParameter("sex").getBytes("iso-8859-1"), "UTF-8");
			teacher.setSex(sex);
			int age = Integer.parseInt(request.getParameter("age"));
			teacher.setAge(age);
			Timestamp comeDate = Timestamp.valueOf(request.getParameter("comeDate"));
			teacher.setComeDate(comeDate);
			String postName = new String(request.getParameter("postName").getBytes("iso-8859-1"), "UTF-8");
			teacher.setPostName(postName);
			String telephone = new String(request.getParameter("telephone").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTelephone(telephone);
			String teacherDesc = new String(request.getParameter("teacherDesc").getBytes("iso-8859-1"), "UTF-8");
			teacher.setTeacherDesc(teacherDesc);

			/* 调用业务层执行更新操作 */
			String result = teacherDAO.UpdateTeacher(teacher);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
