<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>消息管理页面</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript" src="<%=basePath%>include/js/jquery-2.1.1.js"></script>
</head>

<body>
	<table border="1" cellspacing="1"
		style="border-collapse:collapse;cellspacing:'0';border-color: blue;" width="100%">
		<tr>
			<th colspan="7">信息列表</th>
		</tr>
		<tr>
			<td>ID</td>
			<td>发送方</td>
			<td>接收方</td>
			<td>消息种类</td>
			<td>消息内容</td>
			<td>发送时间</td>
			<td>操作</td>
		</tr>
		<c:forEach var="item" items="${messageList}">
			<tr>
				<td>${item.id}</td>
				<td>${item.sender}</td>
				<td>${item.receiver}</td>
				<td>${item.messageType}</td>
				<td>${item.messageInfo}</td>
				<td>${item.sendDate}</td>
				<td>暂无:操作</td>
			</tr>
		</c:forEach>
	</table>
	<form
		action="<%=basePath%>control/platform/receiver/message/message_list.anonymous"
		id="message_list_form">

		<jsp:include page="../../include/page.jsp">
			<jsp:param name="formIdParam" value="message_list_form" />
		</jsp:include>
	</form>
</body>
</html>
