package com.chengxusheji.dao;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.chengxusheji.domain.LeaveClass;
import com.chengxusheji.domain.Student;
import com.chengxusheji.domain.LeaveInfo;

@Service @Transactional
public class LeaveInfoDAO {

	@Resource SessionFactory factory;
    /*每页显示记录数目*/
    private final int PAGE_SIZE = 10;

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加图书信息*/
    public void AddLeaveInfo(LeaveInfo leaveInfo) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(leaveInfo);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveInfo> QueryLeaveInfoInfo(LeaveClass leaveClassObj,String reason,Student studentObj,String addTime,String leaveState,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveInfo leaveInfo where 1=1";
    	if(null != leaveClassObj && leaveClassObj.getLeaveClassId()!=0) hql += " and leaveInfo.leaveClassObj.leaveClassId=" + leaveClassObj.getLeaveClassId();
    	if(!reason.equals("")) hql = hql + " and leaveInfo.reason like '%" + reason + "%'";
    	if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and leaveInfo.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
    	if(!addTime.equals("")) hql = hql + " and leaveInfo.addTime like '%" + addTime + "%'";
    	if(!leaveState.equals("")) hql = hql + " and leaveInfo.leaveState like '%" + leaveState + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List leaveInfoList = q.list();
    	return (ArrayList<LeaveInfo>) leaveInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveInfo> QueryLeaveInfoInfo(LeaveClass leaveClassObj,String reason,Student studentObj,String addTime,String leaveState) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveInfo leaveInfo where 1=1";
    	if(null != leaveClassObj && leaveClassObj.getLeaveClassId()!=0) hql += " and leaveInfo.leaveClassObj.leaveClassId=" + leaveClassObj.getLeaveClassId();
    	if(!reason.equals("")) hql = hql + " and leaveInfo.reason like '%" + reason + "%'";
    	if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and leaveInfo.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
    	if(!addTime.equals("")) hql = hql + " and leaveInfo.addTime like '%" + addTime + "%'";
    	if(!leaveState.equals("")) hql = hql + " and leaveInfo.leaveState like '%" + leaveState + "%'";
    	Query q = s.createQuery(hql);
    	List leaveInfoList = q.list();
    	return (ArrayList<LeaveInfo>) leaveInfoList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveInfo> QueryAllLeaveInfoInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LeaveInfo";
        Query q = s.createQuery(hql);
        List leaveInfoList = q.list();
        return (ArrayList<LeaveInfo>) leaveInfoList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(LeaveClass leaveClassObj,String reason,Student studentObj,String addTime,String leaveState) {
        Session s = factory.getCurrentSession();
        String hql = "From LeaveInfo leaveInfo where 1=1";
        if(null != leaveClassObj && leaveClassObj.getLeaveClassId()!=0) hql += " and leaveInfo.leaveClassObj.leaveClassId=" + leaveClassObj.getLeaveClassId();
        if(!reason.equals("")) hql = hql + " and leaveInfo.reason like '%" + reason + "%'";
        if(null != studentObj && !studentObj.getStudentNo().equals("")) hql += " and leaveInfo.studentObj.studentNo='" + studentObj.getStudentNo() + "'";
        if(!addTime.equals("")) hql = hql + " and leaveInfo.addTime like '%" + addTime + "%'";
        if(!leaveState.equals("")) hql = hql + " and leaveInfo.leaveState like '%" + leaveState + "%'";
        Query q = s.createQuery(hql);
        List leaveInfoList = q.list();
        recordNumber = leaveInfoList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LeaveInfo GetLeaveInfoByLeaveId(int leaveId) {
        Session s = factory.getCurrentSession();
        LeaveInfo leaveInfo = (LeaveInfo)s.get(LeaveInfo.class, leaveId);
        return leaveInfo;
    }

    /*更新LeaveInfo信息*/
    public void UpdateLeaveInfo(LeaveInfo leaveInfo) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(leaveInfo);
    }

    /*删除LeaveInfo信息*/
    public void DeleteLeaveInfo (int leaveId) throws Exception {
        Session s = factory.getCurrentSession();
        Object leaveInfo = s.load(LeaveInfo.class, leaveId);
        s.delete(leaveInfo);
    }

}
