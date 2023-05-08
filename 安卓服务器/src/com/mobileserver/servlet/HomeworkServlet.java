package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.HomeworkDAO;
import com.mobileserver.domain.Homework;

import org.json.JSONStringer;

public class HomeworkServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*构造作业业务层对象*/
	private HomeworkDAO homeworkDAO = new HomeworkDAO();

	/*默认构造函数*/
	public HomeworkServlet() {
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
			/*获取查询作业的参数信息*/
			String courseObj = "";
			if (request.getParameter("courseObj") != null)
				courseObj = request.getParameter("courseObj");
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			Timestamp publishDate = null;
			if (request.getParameter("publishDate") != null)
				publishDate = Timestamp.valueOf(request.getParameter("publishDate"));

			/*调用业务逻辑层执行作业查询*/
			List<Homework> homeworkList = homeworkDAO.QueryHomework(courseObj,title,publishDate);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<Homeworks>").append("\r\n");
			for (int i = 0; i < homeworkList.size(); i++) {
				sb.append("	<Homework>").append("\r\n")
				.append("		<homeworkId>")
				.append(homeworkList.get(i).getHomeworkId())
				.append("</homeworkId>").append("\r\n")
				.append("		<courseObj>")
				.append(homeworkList.get(i).getCourseObj())
				.append("</courseObj>").append("\r\n")
				.append("		<title>")
				.append(homeworkList.get(i).getTitle())
				.append("</title>").append("\r\n")
				.append("		<content>")
				.append(homeworkList.get(i).getContent())
				.append("</content>").append("\r\n")
				.append("		<hwRequire>")
				.append(homeworkList.get(i).getHwRequire())
				.append("</hwRequire>").append("\r\n")
				.append("		<publishDate>")
				.append(homeworkList.get(i).getPublishDate())
				.append("</publishDate>").append("\r\n")
				.append("	</Homework>").append("\r\n");
			}
			sb.append("</Homeworks>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(Homework homework: homeworkList) {
				  stringer.object();
			  stringer.key("homeworkId").value(homework.getHomeworkId());
			  stringer.key("courseObj").value(homework.getCourseObj());
			  stringer.key("title").value(homework.getTitle());
			  stringer.key("content").value(homework.getContent());
			  stringer.key("hwRequire").value(homework.getHwRequire());
			  stringer.key("publishDate").value(homework.getPublishDate());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加作业：获取作业参数，参数保存到新建的作业对象 */ 
			Homework homework = new Homework();
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			homework.setHomeworkId(homeworkId);
			String courseObj = new String(request.getParameter("courseObj").getBytes("iso-8859-1"), "UTF-8");
			homework.setCourseObj(courseObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			homework.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			homework.setContent(content);
			String hwRequire = new String(request.getParameter("hwRequire").getBytes("iso-8859-1"), "UTF-8");
			homework.setHwRequire(hwRequire);
			Timestamp publishDate = Timestamp.valueOf(request.getParameter("publishDate"));
			homework.setPublishDate(publishDate);

			/* 调用业务层执行添加操作 */
			String result = homeworkDAO.AddHomework(homework);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除作业：获取作业的作业id*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			/*调用业务逻辑层执行删除操作*/
			String result = homeworkDAO.DeleteHomework(homeworkId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新作业之前先根据homeworkId查询某个作业*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			Homework homework = homeworkDAO.GetHomework(homeworkId);

			// 客户端查询的作业对象，返回json数据格式, 将List<Book>组织成JSON字符串
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("homeworkId").value(homework.getHomeworkId());
			  stringer.key("courseObj").value(homework.getCourseObj());
			  stringer.key("title").value(homework.getTitle());
			  stringer.key("content").value(homework.getContent());
			  stringer.key("hwRequire").value(homework.getHwRequire());
			  stringer.key("publishDate").value(homework.getPublishDate());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新作业：获取作业参数，参数保存到新建的作业对象 */ 
			Homework homework = new Homework();
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			homework.setHomeworkId(homeworkId);
			String courseObj = new String(request.getParameter("courseObj").getBytes("iso-8859-1"), "UTF-8");
			homework.setCourseObj(courseObj);
			String title = new String(request.getParameter("title").getBytes("iso-8859-1"), "UTF-8");
			homework.setTitle(title);
			String content = new String(request.getParameter("content").getBytes("iso-8859-1"), "UTF-8");
			homework.setContent(content);
			String hwRequire = new String(request.getParameter("hwRequire").getBytes("iso-8859-1"), "UTF-8");
			homework.setHwRequire(hwRequire);
			Timestamp publishDate = Timestamp.valueOf(request.getParameter("publishDate"));
			homework.setPublishDate(publishDate);

			/* 调用业务层执行更新操作 */
			String result = homeworkDAO.UpdateHomework(homework);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
