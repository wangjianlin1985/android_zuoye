<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Course" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Course信息
    List<Course> courseList = (List<Course>)request.getAttribute("courseList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加作业</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    var title = document.getElementById("homework.title").value;
    if(title=="") {
        alert('请输入作业标题!');
        return false;
    }
    var content = document.getElementById("homework.content").value;
    if(content=="") {
        alert('请输入作业内容!');
        return false;
    }
    var hwRequire = document.getElementById("homework.hwRequire").value;
    if(hwRequire=="") {
        alert('请输入作业要求!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="Homework/Homework_AddHomework.action" method="post" id="homeworkAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>课程:</td>
    <td width=70%>
      <select name="homework.courseObj.courseNo">
      <%
        for(Course course:courseList) {
      %>
          <option value='<%=course.getCourseNo() %>'><%=course.getCourseName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>作业标题:</td>
    <td width=70%><input id="homework.title" name="homework.title" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>作业内容:</td>
    <td width=70%><textarea id="homework.content" name="homework.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>作业要求:</td>
    <td width=70%><input id="homework.hwRequire" name="homework.hwRequire" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>发布日期:</td>
    <td width=70%><input type="text" readonly id="homework.publishDate"  name="homework.publishDate" onclick="setDay(this);"/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
