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

@Service @Transactional
public class LeaveClassDAO {

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
    public void AddLeaveClass(LeaveClass leaveClass) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(leaveClass);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveClass> QueryLeaveClassInfo(int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveClass leaveClass where 1=1";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List leaveClassList = q.list();
    	return (ArrayList<LeaveClass>) leaveClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveClass> QueryLeaveClassInfo() { 
    	Session s = factory.getCurrentSession();
    	String hql = "From LeaveClass leaveClass where 1=1";
    	Query q = s.createQuery(hql);
    	List leaveClassList = q.list();
    	return (ArrayList<LeaveClass>) leaveClassList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<LeaveClass> QueryAllLeaveClassInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From LeaveClass";
        Query q = s.createQuery(hql);
        List leaveClassList = q.list();
        return (ArrayList<LeaveClass>) leaveClassList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber() {
        Session s = factory.getCurrentSession();
        String hql = "From LeaveClass leaveClass where 1=1";
        Query q = s.createQuery(hql);
        List leaveClassList = q.list();
        recordNumber = leaveClassList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public LeaveClass GetLeaveClassByLeaveClassId(int leaveClassId) {
        Session s = factory.getCurrentSession();
        LeaveClass leaveClass = (LeaveClass)s.get(LeaveClass.class, leaveClassId);
        return leaveClass;
    }

    /*更新LeaveClass信息*/
    public void UpdateLeaveClass(LeaveClass leaveClass) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(leaveClass);
    }

    /*删除LeaveClass信息*/
    public void DeleteLeaveClass (int leaveClassId) throws Exception {
        Session s = factory.getCurrentSession();
        Object leaveClass = s.load(LeaveClass.class, leaveClassId);
        s.delete(leaveClass);
    }

}
