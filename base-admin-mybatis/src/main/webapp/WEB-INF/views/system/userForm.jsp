<%@ page contentType="text/html;charset=UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>添加用户</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/layui/css/layui.css" media="all">
  <link rel="stylesheet" href="${ctx }/static/layuiadmin/style/admin.css" media="all">
</head>
<body>
<form id="inputForm" class="form-horizontal" action="${ctx}/sys/user/save" method="post">
<input type="hidden" name="id" value="${entity.id }"/>
<input type="hidden" name="ids" />
  <div class="layui-fluid">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md12">
        <div class="layui-card">
          <div class="layui-card-header"><c:if test="${empty entity.id}">添加</c:if><c:if test="${!empty entity.id}">修改</c:if>用户</div>
          <div class="layui-card-body" pad15>
            
            <div class="layui-form" lay-filter="">
              <div class="layui-form-item">
                <label class="layui-form-label">用户名</label>
                <div class="layui-input-inline">
                  <input type="text" name="username" value="${entity.username }" class="layui-input" placeholder="请输入用户名" <c:if test="${!empty entity.id}">disabled="disabled"</c:if>>
                </div>
                <div class="layui-form-mid layui-word-aux">不可修改,用于后台登入名</div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">姓名</label>
                <div class="layui-input-inline">
                  <input type="text" name="nickname" value="${entity.nickname }" lay-verify="nickname" autocomplete="off" placeholder="请输入姓名" class="layui-input">
                </div>
              </div>
              <div class="layui-form-item">
                <label class="layui-form-label">所属角色</label>
                <div class="layui-input-inline">
                   <div class="layui-col-md12">
                   	  <c:forEach var="e" items="${roles }" varStatus="st">
		              	<input type="checkbox" name="roleIds" value="${e.id }" title="${e.name }" lay-skin="primary" 
		              		<c:forEach var="r" items="${entity.roles }">
		              			 <c:if test="${r.id == e.id }">checked</c:if>
		              		</c:forEach> >
		              </c:forEach>
		            </div>
                </div>
              </div>
              <div class="layui-form-item">
                <div class="layui-input-block">
                  <button class="layui-btn"  id="submit" type="submit">提交</button>
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
  <script>
  layui.config({
    base: '${ctx }/static/layuiadmin/' //静态资源所在路径
  }).extend({
    index: 'lib/index' //主入口模块
  }).use(['index', 'set']);
  
  $(function (){
	  $("#inputForm").submit(function(){
			var username = $('input[name="username"]').val();
			if(username == null || username == '' || typeof(username) == 'undefined'){
				layer.alert('用户名不能为空', {
					  icon: 2,
					  skin: 'layer-ext-moon' 
				})
				return false;
			};
			
			var nickname = $('input[name="nickname"]').val();
			if(nickname == null || nickname == '' || typeof(nickname) == 'undefined'){
				layer.alert('姓名不能为空', {
					  icon: 2,
					  skin: 'layer-ext-moon' 
				})
				return false;
			};
			
			var ids = "";
			$('input[name="roleIds"]:checked').each(function(){
				ids += $(this).val() + ",";
			});
			
			if(ids.length > 0){
				ids = ids.substring(0, ids.length - 1);
				$('input[name="ids"]').val(ids);
			}else{
				layer.alert('用户角色不能为空', {
					  icon: 2,
					  skin: 'layer-ext-moon' 
				})
				return false;
			};
		});
	  
  })
  
  	
  </script>
</body>
</html>

