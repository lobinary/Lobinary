$(document).ready(function() {

	$("#searchText").keyup(function() {
		var sv = $("#searchText").val();
		console.info(sv);
		$.ajax({
			type : "GET",
			url : "index/search",
			data : {
				s : sv
			},
			dataType : "json",
			success : function(data) {
				console.info(data);
				$('#subresultDiv').empty();
				var list = data.subSearchResultList;
				if(data.resultNum!=0){
					if(data.resultNum==1){
						window.location.href=list[0].valueStr;
					}
					var resultHtml = "<div class='sa-trayt' type='normal' >"+
							"<ul class='sa-tray-list-container'>";
					$.each(list, function(index, subSearchResult) {
						console.info('准备添加'+subSearchResult);
						resultHtml += "<li pos='1' data='ssafcon%20sampler' id='yui_3_10_0_1_1478155195774_439' class='itt'><b class='yui3-highlight' >"+subSearchResult.displayStr+"</b></li>";
					});
					
					resultHtml += "</ul> </div>";
					$('#subresultDiv').append(resultHtml);
				}
			}
		});
	});

});