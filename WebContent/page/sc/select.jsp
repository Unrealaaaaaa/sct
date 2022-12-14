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
		<c:if test="${param.msg == 0}">
			<span style="color:red;">操作失败！</span>
		</c:if>
		<c:if test="${param.msg == 1}">
			<span style="color:green;">操作成功！</span>
		</c:if>
		<form action="${basePath}sc?method=submit" method="post">
		  	<table class="tablelist">
				<c:forEach items="${courses}" var="course">
				<tr>
					<td width="50px" align="center">
						<!-- 选中的课程 -->
						<!-- 通过让sc表中的cId与course中的cId进行对比，
						从而确认了那些课程被选择的课程会出现√标识 -->
						<input type="checkbox" 
							<c:forEach items="${scs}" var="sc">
								<c:if test="${sc.cId == course.cId}">checked="checked"</c:if>
							</c:forEach>
						name="cId" value="${course.cId}">
					</td> 
					<td>
						${course.cName}
					</td>
					<td>
						${course.teacher.tName}
					</td>
				</tr>
				</c:forEach>
		  	</table>
		  	<br/>
		  	<button class="mybutton">
					<i class="fa fa-save"></i>
					提交选课
				</button>
	  	</form>
	</body>
</html>
