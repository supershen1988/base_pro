<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="com.supershen.example.utils.DateUtils" %>
<%@ page import="com.supershen.example.core.UserHelper" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<header class="main-header">
	<!-- Logo -->
	<a href="index2.html" class="logo"> <!-- mini logo for sidebar mini 50x50 pixels -->
		<span class="logo-lg"><b>控制台</b></span>
	</a>
	<!-- Header Navbar: style can be found in header.less -->
	<nav class="navbar navbar-static-top">
		<!-- Sidebar toggle button-->
		<a href="#" class="sidebar-toggle" data-toggle="offcanvas"
			role="button"> <span class="sr-only">Toggle navigation</span>
		</a>
		<div class="navbar-custom-menu">
			<ul class="nav navbar-nav">
				<!-- User Account: style can be found in dropdown.less -->
				<li class="dropdown user user-menu">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
						<img src="${ctx }/static/dist/img/user2-160x160.jpg" class="user-image" lt="User Image"> 
						<span class="hidden-xs"><%=UserHelper.getCurrentUser().getUsername() %></span>
					</a>
					<ul class="dropdown-menu">
						<!-- User image -->
						<li class="user-header">
							<img src="${ctx }/static/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image">
							<p>
								欢迎您 - <%=UserHelper.getCurrentUser().getUsername() %>
							</p>
						</li>
						<!-- Menu Footer-->
						<li class="user-footer">
							<div class="pull-left">
								<a href="${ctx }/profile" class="btn btn-default btn-flat">个人资料</a>
							</div>
							<div class="pull-right">
								<a href="${ctx }/logout" class="btn btn-default btn-flat">退出</a>
							</div>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</nav>
</header>