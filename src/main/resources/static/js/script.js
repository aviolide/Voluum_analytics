$(document).ready(function(){
var BASE_URL='/campaign_zones_clear_spring';
    // var BASE_URL='';

	$('.start').click( function(){

		$.ajax({
			url: 'http://localhost:8080/start',
			cache: false,
			type : "POST",
			dataType : 'json',
			data : $(".request-form").serialize(),
			success : function(result) {

				// result
				console.log(result);
			},
			error: function(xhr, resp, text) {
				console.log(xhr, resp, text);
			}
		})
    });

	$('.chkplace').click( function(){

		var cid = $(this).closest("tr").find("cid").text();

		$.ajax({
			url: 'http://localhost:8080/check',
			cache: false,
			type : "POST",
			dataType : 'json',
			data : $(".request-form").serialize() + "&campaign=" + cid,
			success : function(result) {

				// result
				console.log(result);

			},
			error: function(xhr, resp, text) {
				console.log(xhr, resp, text);
			}
		})
    });

	$('.allchkplace').click( function(){

		var lines = $("textarea[name='allchkplace']").val().split('\n');
		campaigns = [];
		var campaignId;
		var zones = [];
		var line = 0
		for(var i = 0;i < lines.length;i++){
			var lgh = lines[i].length;
			if (lgh > 10) {
				if (line == 1) {
					campaigns.push( {"campaignId":campaignId, "zones":zones} );
					line=0;
					zones = [];
					campaignId = lines[i];
				} else {
					campaignId = lines[i];
					line=1;
				}
			} else {
				zones.push(lines[i]);
				if (i == lines.length-1) {
					campaigns.push( {"campaignId":campaignId, "zones":zones} );
				}

			}

		}
		//console.log(campaigns);
		$.ajax({
			url: 'http://localhost:8080/allcheck',
			cache: false,
			type : "POST",
			dataType : 'json',
			data : campaigns,
			success : function(result) {

				// result
				console.log(result);

			},
			error: function(xhr, resp, text) {
				console.log(xhr, resp, text);
			}
		})
    });

	$('#addtoformulaform').submit( function(e){
		e.preventDefault();
		var inp = $("input[name='formula']").val();
		if (inp.length > 0) {
			var currformula = $(".currentformula").text();
			if (currformula.length > 0) {
				$(".currentformula").text(currformula+';'+inp);
			} else {
				$(".currentformula").text(inp);
			}
		}

		var param = $(".currentformula").text();

		$.ajax({
			url: BASE_URL+'/add_formula',
			type : "POST",
			contentType : "text/plain;charset=UTF-8",
            dataType : 'text',
			data : param,
			success : function(result) {

				// result
				console.log(result);

			},
			error: function(xhr, resp, text) {
				console.log(xhr, resp, text);
			}
		})
    });

    $('#settimeform').submit( function(e){
        e.preventDefault();
        var settimemonth = $("input[name='settimemonth']").val();
        var settimeday = $("input[name='settimeday']").val();

        var urlpar = BASE_URL+'/set_time?month='+settimemonth+'&days='+settimeday;
        $.ajax({
            url: urlpar,
            type : "GET",
            success : function(result) {
                // result
                console.log(result);

            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })
    });
    $('.btnclear2').click( function(){

        $("input[name='settimemonth']").val("");
        $("input[name='settimeday']").val("");
        $.ajax({
            url: BASE_URL+'/clear_time',
            cache: false,
            type : "GET",
            //dataType : 'json',
            //data : $(this).serialize(),
            success : function(result) {

                // result
                console.log(result);

            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })

    });

	$('.btnclear').click( function(){

		$(".currentformula").text("");
		$("input[name='formula']").val("");

		$.ajax({
			url: BASE_URL+'/clear_formula',
			cache: false,
			type : "GET",
			//dataType : 'json',
			//data : $(this).serialize(),
			success : function(result) {

				// result
				console.log(result);

			},
			error: function(xhr, resp, text) {
				console.log(xhr, resp, text);
			}
		})
    });


    $('.btncheckall').click( function(){

        var currformula = $(".currentformula").text();
        console.log(currformula)
        if (!currformula.length > 0) {
            alert("Formula is empty!");
            return false;
        }

    });
    $('.btnchecktoday').click( function(){

        var currformula = $(".currentformula").text();
        console.log(currformula)
        if (!currformula.length > 0) {
            alert("Formula is empty!");
            return false;
        }

    });
    $('.btncheckactive').click( function(){

        var currformula = $(".currentformula").text();
        console.log(currformula)
        if (!currformula.length > 0) {
            alert("Formula is empty!");
            return false;
        }

    });

    $('.btnapply2').click( function(){

        var offeros = ($("input[name='offeros']").prop('checked') ? true : false);
        var offerbrowser = ($("input[name='offerbrowser']").prop('checked') ? true : false);
        var offerosbrowser = ($("input[name='offerosbrowser']").prop('checked') ? true : false);
        if ((offeros != true) && (offerbrowser != true) && (offerosbrowser != true)) {
            alert("Please choose filter variant");
        }
        $.ajax({
            url: BASE_URL+'/analyze/analyzator/set_group?offeros='+offeros+'&offerbrowser='+offerbrowser+'&offerosbrowser='+offerosbrowser,
            cache: false,
            type : "GET",
            success : function(result) {
                console.log(result);
            },
            error: function(xhr, resp, text) {
                console.log(xhr, resp, text);
            }
        })

    });

});