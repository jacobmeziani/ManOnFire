

$(document).ready(function() {
	
	var delivered = new Array() ;
	var cat = "Everything";
	var working = false;
	var isFinal = 0;
	
	delivered = requestLists(delivered,cat);

	
	if ("ontouchstart" in document.documentElement) {
	    //document.documentElement.className += " touch";
		//alert("touchscreen");
		var touchscreen = true;
	} else {
	    //document.documentElement.className += " no-touch";
		//alert("non-touchscreen");
		var touchscreen = false;
	}
	
	var window_width = $(window).width();
	//////set mobile size
	var mobile_size = 700;
	
	if (window_width<mobile_size) {
		$("#top-nav").addClass("mobile");
		
		$("#top-nav").removeClass("showmethemoney");
		$("#top-nav").addClass("hidden");
		$("#top-nav").find("ul").removeClass("showmethemoney");
		$("#top-nav").find("ul").addClass("hidden");

		$("#filterbutton").click(function()  {
			$("#top-nav").removeClass("hidden");
			$("#top-nav").addClass("showmethemoney");
		});
		$(".menu-item").click(function(event) {
			event.stopPropagation();
			if ($(this).hasAnyClass("final finalcategory")) {
				//do something to set the category
				//close everything hurr
				$("#top-nav").find("ul").removeClass("showmethemoney");
				$("#top-nav").find("ul").addClass("hidden");
				$("#top-nav").removeClass("showmethemoney");
				$("#top-nav").addClass("hidden");
				
				if ($(this).hasClass("all_class")) { 
					var stringest = $(this).find("a").html();
					var newstringest = stringest.split("All ");
					var curr_cat = newstringest[1];
					$("#showCategory").html(curr_cat);
					cat = curr_cat;
		    		$(".spinner").removeClass("hidden");
					delivered.length = 0;
					$("#main-body").html("");
					delivered = requestLists(delivered,cat);
				} else {
					$("#showCategory").html($(this).find("a").html());
					cat = $(this).find("a").html();
		    		$(".spinner").removeClass("hidden");
					delivered.length = 0;
					$("#main-body").html("");
					delivered = requestLists(delivered,cat);
				}
				
			} else if ($(this).hasClass("children")) {
				//$("#top-nav").find("ul").removeClass("showmethemoney");
				//$("#top-nav").find("ul").addClass("hidden");
				//$("#top-nav").removeClass("showmethemoney");
				//$("#top-nav").addClass("hidden");
				//$(this).parent().parent().parent().removeClass("showmethemoney");
				//$(this).parent().parent().parent().addClass("hidden");
				$(this).children("ul").removeClass("hidden");
				$(this).children("ul").addClass("showmethemoney");
			}
		});
		$(".closemenu").click(function() {
			$("#top-nav").find("ul").removeClass("showmethemoney");
			$("#top-nav").find("ul").addClass("hidden");
			$("#top-nav").removeClass("showmethemoney");
			$("#top-nav").addClass("hidden");
		});
		$(".upmenu").click(function(event) {
			event.stopPropagation();
			$(this).parent().removeClass("showmethemoney");
			$(this).parent().addClass("hidden");
		});
	}
	else {
		//shows for not mobile 
	$("#top-nav").addClass("desk");
	$(".closemenu").addClass("hide");
	$(".upmenu").addClass("hide");
	var menu_ul = $("#top-nav");
	var menu_offset = menu_ul.offsetHeight;
	var ul_elements = $("ul","#top-nav");
	for (i = 0;i<ul_elements.length;i++) {
		var parentdude= ul_elements[i].parentNode;
		ul_elements[i].style.left=parentdude.offsetWidth+"px";
		ul_elements[i].style.top=parentdude.offsetTop+"px";
	};
	

	$("#top-nav").removeClass("showmethemoney");
	$("#top-nav").addClass("hidden");
	$("#top-nav").find("ul").removeClass("showmethemoney");
	$("#top-nav").find("ul").addClass("hidden");
	

	$("#top-nav").removeClass("showmethemoney");
	$("#top-nav").addClass("hidden");
	$("#top-nav").find("ul").removeClass("showmethemoney");
	$("#top-nav").find("ul").addClass("hidden");
	//clicker functions go here 
	$("#filterbutton").click(function() {
		$(".menubackground").removeClass("hidden");
		$(".menubackground").addClass("showmethemoney");
		if ($("#top-nav").hasClass("hidden")) {
			$("#top-nav").removeClass("hidden");
			$("#top-nav").addClass("showmethemoney");
		} else if ($("#top-nav").hasClass("showmethemoney")) { 
			$("#top-nav").find("ul").removeClass("showmethemoney");
			$("#top-nav").find("ul").addClass("hidden");
			$("#top-nav").removeClass("showmethemoney");
			$("#top-nav").addClass("hidden");
			$("#top-nav").find("a").removeClass("selected");
		}
	});
	
	$(".menubackground").click(function () {
		$(".menubackground").removeClass("showmethemoney");
		$(".menubackground").addClass("hidden");
		$("#top-nav").find("ul").removeClass("showmethemoney");
		$("#top-nav").find("ul").addClass("hidden");
		$("#top-nav").removeClass("showmethemoney");
		$("#top-nav").addClass("hidden");
		$("#top-nav").find("a").removeClass("selected");
	});
	
	$("#categoryselect").click(function(){
		$(".menubackground").removeClass("showmethemoney");
		$(".menubackground").addClass("hidden");
		$("#top-nav").find("ul").removeClass("showmethemoney");
		$("#top-nav").find("ul").addClass("hidden");
		$("#top-nav").removeClass("showmethemoney");
		$("#top-nav").addClass("hidden");
		$("#top-nav").find("a").removeClass("selected");
	});

	$(".menu-item").click(function (event) {

		event.stopPropagation();
		if ($(this).hasAnyClass("final finalcategory")) {
			$(".menubackground").removeClass("showmethemoney");
			$(".menubackground").addClass("hidden");
			//do something to set the code
			//do something more
			if ($(this).hasClass("all_class")) { 
				var stringest = $(this).find("a").html();
				var newstringest = stringest.split("All ");
				var curr_cat = newstringest[1];
				$("#showCategory").html(curr_cat);
				cat = curr_cat;
				delivered.length = 0;
	    		$(".spinner").removeClass("hidden");
				$("#main-body").html("");
				delivered = requestLists(delivered,cat);
			} else {
				$("#showCategory").html($(this).find("a").html());
				cat = $(this).find("a").html();
				delivered.length = 0;
	    		$(".spinner").removeClass("hidden");
				$("#main-body").html("");
				delivered = requestLists(delivered,cat);
			}
			
			$("#top-nav").find("ul").removeClass("showmethemoney");
			$("#top-nav").find("ul").addClass("hidden");
			$("#top-nav").removeClass("showmethemoney");
			$("#top-nav").addClass("hidden");	
			$("#top-nav").find("a").removeClass("selected");
		};
		if ($(this).hasClass("children")) {
			$(this).parent().find("a").removeClass("selected");
			$(this).children("a").addClass("selected");
			$(this).parent().find("ul").removeClass("showmethemoney");
			$(this).parent().find("ul").addClass("hidden");
			$(this).children("ul").removeClass("hidden");
			$(this).children("ul").addClass("showmethemoney");
		};		
	});	
	};
	//


//	$("#filterbutton").click(function() {
//		delivered.length = 0;
//		$("#main-body").html("");
//		delivered = requestLists(delivered,cat);
//	
//	});
	
	function requestLists (delivered_in,category_in) {
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
				
				buildItem(picpath,listname,category,listid,currentleader);
				var man = (xhr.getResponseHeader("isFinal"));
				//var man = headers.isFinal;
				isFinal = man;
				$(".spinner").addClass("hidden");
				
			}
			
			working = false;
		})
		return delivered_in;
		
	};
	
	//buildRow("picpath","best titties in south florida","titties","yaboy");
	
	
//	$("#categoryselect").click(function() { //loader for deh shit
	
//		if (isFinal == 0) {
//		
//		delivered = requestLists(delivered,cat);
//		} else {alert("Out of Lists")}
//	});
//	
//	$("img").hover(
//			function() {
//				alert("hovering over");
//				$(this).find(".gray-desktop").css("display","block");
//			}, function(){
//				alert("hovering off");
//				$(this).find(".gray-desktop").css("display","none");
//			});
	
	$(window).scroll(function() {
	    if($(window).scrollTop() + $(window).height() == $(document).height()) {
	    	console.log("scrolltop: "+ $(window).scrollTop());
	    	console.log ("window height: " + $(window).height());
	    	console.log ("div height: " + $("#spinnerbody").offset().top);
	    	console.log("document height: "+$(document).height());
	    	
	    	if (isFinal==0) {
	    		$(".spinner").removeClass("hidden");
//	    		if (!working) {
//	    			working = true;
//		    		delivered = requestLists(delivered,cat);
//		    		working = false;
//	    		} else {
//	    			alert("too busy");
//	    			console.log("too busy");
//	    		}
	    	if (!working) {
	    	working = true;
	    	delivered = requestLists(delivered,cat);
	    	}
	    	} else {
	    		console.log("out of lists");
	    	}
	    }
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
	
	function closeMenu() {
		$(".menubackground").removeClass("hidden");
		$(".menubackground").addClass("showmethemoney");
		delivered.length = 0;
		$(".spinner").removeClass("hidden");
		$("#main-body").html("");
		delivered = requestLists(delivered,cat);
	};
	
	
	function buildItem (picpath,listname,category,listid,currentleader) {
		var topitem = $("<div class = col-md-6>");
		var item = $("<div id = list"+listid+">");
		item.addClass("item-desktop");
		var pic = $("<img src = "+picpath+">");
		pic.addClass("img-responsive");
		item.append(pic);
		var span1 = $("<span>");
		span1.text("Current Category: "+ category);
		var span2 = $("<span>");
		span2.text("Current Leader: " + currentleader);
		var infobanner = $("<div class = show-info>");
		infobanner.append(span1);
		infobanner.append(span2);
		item.append(infobanner);
		var namebanner = $("<div class = showlist-desktop>");
		namebanner.text(listname);
		item.append(namebanner);
		item.click(function() {
			//go to list view
			//TODO
			
		});
		topitem.append(item);
		
		
		
		$("#main-body").append(topitem);
	
	};
	
	$.fn.hasAnyClass = function() {
	    for (var i = 0; i < arguments.length; i++) {
	        var classes = arguments[i].split(" ");
	        for (var j = 0; j < classes.length; j++) {
	            if (this.hasClass(classes[j])) {
	                return true;
	            }
	        }
	    }
	    return false;
	};



