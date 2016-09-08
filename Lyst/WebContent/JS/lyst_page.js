 function createCookie(name, value, days) {
            if (days) {
                var date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                var expires = "; expires=" + date.toGMTString();
            }
            else var expires = "";               

            document.cookie = name + "=" + value + expires + "; path=/";
        }

function requestItems(listID, attributeNumber, itemsToGet) {
	//TODO: HURR
	//
	
	//items to get will just be a string of 10 or whatever many item ids
	
	
	working =true;
	
	var xhr = $.ajax({
		url:"bro",
		data: {
			"action": "load",
			"ListID" : listID,
			"Attribute":attributeNumber,
			"ItemsToGet":itemsToGet
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


function requestInitial(listID, attributeNumber) {
	//initial data load that brings up the sorted list of the item IDs neeeded
	var xhr = $.ajax({
		url:"bro",
		data: {
			"action": "initialLoad",
			"ListID" : listID,
			"Attribute":attributeNumber
		},
		type: "GET",
		dataType: "application/json",
	})
	.done(function(json, textStatus, xhr) {
		var returnedList = json.sortedItemeIDs;
		working = false;
	});
	
	return returnedList;
}


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
	
	//list ID needs to be set before the first thing is returned. possibly grabbed from URL
	listID = 0; //hardcoded for now
	
	//var listID = $("#listId").val();
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
	
	sortedList = requestInitial(listID, attributeWanted);
	
	last_delivered = requestItems(listID, attributeWanted, sortedList.slice(0,number_to_fetch));
	
	
})

