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
import com.chengxusheji.dao.LeaveClassDAO;
import com.chengxusheji.domain.LeaveClass;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class LeaveClassAction extends BaseAction {

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

    private int leaveClassId;
    public void setLeaveClassId(int leaveClassId) {
        this.leaveClassId = leaveClassId;
    }
    public int getLeaveClassId() {
        return leaveClassId;
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

    /*��������LeaveClass����*/
    private LeaveClass leaveClass;
    public void setLeaveClass(LeaveClass leaveClass) {
        this.leaveClass = leaveClass;
    }
    public LeaveClass getLeaveClass() {
        return this.leaveClass;
    }

    /*��ת�����LeaveClass��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���LeaveClass��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddLeaveClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            leaveClassDAO.AddLeaveClass(leaveClass);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveClass��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveClass���ʧ��!"));
            return "error";
        }
    }

    /*��ѯLeaveClass��Ϣ*/
    public String QueryLeaveClass() {
        if(currentPage == 0) currentPage = 1;
        List<LeaveClass> leaveClassList = leaveClassDAO.QueryLeaveClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = leaveClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveClassList",  leaveClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryLeaveClassOutputToExcel() { 
        List<LeaveClass> leaveClassList = leaveClassDAO.QueryLeaveClassInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "LeaveClass��Ϣ��¼"; 
        String[] headers = { "������id","����������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<leaveClassList.size();i++) {
        	LeaveClass leaveClass = leaveClassList.get(i); 
        	dataset.add(new String[]{leaveClass.getLeaveClassId() + "",leaveClass.getLeaveClassName()});
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
			response.setHeader("Content-disposition","attachment; filename="+"LeaveClass.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯLeaveClass��Ϣ*/
    public String FrontQueryLeaveClass() {
        if(currentPage == 0) currentPage = 1;
        List<LeaveClass> leaveClassList = leaveClassDAO.QueryLeaveClassInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        leaveClassDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = leaveClassDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = leaveClassDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("leaveClassList",  leaveClassList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�LeaveClass��Ϣ*/
    public String ModifyLeaveClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveClassId��ȡLeaveClass����*/
        LeaveClass leaveClass = leaveClassDAO.GetLeaveClassByLeaveClassId(leaveClassId);

        ctx.put("leaveClass",  leaveClass);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�LeaveClass��Ϣ*/
    public String FrontShowLeaveClassQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������leaveClassId��ȡLeaveClass����*/
        LeaveClass leaveClass = leaveClassDAO.GetLeaveClassByLeaveClassId(leaveClassId);

        ctx.put("leaveClass",  leaveClass);
        return "front_show_view";
    }

    /*�����޸�LeaveClass��Ϣ*/
    public String ModifyLeaveClass() {
        ActionContext ctx = ActionContext.getContext();
        try {
            leaveClassDAO.UpdateLeaveClass(leaveClass);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveClass��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveClass��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��LeaveClass��Ϣ*/
    public String DeleteLeaveClass() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            leaveClassDAO.DeleteLeaveClass(leaveClassId);
            ctx.put("message",  java.net.URLEncoder.encode("LeaveClassɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("LeaveClassɾ��ʧ��!"));
            return "error";
        }
    }

}
