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
import com.chengxusheji.domain.Course;
import com.chengxusheji.domain.Homework;

@Service @Transactional
public class HomeworkDAO {

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
    public void AddHomework(Homework homework) throws Exception {
    	Session s = factory.getCurrentSession();
     s.save(homework);
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Homework> QueryHomeworkInfo(Course courseObj,String title,String publishDate,int currentPage) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Homework homework where 1=1";
    	if(null != courseObj && !courseObj.getCourseNo().equals("")) hql += " and homework.courseObj.courseNo='" + courseObj.getCourseNo() + "'";
    	if(!title.equals("")) hql = hql + " and homework.title like '%" + title + "%'";
    	if(!publishDate.equals("")) hql = hql + " and homework.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	/*计算当前显示页码的开始记录*/
    	int startIndex = (currentPage-1) * this.PAGE_SIZE;
    	q.setFirstResult(startIndex);
    	q.setMaxResults(this.PAGE_SIZE);
    	List homeworkList = q.list();
    	return (ArrayList<Homework>) homeworkList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Homework> QueryHomeworkInfo(Course courseObj,String title,String publishDate) { 
    	Session s = factory.getCurrentSession();
    	String hql = "From Homework homework where 1=1";
    	if(null != courseObj && !courseObj.getCourseNo().equals("")) hql += " and homework.courseObj.courseNo='" + courseObj.getCourseNo() + "'";
    	if(!title.equals("")) hql = hql + " and homework.title like '%" + title + "%'";
    	if(!publishDate.equals("")) hql = hql + " and homework.publishDate like '%" + publishDate + "%'";
    	Query q = s.createQuery(hql);
    	List homeworkList = q.list();
    	return (ArrayList<Homework>) homeworkList;
    }

    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public ArrayList<Homework> QueryAllHomeworkInfo() {
        Session s = factory.getCurrentSession(); 
        String hql = "From Homework";
        Query q = s.createQuery(hql);
        List homeworkList = q.list();
        return (ArrayList<Homework>) homeworkList;
    }

    /*计算总的页数和记录数*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public void CalculateTotalPageAndRecordNumber(Course courseObj,String title,String publishDate) {
        Session s = factory.getCurrentSession();
        String hql = "From Homework homework where 1=1";
        if(null != courseObj && !courseObj.getCourseNo().equals("")) hql += " and homework.courseObj.courseNo='" + courseObj.getCourseNo() + "'";
        if(!title.equals("")) hql = hql + " and homework.title like '%" + title + "%'";
        if(!publishDate.equals("")) hql = hql + " and homework.publishDate like '%" + publishDate + "%'";
        Query q = s.createQuery(hql);
        List homeworkList = q.list();
        recordNumber = homeworkList.size();
        int mod = recordNumber % this.PAGE_SIZE;
        totalPage = recordNumber / this.PAGE_SIZE;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取对象*/
    @Transactional(propagation=Propagation.NOT_SUPPORTED)
    public Homework GetHomeworkByHomeworkId(int homeworkId) {
        Session s = factory.getCurrentSession();
        Homework homework = (Homework)s.get(Homework.class, homeworkId);
        return homework;
    }

    /*更新Homework信息*/
    public void UpdateHomework(Homework homework) throws Exception {
        Session s = factory.getCurrentSession();
        s.update(homework);
    }

    /*删除Homework信息*/
    public void DeleteHomework (int homeworkId) throws Exception {
        Session s = factory.getCurrentSession();
        Object homework = s.load(Homework.class, homeworkId);
        s.delete(homework);
    }

}
