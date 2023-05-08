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

	/*������ҵҵ������*/
	private HomeworkDAO homeworkDAO = new HomeworkDAO();

	/*Ĭ�Ϲ��캯��*/
	public HomeworkServlet() {
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
			/*��ȡ��ѯ��ҵ�Ĳ�����Ϣ*/
			String courseObj = "";
			if (request.getParameter("courseObj") != null)
				courseObj = request.getParameter("courseObj");
			String title = request.getParameter("title");
			title = title == null ? "" : new String(request.getParameter(
					"title").getBytes("iso-8859-1"), "UTF-8");
			Timestamp publishDate = null;
			if (request.getParameter("publishDate") != null)
				publishDate = Timestamp.valueOf(request.getParameter("publishDate"));

			/*����ҵ���߼���ִ����ҵ��ѯ*/
			List<Homework> homeworkList = homeworkDAO.QueryHomework(courseObj,title,publishDate);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
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
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* �����ҵ����ȡ��ҵ�������������浽�½�����ҵ���� */ 
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

			/* ����ҵ���ִ����Ӳ��� */
			String result = homeworkDAO.AddHomework(homework);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ҵ����ȡ��ҵ����ҵid*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = homeworkDAO.DeleteHomework(homeworkId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*������ҵ֮ǰ�ȸ���homeworkId��ѯĳ����ҵ*/
			int homeworkId = Integer.parseInt(request.getParameter("homeworkId"));
			Homework homework = homeworkDAO.GetHomework(homeworkId);

			// �ͻ��˲�ѯ����ҵ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
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
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ������ҵ����ȡ��ҵ�������������浽�½�����ҵ���� */ 
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

			/* ����ҵ���ִ�и��²��� */
			String result = homeworkDAO.UpdateHomework(homework);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
