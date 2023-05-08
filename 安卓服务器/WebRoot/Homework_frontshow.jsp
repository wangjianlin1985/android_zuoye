<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Homework" %>
<%@ page import="com.chengxusheji.domain.Course" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Course信息
    List<Course> courseList = (List<Course>)request.getAttribute("courseList");
    Homework homework = (Homework)request.getAttribute("homework");

%>
<HTML><HEAD><TITLE>查看作业</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>作业id:</td>
    <td width=70%><%=homework.getHomeworkId() %></td>
  </tr>

  <tr>
    <td width=30%>课程:</td>
    <td width=70%>
      <%=homework.getCourseObj().getCourseName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>作业标题:</td>
    <td width=70%><%=homework.getTitle() %></td>
  </tr>

  <tr>
    <td width=30%>作业内容:</td>
    <td width=70%><%=homework.getContent() %></td>
  </tr>

  <tr>
    <td width=30%>作业要求:</td>
    <td width=70%><%=homework.getHwRequire() %></td>
  </tr>

  <tr>
    <td width=30%>发布日期:</td>
        <% java.text.DateFormat publishDateSDF = new java.text.SimpleDateFormat("yyyy-MM-dd");  %>
    <td width=70%><%=publishDateSDF.format(homework.getPublishDate()) %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
