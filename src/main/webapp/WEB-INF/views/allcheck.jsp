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

<div class="main">
    <div class="container">

        <div class="row">

            <div class="col-sm-6">
                <h2>All Data by Formula - ${formula}</h2>
                <textarea name="out2" class="form-control" var="status "rows="5"><c:forEach var="campaignList" items="${campaignList}" varStatus="loop">${campaignList.campaignId}&#13;&#10;<c:forEach var="zone" items="${campaignList.zonesList}">${zone.customVariable1}&#13;&#10;</c:forEach></c:forEach></textarea>
                <p/>
            </div>
        </div>
        <c:forEach var="campaignList" items="${campaignList}" varStatus="loop">
        <h3>${campaignList.campaignId}</h3>
            <div class="content">
                <div class="row">
                    <div class="col-sm-6">
                        <p>All Place &nbsp; ${campaignList.summProfit}$; &nbsp; size ${campaignList.zonesList.size()}; &nbsp; Average CTR = ${campaignList.ctr}; &nbsp; Average COST = ${campaignList.averageConversion}$ </p>
                        <textarea name="out1" class="form-control" rows="5"><c:forEach var="zone" items="${campaignList.zonesList}">${zone.customVariable1}&#13;&#10;</c:forEach></textarea>
                    </div>
                    <%--<div class="col-sm-6">--%>
                        <%--<p>New Place &nbsp; size ${campaignList.zonesListNew.size()}</p>--%>
                        <%--<textarea name="out2" class="form-control" rows="5"><c:forEach var="zone" items="${campaignList.zonesListNew}">${zone.customVariable1}&#13;&#10;</c:forEach></textarea>--%>
                    <%--</div>--%>


                </div>
            </div>
            <p/>
            <hr>
        </c:forEach>
    </div>
    <p/>
</div>


<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>
</body>
</html>