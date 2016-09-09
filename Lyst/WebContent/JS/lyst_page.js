 function createCookie(name, value, days) {
            if (days) {
                var date = new Date();
                date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
                var expires = "; expires=" + date.toGMTString();
            }
            else var expires = "";               

            document.cookie = name + "=" + value + expires + "; path=/";
        }

function requestItems(listID, attributeNumber, completeList, startingIndex, endingIndex) {
	//TODO: HURR
	//
	
	//items to get will just be a string of 10 or whatever many item ids
	
	while (true) {
		try {
			itemsToGet = completeList.slice(startingIndex, endingIndex);
			break;
		}
		catch (e) {
			endingIndex = endingIndex - 1;
			continue;
		}
	}
		
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
				//housekeeping
				$(".spinner").removeClass("hidden");
			    working = true;
			    //code
				var attributeId = $(this).attr('id');
				var split = attributeId.split("attributeNumber");
				attributeWanted = split[1];
				$("#thatList").empty();
				isFinal = 0;
				last_delivered = 0;
				sortedList = requestInitial(listID, attributeWanted);
				itemListSize = sortedList.length;
				last_delivered = requestItems(listID, attributeWanted, sortedList, 0, number_to_fetch);
				
	    	}
		});
		$(window).scroll(function() {
		    if($(window).scrollTop() + $(window).height() == $(document).height()) {	    	
		    	if (isFinal==0) {
			    	if (!working) {
			    		$(".spinner").removeClass("hidden");
				    	working = true;
				    	last_delivered = requestItems(listID, attributeWanted, sortedList, lastDelivered, (lastDelivered + number_to_fetch));
			    	}
		    	} else {
		    		console.log("out of items");
		    	}
		    }
		});
		//set isFinal manually just in case as well
		if ((last_delivered + endingIndex) >= itemListSize) {
			isFinal = 1;
		}

		working = false;
			
		return (last_delivered + endingIndex);

	});
	
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
		var returnList = json.sortedItemIDs;
		working = false;
	});
	
	return returnList;
}

$(document).ready(function() {
	
	//list ID needs to be set before the first thing is returned. possibly grabbed from URL
	
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
	
	sortedList = requestInitial(listID, attributeWanted);
	itemListSize = sortedList.length;
	last_delivered = requestItems(listID, attributeWanted, sortedList.slice(0,number_to_fetch));
	
	
})

