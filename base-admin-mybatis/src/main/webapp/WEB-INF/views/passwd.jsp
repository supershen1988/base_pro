<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>修改密码</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/style/admin.css" media="all">
</head>
<body>
<form id="inputForm" class="form-horizontal" action="${ctx }/profile/passwd" method="post">
<input type="hidden" name="id" value="${entity.id }"/>
<input type="hidden" name="ids" />
  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header">修改密码</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">旧密码</label>
                <div class="layui-input-inline">
                  <input type="password" id="old_password" name="old_password" value="" class="layui-input required" placeholder="请输入旧密码" >
                </div>
              </div>
               <div class="layui-form-item">
                <label class="layui-form-label">新密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="new_password" value="" class="layui-input required" placeholder="请输入新密码" id="new_password">
                </div>
              </div>
               <div class="layui-form-item">
                <label class="layui-form-label">确认密码</label>
                <div class="layui-input-inline">
                  <input type="password" name="plain_password" equalTo="#new_password" value="" class="layui-input required" placeholder="请输入确认密码" >
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn"  id="submit" type="submit">提交</button>
                </div>
              </div>
            </div>
            
          </div>
        </div>
      </div>
    </div>
  </div>
</form>
  <script src="${ctx }/static/layuiadmin/layui/layui.js"></script> 
  <script src="${ctx }/static/jquery/jquery-2.2.3.min.js"></script> 
  <script src="${ctx }/static/jquery-validation-1.14.0/jquery.validate.min.js"></script>
  <script src="${ctx }/static/jquery-validation-1.14.0/localization/messages_zh.js"></script>  
  <script>
	  layui.config({
	    base: '${ctx }/static/layuiadmin/' //静态资源所在路径
	  }).extend({
	    index: 'lib/index' //主入口模块
	  }).use(['index', 'set']);
  	
	 $(document).ready(function() {
		//聚焦第一个输入框
		$("#old_password").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate({
		});
	 });
	</script>
</body>
</html>

