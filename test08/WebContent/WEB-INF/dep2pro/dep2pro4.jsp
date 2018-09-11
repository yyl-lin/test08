<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>项目信息</title>
<script src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$(".pro").click(function() {
			$(this).toggleClass("select");
			
		})

		$("#add").click(function() {
			var proId = "";
			if ($("#noPro").find(".select").length > 0) {
				
				$("#noPro").find(".select").each(function(index, element) {

					proId += $(this).data("id") + ",";
						})
						//截取字符串中最后的逗号
						proId = proId.substring(0, proId.length - 1);
				
				$.ajax({
					
					url:"d2p",
					type:"post",
					data:{type:"addBatch", depId:${dep.id}, proId:proId},
					dataType:"text",
					success:function(data){
						
						if(data="true"){
							var pro = $("#noPro").find(".select");
							pro.removeClass("select");
							$("#pro").append(pro);
							
							
						}				
					}				
				})			
			}else{
				alert("请选择数据");
			}
		})
		
		
		$("#delete").click(function() {
			
			var proId = "";	
			var length = $("#pro .select").length;
			if (length > 0) {
				$("#pro .select").each(function(){
					proId += $(this).data("id") + ",";
				})
				
				//截取字符串最后的逗号
				proId = proId.substring(0, proId.length - 1);

				$.ajax({
					
					url:"d2p",
					type:"post",
					data:{type:"deleteBatch", depId:${dep.id}, proId:proId},
					dataType:"text",
					success:function(data){
						
						if(data="true"){
							var pro = $("#pro").find(".select");
							pro.removeClass("select");
							$("#noPro").append(pro);
							
						}				
					}				
				})			
			}else{
				alert("请选择数据");
			}
		})

	})
</script>
<style type="text/css">
#main {
	width: 600px;
	margin: 20px auto;
}

#pro, #noPro {
	width: 700px;
	height: 200px;
	border: 1px solid #337ab7;
	border-radius: 3px;
	background: #ccc;
}

#btn {
	width: 150px;
	margin: 20px auto;
}

#add {
	margin-right: 80px;
}

.pro {
	background: #337ab7;
	height: 40px;
	line-height: 40px;
	float: left;
	margin-left: 10px;
	color: #fff;
	padding: 0 20px;
	margin-top: 10px;
	border-radius: 3px;
}

.select {
	background: #d9534f;
}
</style>
</head>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<body>


	<div id="main">

		<h3>${dep.name }</h3>
		<div id="pro">
			<c:forEach items="${list }" var="pro">
				<div class="pro" data-id="${pro.id }">${pro.name }</div>
			</c:forEach>

		</div>
		<div id="btn">
			<button id="add" type=" button" class="btn btn-primary">↑</button>
			<button id="delete" type="button" class="btn btn-primary">↓</button>
		</div>

		<div id="noPro">
			<c:forEach items="${noList }" var="pro">
				<div class="pro" data-id="${pro.id }">${pro.name }</div>
			</c:forEach>
		</div>

	</div>

</body>
</html>