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
<script src="//apps.bdimg.com/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>
<script type="text/javascript">
	$().ready(function() {
		$(".pro").click(function() {
			$(this).toggleClass("select");
			
		})


		$("#add").click(function() {
			var length = $("#noPro .select").length;
			var proId = "";
			if (length > 0) {
				$("#noPro .select").each(function(index, element) {

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
							$("#pro").append(pro);
							pro.removeClass("select");
							
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

		
		/*
		*拖拽
		*/
		//pro的容器坐标
		var proLeft=$("#pro").offset().left;
		var proTop=$("#pro").offset().top;
		var noProLeft = $("#noPro").offset().left;
		var noProTop = $("#noPro").offset().top;

		//pro的容器高度和宽度
		//parseFloat去掉单位转化为纯数字
		var proWidth=parseFloat($("#pro").css("width"));
		var proHeight=parseFloat($("#pro").css("height"));
		var noProWidth = parseFloat($("#noPro").css("width"));
		var noProHeight =parseFloat( $("#noPro").css("height"));
		
		var startLeft;
		var startTop;
		$( ".pro" ).draggable({
			
		      start: function() {
		    	  //定义初始坐标,else需要用到
		    	  
		    	  startLeft=$(this).offset().left;
		    	  startTop=$(this).offset().top;
		        },
		        
		        stop: function() {
		        	//停止时的坐标点
		        	var stopLeft=$(this).offset().left;
		        	var stopTop=$(this).offset().top;
		        	//判断停止的坐标是否在pro的容器内
		        	if(stopLeft>=proLeft && stopLeft<=proLeft+proWidth && stopTop>=proTop && stopTop<=proTop+proHeight){
		        		//拿到proId
		        		var proId =$(this).data("id");
		        		var pro=$(this);
		        		$.ajax({
		        			url:"d2p",
							type:"post",
							data:{type:"add2", depId:${dep.id}, proId:proId}, 
							dataType:"text",
							success:function(data){
								
								if(data="true"){
									//设成流布局才能自动排列到最后
									pro.css("position","static");
									
									//将拖动的pro拼到最后一个pro的后面
									//ajax里面的this值得是整个ajax这里不能用
									$("#pro").append(pro);
									
									//拼好之后再变成相对布局才可以继续拖动
									pro.css("position","relative");
									
									//默认0,0则他就会在他为流布局时该在的位置 
									pro.css("left","0");
									pro.css("top","0");
								}				
							}
		        		})
		        	}else if(stopLeft>=noProLeft&&stopLeft<=noProLeft+noProWidth&&stopTop>=noProTop&&stopTop<=noProTop+noProHeight){
		        		var proId = $(this).data("id");
		        		var pro = $(this);
		        		$.ajax({
		        			url:"d2p",
							type:"post",
							data:{type:"delete2", depId:${dep.id}, proId:proId}, 
							dataType:"text",
		    				success:function(data){
		    					if(data=="true"){
		    						pro.css("position","static");
			    					$("#noPro").append(pro);
			    					pro.css("position","relative");
			    					pro.css("left","0");
			    					pro.css("top","0");
		    					}
		    					
		    					

		    					
		    				}
		    			})
		        	}else{
		        		//返回原位置
		        		$(this).offset({left:startLeft, top:startTop});
		        	}
		        	
		        	
		        }
		      });

		
	
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