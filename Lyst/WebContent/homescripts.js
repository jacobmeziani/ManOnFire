$(document).ready(function() {
	$("#randomButton").click(function(){
//		$.get( "bro", function( data ) {
//			var newDoc = document.open("text/html", "replace");
//			newDoc.write(data);
//			newDoc.close();
//			});
		$( "#test" ).load( "/bro #projects li" );
	})
});