<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main id="login_main">
	<div class="container">
	
		<div class="login_screen">
		 <div class="login_elements">
		 	
		 	<h2 id="login_title">Login</h2>
			
			<form class="login_form" action="${pageContext.request.contextPath}/login" method="POST">
				<div class="login_items">
				
					<div class="login_item">
						<label for="email" class="login_label">Eメール :</label>
						<div class="login_input">
							<input type="text" id="email" class="login_info" name="email" value="${email}" autocomplete="off">
						</div>
					</div>
					
					<div class="login_item">
						<label for="password" class="login_label">Password :</label>
						<div class="login_input">
							<input type="password" id="password" class="login_info" name="password" value="${password}" autocomplete="off">
						</div>
					</div>
					
					<c:if test="${errorMessage != null && errorMessage != ''}">
						<p class="error_message">${errorMessage}</p>
					</c:if>
					
					<div class="login_item">
						<div class="login_submit">
							<input type="submit" id="login_btn" class="button login" name="login_btn" value="ログイン">
						</div>
					</div>
					
				</div>
			</form>
			 
		 </div>
		</div>
		
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>