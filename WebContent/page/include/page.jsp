<%@page contentType="text/html; charest=utf-8" pageEncoding="utf-8"%>
<%@taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<table class="page">
			<!-- url方法 -->
			<%-- <td>
				<!-- 第一页时禁用首页跳转功能 -->
				<button type="button" <c:if test="${pageInfo.firstPage == true}">disabled</c:if> onclick="javascript:go(1);">首页</button>
				<!-- 第一页时禁用上一页功能 -->
				<button type="button" <c:if test="${pageInfo.firstPage == true}">disabled</c:if> onclick="javascript:go(${pageInfo.prePage});">上一页</button>
				<!-- 最后一页是禁用下一页功能 -->
				<button type="button" <c:if test="${pageInfo.lastPage == true}">disabled</c:if> onclick="javascript:go(${pageInfo.nextPage});">下一页</button>
				<!-- 最后一页是禁用尾页跳转功能 -->
				<button type="button" <c:if test="${pageInfo.lastPage == true}">disabled</c:if> onclick="javascript:go(${pageInfo.totalPage});">尾页</button>
				<input type="text" class="page-no" name="pageNo" />
				<button>转</button>
				总记录条数${pageInfo.totalCount}条。当前${pageInfo.pageNo}/${pageInfo.totalPage}页 每页${pageInfo.pageSize}条数据
			</td> --%>
			<!-- 表单提交方法 -->
			 <td>
				<!-- 第一页时禁用首页跳转功能 -->
				<button type="button" <c:if test="${pageInfo.firstPage == true}">disabled</c:if> key="1">首页</button>
				<!-- 第一页时禁用上一页功能 -->
				<button type="button" <c:if test="${pageInfo.firstPage == true}">disabled</c:if> key="${pageInfo.prePage}">上一页</button>
				<!-- 最后一页是禁用下一页功能 -->
				<button type="button" <c:if test="${pageInfo.lastPage == true}">disabled</c:if> key="${pageInfo.nextPage}">下一页</button>
				<!-- 最后一页是禁用尾页跳转功能 -->
				<button type="button" <c:if test="${pageInfo.lastPage == true}">disabled</c:if> key="${pageInfo.totalPage}">尾页</button>
				<input type="text" class="page-no" id="page-no" value="${pageInfo.pageNo}"/>
				<button type="button" class="zhuan">转</button> 
				总记录条数${pageInfo.totalCount}条。当前${pageInfo.pageNo}/${pageInfo.totalPage}页 每页${pageInfo.pageSize}条数据
			</td> 
</table>

<!-- 提交表单方法，便于提供给其他的地方使用 -->
<script type="text/javascript">
	$(function(){
		/* not() 选择器选取除了指定元素以外的所有元素,防止将zhuan的表单也一起提交 */
		$('.page button:not(.zhuan)').click(function(){
			/* 将key的值传递给pageNo */
			$('input[name="pageNo"]').val($(this).attr("key"));
			$("#tablelist").submit();
		});
		/* 跳转功能：用于查询指定的页面 */
		$('.page .zhuan').click(function(){
			/* parseInt() 函数解析字符串并返回整数。
			将#page-no的value值和pageInfo.totalPage的值用parseInt()转为整数才能进行比较 */
			if(parseInt($('#page-no').val())<=parseInt(${pageInfo.totalPage})){
				/* 将id为page-no的value值传给pageNo */
				$('input[name="pageNo"]').val($('#page-no').val());
				$("#tablelist").submit();
			}else{
				alert("跳转的页面不能大于总页数!");
			}
		})
	})
</script>




<!-- url提交方法 -->
<!-- <script type="text/javascript">
		function go(page){
			window.location.href="${basePath}/student?method=list&pageNo="+page;
	}
</script> -->