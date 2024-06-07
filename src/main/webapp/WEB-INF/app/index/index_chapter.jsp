<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="index">
			<div class="book_titles">
				<a href="${pageContext.request.contextPath}/home" class="back">
					<img src="${pageContext.request.contextPath}/img/back.png">
				</a>
				<h2>${book_name}</h2>
			</div>
			<ul>
				<c:forEach var="chapter" items="${chapters}">
					<li>
						<a href="${pageContext.request.contextPath}/index/word?id=${chapter.chapter_code}">${chapter.chapter_name}</a>
					</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>
