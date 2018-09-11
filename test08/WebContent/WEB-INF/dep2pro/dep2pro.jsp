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
		$("#add").click(function() {
			//得到选中的项目id
			var proId = $("#selectPro").val();
			
			//depId页面生成的时候就从servlet得到了
			location.href = "d2p?type=add&depId=${dep.id}&proId="+proId;
		})
		//方法一
		//jstl不能.方法只能.属性
		//jstl判断list的size()长度
		<c:if test="${f:length(noList)==0}">
		</c:if>
		//方法二
		if($("#selectPro").children().length==0){
			//取消增加按钮的事件
			$("#add").unbind("click");
			//置灰
			$("#add").addClass("disabled");
		}
		
		$("#delete").click(function() {

			if (selectId > -1) {
				window.location.href = "d2p?type=delete&depId=${dep.id}&proId=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})

		$("tr").click(function() {
			$(this).toggleClass("select");//toggle切换选中
			//selectId = $(this).children().eq(0).text();
			selectId = $(this).data("id");
		})
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
			<tbody>
				<c:forEach items="${list }" var="pro">
					<tr data-id=${pro.id }>
						<td>${pro.id }</td>
						<td>${pro.name }</td>
					<tr />
				</c:forEach>
			</tbody>
		</table>
		<div>
			<div class="col-sm-4" >
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