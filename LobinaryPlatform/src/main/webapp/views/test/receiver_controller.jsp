<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>ReceiverController测试页</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
<body>
	<div>
		<form method="post"
			action="<%=basePath%>control/platform/receiver/message/send_message.anonymous">
			<div align="center">
				关于接受信息的功能测试！
				<table border="1" width="600" align="center">
					<tr align="center">
						<td width="100">ID:</td>
						<td align="center"><input type="text" name="id" value="1"></td>
					</tr>
					<tr align="center">
						<td width="100">发送方:</td>
						<td align="center"><input type="text" name="sender"
							value="发送方名称：sender1"></td>
					</tr>
					<tr align="center">
						<td width="100">接收方:</td>
						<td align="center"><input type="text" name="receiver"
							value="接收方名称：receiver6"></td>
					</tr>
					<tr align="center">
						<td width="100">信息格式:</td>
						<td align="center"><input type="text" name="messageType"
							value="100001"></td>
					</tr>
					<tr align="center">
						<td width="100">信息内容:</td>
						<td align="center"><textarea cols="65" rows="10"
								name="messageInfo">
   		信息内容：
				这些内容是带格式的内容！
							MessageInfo
				</textarea></td>
					</tr>
					<tr align="center">
						<td width="100">发送时间:</td>
						<td align="center"><input type="text" name="sendDate"
							value="2014-06-06"></td>
					</tr>
					<tr>
						<td align="center">功能</td>
						<td align="center"><input type="reset" value="重置">
							&nbsp;&nbsp;&nbsp; <input type="submit" value="提交"></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
	<div>
		<form method="post" enctype="multipart/form-data"
			action="<%=basePath%>control/platform/receiver/file/sendFile.anonymous">
			<div align="center">
				关于接受文件的功能测试！
				<table border="1" width="600" align="center">
					<tr align="center">
						<td width="100">ID:</td>
						<td align="center"><input type="text" name="id" value="1"></td>
					</tr>
					<tr align="center">
						<td width="100">发送方:</td>
						<td align="center"><input type="text" name="sender"
							value="发送方名称：sender1"></td>
					</tr>
					<tr align="center">
						<td width="100">接收方:</td>
						<td align="center"><input type="text" name="receiver"
							value="接收方名称：receiver6"></td>
					</tr>
					<tr align="center">
						<td width="100">文件格式:</td>
						<td align="center"><input type="text" name="fileType"
							value="100001"></td>
					</tr>
					<tr align="center">
						<td width="100">文件路径:</td>
						<td align="center"><input type="text" name="fileLocation"
							value="100001"></td>
					</tr>
					<tr align="center">
						<td width="100">文件:</td>
						<td align="center"><input type="file" name="file"></td>
					</tr>
					<tr align="center">
						<td width="100">发送时间:</td>
						<td align="center"><input type="text" name="sendDate"
							value="2014-06-06"></td>
					</tr>
					<tr>
						<td align="center">功能</td>
						<td align="center"><input type="reset" value="重置">
							&nbsp;&nbsp;&nbsp; <input type="submit" value="提交"></td>
					</tr>
				</table>
			</div>
		</form>
	</div>
</body>
</html>
