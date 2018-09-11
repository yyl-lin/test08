<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>主页面</title>

<style>
* {
	margin: 0;
	padding: 0;
}

#main {
	height: 600px;
	overflow: hidden;
}

#left {
	width: 300px;
	height: 600px;
	float: left;
	padding-left: 20px;
}

#right {
	width: 800px;
	height: 600px;
	float: left;
}

#top {
	clear: both;
	height: 80px;
	background: #DFDFDF;
	height: 80px;
}

#top h1 {
	text-align: center;
	line-height: 80px;
}

#count {
	position: absolute;
	top: 0;
	right: 80px;
	line-height: 80px;
}

.yi {
	width: 160px;
	height: 40px;
	background: #337ab7;
	color: #fff;
	margin-top: 10px;
	border-radius: 3px;
	text-align: center;
	line-height: 40px;
}

a {
	color: #fff;
	text-decoration: none;
}

.er {
	list-style: none;
	width: 160px;
}

.er li {
	margin-top: 5px;
	text-align: center;
	background: #DFDFDF;
	color: #fff;
	height: 30px;
	line-height: 30px;
	font-size: 14px;
}

.er a {
	color: #000;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	var websocket = null;

	//判断当前浏览器是否支持WebSocket
	if ('WebSocket' in window) {
		websocket = new WebSocket("ws://192.168.0.101:8080/test08/websocket");
	} else {
		alert('没有建立websocket连接')
	}
	

	//接收到消息的回调方法,将消息显示在网页上
	websocket.onmessage = function(event) {
		
		$("#count").html(event.data);
	}

	//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口。
	window.onbeforeunload = function() {
		websocket.close();
	}

	//关闭连接
	function closeWebSocket() {
		websocket.close();
	}

	$().ready(function() {
		$(".yi").click(function() {
			$(this).next().slideToggle(500);
		})
	})
</script>
</head>
<body>

	<div id="container">
		<div id="top">
			<h1>员工管理系统</h1>
			<div id="count">本网站共有${applicationScope.num }人访问</div>
		</div>
		<div id="main">
			<div id="left">
				<div class="yi">员工管理</div>
				<ul class="er">
					<li><a href="emp" target="right">员工管理</a></li>
					<li><a href="emp?type=showAdd" target="right">员工添加</a></li>
				</ul>
				<div class="yi">部门管理</div>
				<ul class="er">
					<li><a href="dep" target="right">部门管理</a></li>
					<li><a href="dep?type=showAdd" target="right">部门添加</a></li>
				</ul>
				<div class="yi">项目管理</div>
				<ul class="er">
					<li><a href="pro" target="right">项目管理</a></li>
					<li><a href="pro?type=showAdd" target="right">项目添加</a></li>
				</ul>
				<div class="yi">绩效管理</div>
				<ul class="er">
					<li><a href="sc" target="right">绩效管理1</a></li>
					<li><a href="sc?type=manage" target="right">绩效管理2</a></li>
				</ul>
			</div>
			<iframe id="right" name="right" frameborder="0" scrolling="no"
				src="emp"></iframe>
		</div>
		<!-- 		<div id="bottom"></div> -->
	</div>





</body>
</html>