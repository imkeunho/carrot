<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>${ userinfo.nickname }의거래 후기 페이지</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous" />
<link
	href="${pageContext.request.contextPath}/resources/css/mypagetrade.css"
	rel="stylesheet" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
	crossorigin="anonymous"></script>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"
	integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
	crossorigin="anonymous"></script>
<script
	src="${pageContext.request.contextPath}/resources/js/userTradePage.js"
	defer></script>
</head>
<body>
	<jsp:include page="/WEB-INF/views/header.jsp" />
	<div class="section">
		<div class="section-header">
			<div class="user-info-wrapper">
				<div id="user-info">
					<h4 id="username">${ userinfo.nickname }</h4>
					<span id="location">${ userinfo.loc1 } ${ userinfo.loc2 } ${ userinfo.loc3 }</span>
					<p id="dealCnt">거래횟수: ${ userinfo.completed_cnt }회</p>
				</div>
			</div>
		</div>
		<div class="section-content">
			<div class="section-tab">
				<ul class="nav nav-pills">
					<li class="nav-item"><a class="nav-link" aria-current="page"
						href="userpageSell?id=${userinfo.id}">판매물품</a></li>
					<li class="nav-item"><a class="nav-link active"
						href="userpageTrade?id=${userinfo.id}">거래후기</a></li>
				</ul>
			</div>
			<div class="reviews">
				<!-- 테스트용..!! 임시입니다. 바뀔거에요 -->
				<c:if test="${not empty list}">
					<c:forEach var="list" items="${ list }">
						<c:if test="${list.seller == userinfo.id }">
							<c:if test="${list.seller_content != null }">
								<div class="review card">
									<div class="review-header">
										<h4>${ list.buyer }</h4>
									</div>

									<div class="review-content">${list.seller_content}</div>
									<div class="review-footer">${list.created_at }</div>
								</div>
							</c:if>
						</c:if>
					</c:forEach>
				</c:if>
				<c:if test="${empty list }">
					<h3>내역이 없습니다.</h3>
				</c:if>
			</div>
		</div>
		<div class="section-footer"></div>
	</div>
</body>
</html>
