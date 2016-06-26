$(document).ready(function(){
	
	
	var username = $("#userid").html();
	
	function checkUser() {
		if (username == "") {
			alert("No Active User. Please log in");
			 window.location.href = "contributor_login.html";
		}
		
	};
	
	function readCookies () {
		var allcookies = document.cookie.split(";");
	};
	checkUser();
	
	var i=2;
	$("#add_row").click(function(){
		$('#att_table').append('<tr id = "entry'+i+'"></tr>');
		$('#entry'+i).html("<td>" + i + "</td><td><input type = 'text' name = 'attribute"+i+"' id = 'attribute"+i+"' class = 'form-control' placeholder='Attribute'></td>");
		i++; 
	});
	$("#delete_row").click(function(){
		if(i>1){
			$("#entry"+(i-1)).html('');
			i--;
		}
	});
	
	function getAttributes() {
		alert('in hurr');
		var jsonObject = {};
		var list = $("#list_name").val();
		var att = 1;
		var jsonList = [];
		for (iter = 1;iter<i;iter++) {
			var attribute = $("#attribute"+att).val();
			if (attribute=="") {
				continue;
			}
			jsonList.push({"attribute":attribute});
			att++;
		};
		jsonObject["user"] = username;
		jsonObject["listname"] = list;
		jsonObject["attributes"] = jsonList;
		return JSON.stringify(jsonObject);
	};
	
	$("#submitbutton").click(function() {
		var id = 89;
		
		var xhr = $.ajax({
			 url: "ContributorServlet",
			 data: {
				 "type":"list",
				 "input":getAttributes()
				 
			 },
			 type: "POST",
			 dataType: "text/html",
		})
		.done(function(text,textStatus,xhr) {
			
			alert(text);
		});
		
		//send post to DB
		//TODO
	
		
		const listname = $("#list_name").val();
		var json = JSON.stringify([id,listname,username]);
		alert (json);
		Cookies.set('list'+id,{list:listname,user:username});
		
	});
})