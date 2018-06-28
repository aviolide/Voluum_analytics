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


	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/DataTables/DataTables-1.10.16/css/jquery.dataTables.min.css"/>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
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

<div class="container-fluid">

	<!--<div id="inputs" class="clearfix">
        <input type="file" id="files" name="files[]" multiple />
    </div>
    <output id="list"></output>-->

	<h2 id="cidid">CompaignID: <span></span></h2>
	<hr/>
	<div class="row">
		<div class="col-sm-12">
			<h5>Forecast</h5>
			<div class="btn-box">
				<input name="cost" class="form-control" placeholder="Cost" />
			</div>
			<div class="btn-box">
				<input name="visits" class="form-control" placeholder="Visits" />
			</div>
			<div class="btn-box">
				<button class="btn btn-default mr-sm-2 btnapply">Apply</button>
			</div>
		</div>
	</div>
	<hr/>

	<div class="row">
		<div class="col-sm-2">
			<b>Conversion:</b><br/>
			<input id="min1" name="min1" type="text" placeholder="min"><br/>
			<input id="max1" name="max1" type="text" placeholder="max">
		</div>
		<div class="col-sm-2">
			<b>Visits:</b><br/>
			<input id="min2" name="min2" type="text" placeholder="min"><br/>
			<input id="max2" name="max2" type="text" placeholder="max">
		</div>
		<div class="col-sm-2">
			<b>Revenue:</b><br/>
			<input id="min3" name="min3" type="text" placeholder="min"><br/>
			<input id="max3" name="max3" type="text" placeholder="max">
		</div>
		<div class="col-sm-2">
			<b>ROI:</b><br/>
			<input id="min4" name="min4" type="text" placeholder="min"><br/>
			<input id="max4" name="max4" type="text" placeholder="max">
		</div>
		<div class="col-sm-2">
			<b>Cost:</b><br/>
			<input id="min5" name="min5" type="text" placeholder="min"><br/>
			<input id="max5" name="max5" type="text" placeholder="max">
		</div>
		<div class="col-sm-2">
			<b>Profit:</b><br/>
			<input id="min6" name="min6" type="text" placeholder="min"><br/>
			<input id="max6" name="max6" type="text" placeholder="max">
		</div>
	</div>

	<table id='table-container' class="display" width="100%"></table>
	<div class="btn-box">
		<button class="btn btn-default btnexportcsv">Export To CSV</button>
	</div>
</div>

<script src="${pageContext.request.contextPath}/resources/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/popper.min.js"></script>

<script src="${pageContext.request.contextPath}/resources/bootstrap/js/bootstrap.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/jquery.csv.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/DataTables/datatables.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/csv_to_html_table.js"></script>

<script src="${pageContext.request.contextPath}/resources/js/script.js"></script>

<script type="text/javascript">
    // var BASE_URL = '/campaign_zones_clear_spring';
    var BASE_URL = '';
	var table;
    var dataSet = JSON.parse(${json});
    console.log(dataSet);

    $(document).ready(function() {

        $('.btnapply').click( function(){

            var catid = $("#cidid span").text();
            var cost = $("input[name='cost']").val();
            var visits = $("input[name='visits']").val();
            var campId = window.location.pathname;
            var campIndex = campId.substr(campId.lastIndexOf("/") + 1);
            console.log(campIndex);
            $.ajax({
                url: BASE_URL+"/analyze/analyzator/calculate?forecastcost="+cost+"&forecastvisits="+visits+"&campaign_id="+campIndex,
                cache: false,
                type : "GET",
                success : function(result) {
                    tableRender(result);
                    console.log(result);
                },
                error: function(xhr, resp, text) {
                    console.log(xhr, resp, text);
                }
            })
        });

        function tableRender(data) {
            var campaignId = '';
            var offersList = '';
            var outData = [];
            $.each(data,function(index, value){
                if (index == "campaignId") {
                    campaignId = value;
                    $("#cidid span").text(campaignId);
                };
                if (index == "offersList") {
                    offersList = value;
                    $.each(offersList,function(index2, value2){
                        delete value2["offerId"];
                        //console.log('index2 ' + index2 + ', value2: ' + value2);
                        //value2.campaignId = campaignId;
                        var partarray = Object.values(value2);
                        //partarray.unshift(campaignId);
                        outData.push(partarray);
                        //console.log(partarray);
                    });
                    //console.log(outData);
                };
                //console.log('index ' + index + ', value: ' + value);
            });
            printTable(outData);
            printTable(outData);
            $("#table-container tr td:nth-child(4)").each(function(){

                var txt = $(this).text();
                $(this).html("<div>"+txt+"</div>");
            });
            $("#table-container tr td div").click(function() {
                if ($(this).hasClass("sh")) {
                    $(this).removeClass("sh");
                } else {
                    $(this).addClass("sh");
                }
            })
        }

        tableRender(dataSet);

        $('#min1, #max1, #min2, #max2, #min3, #max3, #min4, #max4, #min5, #max5, #min6, #max6').keyup( function() {
            //filterConv();
            table.draw();
        } );

        $("#table-container tr td:nth-child(4)").each(function(){
            var txt = $(this).text();
            $(this).html("<div>"+txt+"</div>");
        });
        $("#table-container tr td div").click(function() {
            if ($(this).hasClass("sh")) {
                $(this).removeClass("sh");
            } else {
                $(this).addClass("sh");
            }

        })

    });


    function printTable(indata) {
        if (table) {
            table.destroy();
        }
        table = $('#table-container').DataTable({
            "data": indata,
            "columns": [
                //{ title: "campaignId" },
                { title: "group1" },
                { title: "group2" },
                { title: "group3" },
                { title: "offerName" },
                { title: "os" },
                { title: "browser" },
                { title: "visits" },
                { title: "clicks" },
                { title: "conversion" },
                { title: "revenue" },
                { title: "roi" },
                { title: "cost" },
                { title: "profit" },
                { title: "revenueDay" },
                { title: "cost_day" },
                { title: "profit_day" },
                { title: "visits_day" },
                { title: "profit_hour" },
                { title: "cpv" },
                { title: "ctr" },
                { title: "cr" },
                { title: "cv" },
                { title: "epv" },
                { title: "epc" }
            ],
            "paging": false,
            //"scrollX": true,
            //colReorder: true,
            "fixedColumns": true,
            "fixedHeader": true,
            //rowReorder: true,
            "select": true,
            "rowCallback": function( row, data ) {
                if ( data[10] < 0 ) { //roi
                    $('td:eq(10)', row).html( '<span class="red">'+data[10]+'</span>' );
                } else {$('td:eq(10)', row).html( '<span class="green">'+data[10]+'</span>' );}
                if ( data[12] < 0 ) { //profit
                    $('td:eq(12)', row).html( '<span class="red">'+data[12]+'</span>' );
                } else {$('td:eq(12)', row).html( '<span class="green">'+data[12]+'</span>' );}
            }

        });

    };

    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            var min = parseFloat( $('#min1').val(), 10 );
            var max = parseFloat( $('#max1').val(), 10 );

            var col = parseFloat( data[8] ) || 0; // use data for the conv column

            if ( ( isNaN( min ) && isNaN( max ) ) ||
                ( isNaN( min ) && col <= max ) ||
                ( min <= col   && isNaN( max ) ) ||
                ( min <= col   && col <= max ) )
            {
                return true;
            }

            return false;
        }
    );
    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            var min = parseFloat( $('#min2').val(), 10 );
            var max = parseFloat( $('#max2').val(), 10 );

            var col = parseFloat( data[6] ) || 0;

            if ( ( isNaN( min ) && isNaN( max ) ) ||
                ( isNaN( min ) && col <= max ) ||
                ( min <= col   && isNaN( max ) ) ||
                ( min <= col   && col <= max ) )
            {
                return true;
            }

            return false;
        }
    );
    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            var min = parseFloat( $('#min3').val(), 10 );
            var max = parseFloat( $('#max3').val(), 10 );
            var col = parseFloat( data[9] ) || 0;
            if ( ( isNaN( min ) && isNaN( max ) ) ||
                ( isNaN( min ) && col <= max ) ||
                ( min <= col   && isNaN( max ) ) ||
                ( min <= col   && col <= max ) )
            {return true;}
            return false;
        }
    );
    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            var min = parseFloat( $('#min4').val(), 10 );
            var max = parseFloat( $('#max4').val(), 10 );
            var col = parseFloat( data[10] ) || 0;
            if ( ( isNaN( min ) && isNaN( max ) ) ||
                ( isNaN( min ) && col <= max ) ||
                ( min <= col   && isNaN( max ) ) ||
                ( min <= col   && col <= max ) )
            {return true;}
            return false;
        }
    );
    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            var min = parseFloat( $('#min5').val(), 10 );
            var max = parseFloat( $('#max5').val(), 10 );
            var col = parseFloat( data[11] ) || 0;
            if ( ( isNaN( min ) && isNaN( max ) ) ||
                ( isNaN( min ) && col <= max ) ||
                ( min <= col   && isNaN( max ) ) ||
                ( min <= col   && col <= max ) )
            {return true;}
            return false;
        }
    );
    $.fn.dataTable.ext.search.push(
        function( settings, data, dataIndex ) {
            var min = parseFloat( $('#min6').val(), 10 );
            var max = parseFloat( $('#max6').val(), 10 );
            var col = parseFloat( data[12] ) || 0;
            if ( ( isNaN( min ) && isNaN( max ) ) ||
                ( isNaN( min ) && col <= max ) ||
                ( min <= col   && isNaN( max ) ) ||
                ( min <= col   && col <= max ) )
            {return true;}
            return false;
        }
    );



</script>

</body>
</html>