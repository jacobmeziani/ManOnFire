$(document).ready(function() {
	$("#randomButton").click(function() {
		$.get( "bro", function( data ) {
			alert(data);
			});
	})
});