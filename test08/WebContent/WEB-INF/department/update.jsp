<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改部门信息</title>

<style>
#main {
	width: 500px;
	margin: 50px auto;
}
#save{
margin-left: 550px;
}
</style>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
</style>
</head>

<body>

	<div id="main">
		<form action="dep" class="form-horizontal" role="form" method="post">
			<input type="hidden" name="type" value="update" /> <input
				type="hidden" name="id" value="${dep.id }" />

			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">部门名</label>
				<div class="col-sm-10">
					<input type="text" class="form-control" name="name"
						value="${dep.name }">
				</div>
			</div>
			</div>
			<div class="form-group" id="save">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-primary">保存</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>