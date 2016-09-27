$(document).ready(function() {
	
	var delivered = new Array() ;
	var cat = "Everything";
	var working = false;
	var isFinal = 0;
	
	delivered = requestLists(delivered,cat);
	
	var window_width = $(window).width();
	//////set mobile size
	var mobile_size = 768;
	//-----------------------------
	if (window_width<mobile_size) {
		$("#top-nav").addClass("mobile");
		
		$("#top-nav").removeClass("showmethemoney");
		$("#top-nav").addClass("hidden");
		$("#top-nav").find("ul").removeClass("showmethemoney");
		$("#top-nav").find("ul").addClass("hidden");

		$("#clicker").click(function()  {
			$("#top-nav").removeClass("hidden");
			$("#top-nav").addClass("showmethemoney");
		});
		$(".menu-item").click(function(event) {
			event.stopPropagation();
			if ($(this).hasClass("final")) {
				//do something to set the category
				//close everything hurr
				$("#top-nav").find("ul").removeClass("showmethemoney");
				$("#top-nav").find("ul").addClass("hidden");
				$("#top-nav").removeClass("showmethemoney");
				$("#top-nav").addClass("hidden");
				
				if ($(this).hasClass("all_class")) { 
					var stringest = $(this).find("a").html();
					var newstringest = stringest.split("All ");
					cat = newstringest[1];
					$("#showCategory").html(cat);
				} else if ($(this).hasClass("list-item")) {
					$("#showCategory").html($(this).find("a").html());
					if ($(this).hasClass("list-item")) {
						var dbid = event.target.getAttribute("database-id");
						cat = dbid;
					}
				}
				else{
					cat = $(this).find("a").html();
					$("#showCategory").html(cat);			
				}
				$("#main-body").empty();
				$(".spinner").removeClass("hidden");
				delivered = [];
				delivered = requestLists(delivered,cat);
			} else if ($(this).hasClass("children")) {
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
	$("#top-nav").addClass("center-block");
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
	$("#clicker").click(function() {
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
	$(".menu-item").click(function (event) {
		event.stopPropagation();
		if ($(this).hasClass("final")) {
			//do something to set the code
			//do something more
			if ($(this).hasClass("all_class")) { 
				var stringest = $(this).find("a").html();
				var newstringest = stringest.split("All ");
				cat = newstringest[1];
				$("#showCategory").html(cat);
			} else if ($(this).hasClass("list-item")) {
				$("#showCategory").html($(this).find("a").html());
				if ($(this).hasClass("list-item")) {
					var dbid = event.target.getAttribute("database-id");
					cat = dbid;
				}
			}
			else{
				cat = $(this).find("a").html();
				$("#showCategory").html(cat);			
			}

			
			$("#top-nav").find("ul").removeClass("showmethemoney");
			$("#top-nav").find("ul").addClass("hidden");
			$("#top-nav").removeClass("showmethemoney");
			$("#top-nav").addClass("hidden");	
			$("#top-nav").find("a").removeClass("selected");
			$("#main-body").empty();
			$(".spinner").removeClass("hidden");
			delivered = [];
			delivered = requestLists(delivered,cat);
		}
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
			url: "bro",
			data: {
				"action" : "viewLists",
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
	
	$(window).scroll(function() {
	    if($(window).scrollTop()
				+ $(window).height() > $(
						document).height()-50) {
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
		//topitem.addClass("col-lg-4");
		var item = $("<div id = list"+listid+">");
		item.addClass("item-desktop");
		var split = listname.split(" ");
		var listUrl = split[0];
		if(split.length>1){
		for(j=1; j<split.length; j++){
			listUrl+= "_"+split[j];
		}
		}
		var picLink = $("<a href ="+listUrl+">"); 
		var pic = $("<img src = "+"https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/"+picpath+">");
		pic.addClass("img-responsive");
		pic.addClass("img-rounded");
		pic.addClass("imager");
		pic.addClass("center-block");
		picLink.append(pic);
		item.append(picLink);
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



