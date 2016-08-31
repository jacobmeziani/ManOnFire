$(document).ready(function() {
	
	if ("ontouchstart" in document.documentElement) {
		var touchscreen = true;
	} else {
		var touchscreen = false;
	}
	
	global_widths = undefined; //specific pixel breakpoints for each column
	width_of_name_field = 200;
	width_of_attribute = 150;
	width_of_circle = 60;
	
	//must set margin of the circle element to fit inside custom width of the parent 
	
	//*sigh*
	
	marginOffset = (150 - 60) / 2; 
	marginOffset = marginOffset + 'px';
	
	width_of_name_field = width_of_name_field + 'px';
	width_of_attribute = width_of_attribute + 'px';
	
	
	attributes = ["Overallllll",
	              "Speed",
	              "Cunning",
	              "Sweg"]
	//hard coded for now. will be dynamic obvi
	
	
	
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
		$('#items').append(topLevel);
//		
//		var picDiv = $("<div class = picDiv>");
//		var pic = $("<img src = "+picpath+">");
//		picDiv.append(pic);
	}
	
	
	function buildSortBar() {
		numOfIcons = attributes.length;
		if (attributes[0] != "Overall") {
			console.log("Overall is not first attribute");
			return false;
		}
		
		for (var i = 0; i < numOfIcons; i++) {
			var itemDiv = $("<div class = itemBox>");
			var iconHTML = "<span id = \"sort"+i+"\" class=\"glyphicon glyphicon-sort-by-attributes-alt sortButton\"></span>"
			itemDiv.html(iconHTML);
			$('#topbar').append(itemDiv);
		}
		$(".sortButton").click(function() {
			var idString = $(this).attr("id");
			var id = idString.split('sort')[1];
			sortList(id);
		});
	};
	
	function requestItems(listID, attributeNumber, nextRankingNeeded,action, lastSent) {
		
		var xhr = $.ajax({
			url:"MoneyServlet",
			data: {
				"action": action,
				"listID" : listID,
				"Attribute":attributeNumber,
				"NextRanking":nextRankingNeeded,
				"lastReceived":lastSent
			},
			type: "GET",
			dataType: "json",
		})
		.done(function(json, textStatus, xhr) {
			//handle shit
			if (action == "initialLoad") {
				attributes = json.Attributes;
				attributes.prepend("Overall");
				buildSortBar();
			}
			
			listitems = json.Items;
			for (i = 0; i < listitems.length; i++) {
				
			}
			
			
		})
		
		
		return;
	};
	
	
	//------------start building website here bitches---------------
	
	 
	
	
	buildSortBar();
	buildItem('tru', 'DWADE', 4, [1,2,3,4]);
	buildItem('tru', 'DWADE', 4, [1,2,3,4]);
	buildItem('tru', 'DWADE', 4, [1,2,3,4]);
	buildItem('tru', "LEBRONIMUS",87,[100,3,4,5]);
	

	
	$('.pointCircle').css("margin-left", marginOffset);
	$(".itemBox").css("width", width_of_attribute);
	$(".nameBox").css("width",width_of_name_field);
	
})

