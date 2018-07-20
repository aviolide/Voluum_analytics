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
    <div class="row">
        <div class="col-sm-6">
            <p>Data</p>
            <textarea name="out2" class="form-control" rows="5">${campaign.campaignId}&#13;&#10;<c:forEach var="zone" items="${campaign.zonesList}">${zone.customVariable1}&#13;&#10;</c:forEach></textarea>
            <p/>
        </div>
    </div>
	<div class="main">
		<h3>${campaign.campaignId}</h3>
		<div class="content">
			<div class="output">
				<c:forEach var="zone" items="${campaign.zonesList}" varStatus="status">
                    <div class="line">${zone.customVariable1} &nbsp; ${zone.profit}$</div>
                </c:forEach>

			</div>
			<div class="row">
				<div class="col-sm-6">
					<p>All Place &nbsp; ${campaign.summProfit}$ &nbsp; size ${campaign.zonesList.size()}</p>
					<textarea name="out1" class="form-control" rows="5"><c:forEach var="zone" items="${campaign.zonesList}">${zone.customVariable1}&#13;&#10;</c:forEach></textarea>
				</div>
				<%--<div class="col-sm-6">--%>
					<%--<p>New Place</p>--%>
					<%--<textarea name="out2" class="form-control" rows="5"></textarea>--%>
				<%--</div>--%>

			</div>
		</div>
	</div>
    <p/>
</div>

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>
</html>