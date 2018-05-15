<%@ page contentType="text/html;charset=UTF-8"%>

<%@ page import="org.apache.shiro.web.filter.authc.FormAuthenticationFilter"%>
<%@ page import="org.apache.shiro.authc.ExcessiveAttemptsException"%>
<%@ page import="org.apache.shiro.authc.IncorrectCredentialsException"%>
<%@ page import="org.apache.shiro.SecurityUtils"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>登录验证</title>
	<!-- Tell the browser to be responsive to screen width -->
	<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
	<!-- Bootstrap 3.3.6 -->
	<link rel="stylesheet" href="${ctx}/static/bootstrap/css/bootstrap.min.css">
	<!-- Font Awesome 
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">-->
  	<link rel="stylesheet" href="${ctx }/static/bootstrap/css/font-awesome.min.css">
  	<!-- Ionicons 
  	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">-->
  	<link rel="stylesheet" href="${ctx }/static/bootstrap/css/ionicons.min.css">
	<!-- Theme style -->
	<link rel="stylesheet" href="${ctx}/static/dist/css/AdminLTE.min.css">
	<!-- iCheck -->
	<link rel="stylesheet" href="${ctx}/static/plugins/iCheck/square/blue.css">

	<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  	<![endif]-->
  	<script type="text/javascript">
		function FillContent(UserName, PassWord, url){
			$("#username").val(UserName);
			$("#password").val(PassWord);
			$.ajax({
			    type: "post",
			    url: "${ctx}/login",
			    data: {username:UserName, password:PassWord},
			    success: function(data){
			  	  window.location.href=url;
			    }
			});
		}
	</script>
</head>
<body class="hold-transition login-page" onload="inputForm.username.focus();">
	<div class="login-box">
		<div class="login-logo">
			<a href="${ctx}/"><b></b>管理平台</a>
		</div>
		<!-- /.login-logo -->
		<div class="login-box-body">
			<p class="login-box-msg"></p>
			<%
				String error = (String) 
					request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
				if (error != null) {
			%>
			<div class="alert alert-error controls">
				<button class="close" data-dismiss="alert">×</button>
				登录失败，帐号或密码错误！
			</div>
			<%
				}
			%>
			<form id="inputForm" action="${ctx}/login" method="post">
				<div class="form-group has-feedback">
					<input type="text" name="username" class="form-control" placeholder="账号" value="${username }">
					<span class="glyphicon glyphicon-user form-control-feedback"></span>
				</div>
				<div class="form-group has-feedback">
					<input type="password" name="password" class="form-control" placeholder="密码">
					<span class="glyphicon glyphicon-lock form-control-feedback"></span>
				</div>
				<div class="row">
					<!-- /.col -->
					<div class="col-xs-12" style="text-align: center">
						<button type="submit" class="btn btn-primary btn-block btn-flat">登录</button>
					</div>
					<!-- /.col -->
				</div>
			</form>
		</div>
	<!-- /.login-box-body -->
	</div>
	<!-- /.login-box -->
	<!-- jQuery 2.2.3 -->
	<script src="${ctx}/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
	<!-- Bootstrap 3.3.6 -->
	<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
	<!-- iCheck -->
	<script src="${ctx}/static/plugins/iCheck/icheck.min.js"></script>
	<script type="text/javascript">
		$(function () {
			$('input').iCheck({
				checkboxClass: 'icheckbox_square-blue',
				radioClass: 'iradio_square-blue',
				increaseArea: '20%' // optional
			});
		});
	</script>
</body>
</html>
