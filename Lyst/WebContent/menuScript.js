$(document).ready(function() {
	
	$("#rateMenuButton").click(function() {
		$.get("bro", {
			"action" : "navigatingBack"
		}, function(html) {
			window.location.href = 'home.jsp';
		});
	})
})
