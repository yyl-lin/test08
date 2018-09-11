<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.*, dao.*, entity.*,  servlet.*, java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>登录</title>
<script src="js/jquery.js"></script>
<script type="text/javascript">

	//self:本页面;    top:最上层页面
	if(self!=top){
		top.location="user?type=showLogin";
	}
   

	$().ready(function() {

		function showMessage(mes) {
			//显示错误提示信息
			$("#message").html(mes);
			//三秒后清空
			setTimeout(function() {
				$("#message").html("");
			}, 2000);
		}
		
		 //取出传回来的参数error并与yes比较
	    var errori ='<%=request.getParameter("error")%>';
		if (errori == 'yes') {
			showMessage("请输入正确的账号或密码");
			return false;
		}
		
		//验证码	
		var mesi ='<%=request.getParameter("mes")%>
	';
		if (mesi == 'yes') {
			showMessage("请输入正确的验证码！");
			return false;
		}
		if (mesi == "") {
			showMessage("验证码不能为空");
			return false;
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

		})

		//点击验证码更换图片
		$("#image").click(function() {
			//重新发送一次请求，但是url不能一样，否则认为还是访问这张图片
			$(this).attr("src", "user?type=randomImage&" + Math.random());
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

#message, #mes {
	color: red;
	height: 40px;
	font-size: 15px;
}
</style>
</head>
<body>

	<div id="main">
		<div id="title">登录</div>
		<form action="user?type=doLogin" class="form-horizontal" role="form"
			method="post">
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
				<label for="lastname" class="col-sm-2 control-label">验证：</label>
				<div class="col-sm-6">
					<input id="random" type="text" class="form-control" name="random"
						placeholder="请输入验证码">
				</div>
				<div class="col-xs-4">
					<img id="image" src="user?type=randomImage" />
				</div>
			</div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">登录</button>
				</div>
			</div>
			<div id="message">${mes }</div>

		</form>

	</div>
</body>
</html>