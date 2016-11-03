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
//				$.each(data, function(subSearchResult, data.subSearchResultList) {
//					$('#subresultDiv').append();
//				});
				$('#resText').html(html);
			}
		});
	});

	/*
	 * <div id="yui_3_10_0_1_1478155195774_18" class="sa lowlight">
	 * 		<div class="sa-tray" type="normal" id="yui_3_10_0_1_1478155195774_441">
	 * 			<ul class="sa-tray-list-container" id="yui_3_10_0_1_1478155195774_440">
	 * 				<li pos="1" data="ssafcon%20sampler" id="yui_3_10_0_1_1478155195774_439" class=""><b class="yui3-highlight" id="yui_3_10_0_1_1478155195774_446">ssafc</b>on sampler</li>
	 * 				<li pos="2" data="ssfc" id="yui_3_10_0_1_1478155195774_442" class="">ssfc</li><li pos="3" data="ssafcon%20samplers" id="yui_3_10_0_1_1478155195774_443" class=""><b class="yui3-highlight" id="yui_3_10_0_1_1478155195774_448">ssafc</b>on samplers</li>
	 * 				<li pos="4" data="ssfcu" id="yui_3_10_0_1_1478155195774_444" class="">ssfcu</li>
	 * 			</ul>
	 * 		</div>
	 * </div>
	 * /
});