$(document).ready(function() {
	
	$.get( "bro", {"action":"initial"}, function( data ) {
		var newDoc = document.open("text/html", "replace");
		newDoc.write(data);
		newDoc.close();
		});
})
