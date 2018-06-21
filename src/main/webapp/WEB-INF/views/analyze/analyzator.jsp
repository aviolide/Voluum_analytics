<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="ru">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

	<title></title>


	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>

	<link href="${pageContext.request.contextPath}/resources/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	<link href="${pageContext.request.contextPath}/resources/css/style.css" rel="stylesheet">

	<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
	<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
	<!--[if lt IE 9]>
	<script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
	<script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
	<![endif]-->
</head>
<body>

<div class="container">

	<div class="header">

		<div class="row">
			<div class="col-sm-2">
				<p>Group by:</p>
			</div>
			<div class="col-sm-10">
				<div class="custom-control custom-checkbox">
					<input type="checkbox" class="custom-control-input" id="customCheck1" name="offeros" value="true">
					<label class="custom-control-label" for="customCheck1">Offer & OS</label>
				</div>
				<div class="custom-control custom-checkbox">
					<input type="checkbox" class="custom-control-input" id="customCheck2" name="offerbrowser" value="true">
					<label class="custom-control-label" for="customCheck2">Offer & Browser</label>
				</div>
				<div class="custom-control custom-checkbox">
					<input type="checkbox" class="custom-control-input" id="customCheck3" name="offerosbrowser" value="true">
					<label class="custom-control-label" for="customCheck3">Offer & OS & Browser</label>
				</div>
			</div>
		</div>
		<br/>
		<div class="row">
			<div class="col-sm-12">
				<div class="btn-box">
					<button class="btn btn-success btnapply2">Apply</button>
				</div>
			</div>
		</div>


	</div>
	<div class="main">
		<table class="table">
			<thead>
			<tr>
				<th scope="col">Status</th>
				<th scope="col">Name</th>
				<th scope="col">ID</th>
				<th scope="col">Visit</th>
				<th scope="col"></th>
			</tr>
			</thead>
			<tbody>
			<%--<form:form method="post" action="campaigns_result">--%>
			<c:forEach var="campaign" items="${campaignList.rows}" varStatus="status">
				<tr>
					<c:if test="${campaign.active == true}">
						<td><span class="status-act"></span></td>
					</c:if>
					<c:if test="${campaign.active == false}">
						<td><span class="status-off"></span></td>
					</c:if>
					<td>${campaign.campaignName}</td>
					<td class="cid">${campaign.campaignId}</td>
					<td class="">${campaign.visits}</td>

					<td>

						<div class="btn btnanalyze">
							<a href="<c:url value='/analyze/analyzator/${status.index}'/>" target="_blank">Analyze</a>
						</div>

					</td>
				</tr>
			</c:forEach>
			<%--</form:form>--%>
			</tbody>
		</table>
	</div>



</div>

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
</body>
</html>