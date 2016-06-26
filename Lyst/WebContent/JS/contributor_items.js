$(document).ready(function(){
	
	var username;
	var i = 1;
	function checkUser() {
		username = $("#userid").html();
		if (username == "") {
			alert("No Active User. Please log in");
			 window.location.href = "contributor_login.html";
		}
		
	};
	
	function writeList(listname,listid) {
		var list = '<li><a class = "listtoselect" id = "list'+listid+'">'+listname+'</a></li>'
		$("#listselect").append(list);
	};
	
	function addRow(index,itemname, picpath) {
		$('#item_table').append('<tr id = "entry'+index+'"></tr>');
		$('#entry'+index).html("<td>"+index+"</td><td><input type = 'text' name = 'item"+index+"' id = 'item"+index+"' class= 'form-control' placeholder='Item Name'></td><td><input type = 'text' name = 'picpath"+index+"' id = 'picpath"+index+"' class ='form-control' placeholder='File Path'></td>")
		
		if (itemname!=null) {
			$('#item'+index).val(itemname);
		};
		if (picpath!=null) {
			$('#picpath'+index).val(picpath);
		};
	};
	
	function loadPendingList() {
		var cookie = Cookies.get('pendinglist');
		if (cookie==undefined) {
			addRow(i,null,null);
			i++;
			return;
		}
		cookie = $.parseJSON(cookie);
		if (cookie.user != username) {
			addRow(i,null,null);
			i++;
			return;
		}
		$("#selectedlist").html(cookie.listname);
		$("#selectedid").val(cookie.listid);
		var items = cookie.items;
		var indextotal = cookie.indextotal;
		
		for (iter = 0;iter < (indextotal-1);iter++) {
			var item = items[iter];
			addRow(item.index,item.itemname,item.picpath);
			i++;
		}	
	}
	checkUser();

	loadPendingList();
	
		
	$("#add_row").click(function() {
		addRow(i,null,null);
		i++;
	});
	
	$("#delete_row").click(function(){
		if(i>1){
			$("#entry"+(i-1)).html('');
			i--;
		}
	});
	var allcookies = Cookies.get();
	for (var key in allcookies) {
		var listid = key.split("list");
		if (listid.length==1) {
			continue;
		}
		listid = listid[1];
		var listjson = $.parseJSON(allcookies[key]);
		if (username!=listjson.user) {
			//user did not create list
			continue;
		}
		writeList(listjson.list,listid) 
	};
	
	$("#savebutton").click(function() {
		var jsonObject = {};
		var listname = $("#selectedlist").html();
		var listid = $("#selectedid").val();
		if (listname == "") {
			alert ("aint no list bihh");
			return;
		}	
		
		jsonObject["listname"] = listname;
		jsonObject["listid"] = listid;
		jsonObject["indextotal"] = i;
		jsonObject["user"] = username;
		var jsonList = [];
		for (iter = 1;iter <i;iter++) {
			var itemname = $("#item"+iter).val();
			var picpath = $("#picpath"+iter).val();
			jsonList.push({"index":iter,"itemname":itemname,"picpath":picpath});
		}
		
		jsonObject["items"] = jsonList;
		var json = JSON.stringify(jsonObject);
		alert(json);
		Cookies.set("pendinglist",json);
		
	});
	
	$(".listtoselect").click(function() {
		var list = $(this).html();
		var id = $(this).attr("id");
		var id = id.split("list")[1];
		$("#selectedlist").html(list);
		$("#selectedid").val(id);
		
	});
	
	
	
});