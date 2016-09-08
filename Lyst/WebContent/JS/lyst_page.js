 function createCookie(name, value, days) {
            if (days) {
                var date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                var expires = "; expires=" + date.toGMTString();
            }
            else var expires = "";               

            document.cookie = name + "=" + value + expires + "; path=/";
        }

function requestItems(listID, attributeNumber, nextRankingNeeded, action, lastSent) {
	
//	$.get("bro", {
//		"action" : action,
//		"ListID" : listID,
//		"Attribute":attributeNumber,
//		"NextRanking":nextRankingNeeded,
//		"LastRankDelivered":lastSent
//	}, function(html) {
//		var parsed = $('<div/>').append(html);
//		$("#thatList").append(parsed.find("#thatList"));
//	});
//	
//	
	working =true;
	var xhr = $.ajax({
		url:"bro",
		data: {
			"action": action,
			"ListID" : listID,
			"Attribute":attributeNumber,
			"NextRanking":nextRankingNeeded,
			"LastRankDelivered":lastSent
		},
		type: "GET",
		dataType: "html",
	})	
	.done(function(html, textStatus, xhr) {	
		var parsed = $('<div/>').append(html);
		$("#thatList").append(parsed.find("#thatList"));
		isFinal = (xhr.getResponseHeader("isFinal"));
		$(".spinner").addClass("hidden");
		$(".attributeBox").click(function() {
			if (!working) {
			var attributeId = $(this).attr('id');
			var split = attributeId.split("attributeNumber");
			attributeWanted = split[1];
			$("#thatList").empty();
			$(".spinner").removeClass("hidden");
			isFinal = 0;
			last_delivered = 0;
	    		$(".spinner").removeClass("hidden");
		    	working = true;
		    	last_delivered = requestItems(listID, attributeWanted, (number_to_fetch + last_delivered), "initialLoad", last_delivered);
	    	}			
		});
		$(window).scroll(function() {
		    if($(window).scrollTop() + $(window).height() == $(document).height()) {	    	
		    	if (isFinal==0) {
			    	if (!working) {
			    		$(".spinner").removeClass("hidden");
				    	working = true;
				    	last_delivered = requestItems(listID, attributeWanted, (number_to_fetch + last_delivered), "load", last_delivered);
			    	}
		    	} else {
		    		console.log("out of items");
		    	}
		    }
		});
		working = false;
	});
	
	
	return nextRankingNeeded;
};

function sortList(attributeID) {
	if (attributeID == attributeWanted) {
		alert("Already sorting by this attribute");
		return;
	}
	$(".spinner").removeClass("hidden");
	$("#itemList").empty();
	
	attributeWanted = attributeID;
	isFinal = 0;
	last_delivered = 0;
	
	last_delivered = requestItems(listID, attributeWanted, (number_to_fetch + last_delivered), "initialLoad", last_delivered);
	
};
$(document).ready(function() {
	
	var listID = $("#listId").val();
	var listName = $("#listTitle").text();
	
	$(".spinner").removeClass("hidden");
	
	if ("ontouchstart" in document.documentElement) {
		var touchscreen = true;
	} else {
		var touchscreen = false;
	}
	
	//global variables in page
	attributes = undefined;
	attributeWanted = 0;
	isFinal = 0;
	working = false;
	last_delivered = 0;
	
	number_to_fetch = 10;
	// END global variables
	
	$("#newShowdownButton").click(function(){
		createCookie("listId", listID, 2);
		createCookie("currentCategory", listID, 2);
		createCookie("categoryName", listName, 2);
		createCookie("isList", "true", 2);
		window.location.href = "/";
	});
	
	last_delivered = requestItems(listID, attributeWanted, (number_to_fetch + last_delivered), "initialLoad", last_delivered);
	
})

