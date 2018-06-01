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
<title>开启分页 - 数据表格</title>
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
<form  id="inputForm" action="${ctx}/sys/role/savePerm" method="post">
	<input type="hidden" name="id" value="${entity.id }"/>
	<div class="layui-fluid">
		<div class="layui-row layui-col-space15">
			<div class="layui-col-md12">
				<div class="layui-card">
					<div class="layui-card-header">权限配置</div>
					
					<div class="layui-card-body">
					
					<div class="layui-row layui-col-space10 layui-form-item">
		                <div class="layui-col-lg3">
		                  <label class="layui-form-label">角色：</label>
		                  <div class="layui-input-block">
		                     <input type="text" name="name" value="${entity.name }" class="layui-input" disabled="disabled">
		                  </div>
		                </div>
		              </div>
						<table class="layui-table">
							<thead>
								<tr>
								    <th colspan="2">权限</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${perms }" var="perm">
									<tr>
										<th style="width:120px">${perm.remark}</th>
										<td>
										<div class="layui-col-md12">
											<c:forEach items="${perm.children }" var="r"  varStatus="st">
													<input type="checkbox" name="perm" value="${r.id}" lay-skin="primary" title="${r.remark }" 
														<c:forEach items="${entity.perms}" var="e"> 
										                 	<c:if test="${r.id eq e.id}"> checked</c:if>
										                 </c:forEach>
													/> ${r.remark }
											</c:forEach>
											</div>
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					<div class="layui-form-item">
		                <div class="layui-input-block">
		                <tags:hasAnyPermissions name="role:update">
		                  <button class="layui-btn"  id="submit" type="submit">提交</button>
		                </tags:hasAnyPermissions>
		                  <button type="button" class="layui-btn layui-btn-primary" onclick="history.back(-1);">返回</button>
		                </div>
		              </div>
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
		}).use(['index', 'set']);
		
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
</html>
