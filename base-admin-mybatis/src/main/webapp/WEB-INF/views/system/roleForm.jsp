<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>添加角色</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/style/admin.css" media="all">
</head>
<body>
<form id="inputForm" class="form-horizontal" action="${ctx}/sys/role/save" method="post">
<input type="hidden" name="id" value="${entity.id }"/>
  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header"><c:if test="${empty entity.id}">添加</c:if><c:if test="${!empty entity.id}">修改</c:if>角色</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">角色名</label>
                <div class="layui-input-inline">
                  <input type="text" name="name" value="${entity.name }" class="layui-input" lay-verify="required" placeholder="请输入角色名" id="roleName">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">备注</label>
                <div class="layui-input-inline">
                  <input type="text" name="remark" value="${entity.remark }" placeholder="请输入备注" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                <tags:hasAnyPermissions name="role:create, role:update">
                  <button class="layui-btn"  id="submit" type="submit" lay-submit>提交</button>
                </tags:hasAnyPermissions>
                  <button type="button" class="layui-btn layui-btn-primary" onclick="history.back(-1);">返回</button>
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
  
  $(function (){
		//聚焦第一个输入框
		$("#roleName").focus();
		
		//为inputForm注册validate函数
		$("#inputForm").validate({
			rules : {
				name : {
					remote : {
						url: "${ctx}/sys/role/checkRoleName"
						, type: "post"
						, data: {old: "${entity.name}"}
					}
				}
			},
			messages : {
				name : {
					remote : "<div style='color:red'>角色名称已存在!</div>"
				}
			}
		});
  })
  
  	
  </script>
</body>
</html>
