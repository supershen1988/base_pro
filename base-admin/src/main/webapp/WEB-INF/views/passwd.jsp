<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>修改密码</title>
</head>
<body>
	<section class="content-header">
		<h1>
			用户 <small>修改密码</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li><a href="${ctx}/profile"> 个人资料</a></li>
			<li class="active"> 修改密码</li>
		</ol>
	</section>
    <c:if test="${message != null && message != ''}">
	    <div class="alert alert-info" style="margin-top:20px;">
	        <button type="button" class="close" data-dismiss="alert">×</button>
	       	${message}
	    </div>
    </c:if>
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
					<div class="box-header"></div>
					<form id="inputForm" class="form-horizontal" action="${ctx }/profile/passwd" method="post">
						<div class="form-group">
							<label class="col-sm-2 control-label">旧密码</label>
							<div class="col-sm-3">
								<input type="password" class="form-control required" name="old_password"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">新密码</label>
							<div class="col-sm-3">
								<input type="password" class="form-control required" id="new_password" name="new_password"/>
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">确认密码</label>
							<div class="col-sm-3">
								<input type="password" class="form-control required" name="plain_password"
									equalTo="#new_password"/>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary">提交</button>
								<button type="button" class="btn" onclick="location.href='${ctx}/profile'">取消</button>
							</div>
						</div>
					</form>
					<div class="box-footer clearfix"></div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#sn").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
			});
		});
	</script>
</body>
</html>