<%@page contentType="text/html; charest=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>新增</title>
		<link rel="stylesheet" href="${basePath}static/css/styles.css" />
		<link rel="stylesheet" href="${basePath}static/css/font-awesome-4.7.0/css/font-awesome.min.css" /><!-- 从导入的图标库引入图标 -->
		<script src="${basePath}static/js/jquery.min.js" type="text/javascript"></script><!-- 引入jquery -->
		<!-- 12~40行代码主要实现了表单效验功能，以防在输入、存储时引入无效信息
		主要运用了jQuery Validate(表单效验)js包，更多详细用法和解释可参考jQuery Validate|菜鸟教程(https://www.runoob.com/jquery/jquery-plugin-validate.html) -->
		<!-- 引用JQuery-validation(用于表单效验) -->
		<script src="${basePath}static/js/jquery-validation-1.14.0/jquery.validate.js" type="text/javascript"></script>
		<!-- 引入本地化默认提示信息文件(后缀zh表示为中国，可更改) -->
		<script src="${basePath}static/js/jquery-validation-1.14.0/localization/messages_zh.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(function(){
				$("#addForm").validate({
					rules:{
						stuNo:{
							/* 必填字段 */
							required:true,
							/* 只能填整数 */
							digits:true
						},
						stuName:"required",
						stuPwd:{
							required:true,
							rangelength:[6,15]
						}
					}
				/* messages可用于自定义效验提示信息，不写则会自动调用默认提示信息 */
					/* messages:{
						 stuPwd:{
							 rangelength: $.validator.format( "Please enter a value between {0} and {1} characters long." )
						 }
					} */
				});
			});
		</script>
	</head>
	<body>
		<div class="add">
			<form id="addForm" action="${basePath}student?method=add" method="post">
				<table class="tableadd" style="width: 55%">
					<tr>
						<td>学号</td>
						<td style="color: red;"><input type="text" name="stuNo"></td>
					</tr>
					<tr>
						<td>姓名</td>
						<td style="color: red;"><input type="text" name="stuName"></td>
					</tr>
					<tr>
						<td>密码</td>
						<td><input type="password" name="stuPwd" value="123456">
							初始密码为123456
						</td>
					</tr>
					<tr>
						<td colspan="4" align="left">
							<!-- 点击返回按钮时执行history.back(-1)返回上一级页面
							（需要把按钮类型改为普通，否则默认为submit，返回时路径出错,无法正常返回） -->
							<button class="edit" type="button" onclick="window.history.back(-1);">
								<i class="fa fa-arrow-left"></i>
								返回
							</button>
							<button class="remove" type="submit">
								<i class="fa fa-save"></i>
								提交
							</button>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</body>
</html>
