<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
	<title>角色管理</title>
	<style type="text/css">
		.form-group {margin-left: 15px; border: 1px solid #e7e7e7;}
		.form-group > label {margin-left: 15px;}
		.form-group-tatil{height: 35px; border-bottom: 2px solid #3c8dbc; line-height: 35px; padding-left: 1%;}
		.form-group-item{display: inline-block; padding: 10px;}
		
		.form-group:last-child{border: 0px; text-align: center;} 
	</style>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>
			角色管理 
			<small>权限配置</small>
		</h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li><a href="${ctx}/sys/role"> 角色列表</a></li>
			<li class="active">权限配置</li>
		</ol>
	</section>
	<!-- Main content -->
	<section class="content">
		<div class="row">
			<div class="col-xs-12">
				<div class="box">
					<div class="box-header"><div class="form-group-tatil">${entity.name }</div></div>
					<div class="box-body">
						<form id="inputForm" action="${ctx }/sys/role/savePerm" method="post" >
							<input type="hidden" name="id" value="${entity.id }"/>
							<table class="table table-bordered table-striped  table-form">
								<tr>
									<th colspan="2">权限</th>
								</tr>
								<c:forEach items="${perms }" var="perm">
									<tr>
										<th style="width:120px">${perm.remark}</th>
										<td>
											<c:forEach items="${perm.children }" var="r"  varStatus="st">
													<input type="checkbox" name="perm" value="${r.id}"
														<c:forEach items="${entity.perms}" var="e"> 
										                 	<c:if test="${r.id eq e.id}"> checked="checked"</c:if>
										                 </c:forEach>
													/> ${r.remark }
											</c:forEach>
										</td>
									</tr>
								</c:forEach>
							</table>
		                  	<div class="form-group">
								<shiro:hasPermission name="role:update">
		                  		<button type="submit" class="btn btn-primary">确定</button>
		                  		</shiro:hasPermission>
		                  		<button type="button" class="btn" onclick="history.back();">取消</button>
		                  	
		                  	</div>
						</form>
					</div>
					<div class="box-footer clearfix"></div>
				</div>
			</div>
		</div>
	</section>
	<script type="text/javascript">
	$(document).ready(function() {
		
		$("form").submit(function(){
			if($("input[name='perm']:checked").length <=0){
				layer.alert('权限不能为空！', {
					  icon: 2,
					  skin: 'layer-ext-moon' 
				});
				return false;
			}
		});

	});
	</script>
</body>