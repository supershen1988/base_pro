<%@page import="com.supershen.example.utils.UserHelper"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<html>
<head>
<title>用户管理</title>
</head>
<body>
	<!-- Content Header (Page header) -->
	<section class="content-header">
		<h1>用户<small>列表</small></h1>
		<ol class="breadcrumb">
			<li><a href="${ctx}/"><i class="fa fa-dashboard"></i> 首页</a></li>
			<li class="active">用户列表</li>
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
						<form class="form-inline" id="paramTypeForm" action="${ctx}/sys/user">
							<div class="form-group">
								<label for="searchCode">名称</label>
								<input type="text" id="username" name="search_LIKE_username" 
									class="form-control" value="${param.search_LIKE_username}">
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
									<th>用户账号</th>
									<th>姓名</th>
									<th>角色</th>
									<th>创建人</th>
									<th>创建时间</th>
									<th>更新人</th>
									<th>更新时间</th>
									<th style="width: 140px">操作</th>
								</tr>
								<c:forEach var="e" items="${page.content }" varStatus="st">
									<tr>
										<td>${st.index + 1 }</td>
										<td>${e.username }</td>
										<td>${e.nickname }</td>
										<td>${e.roleRemarks }</td>
										<td>${e.creater }</td>
										<td>
											<fmt:formatDate type="both" value="${e.createTime }" pattern="yyyy-MM-dd HH:mm" />
										</td>
										<td>${e.updater }</td>
										<td>
											<fmt:formatDate type="both" value="${e.updateTime }" pattern="yyyy-MM-dd HH:mm" />
										</td>
										<td>
											<shiro:hasPermission name="user:update">
												<a href="${ctx}/sys/user/update/${e.id}">修改</a>
												<a href="javascript:changePasswordEntity('${ctx}/sys/user/changePassword/${e.id}');" >重置密码 </a>
											</shiro:hasPermission>
											<shiro:hasPermission name="user:delete">
												<a href="javascript:deleteEntity('${ctx}/sys/user/delete/${e.id}');" >删除 </a>
											</shiro:hasPermission>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
					<!-- /.box-body -->
					<div class="box-footer clearfix">
						<shiro:hasPermission name="user:create">
							<a class="btn btn-default " href="${ctx}/sys/user/create">新增</a>
						</shiro:hasPermission>
						<tags:pagination page="${page}" paginationSize="5" />
					</div>
				</div>
				<!-- /.box -->
			</div>
		</div>
	</section>
	<!-- /.content -->
	<script type="text/javascript">
		function changePasswordEntity(url){
			if(url == null || url == '' || typeof(url) == 'undefined'){
				return false;
			}
			
			layer.confirm('确定重置密码?', function(){
				location.href = url;
			}, function(){
				layer.closeAll();
			});
		}
	</script>
</body>
</html>