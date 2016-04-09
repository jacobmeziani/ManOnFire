$(document).ready(function() {

	var menu_ul = $("#top-nav");
	var menu_offset = menu_ul.offsetHeight;
	var ul_elements = $("ul","#top-nav");
	console.log('ya boy');		
	for (i = 0;i<ul_elements.length;i++) {
		console.log(ul_elements[i]);
		var parentdude= ul_elements[i].parentNode;
		ul_elements[i].style.left=parentdude.offsetWidth+"px";
		ul_elements[i].style.top=parentdude.offsetTop+"px";
	};
	

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
		}
	});

	$(".menu-item").click(function (event) {
		event.stopPropagation();
		if ($(this).hasClass("final")) {
			//do something to set the code
			//do something more
			$("#showCategory").html($(this).find("a").html());
			
			$("#top-nav").find("ul").removeClass("showmethemoney");
			$("#top-nav").find("ul").addClass("hidden");
			$("#top-nav").removeClass("showmethemoney");
			$("#top-nav").addClass("hidden");				
		};
		if ($(this).hasClass("children")) {
			$(this).parent().find("ul").removeClass("showmethemoney");
			$(this).parent().find("ul").addClass("hidden");
			$(this).children("ul").removeClass("hidden");
			$(this).children("ul").addClass("showmethemoney");
		};		
	});	
});

