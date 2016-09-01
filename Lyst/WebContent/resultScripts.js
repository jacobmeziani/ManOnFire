$(document).ready(function() {

	$("#newShowdownButton").click(function() {
		$(this).attr("disabled", "disabled");
		$.get("bro", {
			"action" : "navigatingBack"
		}, function(html) {
			window.location.href = 'home.jsp';
		});
	})

});