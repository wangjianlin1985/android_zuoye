<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.LeaveClass" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的LeaveClass信息
    List<LeaveClass> leaveClassList = (List<LeaveClass>)request.getAttribute("leaveClassList");
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加请假</TITLE> 
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
    var reason = document.getElementById("leaveInfo.reason").value;
    if(reason=="") {
        alert('请输入请假原因!');
        return false;
    }
    var content = document.getElementById("leaveInfo.content").value;
    if(content=="") {
        alert('请输入请假详情!');
        return false;
    }
    var leaveState = document.getElementById("leaveInfo.leaveState").value;
    if(leaveState=="") {
        alert('请输入审核状态!');
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
    <s:form action="LeaveInfo/LeaveInfo_AddLeaveInfo.action" method="post" id="leaveInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>请假类别:</td>
    <td width=70%>
      <select name="leaveInfo.leaveClassObj.leaveClassId">
      <%
        for(LeaveClass leaveClass:leaveClassList) {
      %>
          <option value='<%=leaveClass.getLeaveClassId() %>'><%=leaveClass.getLeaveClassName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>请假原因:</td>
    <td width=70%><input id="leaveInfo.reason" name="leaveInfo.reason" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>请假详情:</td>
    <td width=70%><textarea id="leaveInfo.content" name="leaveInfo.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>请假学生:</td>
    <td width=70%>
      <select name="leaveInfo.studentObj.studentNo">
      <%
        for(Student student:studentList) {
      %>
          <option value='<%=student.getStudentNo() %>'><%=student.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>请假时间:</td>
    <td width=70%><input id="leaveInfo.addTime" name="leaveInfo.addTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>审核状态:</td>
    <td width=70%><input id="leaveInfo.leaveState" name="leaveInfo.leaveState" type="text" size="20" /></td>
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
