$(document).ready(function() {
	
	$.get( "bro", "initial=true", function( data ) {
		var newDoc = document.open("text/html", "replace");
		newDoc.write(data);
		newDoc.close();
		});
})
