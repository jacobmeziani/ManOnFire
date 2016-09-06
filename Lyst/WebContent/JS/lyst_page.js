

//function buildItem (picppath, itemname, itemid, scores) {
//		//check that scores and attributes are same length
//		
//		if (scores.length != attributes.length) {
//			return false;
//		}
//		
//		var topLevel = $('<div class = "itemInList" id = "item'+itemid+'">');
//		var nameBox = $('<div class ="nameBox">');
//		nameBox.html(itemname);
//		topLevel.append(nameBox);
//		for (var i = 0; i < scores.length; i++) {
//			att = attributes[i];
//			score = scores[i];
//			
//			var itemBox = $('<div class = "itemBox">');
//			var attributeName = $('<div class = "attributeName">');
//			attributeName.html(att);
//			var pointCircle = $('<div class = "pointCircle">');
//			pointCircle.html(score);
//			itemBox.append(attributeName);
//			itemBox.append(pointCircle);
//			topLevel.append(itemBox);
//		}
//		$('#itemList').append(topLevel);
////		
////		var picDiv = $("<div class = picDiv>");
////		var pic = $("<img src = "+picpath+">");
////		picDiv.append(pic);
//	};
//
//function buildSortBar() {
//	if (!topbarBuilt) {
//		topbarBuilt = true;
//		numOfIcons = attributes.length;
//		if (attributes[0] != "Overall") {
//			console.log("Overall is not first attribute");
//			return false;
//		};
//		
//		for (var i = 0; i < numOfIcons; i++) {
//			var itemDiv = $("<div class = itemBox>");
//			var iconHTML = "<span id = \"sort"+i+"\" class=\"glyphicon glyphicon-sort-by-attributes-alt sortButton\"></span>"
//			itemDiv.html(iconHTML);
//			$('#topbar').append(itemDiv);
//		};
//		
//		$(".sortButton").click(function() {
//			var idString = $(this).attr("id");
//			var id = idString.split('sort')[1];
//			sortList(id);
//		});
//	}
//};

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
	
	//hardcoded shiii
	listID = 0;
	//----------------
	
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
	
	
	last_delivered = requestItems(listID, attributeWanted, (number_to_fetch + last_delivered), "initialLoad", last_delivered);
	
})

