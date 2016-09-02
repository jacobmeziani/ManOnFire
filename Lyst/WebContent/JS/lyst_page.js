

function buildItem (picppath, itemname, itemid, scores) {
		//check that scores and attributes are same length
		
		if (scores.length != attributes.length) {
			return false;
		}
		
		var topLevel = $('<div class = "itemInList" id = "item'+itemid+'">');
		var nameBox = $('<div class ="nameBox">');
		nameBox.html(itemname);
		topLevel.append(nameBox);
		for (var i = 0; i < scores.length; i++) {
			att = attributes[i];
			score = scores[i];
			
			var itemBox = $('<div class = "itemBox">');
			var attributeName = $('<div class = "attributeName">');
			attributeName.html(att);
			var pointCircle = $('<div class = "pointCircle">');
			pointCircle.html(score);
			itemBox.append(attributeName);
			itemBox.append(pointCircle);
			topLevel.append(itemBox);
		}
		$('#itemList').append(topLevel);
//		
//		var picDiv = $("<div class = picDiv>");
//		var pic = $("<img src = "+picpath+">");
//		picDiv.append(pic);
	};

function buildSortBar() {
	if (!topbarBuilt) {
		topbarBuilt = true;
		numOfIcons = attributes.length;
		if (attributes[0] != "Overall") {
			console.log("Overall is not first attribute");
			return false;
		};
		
		for (var i = 0; i < numOfIcons; i++) {
			var itemDiv = $("<div class = itemBox>");
			var iconHTML = "<span id = \"sort"+i+"\" class=\"glyphicon glyphicon-sort-by-attributes-alt sortButton\"></span>"
			itemDiv.html(iconHTML);
			$('#topbar').append(itemDiv);
		};
		
		$(".sortButton").click(function() {
			var idString = $(this).attr("id");
			var id = idString.split('sort')[1];
			sortList(id);
		});
	}
};

function requestItems(listID, attributeNumber, nextRankingNeeded, action, lastSent) {
	
	var xhr = $.ajax({
		url:"MoneyServlet",
		data: {
			"action": action,
			"ListID" : listID,
			"Attribute":attributeNumber,
			"NextRanking":nextRankingNeeded,
			"LastRankDelivered":lastSent
		},
		type: "GET",
		dataType: "json",
	})
	
	.done(function(json, textStatus, xhr) {
		//handle shit
		if (action == "initialLoad") {
			var ov  = ["Overall"];
			var atts = json.Attributes;
			attributes = ov.concat(atts);
			buildSortBar();
		}
		
		listitems = json.Items;
		for (i = 0; i < listitems.length; i++) {
			var item = listitems[i];
			var picpath = item.PicPath;
			var itemname = item.ItemName;
			var itemID = item.ItemID;
			var ratings = item.Ratings;
	
			buildItem(picpath, itemname, itemID, ratings);
		}
		
		
		
		$('.pointCircle').css("margin-left", marginOffset);
		$(".itemBox").css("width", width_of_attribute);
		$(".nameBox").css("width",width_of_name_field);
		isFinal = (xhr.getResponseHeader("isFinal"));
		working = false;
		$(".spinner").addClass("hidden");
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
	width_of_name_field = 200;
	width_of_attribute = 150;
	width_of_circle = 60;
	last_delivered = 0;
	topbarBuilt = false;
	
	number_to_fetch = 10;
	// END global variables
	
	
	//must set margin of the circle element to fit inside custom width of the parent 
	
	//*sigh*
	
	marginOffset = (150 - 60) / 2; 
	marginOffset = marginOffset + 'px';
	
	width_of_name_field = width_of_name_field + 'px';
	width_of_attribute = width_of_attribute + 'px';
	
	//------------start building website here bitches---------------
	
//	buildSortBar();
//	buildItem('tru', 'DWADE', 4, [1,2,3,4]);
//	buildItem('tru', 'DWADE', 4, [1,2,3,4]);
//	buildItem('tru', 'DWADE', 4, [1,2,3,4]);
//	buildItem('tru', "LEBRONIMUS",87,[100,3,4,5]);
	
	last_delivered = requestItems(listID, attributeWanted, (number_to_fetch + last_delivered), "initialLoad", last_delivered);
//	
//	$('.pointCircle').css("margin-left", marginOffset);
//	$(".itemBox").css("width", width_of_attribute);
//	$(".nameBox").css("width",width_of_name_field);
	
	$(window).scroll(function() {
	    if($(window).scrollTop() + $(window).height() == $(document).height()) {
	    	console.log("scrolltop: "+ $(window).scrollTop());
	    	console.log ("window height: " + $(window).height());
	    	console.log ("div height: " + $("#spinnerbody").offset().top);
	    	console.log("document height: "+$(document).height());
	    	
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
	
})

