$(document).ready(function() {
	
	$('.table > tbody > tr').click(function() {
		alert( "damn daniel" );
	});
	
	$.get( "bro", function( data ) {
		var newDoc = document.open("text/html", "replace");
		newDoc.write(data);
		newDoc.close();
		});
	
	$("#randomButton a").click(function(){
		alert("dus dit it");
	});
});
