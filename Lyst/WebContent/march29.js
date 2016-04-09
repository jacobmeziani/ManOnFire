$(document).ready(function () {
	$("#clickme").click(function() {
		$("#nav>li>a").slideToggle();
	});
	
	$("#nav>li>a").click(function(){
	  alert("inside click");
    if ($(this).attr('class') != 'active'){
    	alert("inside if");
      $('#nav li ul').slideUp();
      $(this).next().slideToggle();
      $('#nav li a').removeClass('active');
      $(this).addClass('active');
    }
  });
	//------division for deh shit 
	
	$("#clicker").click(function() {
		alert('oh yas')
		$("#top-nav>li").removeClass("hidden");
		$("#top-nav>li").addClass("showmethemoney");

	});
//	var ul_elements = $("#top-nav").getElementsByTagName("ul");
//	for (i=0;i<ul_elements.length;i++) {
//		var parent_man = ul_elements[i].parentNode();
//		ul_elements[i].style.left="200px";
//		ul_elements[i].style.top=parent_man.offsetTop+"px";
//	}
	
	
	$(".menu-item").click(function() {
		if ($(this).hasClass("final")) {
			alert ("new categorical selector"+$(this).attr("id"));
		};
		if ($(this).hasClass("children")) {
			alert("inside if");
			var parent = $(this).parent();
			$("li",this).style.left=parent.offsetWidth+"px";
			$("li",this).style.top=parent.offsetTop+"px";
			$("li",this).removeClass("hidden");
			$("li",this).addClass("showmethemoney");
		};
	});
});

