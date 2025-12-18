<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.io.*,java.util.*,java.text.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="../common/header.jsp" flush="true"/>

<main>
	<div class="container">
		<div class="index">
			<div class="book_titles">
				<a href="${pageContext.request.contextPath}/index/chapter?book_code=${bookCode}" class="back">
					<img src="${pageContext.request.contextPath}/img/back.png">
				</a>
				<div class="titles">
					<h2 id="book_name">${bookName}</h2>
					<h3 id="chapter_name">${chapterName}</h3>
					<input type="hidden" id="user_id" value="${userId}">
				</div>
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
						<c:set var="flag" value="${marks[word.wordCode]}"/>
						
						<tr>
							<c:choose>
								<c:when test="${flag == true}">
									<td class="pc-only-td correct">${word.wordNumber}</td>
									<td class="sp-only-td sp-num correct" rowspan="3">${word.wordNumber}</td>
								</c:when>
								<c:when test="${flag == false}">
									<td class="pc-only-td incorrect">${word.wordNumber}</td>
									<td class="sp-only-td sp-num incorrect" rowspan="3">${word.wordNumber}</td>
								</c:when>
								<c:otherwise>
									<td class="pc-only-td">${word.wordNumber}</td>
									<td class="sp-only-td sp-num" rowspan="3">${word.wordNumber}</td>
								</c:otherwise>
							</c:choose>
							<td class="pc-only-td">
								<div class="word_td">
									<span class="word">**********</span>
									<input type="hidden" value="${word.wordSpell}" class="spell_input">
									<div class="icon_space">
										<img src="${pageContext.request.contextPath}/img/view.png" class="icon view show">
										<img src="${pageContext.request.contextPath}/img/mask.png" class="icon mask hide">
									</div>
								</div>
							</td>
							<td class="sp-only-td sp-word" colspan="3">
								<div class="word_td">
									<span class="word">**********</span>
									<input type="hidden" value="${word.wordSpell}" class="spell_input">
									<div class="icon_space">
										<img src="${pageContext.request.contextPath}/img/view.png" class="icon view show">
										<img src="${pageContext.request.contextPath}/img/mask.png" class="icon mask hide">
									</div>
								</div>
							</td>
							<td class="pc-only-td">
								<div class="answer_td">
									<input
										type="text"
										class="my_answer"
										autocomplete="off"
										autocorrect="off"
										autocapitalize="none"
										spellcheck="false"
										inputmode="latin"
										lang="en"
									/>
									<button class="answer_button">answer</button>
									<input type="hidden" value="${word.wordCode}">
								</div>
							</td>
							<td class="pc-only-td">
								<img src="${pageContext.request.contextPath}/img/play.png" class="icon play">
								<input type="hidden" value="${word.wordSound}">
							</td>
							<td class="pc-only-td">
								<img src="${pageContext.request.contextPath}/img/pause.png" class="icon pause show">
								<img src="${pageContext.request.contextPath}/img/replay.png" class="icon replay hide">
							</td>
							<td class="pc-only-td">
								<span class="judgement"></span>
							</td>
						</tr>
						<tr class="sp-only-tr">
							<td class="sp-answer" colspan="3">
								<div class="answer_td">
									<input
										type="text"
										class="my_answer"
										autocomplete="off"
										autocorrect="off"
										autocapitalize="none"
										spellcheck="false"
										inputmode="latin"
										lang="en"
									/>
									<button class="answer_button sp">answer</button>
									<input type="hidden" value="${word.wordCode}">
								</div>
							</td>
						</tr>
						<tr class="sp-only-tr">
							<td class="sp-icon">
								<img src="${pageContext.request.contextPath}/img/play.png" class="icon play">
								<input type="hidden" value="${word.wordSound}">
							</td>
							<td class="sp-icon">
								<img src="${pageContext.request.contextPath}/img/pause.png" class="icon pause show">
								<img src="${pageContext.request.contextPath}/img/replay.png" class="icon replay hide">
							</td>
							<td class="sp-icon">
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
