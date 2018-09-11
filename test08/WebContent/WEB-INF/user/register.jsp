<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.*, dao.*, entity.*,  servlet.*, java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>注册</title>
<script src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {

		function showMessage(mes) {
			//显示错误提示信息
			$("#message").html(mes);
			//一秒后清空
			setTimeout(function() {
				$("#message").html("");
			}, 2000);
		}

		$("form").submit(function() {

			//账号
			str = $("#name").val();
			if (str == "") {
				showMessage("账号不能为空");
				return false;
			}
			//密码
			str = $("#password").val();
			if (str == "") {
				showMessage("密码不能为空");
				return false;
			}

			//确认
			reStr = $("#confirmPassword").val();
			if (str != reStr) {
				showMessage("两次密码不一致");
				return false;
			}

			return true;
		})
	})
</script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<style type="text/css">
#main {
	width: 600px;
	margin: 20px auto;
	margin-top: 100px;
}

#title {
	margin-left: 200px;
	margin-bottom: 50px;
	font-size: 30px;
	font-weight: bold;
	font-size: 30px;
	font-size: 30px
}

.btn {
	margin-left: 90px;
}

#message {
	color: red;
	height: 40px;
	font-size: 15px;
}
</style>
</head>
<body>

	<div id="main">
		<div id="title">注册</div>
		<form action="user?type=doRegister" class="form-horizontal"
			role="form" method="post">
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">账号：</label>
				<div class="col-sm-6">
					<input id="name" type="text" class="form-control" name="username"
						placeholder="请输入账号" value="${name}">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">密码：</label>
				<div class="col-sm-6">
					<input id="password" type="password" class="form-control"
						name="password" placeholder="请输入密码">
				</div>
			</div>

			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">确认密码：</label>
				<div class="col-sm-6">
					<input id="confirmPassword" type="password" class="form-control"
						name="confirmPassword" placeholder="确认密码">
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">注册</button>
				</div>
			</div>
		</form>
		<div id="message"></div>
	</div>
</body>
</html>