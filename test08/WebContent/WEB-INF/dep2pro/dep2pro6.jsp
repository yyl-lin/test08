<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="f"%>


<script type="text/javascript">
	$().ready(function() {
		$("#project .pro").click(function() {
			$(this).toggleClass("select");
			
		})

		$("#project #add").click(function() {
			var proId = "";
			if ($("#project #noPro").find(".select").length > 0) {
				
				$("#project #noPro").find(".select").each(function(index, element) {

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
							var pro = $("#project #noPro").find(".select");
							pro.removeClass("select");
							$("#project #pro").append(pro);
							
							
						}				
					}				
				})			
			}else{
				alert("请选择数据");
			}
		})
		
		
		$("#project #delete").click(function() {
			
			var proId = "";	
			var length = $("#project #pro .select").length;
			if (length > 0) {
				$("#project #pro .select").each(function(){
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
							var pro = $("#project #pro").find(".select");
							pro.removeClass("select");
							$("#project #noPro").append(pro);
							
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
#project {
	width: 600px;
	margin: 20px auto;
}

#project #pro, #noPro {
	width: 700px;
	height: 200px;
	border: 1px solid #337ab7;
	border-radius: 3px;
	background: #ccc;
}

#project #btn {
	width: 150px;
	margin: 20px auto;
}

#project #add {
	margin-right: 80px;
}

#project .pro {
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

#project .select {
	background: #d9534f;
}
</style>

<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />



	<div id="project">

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
