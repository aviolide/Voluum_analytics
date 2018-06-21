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
	<div class="request-box">
			<div class="row">			
				<div class="col-sm-6">
					<form:form method="post" action="allcampaigns" target="_blank">
						<%--<form class="request-form">--%>
                            <%--<table>--%>
							<form:input path="apiId" cssClass="form-control" placeholder="Api Id"/>
							<form:errors path="apiId"/>
                            <form:input path="apiKey" cssClass="form-control" placeholder="Api Key"/>
							<form:errors path="apiKey"/>
                            <form:input path="formula" cssClass="form-control" placeholder="Rule"/>
							<form:errors path="formula"/>
                            <form:input path="propeller" cssClass="form-control" placeholder="zoneid"/>
                        <tr>
                            <td colspan="2">
                                <input type="submit" value="Start" class="btn start"/>
                            </td>
                        </tr>
					</form:form>

				</div>
                <form:form method="post" action="allcheck" cssClass="col-sm-6" target="_blank">
                    <%--<div class="col-sm-6">--%>
                        <textarea name="allchkplace" class="form-control" rows="7" placeholder="ids"></textarea>
						<%--<div class="btn allchkplace">All Check Place</div>--%>
						<%--<input class="form-control" type="text" name="usertxt" placeholder="your text here"	>--%>
                        <input type="submit" value="All Check" class="btn allchkplace"/>
						<%--<a href="#" target="_blank"/>--%>
                    <%--</div>--%>
                </form:form>
			</div>
	</div>
</div>


	<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>

	<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>
</html>