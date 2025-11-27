<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="index">
			<div class="book_titles">
				<a href="${pageContext.request.contextPath}/index/chapter?id=${book_code}" class="back">
					<img src="${pageContext.request.contextPath}/img/back.png">
				</a>
				<h2 id="book_name">${book_name}</h2>
				<h3 id="chapter_name">${chapter_name}</h3>
			</div>
			<table border="1">
				<thead>
					<tr>
						<td>No.</td>
						<td>Word</td>
						<td>Input</td>
						<td>Sound</td>
						<td>Pause</td>
						<td>Judgement</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="word" items="${words}">
						<tr>
							<td>${word.word_number}</td>
							<td>
								<div class="word_td">
									<span class="word">**********</span>
									<input type="hidden" value="${word.word_spell}" class="spell_input">
									<div class="icon_space">
										<img src="${pageContext.request.contextPath}/img/view.png" class="icon view show">
										<img src="${pageContext.request.contextPath}/img/mask.png" class="icon mask hide">
									</div>
								</div>
							</td>
							<td>
								<div class="answer_td">
									<input type="text" class="my_answer">
									<button class="answer_button">answer</button>
								</div>
							</td>
							<td>
								<img src="${pageContext.request.contextPath}/img/play.png" class="icon play">
								<input type="hidden" value="${word.word_sound}">
							</td>
							<td>
								<img src="${pageContext.request.contextPath}/img/pause.png" class="icon pause show">
								<img src="${pageContext.request.contextPath}/img/replay.png" class="icon replay hide">
							</td>
							<td>
								<span class="judgement"></span>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			
			<div class="pages">
				<c:forEach var="page" items="${pages}">
					<c:choose>
						<c:when test="${page.link == ''}">
							<c:choose>
								<c:when test="${page.display == '...'}">
									<div class="element">${page.display}</div>
								</c:when>
								<c:otherwise>
									<div class="element page_nation current">${page.display}</div>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${page.display == 'left' || page.display == 'right'}">
									<a href="${pageContext.request.contextPath}${page.link}" class="element adjacent">
										<img src="${pageContext.request.contextPath}/img/${page.display}.png">
									</a>
								</c:when>
								<c:otherwise>
									<a href="${pageContext.request.contextPath}${page.link}" class="element page_nation link">${page.display}</a>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</c:forEach>
			</div>
		</div>
	</div>
</main>

<jsp:include page="../common/footer.jsp" flush="true"/>
