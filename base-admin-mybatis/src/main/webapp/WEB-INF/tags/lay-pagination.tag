<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="com.baomidou.mybatisplus.plugins.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
int current = page.getCurrent();
int begin = Math.max(1, current - paginationSize/2);
int end = Math.min(begin + (paginationSize - 1), (int)page.getPages());
int count = (int)page.getTotal();
//System.out.println("current:" + current+",begin:" +begin + ",end:" + end +",totalPages:" + page.getPages() );
request.setAttribute("count", count);
request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
%>
<div class="layui-box layui-laypage layui-laypage-default" id="layui-laypage-1">
	<span class="layui-laypage-count">共 ${count}条</span>
	<% if (page.hasPrevious()){%>
	<a href="?page=${current-1}&sortType=${sortType}&${searchParams}" class="layui-laypage-prev" data-page="0">上一页</a>
	<%}else{%>
	<a href="javascript:;" class="layui-laypage-prev layui-disabled" data-page="0">上一页</a>
	<%} %>
	
	<c:forEach var="i" begin="${begin}" end="${end}">
		<c:choose>
			<c:when test="${i == current}">
				<span class="layui-laypage-curr">
					<em class="layui-laypage-em"></em>
					<em>${i }</em>
				</span>
			</c:when>
			<c:otherwise>
				<a href="?page=${i}&sortType=${sortType}&${searchParams}" data-page="${i }">${i }</a>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<% if (page.hasNext()){%> 
	<a href="?page=${current+1}&sortType=${sortType}&${searchParams}" class="layui-laypage-next" data-page="2">下一页</a>
	<%}else{%>
	<a href="javascript:;" class="layui-laypage-next layui-disabled" data-page="2">下一页</a>
	<%} %>
	
		<span class="layui-laypage-limits">
			<select name="pageSize" id="pageSizeSelect">
				<option value="10" <c:if test="${page.size == 10}">selected="selected"</c:if>>10 条/页</option>
				<option value="20" <c:if test="${page.size == 20}">selected="selected"</c:if>>20 条/页</option>
				<option value="30" <c:if test="${page.size == 30}">selected="selected"</c:if>>30 条/页</option>
				<option value="40" <c:if test="${page.size == 40}">selected="selected"</c:if>>40 条/页</option>
				<option value="50" <c:if test="${page.size == 50}">selected="selected"</c:if>>50 条/页</option>
			</select>
		</span>
		<span class="layui-laypage-skip">
			到第<input type="number" min="1" class="layui-input" name="page" value="${current}">
			页
			<button type="button" class="layui-laypage-btn" name="pageBtn">确定</button>
		</span>
	
	<script >
		$(function(){
			$('#pageSizeSelect').change(function(){
				location.href='?pageSize='+$(this).val()+'&page='+$('input[name="page"]').val()+'&sortType=${sortType}&${searchParams}';
			});
			
			$('button[name="pageBtn"]').click(function(){
				location.href='?pageSize='+$('#pageSizeSelect').val()+'&page='+$('input[name="page"]').val()+'&sortType=${sortType}&${searchParams}';
			});
		})
	</script>
</div>
