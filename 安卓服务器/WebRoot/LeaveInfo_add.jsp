<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.LeaveClass" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�LeaveClass��Ϣ
    List<LeaveClass> leaveClassList = (List<LeaveClass>)request.getAttribute("leaveClassList");
    //��ȡ���е�Student��Ϣ
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>������</TITLE> 
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
    var reason = document.getElementById("leaveInfo.reason").value;
    if(reason=="") {
        alert('���������ԭ��!');
        return false;
    }
    var content = document.getElementById("leaveInfo.content").value;
    if(content=="") {
        alert('�������������!');
        return false;
    }
    var leaveState = document.getElementById("leaveInfo.leaveState").value;
    if(leaveState=="") {
        alert('���������״̬!');
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
    <td width=30%>������:</td>
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
    <td width=30%>���ԭ��:</td>
    <td width=70%><input id="leaveInfo.reason" name="leaveInfo.reason" type="text" size="50" /></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><textarea id="leaveInfo.content" name="leaveInfo.content" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>���ѧ��:</td>
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
    <td width=30%>���ʱ��:</td>
    <td width=70%><input id="leaveInfo.addTime" name="leaveInfo.addTime" type="text" size="20" /></td>
  </tr>

  <tr>
    <td width=30%>���״̬:</td>
    <td width=70%><input id="leaveInfo.leaveState" name="leaveInfo.leaveState" type="text" size="20" /></td>
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
