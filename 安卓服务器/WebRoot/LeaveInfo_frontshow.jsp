<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.LeaveInfo" %>
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
    LeaveInfo leaveInfo = (LeaveInfo)request.getAttribute("leaveInfo");

%>
<HTML><HEAD><TITLE>�鿴���</TITLE>
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
    <td width=30%>���id:</td>
    <td width=70%><%=leaveInfo.getLeaveId() %></td>
  </tr>

  <tr>
    <td width=30%>������:</td>
    <td width=70%>
      <%=leaveInfo.getLeaveClassObj().getLeaveClassName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ԭ��:</td>
    <td width=70%><%=leaveInfo.getReason() %></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><%=leaveInfo.getContent() %></td>
  </tr>

  <tr>
    <td width=30%>���ѧ��:</td>
    <td width=70%>
      <%=leaveInfo.getStudentObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���ʱ��:</td>
    <td width=70%><%=leaveInfo.getAddTime() %></td>
  </tr>

  <tr>
    <td width=30%>���״̬:</td>
    <td width=70%><%=leaveInfo.getLeaveState() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
