<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>员工信息</title>
<script src="js/jquery.js"></script>
<script type="text/javascript"> 
	$(document).ready(function() {
		var selectId = -1;
		$("#showAdd").click(function() {
			window.location.href = "emp?type=showAdd";
			})
			
			$("#showAdd2").click(function() {
			window.location.href = "emp?type=showAdd2";
			})
			
			$("#showUpdate").click(function() {
				if (selectId > -1) {
					window.location.href = "emp?type=showUpdate&id="+ selectId;
					} else {
						alert("请选择一条数据");
						}
				})
				
				$("#delete").click(function() {
					if (selectId > -1) {
						window.location.href = "emp?type=delete&id=" + selectId;
						} else {
							alert("请选择一条数据");
							}
					})
					
					
					//多处用，提取出来做方法
					function doBatch(type) {
			var length = $("#emp .select").length;

			//方法一
			//var ids = "";
			//方法二数组
			var ids = new Array();
			if (length > 0) {
			//遍历得到选中数据的id
			$("#emp .select").each(function(index, element) {
				ids.push($(this).data("id"));
				//ids += $(this).data("id") + ",";
				})
				
				//截取字符串中的数据
				//ids = ids.substring(0, ids.length - 1);
				window.location.href = "emp?type=" + type+ "&ids=" + ids;
				} else {
					alert("请选中数据");
					}
			}
		$("#deleteBatch").click(function() {
			
			doBatch("deleteBatch");
			
		})

		$("#showUpdateBatch1").click(function() {
			doBatch("showUpdateBatch1");

		})

		$("#showUpdateBatch2").click(function() {

			doBatch("showUpdateBatch2");
			
		})

		$("tr").click(function() {
			$(this).toggleClass("select");//toggle切换选中
			//selectId = $(this).children().eq(0).text();
			selectId = $(this).data("id");
			})

		//双击事件
		$("tr").dblclick(function() {
			//双击之后移除双击事件
			$(this).unbind("dblclick");
			//单击之后移除单击事件
			$(this).unbind("click");
			//给双击事件添加class类，遍历数据时要用
			$(this).addClass("updateEmp");
			//找到第一个tr下的td，也可以用加上属性后find属性选择器
			//得到第一个单元格的内容：姓名
			var name = $(this).children().eq(1).text();
			//变成显示name的文本框
			$(this).children().eq(1).html("<input type = 'text' name ='name'  value='"+name+"'/>");
			//得到第2个单元格的内容：性别
			var sex = $(this).children().eq(2).text();
			//变成显示sex的下拉框
			var select = "";
			if (sex == "男") {
				select = "<select name = 'sex'><option value = '男' selected>男</option><option value = '女'>女</option></select>";
				} else {
					select = "<select name = 'sex'><option value = '男'>男</option><option value = '女' selected>女</option></select>";
					}
			$(this).children().eq(2).html(select);
			//得到第3个单元格的内容：年龄
			var age = $(this).children().eq(3).text();
			//变成显示年龄的文本框
			$(this).children().eq(3).html("<input type = 'text' name='age' value='"+age+"'/>");
			})

			$("#updateBatch3").click(function() {
				//var emps = "";
				//定义一个数组用来存放多个对象
				var array = new Array();
				$(".updateEmp").each(function(index,element) {
					var id = $(this).data("id");
					var name = $(this).find("[name=name]").val();
					var sex = $(this).find("[name=sex]").val();
					var age = $(this).find("[name=age]").val();
					//json代替字符串拼接
					//创建一个JavaScript对象（有api可以转为json对象）
					var emp = {
							id : id,
							name : name,
							sex : sex,
							age : age
							}
					//将多个对象存到数组中
					array.push(emp);
					})
					//url中不能有大括号，需要把大括号用转义字符替换
					var str = JSON.stringify(array);
					str = str.replace(/{/g, "%7b");//g表示全局
					str = str.replace(/}/g, "%7d");

					window.location.href = "emp?type=updateBatch3&emps="+ str;
					})
					//让第一页的a标签不起作用
					if(${p.ye}<=1){
					$("#pre").addClass("disabled");//出现不能点的提示图
					$("#pre").find("a").attr("onclick", "return false");
					} 
					//让最后一页的a标签不起作用 
					if(${p.ye }>=${p.maxYe} ){ 
						$("#next").addClass("disabled");//出现不能点的提示图
						$("#next").find("a").attr("onclick", "return false");
						}
					
					//移入图片变大
					$("#emp img").hover(function(event){
						
						var photo = $(this).attr("src");
						$("#bigPhoto img").attr("src",photo);
						$("#bigPhoto").show();
						//整个页面的xy轴:跟随鼠标移动
						$("#bigPhoto").css({left:event.pageX+10,top:event.pageY+10})
						
					},function(){
						$("#bigPhoto").hide();
					})
					
	})  
</script>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<style>
#main {
	width: 700px;
	margin: 20px auto;
}

#emp .select {
	background: #337ab7;
}

#emp td {
	width: 200px;
}

#emp input {
	width: 100px;
}

#emp select {
	width: 100px;
	height: 27px;
}

#emp img {
	width: 30px;
	height: 30px;
}

#bigPhoto {
	display: none;
	width: 100px; height : 100px;
	position: absolute;
	height: 100px;
}

#bigPhoto img {
	width: 100px;
	height: 100px;
}
</style>

</head>
<body>

	<div id="main">

		<form action="emp" class="form-horizontal" role="form" method="post">

			<div class="form-group">
				<div class="col-sm-3">
					<input type="text" class="form-control" name="name"
						placeholder="姓名" value=${c.name}>
				</div>
				<div class="col-sm-2">
					<select name="sex" class="form-control">
						<option value="">性别</option>
						<!-- 搜索后文本框里仍然显示搜索的条件 -->
						<option value="男" <c:if test="${c.sex=='男'}">selected</c:if>>男</option>
						<option value="女" <c:if test="${c.sex=='女'}">selected</c:if>>女</option>
					</select>
				</div>
				<div class="col-sm-2">
					<!-- 解决年龄-1的问题 -->
					<input type="text" class="form-control" name="age" placeholder="年龄"
						value=${c.age!=-1?c.age:''}>
				</div>
				<div class="col-sm-3">
					<select name="depId" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${depList }" var="dep">

							<!-- 搜索后文本框里仍然显示搜索的条件 -->
							<!-- value应该是程序员需要用到的id而不是显示的name -->
							<option value="${dep.id }"
								<c:if test="${dep.id==c.dep.id }">selected</c:if>>${dep.name }</option>

						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<div class="col-sm-2">
						<button type="submit" class="btn btn-primary">搜索</button>
					</div>
				</div>
			</div>


		</form>


		<table id="emp" class="table table-bordered table-striped table-hover">
			<thead>
				<tr>
					<th>id</th>
					<th>姓名</th>
					<th>性别</th>
					<th>年龄</th>
					<th>部门</th>
					<th>照片</th>
				</tr>
			</thead>
			<tbody>

				<!-- 遍历集合用items=""  var="emp"-->
				<c:forEach items="${list}" var="emp">
					<tr data-id="${emp.id}">
						<td>${emp.id}</td>
						<td>${emp.name}</td>
						<td>${emp.sex}</td>
						<td>${emp.age}</td>
						<td>${emp.dep.name}</td>
						<td><c:if test="${not empty emp.photo }">
								<img src="pic/${emp.photo}" />
							</c:if></td>
					<tr />
				</c:forEach>
			</tbody>
		</table>

		<ul class="pagination">
			<li><a
				href="emp?ye=${1}&name=${c.name}&sex=${c.sex }&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">首页</a></li>
			<li id="pre"><a
				href="emp?ye=${p.ye-1}&name=${c.name}&sex=${c.sex }&age=${c.age!=-1?c.age:''}&depId=${c.dep.id} ">上一页</a></li>
			<!-- 添加页码连接 -->
			<!-- 动态选中（添加背景色） -->
			<!--正常的有i的循环 begin="" end="" varStatus="status"-->
			<c:forEach begin="${p.beginYe}" end="${p.endYe}" varStatus="status">
				<li <c:if test="${p.ye==status.index}"> class="active" </c:if>>
					<a
					href="emp?ye=${status.index}&name=${c.name}&sex=${c.sex }&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">${status.index}</a>
				</li>
			</c:forEach>
			<li id="next"><a
				href="emp?ye=${p.ye+1}&name=${c.name}&sex=${c.sex }&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">下一页</a></li>
			<li><a
				href="emp?ye=${p.maxYe}&name=${c.name}&sex=${c.sex }&age=${c.age!=-1?c.age:''}&depId=${c.dep.id}">尾页</a></li>
		</ul>
		<div>
			<button id="showAdd" type="button" class="btn btn-primary">增加</button>
			<button id="showAdd2" type="button" class="btn btn-primary">增加2</button>
			<button id="showUpdate" type="button" class="btn btn-primary">修改</button>
			<button id="delete" type="button" class="btn btn-primary">删除</button>
			<button id="showUpdateBatch1" type="button" class="btn btn-primary">批量修改1</button>
			<button id="showUpdateBatch2" type="button" class="btn btn-primary">批量修改2</button>
			<button id="updateBatch3" type="button" class="btn btn-primary">批量修改3</button>
			<button id="deleteBatch" type="button" class="btn btn-primary">批量删除</button>
		</div>
		<div id="bigPhoto">
			<img src="" />
		</div>
	</div>
</body>
</html>