<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page
	import="java.util.*, dao.*, entity.*,  servlet.*, java.sql.*,util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>绩效信息</title>
<script src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {
		var selectId = -1;
		$("tr").click(function() {
			$(this).toggleClass("select");//toggle切换选中
			selectId = $(this).data("id");
		})
		
		//双击事件
		$("tr").dblclick(function() {
			//双击之后移除双击事件
			$(this).unbind("dblclick");
			//单击之后移除单击事件
			$(this).unbind("click");
			//给双击事件添加class类，遍历数据时要用
			$(this).addClass("updateSc");
			//找到第一个tr下的td，也可以用加上属性后find属性选择器
			var value = $(this).children().eq(4).text();
			$(this).children().eq(4).html("<input type = 'text' name='value' value='"+value+"'/>");
			
			
			
			})

		
		
		$("#save").click(function() {
			//var emps = "";
			//定义一个数组用来存放多个对象
			var array = new Array();
			$(".updateSc").each(function(index,element) {
				var id = $(this).data("id");
				var value = $(this).find("[name=value]").val();

				
				//json代替字符串拼接
				//创建一个JavaScript对象（有api可以转为json对象）
				var sc = {
						id : id,
						value : value,

						}
						//将多个对象存到数组中
						array.push(sc);
				})
				//url中不能有大括号，需要把大括号用转义字符替换
				var str = JSON.stringify(array);
				str = str.replace(/{/g, "%7b");//g表示全局
				str = str.replace(/}/g, "%7d");
				window.location.href = "sc?type=save&sc=" + str;
				})
		
		
		
		//让第一页的a标签不起作用
		if(${p.ye }<=1){
			$("#pre").addClass("disabled");//出现不能点的提示图
			$("#pre").find("a").attr("onclick", "return false");
		} 
		//让最后一页的a标签不起作用 
		if(${p.ye }>=${p.maxYe} ){ 
			$("#next").addClass("disabled");//出现不能点的提示图
			$("#next").find("a").attr("onclick", "return false");
		}
		
	})
</script>
<style type="text/css">
#main {
	width: 700px;
	margin: 20px auto;
}

#sc .select {
	background: #337ab7;
}

#sc td {
	width: 200px;
}

#sc input {
	width: 100px;
}

#sc select {
	width: 100px;
	height: 27px;
}

</style>
</head>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<body>


	<div id="main">

		<form action="sc" class="form-horizontal" role="form" method="post">

			<div class="form-group">
				<div class="col-sm-2">
					<input type="text" class="form-control" name="eName"
						placeholder="姓名" value=${c.emp.name}>>
				</div>

				<div class="col-sm-2">
					<select name="dName" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${depList }" var="dep">

							<!-- 搜索后文本框里仍然显示搜索的条件 -->
							<!-- value应该是程序员需要用到的id而不是显示的name -->
							<option value="${dep.name }"
								<c:if test="${dep.name==c.emp.dep.name }">selected</c:if>>${dep.name}</option>

						</c:forEach>
					</select>
				</div>

				<div class="col-sm-3">
					<select name="pName" class="form-control">
						<option value="">项目</option>
						<c:forEach items="${proList }" var="pro">

							<!-- 搜索后文本框里仍然显示搜索的条件 -->
							<!-- value应该是程序员需要用到的id而不是显示的name -->
							<option value="${pro.name }"
								<c:if test="${pro.name==c.pro.name }">selected</c:if>>${pro.name}</option>

						</c:forEach>
					</select>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" name="value"
						placeholder="成绩" value=${c.value!=-1?c.value:''}>
				</div>
				<div class="col-sm-2">
					<select name="grade" class="form-control">
						<option value="">等级</option>
						<!-- 搜索后文本框里仍然显示搜索的条件 -->
						<option value="优秀" <c:if test="${c.grade=='优秀'}">selected</c:if>>优秀</option>
						<option value="良好" <c:if test="${c.grade=='良好'}">selected</c:if>>良好</option>
						<option value="一般" <c:if test="${c.grade=='一般'}">selected</c:if>>一般</option>
						<option value="及格" <c:if test="${c.grade=='及格'}">selected</c:if>>及格</option>
						<option value="不及格" <c:if test="${c.grade=='不及格'}">selected</c:if>>不及格</option>
					</select>
				</div>

			</div>
			<div class="form-group" class="sou">
				<div class="col-sm-10">
					<button type="submit" class="btn btn-primary">搜索</button>
				</div>
			</div>

		</form>


		<table id="sc" class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>id</th>
					<th>姓名</th>
					<th>部门</th>
					<th style="width: 260px">项目</th>
					<th>分数</th>
					<th>等级</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="sc">
					<tr data-id=${sc.id }>
						<td>${sc.id }</td>
						<td>${sc.emp.name }</td>
						<td>${sc.emp.dep.name }</td>
						<td>${sc.pro.name }</td>
						<td>${sc.value }</td>
						<td>${sc.grade }</td>
					<tr />
				</c:forEach>
			</tbody>
		</table>
		<ul class="pagination">
			<li><a
				href="sc?ye=${1}&eName=${c.emp.name}&dName=${c.emp.dep.name}&pName=${c.pro.name}&value=${c.value!=-1?c.value:''}&grade=${c.grade}">首页</a></li>
			<li id="pre"><a
				href="sc?ye=${p.ye-1}&eName=${c.emp.name}&dName=${c.emp.dep.name}&pName=${c.pro.name}&value=${c.value!=-1?c.value:''}&grade=${c.grade} ">上一页</a></li>
			<!-- 添加页码连接 -->
			<!-- 动态选中（添加背景色） -->
			<!--正常的有i的循环 begin="" end="" varStatus="status"-->
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}"> class="active" </c:if>>
					<a
					href="sc?ye=${status.index}&eName=${c.emp.name}&dName=${c.emp.dep.name}&pName=${c.pro.name}&value=${c.value!=-1?c.value:''}&grade=${c.grade}">${status.index}</a>
				</li>
			</c:forEach>
			<li id="next"><a
				href="sc?ye=${p.ye+1}&eName=${c.emp.name}&dName=${c.emp.dep.name}&pName=${c.pro.name}&value=${c.value!=-1?c.value:''}&grade=${c.grade}">下一页</a></li>
			<li><a
				href="sc?ye=${p.maxYe}&eName=${c.emp.name}&dName=${c.emp.dep.name}&pName=${c.pro.name}&value=${c.value!=-1?c.value:''}&grade=${c.grade}">尾页</a></li>
		</ul>


		<div>
			<button id="save" type="button" class="btn btn-primary">保存</button>
		</div>
	</div>


</body>
</html>