<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<aside class="main-sidebar">
	<section class="sidebar">
		<ul class="sidebar-menu">
			<li id="dashboard-menu"><a href="${ctx}/"><i class='fa fa-link'></i> <span>首页</span></a></li>
			<tags:hasAnyPermissions name="perm_user,perm_role">
				<li id="sys-menu" class="treeview">
					<a href="#">
						<i class="fa fa-dashboard"></i> <span>系统管理</span>
						<span class="pull-right-container">
						<i class="fa fa-angle-left pull-right"></i>
					</span>
					</a>
					<ul class="treeview-menu">
						<shiro:hasPermission name="perm_user">
						<li id="sys-user-menu"><a href="${ctx }/sys/user"><i class="fa fa-circle-o"></i> 用户管理</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="perm_role">
						<li id="sys-role-menu"><a href="${ctx }/sys/role"><i class="fa fa-circle-o"></i> 角色管理</a></li>
						</shiro:hasPermission>
					</ul>
				</li>
			</tags:hasAnyPermissions>
		</ul>
	</section>
</aside>