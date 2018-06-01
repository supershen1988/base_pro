<%@page import="com.supershen.example.core.UserHelper"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />


<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>角色管理</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
<link rel="stylesheet"
	href="${ctx }/static/layuiadmin/layui/css/layui.css" media="all">
<link rel="stylesheet" href="${ctx }/static/layuiadmin/style/admin.css"
	media="all">
	<script src="${ctx }/static/jquery/jquery-2.2.3.min.js"></script>
</head>
<body>
<form  id="paramTypeForm" action="${ctx}/sys/role">
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">角色管理</div>
					
					<div class="layui-card-body">
					<c:if test="${message != null && message != ''}">
					    <blockquote class="layui-elem-quote">
					       	${message}
					    </blockquote>
				    </c:if>
					<div class="layui-row layui-col-space10 layui-form-item">
		                <div class="layui-col-lg3">
		                  <label class="layui-form-label">角色名称：</label>
		                  <div class="layui-input-block">
		                    <input type="text" name="search_LIKE_name" value="${param.search_LIKE_name}" placeholder="" autocomplete="off" class="layui-input">
		                  </div>
		                </div>
		               
		                 <div class="layui-col-lg3">
		                 	<button class="layui-btn" type="submit">查询</button>
		                 	<button class="layui-btn" type="button" onclick="window.location.href='${ctx}/sys/role/create'">添加角色</button>
		             	 </div>
		             	 <div class="layui-col-lg3">
		                  
		                 </div>
		              </div>
		                 
						<table class="layui-table">
							<thead>
								<tr>
								    <th>编号</th>
									<th>角色名称</th>
									<th>角色描述</th>
									<th style="width: 140px">操作</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach var="e" items="${page.records }" varStatus="st">
								<tr>
									<td>${st.index + 1 }</td>
										<td>${e.name }</td>
										<td>${e.remark }</td>
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
						<tags:lay-pagination page="${page}" paginationSize="5" />
						
						
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
	<script src="${ctx }/static/layuiadmin/layui/layui.js"></script>
	<script src="${ctx }/static/base/base.js"></script>
	<script type="text/javascript">
		//初始化layui
		layui.config({
			base : '${ctx }/static/layuiadmin/' // 静态资源所在路径
		}).extend({
			index : 'lib/index' // 主入口模块
		}).use('index');
	</script>
	
</body>
</html>