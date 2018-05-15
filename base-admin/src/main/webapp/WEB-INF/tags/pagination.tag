<%@tag pageEncoding="UTF-8"%>
<%@ attribute name="page" type="org.springframework.data.domain.Page" required="true"%>
<%@ attribute name="paginationSize" type="java.lang.Integer" required="true"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%
int current = page.getNumber() + 1;
int begin = Math.max(1, current - paginationSize/2);
int end = Math.min(begin + (paginationSize - 1), page.getTotalPages());

request.setAttribute("current", current);
request.setAttribute("begin", begin);
request.setAttribute("end", end);
%>

<ul class="pagination pagination-sm no-margin pull-right">
	<li class="paginate_button previous" id="example2_previous">
		<% if (page.hasPrevious()){%>
		<a href="?page=${current-1}&sortType=${sortType}&${searchParams}" aria-controls="example2" data-dt-idx="0" tabindex="0">上一页</a>
		<%}else{%>
		<a aria-controls="example2" data-dt-idx="0" tabindex="0" class="disabled">首页</a>
		<%} %>
	</li>
	<c:forEach var="i" begin="${begin}" end="${end}">
		<c:choose>
			<c:when test="${i == current}">
				<li class="paginate_button active">
					<a aria-controls="example2" data-dt-idx="1" tabindex="0">${i }</a>
				</li>
			</c:when>
			<c:otherwise>
				<li class="paginate_button ">
					<a href="?page=${i}&sortType=${sortType}&${searchParams}" aria-controls="example2" data-dt-idx="2" tabindex="0">${i }</a>
				</li>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<li class="paginate_button next" id="example2_next">
		<% if (page.hasNext()){%> 
		<a href="?page=${current+1}&sortType=${sortType}&${searchParams}" aria-controls="example2" data-dt-idx="7" tabindex="0">下一页</a>
		<%}else{%>
		<a aria-controls="example2" data-dt-idx="7" tabindex="0" class="disabled">尾页</a>
		<%} %>
	</li>
</ul>

