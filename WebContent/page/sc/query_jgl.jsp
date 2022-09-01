<%@page contentType="text/html; charest=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>选课</title>
		<link rel="stylesheet" href="${basePath}static/css/styles.css" />
		<link rel="stylesheet" href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css" /><!-- 从导入的图标库引入图标 -->
		<script src="${basePath}static/js/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript">
			
		</script>
	</head>
	<body>
		<table class="tablelist">
			<thead>
				<tr>
					<th>ID</th>
					<th>课程名</th>
					<th>及格人数</th>
					<th>总人数</th>
					<th>及格率</th>
				</tr>
			</thead>
			<c:forEach items="${list}" var="sc_sta">
			<tr>
				<td >${sc_sta.cId}</td>
				<td>${sc_sta.cName}</td>
				<td>${sc_sta.jgnum}</td>
				<td>${sc_sta.allnum}</td>
				<td>${sc_sta.jgl}%</td>
			</tr>
			</c:forEach>
		</table>
		<%-- <%@include file="../include/page.jsp"%> --%>
	</body>
</html>
