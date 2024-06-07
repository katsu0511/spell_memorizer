<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="index">
			<ul>
				<c:forEach var="book" items="${books}">
					<li>
						<a href="${pageContext.request.contextPath}/index/chapter?id=${book.book_code}">${book.book_name}</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>
