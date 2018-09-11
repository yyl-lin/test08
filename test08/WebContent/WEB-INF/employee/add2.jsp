<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@page import="java.util.*, dao.*, entity.*,  servlet.*, java.sql.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>添加员工</title>
<script src="js/jquery.js"></script>
<script type="text/javascript">
	$().ready(function() {

		$("#upload").click(function() {

			//文件上传需要表单
			var formData = new FormData();//模拟出空的表单
			//$("[name=photo]")拿到文件域
			//formData.append("photo", $("[name=photo]")[0].files[0]);
			
			//多个文件上传：循环
			for(var i = 0; i <$("[name=photo]")[0].files.length;i++){
				formData.append("photo", $("[name=photo]")[0].files[i]);
			}

			$.ajax({

				url : "emp?type=upload",
				type : "post",
				//data需要得到图片
				data : formData,

				//上传文件需要加入的三个属性
				cache : false,
				processData : false,
				contentType : false,

				dataType : "text",
				//成功后需要接受文件名（字符串）
				success : function(data) {
					//局部刷新弹出上传的照片
					var str = "<img src='pic/"+data+"'/>";
					//构建隐藏域:点击保存后需要向后台发送文件名（从后台拿到的字符串文件名data而不是客户端的文件名）
					str+="<input type='hidden' name='picture' value='"+data+"'/>";
					//$("#photos").html(str);//找到photos下的html弹出照片
					$("#photos").append(str);//展示多个
				}

			})
		})
		
		//点击移除新增的展示图片，不能用$("#photos img")
		$(document).on("click", "#photos img", function(){
			//上传时每个图片都有隐藏域而且跟在图片后面，
			//所以需要先移除隐藏域
			$(this).next().remove();
			$(this).remove();
		})

	})
</script>
<style>
#main {
	width: 500px;
	margin: 20px auto;
}

#photos img {width：100px;
	height: 100px;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
</style>
</head>

<body>
	<div id="main">
		<form action="emp?type=add2" class="form-horizontal" role="form"
			method="post">

			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">姓名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						placeholder="请输入姓名">
				</div>
			</div>
			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">性别</label>
				<div class="col-sm-10">
					<input type="radio" name="sex" checked value="男">男 <input
						type="radio" name="sex" value="女">女
				</div>
			</div>
			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">年龄</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="age"
						placeholder="请输入年龄">
				</div>
			</div>
			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">部门</label>
				<div class="col-sm-10 ">
					<select name="depId" class="form-control">
						<option value="">部门</option>
						<c:forEach items="${depList }" var="dep">

							<!-- 搜索后文本框里仍然显示搜索的条件 -->
							<!-- value应该是程序员需要用到的id而不是显示的name -->
							<option value="${dep.id }">${dep.name }</option>

						</c:forEach>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label for="lastname" class="col-sm-2 control-label">头像</label>
				<div class="col-sm-7">
					<input type="file" class="form-control" name="photo" multiple>
				</div>
				<div class="col-sm-3">
					<input type="button" id="upload" class="form-control btn-primary"
						value="上传">
				</div>
			</div>

			<div id="photos" class="form-group"></div>

			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</div>


		</form>
	</div>
</body>
</html>