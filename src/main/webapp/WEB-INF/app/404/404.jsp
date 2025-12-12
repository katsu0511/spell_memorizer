<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="main">
			<div class="display">
				<p class="not_found">404 - Not Found</p>
				<a class="back_btn" href="${pageContext.request.contextPath}/home">Home</a>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>
