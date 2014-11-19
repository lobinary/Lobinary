<%@ page language="java"
	import="com.lobinary.platform.model.PageParameter" pageEncoding="UTF-8"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<script type="text/javascript">
	var formId = '<%=request.getParameter("formIdParam")%>';
	function turnPage(turntype) {
		var currentpage = document.getElementById("currentPage").value;
		var countPage = document.getElementById("countPage").value;
		switch (turntype) {
		default:
			break;
		case "first":
			currentpage = 1;
			break;
		case "pre":
			if (parseInt(currentpage) > 1) {
				currentpage = parseInt(currentpage) - 1;
			} else {
				currentpage = 1;
			}
			break;
		case "next":
			if (parseInt(currentpage) < parseInt(countPage)) {
				currentpage = parseInt(currentpage) + 1;
			} else {
				currentpage = countPage;
			}
			break;
		case "last":
			currentpage = countPage;
			break;
		}
		document.getElementById("currentPage").value = currentpage;
		document.getElementById(formId).submit();
	}
	function jumpPage(pageNum) {
		document.getElementById("currentPage").value = parseInt(pageNum);
		document.getElementById(formId).submit();
	}
	function changePageSize() {
		document.getElementById("currentPage").value = 1;
		document.getElementById(formId).submit();
	}
	$(function initPageSize() {
		document.getElementById("pageSize").value = '${pageParameter.pageSize}';
	});
</script>
<input id="currentPage" name="currentPage" type="hidden"
	value="${pageParameter.currentPage}" />
<input id="countPage" name="countPage" type="hidden"
	value="${pageParameter.countPage}" />

<div
	style="width: 70%; float: left; font-size: 9pt; padding: 2px 0 0 0;">
	<a href="javascript:turnPage('first');void(0);">首页</a>&nbsp; <a
		href="javascript:turnPage('pre');void(0);">上一页</a>&nbsp;
	<%
		PageParameter pageParameter = (PageParameter) request.getAttribute("pageParameter");
		for (int pageNum : pageParameter.getPageNumList()) {
			if (pageParameter.getCurrentPage() != pageNum) {
	%>
	<a href="javascript:jumpPage(<%=pageNum%>);void(0);"><%=pageNum%></a>&nbsp;
	<%
		} else {
	%>
	<%=pageNum%> &nbsp;
	<%
		}
		}
	%>
	<a href="javascript:turnPage('next');void(0);">下一页</a>&nbsp; <a
		href="javascript:turnPage('last');void(0);">末页</a>&nbsp;
	共：&nbsp;${pageParameter.countRows }&nbsp;条记录&nbsp;&nbsp;每页：&nbsp; <select
		id="pageSize" name="pageSize" onChange="changePageSize(this.value)">
		<option value="10">10</option>
		<option value="20">20</option>
		<option value="50">50</option>
		<option value="100">100</option>
		<option value="200">200</option>
	</select> &nbsp;条：&nbsp;&nbsp;当前第&nbsp; ${pageParameter.currentPage }/${pageParameter.countPage }&nbsp;页
</div>
<div style="width: 30%; float: left; text-align: right; font-size: 9pt;">
</div>
