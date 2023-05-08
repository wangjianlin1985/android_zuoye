package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.HomeworkDAO;
import com.chengxusheji.domain.Homework;
import com.chengxusheji.dao.CourseDAO;
import com.chengxusheji.domain.Course;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class HomeworkAction extends BaseAction {

    /*�������Ҫ��ѯ������: �γ�*/
    private Course courseObj;
    public void setCourseObj(Course courseObj) {
        this.courseObj = courseObj;
    }
    public Course getCourseObj() {
        return this.courseObj;
    }

    /*�������Ҫ��ѯ������: ��ҵ����*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int homeworkId;
    public void setHomeworkId(int homeworkId) {
        this.homeworkId = homeworkId;
    }
    public int getHomeworkId() {
        return homeworkId;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource CourseDAO courseDAO;
    @Resource HomeworkDAO homeworkDAO;

    /*��������Homework����*/
    private Homework homework;
    public void setHomework(Homework homework) {
        this.homework = homework;
    }
    public Homework getHomework() {
        return this.homework;
    }

    /*��ת�����Homework��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Course��Ϣ*/
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        return "add_view";
    }

    /*���Homework��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddHomework() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Course courseObj = courseDAO.GetCourseByCourseNo(homework.getCourseObj().getCourseNo());
            homework.setCourseObj(courseObj);
            homeworkDAO.AddHomework(homework);
            ctx.put("message",  java.net.URLEncoder.encode("Homework��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Homework���ʧ��!"));
            return "error";
        }
    }

    /*��ѯHomework��Ϣ*/
    public String QueryHomework() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Homework> homeworkList = homeworkDAO.QueryHomeworkInfo(courseObj, title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        homeworkDAO.CalculateTotalPageAndRecordNumber(courseObj, title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = homeworkDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = homeworkDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("homeworkList",  homeworkList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseObj", courseObj);
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryHomeworkOutputToExcel() { 
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Homework> homeworkList = homeworkDAO.QueryHomeworkInfo(courseObj,title,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Homework��Ϣ��¼"; 
        String[] headers = { "��ҵid","�γ�","��ҵ����","��ҵҪ��","��������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<homeworkList.size();i++) {
        	Homework homework = homeworkList.get(i); 
        	dataset.add(new String[]{homework.getHomeworkId() + "",homework.getCourseObj().getCourseName(),
homework.getTitle(),homework.getHwRequire(),new SimpleDateFormat("yyyy-MM-dd").format(homework.getPublishDate())});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Homework.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*ǰ̨��ѯHomework��Ϣ*/
    public String FrontQueryHomework() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Homework> homeworkList = homeworkDAO.QueryHomeworkInfo(courseObj, title, publishDate, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        homeworkDAO.CalculateTotalPageAndRecordNumber(courseObj, title, publishDate);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = homeworkDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = homeworkDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("homeworkList",  homeworkList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("courseObj", courseObj);
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("title", title);
        ctx.put("publishDate", publishDate);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�Homework��Ϣ*/
    public String ModifyHomeworkQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������homeworkId��ȡHomework����*/
        Homework homework = homeworkDAO.GetHomeworkByHomeworkId(homeworkId);

        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("homework",  homework);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�Homework��Ϣ*/
    public String FrontShowHomeworkQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������homeworkId��ȡHomework����*/
        Homework homework = homeworkDAO.GetHomeworkByHomeworkId(homeworkId);

        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("homework",  homework);
        return "front_show_view";
    }

    /*�����޸�Homework��Ϣ*/
    public String ModifyHomework() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Course courseObj = courseDAO.GetCourseByCourseNo(homework.getCourseObj().getCourseNo());
            homework.setCourseObj(courseObj);
            homeworkDAO.UpdateHomework(homework);
            ctx.put("message",  java.net.URLEncoder.encode("Homework��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Homework��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��Homework��Ϣ*/
    public String DeleteHomework() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            homeworkDAO.DeleteHomework(homeworkId);
            ctx.put("message",  java.net.URLEncoder.encode("Homeworkɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Homeworkɾ��ʧ��!"));
            return "error";
        }
    }

}
