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
		<h1>角色<small>列表</small></h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li class="active">角色列表</li>
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
					<div class="box-header with-border">
						<form class="form-inline" id="paramTypeForm" action="${ctx}/sys/role">
							<div class="form-group">
								<label for="searchCode">名称</label>
								<input type="text" id="name" name="search_LIKE_name" 
									class="form-control" value="${param.search_LIKE_name}">
							</div>
							<button type="submit" class="btn btn-default">检索</button>
						</form>
					</div>
					<!-- /.box-header -->
					<div class="box-body">
						<table class="table table-bordered table-striped">
							<tbody>
								<tr>
									<th style="width: 10px">#</th>
									<th>角色名称</th>
									<th>角色描述</th>
									<th>所含权限</th>
									<th style="width: 140px">操作</th>
								</tr>
								<c:forEach var="e" items="${page.content }" varStatus="st">
									<tr>
										<td>${st.index + 1 }</td>
										<td>${e.name }</td>
										<td>${e.remark }</td>
										<td>${e.permRemarks }</td>
										<td>
											<shiro:hasPermission name="role:update">
												<a href="${ctx}/sys/role/update/${e.id}" >修改</a>
												<a href="${ctx}/sys/role/perm/${e.id}" >配置权限</a>
											</shiro:hasPermission>
											<shiro:hasPermission name="role:delete">
												<a href="javascript:deleteEntity('${ctx}/sys/role/delete/${e.id}');" >删除 </a>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- /.box-body -->
					<div class="box-footer clearfix">
						<shiro:hasPermission name="role:create">
							<a class="btn btn-default " href="${ctx}/sys/role/create">新增</a>
						</shiro:hasPermission>
						<tags:pagination page="${page}" paginationSize="5" />
					</div>
				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
	<!-- /.content -->
</body>
</html>