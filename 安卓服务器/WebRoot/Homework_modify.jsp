<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Homework" %>
<%@ page import="com.chengxusheji.domain.Course" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Course��Ϣ
    List<Course> courseList = (List<Course>)request.getAttribute("courseList");
    Homework homework = (Homework)request.getAttribute("homework");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸���ҵ</TITLE>
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
/*��֤��*/
function checkForm() {
    var title = document.getElementById("homework.title").value;
    if(title=="") {
        alert('��������ҵ����!');
        return false;
    }
    var content = document.getElementById("homework.content").value;
    if(content=="") {
        alert('��������ҵ����!');
        return false;
    }
    var hwRequire = document.getElementById("homework.hwRequire").value;
    if(hwRequire=="") {
        alert('��������ҵҪ��!');
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
    <TD align="left" vAlign=top ><s:form action="Homework/Homework_ModifyHomework.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��ҵid:</td>
    <td width=70%><input id="homework.homeworkId" name="homework.homeworkId" type="text" value="<%=homework.getHomeworkId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>�γ�:</td>
    <td width=70%>
      <select name="homework.courseObj.courseNo">
      <%
        for(Course course:courseList) {
          String selected = "";
          if(course.getCourseNo().equals(homework.getCourseObj().getCourseNo()))
            selected = "selected";
      %>
          <option value='<%=course.getCourseNo() %>' <%=selected %>><%=course.getCourseName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><input id="homework.title" name="homework.title" type="text" size="50" value='<%=homework.getTitle() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ҵ����:</td>
    <td width=70%><textarea id="homework.content" name="homework.content" rows=5 cols=50><%=homework.getContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>��ҵҪ��:</td>
    <td width=70%><input id="homework.hwRequire" name="homework.hwRequire" type="text" size="50" value='<%=homework.getHwRequire() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <% DateFormat publishDateSDF = new SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><input type="text" readonly  id="homework.publishDate"  name="homework.publishDate" onclick="setDay(this);" value='<%=publishDateSDF.format(homework.getPublishDate()) %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
