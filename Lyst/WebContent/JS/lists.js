

$(document).ready(function() {
	
	if ("ontouchstart" in document.documentElement) {
	    //document.documentElement.className += " touch";
		//alert("touchscreen");
		var touchscreen = true;
	} else {
	    //document.documentElement.className += " no-touch";
		//alert("non-touchscreen");
		var touchscreen = false;
	}


	
	
//	function requestLists (delivered_in,category_in) {
//		$(".spinner").removeClass("hidden");
//		var xhr = $.ajax({
//			url: "ListHandler",
//			data: {
//				"category": category_in,
//				"delivered": delivered_in
//			},
//			type: "GET",
//			dataType: "json",
//		})
//		.done(function(json,textStatus,xhr) {
//			
//			for (i = 0; i<json.lists.length;i++) {
//				var item = json.lists[i];
//				var picpath = item.PicPath;
//				var listname = item.ListName;
//				var category = item.Category;
//				var currentleader = item.CurrentLeader;
//				var listid = item.ListID;
//				delivered_in.push(listid);
//				
//				buildItem(picpath,listname,category,listid,currentleader);
//				var man = (xhr.getResponseHeader("isFinal"));
//				//var man = headers.isFinal;
//				isFinal = man;
//				$(".spinner").addClass("hidden");


			}
			

		})

		return delivered_in;
		
		
	};
	
	var delivered = new Array() ;
	var cat = "Everything";
	
	var isFinal = 0;
	
	//buildRow("picpath","best titties in south florida","titties","yaboy");
	
	delivered = requestLists(delivered,cat);
	
	$("#clicker").click(function() {
		if (isFinal == 0) {
		
		delivered = requestLists(delivered,cat);
		} else {alert("Out of Lists")}
	});
	
//	$("img").hover(
//			function() {
//				alert("hovering over");
//				$(this).find(".gray-desktop").css("display","block");
//			}, function(){
//				alert("hovering off");
//				$(this).find(".gray-desktop").css("display","none");
//			});
	$(".item-desktop").click(function() {
		alert($(this).html());
	});
});
	function buildRow(picpath,listname,category,listid,currentleader) {
		var topitem = $("<div id = list"+listid+">");
		topitem.addClass("item-desktop");
		var row = $("<div>");
		row.addClass("row");
		var first_column = $("<div>");
		first_column.addClass("col-md-3");
		first_column.text("picpath");
		row.append(first_column); //append
		var second_column = $("<div>");
		second_column.addClass("col-md-6");
		var showlist = $("<div>");
		showlist.addClass("list-name-description");
		showlist.text(listname);
		second_column.append(showlist); //append
		var showleader = $("<div>");
		showleader.addClass("current-leader-description");
		showleader.text("Current leader: " + currentleader);
		second_column.append(showleader); //append
		row.append(second_column); //append
		var third_column = $("<div>");
		third_column.addClass("col-md-3 category-name-description");
		third_column.text("Category: " + category);
		row.append(third_column); //append
		topitem.append(row);
		$("#main-body").append(topitem);
	};
	
	function buildItem (picpath,listname,category,listid,currentleader) {
		var topitem = $("<div class = col-md-6>");
		var item = $("<div id = list"+listid+">");
		item.addClass("item-desktop");
		var gray = $("<div class = gray-desktop>");
		gray.text(listname);
		item.append(gray);
		var pic = $("<img src = "+picpath+">");
		pic.addClass("img-responsive");
		item.append(pic);
		
		topitem.append(item);
		
		
		
		$("#main-body").append(topitem);
	
	};



