<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<html>
<head>
<title>用户管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			用户 
			<small>
				<c:if test="${empty entity.id}">添加</c:if>
				<c:if test="${!empty entity.id}">修改</c:if>
			</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li><a href="${ctx}/sys/user"> 用户列表</a></li>
			<li class="active">
				<c:if test="${empty entity.id}">添加用户</c:if>
				<c:if test="${!empty entity.id}">修改用户</c:if>
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
					<form id="inputForm" class="form-horizontal" action="${ctx}/sys/user/save" method="post">
						<input type="hidden" name="id" value="${entity.id }"/>
						<input type="hidden" name="ids" />
						<div class="form-group">
							<label class="col-sm-2 control-label">用户账号</label>
							<div class="col-sm-3">
								<input id="username" name="username" value="${entity.username }" type="text" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">姓名</label>
							<div class="col-sm-3">
								<input id="nickname" name="nickname" value="${entity.nickname }" type="text" class="form-control required" />
							</div>
						</div>
						<div class="form-group">
							<label class="col-sm-2 control-label">所属角色</label>
							<div class="col-sm-10">
								<c:forEach var="e" items="${roles }" varStatus="st">
									<div class="checkbox" style="display: inline-block">
					                    <label>
					                      	<input type="checkbox" name="roleIds" value="${e.id }" 
					                      	<c:forEach var="r" items="${entity.roles }">
					                      	<c:if test="${r.id == e.id }">checked="checked"</c:if>
					                      	</c:forEach>>${e.name }
					                    </label>
				                  	</div>
								</c:forEach>
							</div>
						</div>
						<div class="form-group">
							<div class="col-sm-offset-2 col-sm-10">
								<tags:hasAnyPermissions name="user:create, user:update">
								<button id="submit" class="btn btn-primary">提交</button>
								</tags:hasAnyPermissions>
								<a href="${ctx}/sys/user" class="btn btn-default">取消</a>
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
		$("#username").focus();
		//为inputForm注册validate函数
		
		$("form").submit(function(){
			var username = $('input[name="username"]').val();
			if(username == null || username == '' || typeof(username) == 'undefined'){
				layer.alert('用户名不能为空', {
					  icon: 2,
					  skin: 'layer-ext-moon' 
				})
				return false;
			};
			
			var nickname = $('input[name="nickname"]').val();
			if(nickname == null || nickname == '' || typeof(nickname) == 'undefined'){
				layer.alert('姓名不能为空', {
					  icon: 2,
					  skin: 'layer-ext-moon' 
				})
				return false;
			};
			
			var ids = "";
			$('input[name="roleIds"]:checked').each(function(){
				ids += $(this).val() + ",";
			});
			
			if(ids.length > 0){
				ids = ids.substring(0, ids.length - 1);
				$('input[name="ids"]').val(ids);
			}else{
				layer.alert('用户角色不能为空', {
					  icon: 2,
					  skin: 'layer-ext-moon' 
				})
				return false;
			};
		});
	});
	</script>
</body>
</html>