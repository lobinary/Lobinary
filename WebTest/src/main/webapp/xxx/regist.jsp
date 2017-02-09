<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'regist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
	<script type="text/javascript" src="/WebTest/js/base.js"></script>
  	<script type="text/javascript">
  
  	function 加载数据(){
  		l($('#资金类型ID'));
  		加载资金类型数据();
  	}
  	
  	function 加载资金类型数据(){
  		$.ajax({//Jquery中的ajax请求方法
  		    url: "/WebTest/servlet/registServlet", //请求的url地址
  		    dataType: "json",   			//返回格式为json
  		    async: true, 					//请求是否异步，默认为异步，这也是ajax重要特性
  		    data: { "id": "value" },    	//参数值
  		    type: "post",   				//请求方式
  		    beforeSend: function() {		//请求前的处理
  		    	l("发送前");
  		    },
  		    success: function(req) {		//请求成功时处理
  		        l("请求成功,准备解析数据");
  		    
  		    	//清空select
  		    	$('#资金类型ID option').each(function(){//遍历select 删除已经存在的元素
  		    	    //if( $(this).val() == '5'){ //如果这个元素的值  也就是option中的value等于5
  		    	         $(this).remove(); //移除这个option
  		    	     //}
  		    	});
  		    	
  		    	//给select装配数据
  		    	l(req);//		{ "data": "支付宝, 微信,基金,现金"}
  		    	var 资金类型数组 = req.data.split(",");//获取req中的data,对其用“,”分割，分割方法split
  		    	for(var i=1;i<资金类型数组.length;i++){//遍历分割出来的数组
  		    		$("#资金类型ID").append("<option value='"+i+"'>"+资金类型数组[i]+"</option>");//向select插入option
  		    	}
  		    	
  		    },
  		    complete: function() {			//请求完成的处理
  		        l("解析数据完成");
  		    },
  		    error: function() {				//请求出错处理
  		        l("解析数据错误");
  		    }
  		});
  	}
  
  </script>
  
  <body onload="加载数据()">
    <form action="/servlet/registServlet" >
    	<div align="center">添加账户资金</div>
    	<table border="1" align="center">
	    	<tr>
	    		<td>姓名：</td>
	    		<td><input name="userName" value="" ></td>
	    	</tr>
	    	<tr>
	    		<td>密码：</td>
	    		<td><input name="password" type="password" value="" ></td>
	    	</tr>
	    	<tr>
	    		<td>资金类型：</td>
	    		<td>
					<select id="资金类型ID" >
						<option value="0" > 资金类型加载失败...... </option>
					</select>
				</td>
	    	</tr>
	    	<tr>
	    		<td></td>
	    		<td align="center"><button type="submit" >提交</button></td>
	    	</tr>
    	</table>
    </form>
  </body>
</html>
