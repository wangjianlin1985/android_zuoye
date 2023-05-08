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

    /*�������Ҫ��ѯ������: ������*/
    private LeaveClass leaveClassObj;
    public void setLeaveClassObj(LeaveClass leaveClassObj) {
        this.leaveClassObj = leaveClassObj;
    }
    public LeaveClass getLeaveClassObj() {
        return this.leaveClassObj;
    }

    /*�������Ҫ��ѯ������: ���ԭ��*/
    private String reason;
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getReason() {
        return this.reason;
    }

    /*�������Ҫ��ѯ������: ���ѧ��*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*�������Ҫ��ѯ������: ���ʱ��*/
    private String addTime;
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getAddTime() {
        return this.addTime;
    }

    /*�������Ҫ��ѯ������: ���״̬*/
    private String leaveState;
    public void setLeaveState(String leaveState) {
        this.leaveState = leaveState;
    }
    public String getLeaveState() {
        return this.leaveState;
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

    private int leaveId;
    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }
    public int getLeaveId() {
        return leaveId;
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
    @Resource LeaveClassDAO leaveClassDAO;
    @Resource StudentDAO studentDAO;
    @Resource LeaveInfoDAO leaveInfoDAO;

    /*��������LeaveInfo����*/
    private LeaveInfo leaveInfo;
    public void setLeaveInfo(LeaveInfo leaveInfo) {
        this.leaveInfo = leaveInfo;
    }
    public LeaveInfo getLeaveInfo() {
        return this.leaveInfo;
    }

    /*��ת�����LeaveInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�LeaveClass��Ϣ*/
        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        return "add_view";
    }

    /*���LeaveInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveClass leaveClassObj = leaveClassDAO.GetLeaveClassByLeaveClassId(leaveInfo.getLeaveClassObj().getLeaveClassId());
            leaveInfo.setLeaveClassObj(leaveClassObj);
            Student studentObj = studentDAO.GetStudentByStudentNo(leaveInfo.getStudentObj().getStudentNo());
            leaveInfo.setStudentObj(studentObj);
            leaveInfoDAO.AddLeaveInfo(leaveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLeaveInfo��Ϣ*/
    public String QueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(addTime == null) addTime = "";
        if(leaveState == null) leaveState = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(leaveClassObj, reason, studentObj, addTime, leaveState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(leaveClassObj, reason, studentObj, addTime, leaveState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryLeaveInfoOutputToExcel() { 
        if(reason == null) reason = "";
        if(addTime == null) addTime = "";
        if(leaveState == null) leaveState = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(leaveClassObj,reason,studentObj,addTime,leaveState);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveInfo��Ϣ��¼"; 
        String[] headers = { "���id","������","���ԭ��","���ѧ��","���ʱ��","���״̬"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"LeaveInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLeaveInfo��Ϣ*/
    public String FrontQueryLeaveInfo() {
        if(currentPage == 0) currentPage = 1;
        if(reason == null) reason = "";
        if(addTime == null) addTime = "";
        if(leaveState == null) leaveState = "";
        List<LeaveInfo> leaveInfoList = leaveInfoDAO.QueryLeaveInfoInfo(leaveClassObj, reason, studentObj, addTime, leaveState, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveInfoDAO.CalculateTotalPageAndRecordNumber(leaveClassObj, reason, studentObj, addTime, leaveState);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�LeaveInfo��Ϣ*/
    public String ModifyLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveId��ȡLeaveInfo����*/
        LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfoByLeaveId(leaveId);

        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("leaveInfo",  leaveInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LeaveInfo��Ϣ*/
    public String FrontShowLeaveInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveId��ȡLeaveInfo����*/
        LeaveInfo leaveInfo = leaveInfoDAO.GetLeaveInfoByLeaveId(leaveId);

        List<LeaveClass> leaveClassList = leaveClassDAO.QueryAllLeaveClassInfo();
        ctx.put("leaveClassList", leaveClassList);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("leaveInfo",  leaveInfo);
        return "front_show_view";
    }

    /*�����޸�LeaveInfo��Ϣ*/
    public String ModifyLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            LeaveClass leaveClassObj = leaveClassDAO.GetLeaveClassByLeaveClassId(leaveInfo.getLeaveClassObj().getLeaveClassId());
            leaveInfo.setLeaveClassObj(leaveClassObj);
            Student studentObj = studentDAO.GetStudentByStudentNo(leaveInfo.getStudentObj().getStudentNo());
            leaveInfo.setStudentObj(studentObj);
            leaveInfoDAO.UpdateLeaveInfo(leaveInfo);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LeaveInfo��Ϣ*/
    public String DeleteLeaveInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveInfoDAO.DeleteLeaveInfo(leaveId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
