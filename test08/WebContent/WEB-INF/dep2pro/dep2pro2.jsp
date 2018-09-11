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
		
		var selectId = -1;
		var flag =1;
		
		
		//将点击事件换成对文档加载
		$(document).on("click","tr", function() {
			$("tr").removeClass("select");
			$(this).addClass("select");
			//$(this).toggleClass("select");//toggle切换选中
			//selectId = $(this).children().eq(0).text();
			selectId = $(this).data("id");
		})
		
		$("#add").bind("click", add);
		function add() { 
			//得到选中的项目id
			var proId = $("#selectPro").val();
			var i = 0;
			//depId页面生成的时候就从servlet得到了
			//location.href = "d2p?type=add&depId=${dep.id}&proId="+proId;
			
			$.ajax({
				
				url:"d2p",
				type:"post",
				data:{ type:"add2", depId:${dep.id}, proId:proId},
				dataType:"text",
				success:function(data){
					if(data=="true"){
						var proName ="";
						
						//拿到proId对应的name
						$("#selectPro").children().each(function(index,element){
							if($(this).val()==proId){
								proName = $(this).text();
								//得到选中的索引，删除的时候需要用
								i = index;
							}
						})
						
						//将id和name拼接到一块,并同时拼上data-id，否则无法删除
						var tr="<tr data-id="+proId+"><td>"+proId+"</td><td>"+proName+"</td></tr>"
						$("#pro").append(tr);
						//下拉框内项目的移除
						$("#selectPro").children().eq(i).remove();
						
						//方法二
						if($("#selectPro").children().length==0){
							flag=0;
							//取消增加按钮的事件
							$("#add").unbind("click");
							//置灰
							$("#add").addClass("disabled");
						}
					}
				}
				
				
			})
			

		}

		
		$("#delete").click(function() {

			if (selectId > -1) {
				//window.location.href = "d2p?type=delete&depId=${dep.id}&proId=" + selectId;
				
			var i = 0;
			
			$.ajax({
				
				
				url:"d2p",
				type:"post",
				data:{ type:"delete2", depId:${dep.id }, proId:selectId},
				dataType:"text",
				success:function(data){
					if(data=="true"){
						var proName ="";
						
						//拿到proId对应的name
						$("tr").each(function(index,element){
							if($(this).data("id")==selectId){
								proName = $(this).children().eq(1).text();
								//得到选中的索引，删除的时候需要用
								i = index;
							}
						})
						
						//将id和name拼接到一块
						var option="<option value='"+selectId+"'>"+proName+"</option>"
						//将选中的项目添加到下拉框
						$("#selectPro").append(option);
						//将表中选中的项目的移除
						$("tr").eq(i).remove();	
						
						//方法二
						if($("#selectPro").children().length==0){
							flag=1;
							//增加按钮的事件
							$("#add").bind("click",add);
							//置灰
							$("#add").removeClass("disabled");
							
						}
// 						else if($("#pro").children().length==0){
// 							$("#delete").unbind("click");
// 							//置灰
// 							$("#delete").addClass("disabled");
// 						}
						
					}
				}
				
				
			})
		 
			
			} else {
				alert("请选择一条数据");
			}
			
			
		})
		//方法二
		if($("#selectPro").children().length==0){
			flag=0;
			//取消增加按钮的事件
			$("#add").unbind("click");
			//置灰
			$("#add").addClass("disabled"); 
		}
// 		if($("#pro").children().length==0){
// 			flag=0;
			
// 			$("#delete").unbind("click");
// 			//置灰
// 			$("#delete").addClass("disabled"); 
// 		}

	})
</script>
<style type="text/css">
#main {
	width: 600px;
	margin: 20px auto;
}

#pro .select {
	background: #337ab7;
}

#pro td {
	width: 200px;
}

#pro input {
	width: 100px;
}

#pro select {
	width: 100px;
	height: 27px;
}
</style>
</head>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<body>


	<div id="main">

		<h3>${dep.name }</h3>

		<table id="pro" class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>id</th>
					<th>项目名</th>
				</tr>
			</thead>
			<tbody id ="pro">
				<c:forEach items="${list }" var="pro">
					<tr data-id=${pro.id }>
						<td>${pro.id }</td>
						<td>${pro.name }</td>
					<tr />
				</c:forEach>
			</tbody>
		</table>
		<div>
			<div class="col-sm-4">
				<select class="form-control" id="selectPro">
					<c:forEach items="${noList }" var="pro">
						<option value="${pro.id }">${pro.name }</option>

					</c:forEach>
				</select>
			</div>
			<button id="add" type="button" class="btn btn-primary">增加</button>
			<button id="delete" type="button" class="btn btn-primary">删除</button>
		</div>
	</div>

</body>
</html>