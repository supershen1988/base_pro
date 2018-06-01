<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta http-equiv="X-UA-Compatible" content="IE=8" >
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" >
		<title>深诚Admin  - <sitemesh:write property='title'/></title>
		<!-- Tell the browser to be responsive to screen width -->
		<meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
		<!-- Bootstrap 3.3.6 -->
		<link rel="stylesheet" href="${ctx }/static/bootstrap/css/bootstrap.min.css">
		<!-- Font Awesome 
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.5.0/css/font-awesome.min.css">-->
		<link rel="stylesheet" href="${ctx }/static/bootstrap/css/font-awesome.min.css">
		<!-- Ionicons 
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/ionicons/2.0.1/css/ionicons.min.css">-->
		<link rel="stylesheet" href="${ctx }/static/bootstrap/css/ionicons.min.css">
		<!-- Theme style -->
		<link rel="stylesheet" href="${ctx }/static/dist/css/AdminLTE.min.css">
		<!-- AdminLTE Skins. Choose a skin from the css/skins
		     folder instead of downloading all of them to reduce the load. -->
		<link rel="stylesheet" href="${ctx }/static/dist/css/skins/_all-skins.min.css">
		<!-- iCheck -->
		<link rel="stylesheet" href="${ctx }/static/plugins/iCheck/flat/blue.css">
		<!-- Morris chart -->
		<link rel="stylesheet" href="${ctx }/static/plugins/morris/morris.css">
		<!-- jvectormap -->
		<link rel="stylesheet" href="${ctx }/static/plugins/jvectormap/jquery-jvectormap-1.2.2.css">
		<!-- Date Picker -->
		<link rel="stylesheet" href="${ctx }/static/plugins/datepicker/datepicker3.css">
		<!-- Daterange picker -->
		<link rel="stylesheet" href="${ctx }/static/plugins/daterangepicker/daterangepicker.css">
		<!-- bootstrap wysihtml5 - text editor -->
		<link rel="stylesheet" href="${ctx }/static/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
		<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
		<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
		<!--[if lt IE 9]>
		<script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
		<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
		<![endif]-->
		<!-- Control Sidebar -->
		<!-- jQuery 2.2.3 -->
		<script src="${ctx }/static/plugins/jQuery/jquery-2.2.3.min.js"></script>
		<!-- jQuery UI 1.11.4 
		<script src="https://code.jquery.com/ui/1.11.4/jquery-ui.min.js"></script>-->
		<script src="${ctx }/static/plugins/jQueryUI/jquery-ui.min.js"></script>
		<!-- Resolve conflict in jQuery UI tooltip with Bootstrap tooltip -->
		<script>
		  $.widget.bridge('uibutton', $.ui.button);
		</script>
		<!-- Bootstrap 3.3.6 -->
		<script src="${ctx }/static/bootstrap/js/bootstrap.min.js"></script>
		<!-- Sparkline -->
		<script src="${ctx }/static/plugins/sparkline/jquery.sparkline.min.js"></script>
		<!-- jvectormap -->
		<script src="${ctx }/static/plugins/jvectormap/jquery-jvectormap-1.2.2.min.js"></script>
		<script src="${ctx }/static/plugins/jvectormap/jquery-jvectormap-world-mill-en.js"></script>
		<!-- jQuery Knob Chart -->
		<script src="${ctx }/static/plugins/knob/jquery.knob.js"></script>
		<!-- daterangepicker 
		<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.11.2/moment.min.js"></script>-->
		<script src="${ctx }/static/plugins/moment/moment.min.js"></script>
		<script src="${ctx }/static/plugins/daterangepicker/daterangepicker.js"></script>
		<!-- datepicker -->
		<script src="${ctx }/static/plugins/datepicker/bootstrap-datepicker.js"></script>
		<!-- Slimscroll -->
		<script src="${ctx }/static/plugins/slimScroll/jquery.slimscroll.min.js"></script>
		<!-- FastClick -->
		<script src="${ctx }/static/plugins/fastclick/fastclick.js"></script>
		<!-- AdminLTE App -->
		<script src="${ctx }/static/dist/js/app.min.js"></script>
		<!-- AdminLTE dashboard demo (This is only for demo purposes) -->
		<!-- AdminLTE for demo purposes -->
		<script src="${ctx }/static/dist/js/demo.js"></script>
		<script src="${ctx }/static/layer/layer.js"></script>
		<script src="${ctx }/static/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
		<script src="${ctx }/static/jquery-validation-1.14.0/dist/localization/messages_zh.js"></script>
		<script src="${ctx }/static/plugins/jquery.form/jquery.form.js"></script>
		<style type="text/css">
			#printContent {display: none; font-size: 12px;}
			.error{color:red}
		</style>
	</head>
	<body class="hold-transition skin-blue sidebar-mini">
		<div class="wrapper">
			<%@ include file="/WEB-INF/layouts/header.jsp"%>
			<%@ include file="/WEB-INF/layouts/menus.jsp"%>  
			<div class="content-wrapper">
				<section class="content">
					<sitemesh:write property='body'/>
				</section>
			</div>
		</div>
		<script type="text/javascript">
			/** 设置日期框格式. */
			$(document).ready(function() {
				$('.input-group.date').datepicker({
					format: "yyyy-mm-dd",
				    // weekStart: 1,
				    todayBtn: "linked",
				    clearBtn: true,
				    language: "zh-CN",
				    autoclose: true,
				    todayHighlight: true,
				    toggleActive: true,
				});
				
				// 定住已选菜单
				var projectPath = window.location.pathname;
				
				var menus = projectPath.split("/");
				var ulId = '#' + menus[2] + '-menu';
				//alert(ulId);
				$(ulId).addClass('active');
				var subId = "";
				if (menus.length == 4) {
					subId = '#' + menus[2] + "-" + menus[3] + '-menu';
					
				}
				if (menus.length == 5) {
					subId = '#' + menus[2] + "-" + menus[3] + "-" + menus[4] + '-menu';
				}
				if (menus.length > 5) {
					subId = '#' + menus[2] + "-" + menus[3] + "-" + menus[4] + '-menu';
				}
				if(subId != '' && typeof(subId) != 'undefined'){
					$(subId).addClass('active');
				}
				
			});
			
			function deleteEntity(url){
				if(url == null || url == '' || typeof(url) == 'undefined'){
					return false;
				}
				
				layer.confirm('确定删除?', function(){
					location.href = url;
				}, function(){
					layer.closeAll();
				});
			}
		</script>
	</body>
</html>
