<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>角色管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			角色 
			<small>
				<c:if test="${empty entity.id}">添加</c:if>
				<c:if test="${!empty entity.id}">修改</c:if>
			</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li><a href="${ctx}/sys/role"> 角色列表</a></li>
			<li class="active">
				<c:if test="${empty entity.id}">添加角色</c:if>
				<c:if test="${!empty entity.id}">修改角色</c:if>
			</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
					<div class="box-header"></div>
					<!-- form start -->
					<form id="inputForm" class="form-horizontal" action="${ctx}/sys/role/save" method="post">
						<input type="hidden" name="id" value="${entity.id }"/>
						<div class="form-group">
							<label class="col-sm-2 control-label">角色名称</label>
							<div class="col-sm-3">
								<input id="roleName"  name="name" value="${entity.name }" type="text" 
									class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">角色描述</label>
							<div class="col-sm-3">
								<input name="remark" value="${entity.remark }" type="text" 
									class="form-control" />
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<tags:hasAnyPermissions name="role:create, role:update">
								<button id="submit" class="btn btn-primary">提交</button>
								</tags:hasAnyPermissions>
								<a href="${ctx}/sys/role" class="btn btn-default">取消</a>
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
		$("#roleName").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate({
			rules : {
				name : {
					remote : {
						url: "${ctx}/sys/role/checkRoleName"
						, type: "post"
						, data: {old: "${entity.name}"}
					}
				}
			},
			messages : {
				name : {
					remote : "角色名称已存在!"
				}
			}
		});
	});
	</script>
</body>
</html>