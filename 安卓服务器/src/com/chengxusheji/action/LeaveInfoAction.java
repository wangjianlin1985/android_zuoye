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
import com.chengxusheji.dao.LeaveInfoDAO;
import com.chengxusheji.domain.LeaveInfo;
import com.chengxusheji.dao.LeaveClassDAO;
import com.chengxusheji.domain.LeaveClass;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LeaveInfoAction extends BaseAction {

    /*界面层需要查询的属性: 请假类别*/
    private LeaveClass leaveClassObj;
    public void setLeaveClassObj(LeaveClass leaveClassObj) {
        this.leaveClassObj = leaveClassObj;
    }
    public LeaveClass getLeaveClassObj() {
        return this.leaveClassObj;
    }

    /*界面层需要查询的属性: 请假原因*/
    private String reason;
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }

    /*界面层需要查询的属性: 请假学生*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*界面层需要查询的属性: 请假时间*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

    /*界面层需要查询的属性: 审核状态*/
    private String leaveState;
    public void setLeaveState(String leaveState) {
        this.leaveState = leaveState;
    }
    public String getLeaveState() {
        return this.leaveState;
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

    private int leaveId;
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }
    public int getLeaveId() {
        return leaveId;
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
    @Resource LeaveClassDAO leaveClassDAO;
    @Resource StudentDAO studentDAO;
    @Resource LeaveInfoDAO leaveInfoDAO;

    /*待操作的LeaveInfo对象*/
    private LeaveInfo leaveInfo;
    public void setLeaveInfo(LeaveInfo leaveInfo) {
        this.leaveInfo = leaveInfo;
    }
    public LeaveInfo getLeaveInfo() {
        return this.leaveInfo;
    }

    /*跳转到添加LeaveInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的LeaveClass信息*/
        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*添加LeaveInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveClass leaveClassObj = leaveClassDAO.GetLeaveClassByLeaveClassId(leaveInfo.getLeaveClassObj().getLeaveClassId());
            leaveInfo.setLeaveClassObj(leaveClassObj);
            Student studentObj = studentDAO.GetStudentByStudentNo(leaveInfo.getStudentObj().getStudentNo());
            leaveInfo.setStudentObj(studentObj);
            leaveInfoDAO.AddLeaveInfo(leaveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo添加失败!"));
            return "error";
        }
    }

    /*查询LeaveInfo信息*/
    public String QueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(addTime == null) addTime = "";
        if(leaveState == null) leaveState = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(leaveClassObj, reason, studentObj, addTime, leaveState, currentPage);
        /*计算总的页数和总的记录数*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(leaveClassObj, reason, studentObj, addTime, leaveState);
        /*获取到总的页码数目*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leaveInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveInfoList",  leaveInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveClassObj", leaveClassObj);
        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        ctx.put("reason", reason);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("addTime", addTime);
        ctx.put("leaveState", leaveState);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryLeaveInfoOutputToExcel() { 
        if(reason == null) reason = "";
        if(addTime == null) addTime = "";
        if(leaveState == null) leaveState = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(leaveClassObj,reason,studentObj,addTime,leaveState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveInfo信息记录"; 
        String[] headers = { "请假id","请假类别","请假原因","请假学生","请假时间","审核状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveInfoList.size();i++) {
        	LeaveInfo leaveInfo = leaveInfoList.get(i); 
        	dataset.add(new String[]{leaveInfo.getLeaveId() + "",leaveInfo.getLeaveClassObj().getLeaveClassName(),
leaveInfo.getReason(),leaveInfo.getStudentObj().getName(),
leaveInfo.getAddTime(),leaveInfo.getLeaveState()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LeaveInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询LeaveInfo信息*/
    public String FrontQueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(addTime == null) addTime = "";
        if(leaveState == null) leaveState = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(leaveClassObj, reason, studentObj, addTime, leaveState, currentPage);
        /*计算总的页数和总的记录数*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(leaveClassObj, reason, studentObj, addTime, leaveState);
        /*获取到总的页码数目*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = leaveInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveInfoList",  leaveInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("leaveClassObj", leaveClassObj);
        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        ctx.put("reason", reason);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("addTime", addTime);
        ctx.put("leaveState", leaveState);
        return "front_query_view";
    }

    /*查询要修改的LeaveInfo信息*/
    public String ModifyLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfoByLeaveId(leaveId);

        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("leaveInfo",  leaveInfo);
        return "modify_view";
    }

    /*查询要修改的LeaveInfo信息*/
    public String FrontShowLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键leaveId获取LeaveInfo对象*/
        LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfoByLeaveId(leaveId);

        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("leaveInfo",  leaveInfo);
        return "front_show_view";
    }

    /*更新修改LeaveInfo信息*/
    public String ModifyLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveClass leaveClassObj = leaveClassDAO.GetLeaveClassByLeaveClassId(leaveInfo.getLeaveClassObj().getLeaveClassId());
            leaveInfo.setLeaveClassObj(leaveClassObj);
            Student studentObj = studentDAO.GetStudentByStudentNo(leaveInfo.getStudentObj().getStudentNo());
            leaveInfo.setStudentObj(studentObj);
            leaveInfoDAO.UpdateLeaveInfo(leaveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除LeaveInfo信息*/
    public String DeleteLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveInfoDAO.DeleteLeaveInfo(leaveId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo删除失败!"));
            return "error";
        }
    }

}
