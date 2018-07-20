
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
            <div class="col-sm-6">

                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-inline" id="addtoformulaform">
                            <input type="text" class="form-control mr-sm-2" name="formula" placeholder="formula">

                            <button type="submit" class="btn btn-primary mr-sm-2">Add to formula</button>
                            <button class="btn btn-default btnclear">Clear</button>
                        </form>
                    </div>
                </div>
                </br>
                <div class="row">
                    <div class="col-sm-12">
                        <form class="form-inline" id="settimeform">
                            <input type="text" class="form-control mr-sm-2" name="settimemonth" placeholder="month" style="max-width: 110px;">
                            <input type="text" class="form-control mr-sm-2" name="settimeday" placeholder="day" style="max-width: 110px;">
                            <div class="btn-box">
                                <button type="submit" class="btn btn-primary mr-sm-2">Set time</button>
                            </div>
                            <div class="btn-box">
                                <button class="btn btn-default btnclear2">Clear</button>
                            </div>
                        </form>
                    </div>
                </div>
                </br>
                <div class="row">
                    <div class="col-sm-12">
                        <form:form method="get" action="check_all" target="_blank" cssClass="btn-box">
                            <input type="submit" value="Check All"  class="btn btn-default mr-sm-2 btncheckall"/>
                        </form:form>
                        <form:form method="get" action="check_today" target="_blank" cssClass="btn-box">
                            <input type="submit" value="Check Today" class="btn btn-default mr-sm-2 btnchecktoday"/>
                        </form:form>
                        <form:form method="get" action="check_active" target="_blank" cssClass="btn-box">
                            <input type="submit" value="Check Active" class="btn btn-default btncheckactive"/>
                        </form:form>
                        <form:form method="get" action="check_by_time" target="_blank" cssClass="btn-box">
                            <input type="submit" value="Check By Time" class="btn btn-default btncheckactive"/>
                        </form:form>
                    </div>
                </div>
                </br>
                <div class="row">
                    <div class="col-sm-12">
                        <form:form method="get" action="analyze/analyzator" target="_blank" cssClass="btn-box">
                            <button class="btn btn-success btnanalyzeall">Go to Analyze</button>
                        </form:form>
                    </div>
                </div>
                </br>
                <div class="row">
                    <div class="col-sm-12">
                        <h3>Your formula - </h3>
                        <%--<textarea class="form-control currentformula" name="out1" class="form-control" rows="1"></textarea>--%>
                        <div class="form-control currentformula" id="currentformula"></div>
                    </div>
                </div>
            </div>
            <form:form method="post" action="check_field" cssClass="col-sm-6" target="_blank">
                <textarea name="allchkplace" class="form-control" rows="7" placeholder="ids"></textarea>
                <input type="submit" value="Compare Campaigns" class="btn allchkplace"/>
            </form:form>
        </div>
        </br>

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
                                <div class="btn chkplace">
                                    <a href="<c:url value='/campaign_result/${status.index}'/>" target="_blank">Check</a>
                                    <%--<form:button path="/getcampaign/${status.index}" value="${campaign}">Check</form:button>--%>
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