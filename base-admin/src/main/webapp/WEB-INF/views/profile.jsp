<%@ page contentType="text/html;charset=UTF-8" %>

<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
	<title>个人资料</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>用户
			<small>个人资料</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}/"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li class="active"> 个人资料</li>
		</ol>
	</section>
	<c:if test="${message != null && message != ''}">
	    <div class="alert alert-info" style="margin-top:20px;">
	        <button type="button" class="close" data-dismiss="alert">×</button>
	       	${message}
	    </div>
    </c:if>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
					<div class="box-header">
						<a class="btn pull-right "href="${ctx }/profile/passwd">修改密码</a>
					</div>
					<form id="inputForm" class="form-horizontal" action="${ctx}/profile" method="post">
						<input type="hidden" name="id" value="${entity.id}"/>
						<input type="hidden" name="username" value="${entity.username}"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">账号</label>
							<div class="col-sm-3">
								<p class="form-control-static">${entity.username}</p>
							</div>
						</div>
						<div class="form-group" style="display: none;">
							<div class="col-sm-offset-2 col-sm-10">
								<button type="submit" class="btn btn-primary">提交</button>
								<button type="button" class="btn" onclick="location.href='${ctx}/'">取消</button>
							</div>
						</div>
					</form>
					<div class="box-footer clearfix"></div>
				</div>
			</div>
		</div>
	</section>
	<!-- /.content -->
	<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#dhhm").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
			});
		});
	</script>
</body>
</html>
