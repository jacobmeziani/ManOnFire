$(document).ready(function() {
	$("#myCardRight").toggleClass("flip");
	$("#myCard").toggleClass("flip");
	$("#randomButton").click(function() {
		$("#myCard").toggleClass("flip");
		$("#myCardRight").toggleClass("flip");
		$.get( "bro", function( html ) {
			var parsed = $('<div/>').append(html);
			
			$("#leftPic").html(parsed.find("#leftPic"));
			$("#leftName").html(parsed.find("#leftName"));
			$("#list").html(parsed.find("#list"));
			$("#rightPic").html(parsed.find("#rightPic"));
			$("#rightName").html(parsed.find("#rightName"));
			$("#rightName2").html(parsed.find("#rightName2"));
			$("#leftName2").html(parsed.find("#leftName2"));
			$("#myCard").toggleClass("flip");
			$("#myCardRight").toggleClass("flip");
			});
	})
});