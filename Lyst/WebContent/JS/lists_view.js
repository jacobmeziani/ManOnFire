

$(document).ready(function() {


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
	
	function requestLists (delivered_in,category_in) {
		$(".spinner").removeClass("hidden");
		var xhr = $.ajax({
			url: "ListHandler",
			data: {
				"category": category_in,
				"delivered": delivered_in
			},
			type: "GET",
			dataType: "json",
		})
		.done(function(json,textStatus,xhr) {
			
			for (i = 0; i<json.lists.length;i++) {
				var item = json.lists[i];
				var picpath = item.PicPath;
				var listname = item.ListName;
				var category = item.Category;
				var currentleader = item.CurrentLeader;
				var listid = item.ListID;
				delivered_in.push(listid);
				
				buildRow(picpath,listname,category,listid,currentleader);
				var man = (xhr.getResponseHeader("isFinal"));
				//var man = headers.isFinal;
				isFinal = man;
				$(".spinner").addClass("hidden");


			}
			

		})

		return delivered_in;
		
		
	};
	if (window.jQuery) {
	    // jQuery is available.

	    // Print the jQuery version, e.g. "1.0.0":
	    console.log(window.jQuery.fn.jquery);
	} else {alert ("no jquery")}
	
	var delivered = new Array() ;
	var cat = "TV Shows";
	
	var isFinal = 0;
	
	//buildRow("picpath","best titties in south florida","titties","yaboy");
	
	delivered = requestLists(delivered,cat);
	
	$("#clicker").click(function() {
		if (isFinal == 0) {
		
		delivered = requestLists(delivered,cat);
		} else {alert("Out of Lists")}
		
		
//		$.ajax({
//			url: "ListHandler",
//			data: {
//				"category": "TV Shows",
//				"delivered": arr
//			},
//			type: "GET",
//			dataType : "json",
//		})
//		.done(function( json ) {
//			// alert (back);
//			var responseback = json;
//			for (var i = 0;i<responseback.lists.length;i++) {
//				var picpath = responseback.lists[i].PicPath;
//				var listname = responseback.lists[i].ListName;
//				alert (listname);
//			}
//			
//			
//			
//			
//			
//			
//			
//			
////			
////			$("#listname").html(json.listname);
////			$("#category").html(json.Category)
//
//		})
//		// Code to run if the request fails; the raw request and
//		// status codes are passed to the function
//		.fail(function( xhr, status, errorThrown ) {
//			alert( "Sorry, there was a problem!" );
//			console.log( "Error: " + errorThrown );
//			console.log( "Status: " + status );
//			console.dir( xhr );
//		})
//		// Code to run regardless of success or failure;
//		.always(function( xhr, status ) {
//			//alert( "The request is complete!" );
//		});

	});

})
