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

    /*界面层需要查询的属性: 课程*/
    private Course courseObj;
    public void setCourseObj(Course courseObj) {
        this.courseObj = courseObj;
    }
    public Course getCourseObj() {
        return this.courseObj;
    }

    /*界面层需要查询的属性: 作业标题*/
    private String title;
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return this.title;
    }

    /*界面层需要查询的属性: 发布日期*/
    private String publishDate;
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }
    public String getPublishDate() {
        return this.publishDate;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
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

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource CourseDAO courseDAO;
    @Resource HomeworkDAO homeworkDAO;

    /*待操作的Homework对象*/
    private Homework homework;
    public void setHomework(Homework homework) {
        this.homework = homework;
    }
    public Homework getHomework() {
        return this.homework;
    }

    /*跳转到添加Homework视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Course信息*/
        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        return "add_view";
    }

    /*添加Homework信息*/
    @SuppressWarnings("deprecation")
    public String AddHomework() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Course courseObj = courseDAO.GetCourseByCourseNo(homework.getCourseObj().getCourseNo());
            homework.setCourseObj(courseObj);
            homeworkDAO.AddHomework(homework);
            ctx.put("message",  java.net.URLEncoder.encode("Homework添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Homework添加失败!"));
            return "error";
        }
    }

    /*查询Homework信息*/
    public String QueryHomework() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Homework> homeworkList = homeworkDAO.QueryHomeworkInfo(courseObj, title, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        homeworkDAO.CalculateTotalPageAndRecordNumber(courseObj, title, publishDate);
        /*获取到总的页码数目*/
        totalPage = homeworkDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryHomeworkOutputToExcel() { 
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Homework> homeworkList = homeworkDAO.QueryHomeworkInfo(courseObj,title,publishDate);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Homework信息记录"; 
        String[] headers = { "作业id","课程","作业标题","作业要求","发布日期"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Homework.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
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
    /*前台查询Homework信息*/
    public String FrontQueryHomework() {
        if(currentPage == 0) currentPage = 1;
        if(title == null) title = "";
        if(publishDate == null) publishDate = "";
        List<Homework> homeworkList = homeworkDAO.QueryHomeworkInfo(courseObj, title, publishDate, currentPage);
        /*计算总的页数和总的记录数*/
        homeworkDAO.CalculateTotalPageAndRecordNumber(courseObj, title, publishDate);
        /*获取到总的页码数目*/
        totalPage = homeworkDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的Homework信息*/
    public String ModifyHomeworkQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键homeworkId获取Homework对象*/
        Homework homework = homeworkDAO.GetHomeworkByHomeworkId(homeworkId);

        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("homework",  homework);
        return "modify_view";
    }

    /*查询要修改的Homework信息*/
    public String FrontShowHomeworkQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键homeworkId获取Homework对象*/
        Homework homework = homeworkDAO.GetHomeworkByHomeworkId(homeworkId);

        List<Course> courseList = courseDAO.QueryAllCourseInfo();
        ctx.put("courseList", courseList);
        ctx.put("homework",  homework);
        return "front_show_view";
    }

    /*更新修改Homework信息*/
    public String ModifyHomework() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Course courseObj = courseDAO.GetCourseByCourseNo(homework.getCourseObj().getCourseNo());
            homework.setCourseObj(courseObj);
            homeworkDAO.UpdateHomework(homework);
            ctx.put("message",  java.net.URLEncoder.encode("Homework信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Homework信息更新失败!"));
            return "error";
       }
   }

    /*删除Homework信息*/
    public String DeleteHomework() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            homeworkDAO.DeleteHomework(homeworkId);
            ctx.put("message",  java.net.URLEncoder.encode("Homework删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Homework删除失败!"));
            return "error";
        }
    }

}
