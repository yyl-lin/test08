<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page
	import="java.util.*, dao.*, entity.*,  servlet.*, java.sql.*,util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>部门信息</title>
<script src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var selectId = -1;
		$("#showAdd").click(function() {
			location.href = "dep?type=showAdd";
		})
		$("#showUpdate").click(function() {

			if (selectId > -1) {
				window.location.href = "dep?type=showUpdate&id=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})

		$("#delete").click(function() {

			if (selectId > -1) {
				window.location.href = "dep?type=delete&id=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})

		$("#manageProject").click(function() {

			if (selectId > -1) {
				window.location.href = "d2p?depId=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})

		$("#manageProject2").click(function() {

			if (selectId > -1) {
				window.location.href = "d2p?type=m2&depId=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})

		$("#manageProject3").click(function() {

			if (selectId > -1) {
				window.location.href = "d2p?type=m3&depId=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})

		$("#manageProject4").click(function() {

			if (selectId > -1) {
				window.location.href = "d2p?type=m4&depId=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})
		$("#manageProject5").click(function() {

			if (selectId > -1) {
				window.location.href = "d2p?type=m5&depId=" + selectId;
			} else {
				alert("请选择一条数据");
			}
		})

		$("#manageProject6").click(function() {

			if (selectId > -1) {
				$("#modalBody").html("");
				var url = "d2p?type=m6&depId=" + selectId;
				$("#modalBody").load(url);
				$("#myModal").modal("show");

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

#dep .select {
	background: #337ab7;
}

#dep td {
	width: 200px;
}

#dep input {
	width: 100px;
}

#dep select {
	width: 100px;
	height: 27px;
}
</style>
</head>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="bootstrap/js/bootstrap.min.js" rel="stylesheet" />
<body>


	<div id="main">

		<form action="dep" class="form-horizontal" role="form" method="post">

			<div class="form-group">
				<div class="col-sm-4">
					<input type="text" class="form-control" name="name"
						placeholder="请输入部门名" value=${c.name}>
				</div>
				<div class="col-sm-4">
					<!-- 解决人数-1的问题 -->
					<input type="text" class="form-control" name="empCount"
						placeholder="请输入人数" value=${c.empCount!=-1?c.empCount:''}>
				</div>
				<div class="form-group">
					<div class="col-sm-4">
						<button type="submit" class="btn btn-primary">搜索</button>
					</div>
				</div>
			</div>


		</form>


		<table id="dep" class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>id</th>
					<th>部门名</th>
					<th>人数</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="dep">
					<tr data-id=${dep.id }>
						<td>${dep.id }</td>
						<td>${dep.name }</td>
						<td><a href="emp?depId=${dep.id }"> ${dep.empCount }</a></td>
					<tr />
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination">
			<li><a
				href="dep?ye=${1}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">首页</a></li>
			<li id="pre"><a
				href="dep?ye=${p.ye-1}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''} ">上一页</a></li>
			<!-- 添加页码连接 -->
			<!-- 动态选中（添加背景色） -->
			<!--正常的有i的循环 begin="" end="" varStatus="status"-->
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}"> class="active" </c:if>>
					<a
					href="dep?ye=${status.index}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">${status.index}</a>
				</li>
			</c:forEach>
			<li id="next"><a
				href="dep?ye=${p.ye+1}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">下一页</a></li>
			<li><a
				href="dep?ye=${p.maxYe}&name=${c.name}&empCount=${c.empCount!=-1?c.empCount:''}">尾页</a></li>
		</ul>


		<div>
			<button id="showAdd" type="button" class="btn btn-primary">增加</button>
			<button id="showUpdate" type="button" class="btn btn-primary">修改</button>
			<button id="delete" type="button" class="btn btn-primary">删除</button>
			<button id="manageProject" type="button" class="btn btn-primary">管理项目</button>
			<button id="manageProject2" type="button" class="btn btn-primary">管理项目2</button>
			<button id="manageProject3" type="button" class="btn btn-primary">管理项目3</button>
			<button id="manageProject4" type="button" class="btn btn-primary">管理项目4</button>
			<button id="manageProject5" type="button" class="btn btn-primary">管理项目5</button>
			<button id="manageProject6" type="button" class="btn btn-primary">管理项目6</button>
		</div>
		
		
		<!-- 模态框（Modal） -->
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
						<h4 class="modal-title" id="myModalLabel">模态框（Modal）标题</h4>
					</div>
					<div class="modal-body" id="modalBody"></div>

				</div>
				<!-- /.modal-content -->
			</div>
			<!-- /.modal-dialog -->
		</div>
		<!-- /.modal -->
	</div>



</body>
</html>