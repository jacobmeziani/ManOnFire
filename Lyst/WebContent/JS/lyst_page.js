function createCookie(name, value, days) {
	if (days) {
		var date = new Date();
		date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
		var expires = "; expires=" + date.toGMTString();
	} else
		var expires = "";

	document.cookie = name + "=" + value + expires + "; path=/";
}

function requestItems(listID, attributeNumber, completeList, startingIndex,
		endingIndex) {
	// TODO: HURR
	//

	// items to get will just be a string of 10 or whatever many item ids
	working = true;

	while (true) {
		try {
			itemsToGet = completeList.slice(startingIndex, endingIndex);
			console.log('indexed down');
			break;
		} catch (e) {
			endingIndex = endingIndex - 1;
			continue;
		}
	}

	console.log(itemsToGet);

	var xhr = $.ajax({
		url : "bro",
		data : {
			"action" : "load",
			"ListID" : listID,
			"Attribute" : attributeNumber,
			"ItemsToGet" : itemsToGet,
			"Toggled" : toggled
		},
		type : "GET",
		dataType : "html",
	}).done(function(html, textStatus, xhr) {
		var parsed = $('<div/>').append(html);
		$("#thatList").append(parsed.find("#thatList"));
		isFinal = (xhr.getResponseHeader("isFinal"));
		$(".spinner").addClass("hidden");
		// set isFinal manually just in case as well
		if (endingIndex >= itemListSize) {
			isFinal = 1;
		} else {
			isFinal = 0;
		}

		working = false;

	});

	return (endingIndex);

};

function requestInitial(listID, attributeNumber, attributeName) {
	working = true;
	// initial data load that brings up the sorted list of the item IDs neeeded
	var xhr = $.ajax({
		url : "bro",
		data : {
			"action" : "initialLoad",
			"ListID" : listID,
			"Attribute" : attributeNumber,
		},
		type : "GET",
		dataType : "json",
	}).done(
			function(json, textStatus, xhr) {
				sortedList = json.sortedItemIDs;
				$("#listDescriptor").text("Sorted by: " + attributeName);
				itemListSize = sortedList.length;
				last_delivered = requestItems(listID, attributeWanted,
						sortedList, 0, number_to_fetch);

				working = false;
			});

};

$(document)
		.ready(
				function() {
					// list ID needs to be set before the first thing is
					// returned. possibly grabbed from URL

					var listID = $("#listId").val();
					var listName = $("#listTitle").text();
					var attributeName = "Overall";

					$(".spinner").removeClass("hidden");

					if ("ontouchstart" in document.documentElement) {
						var touchscreen = true;
					} else {
						var touchscreen = false;
					}

					// global variables in page
					attributes = undefined;
					attributeWanted = 0;
					isFinal = 0;
					working = false;
					last_delivered = 0;
					number_to_fetch = 10;
					// END global variables

					$("#thatList").on(
							'click',
							'.attributeBox',
							function() {
								if (!working) {
									// housekeeping
									$(".spinner").removeClass("hidden");
									working = true;
									// code
									var attributeId = $(this).attr('id');
									var split = attributeId
											.split("attributeNumber");
									var attributeName = $(this).find(
											"#attributeNameText").text();
									attributeWanted = split[1];
									$("#thatList").empty();
									isFinal = 0;
									last_delivered = 0;
									sortedList = requestInitial(listID,
											attributeWanted, attributeName);

								}
							});
					$("#thatList").on(
							'click',
							'.attributeBoxMini',
							function() {
								if (!working) {
									// housekeeping
									$(".spinner").removeClass("hidden");
									working = true;
									// code
									var attributeId = $(this).attr('id');
									var split = attributeId
											.split("attributeNumber");
									var attributeName = $(this).find(
											"#attributeNameText").text();
									attributeWanted = split[1];
									$("#thatList").empty();
									isFinal = 0;
									last_delivered = 0;
									sortedList = requestInitial(listID,
											attributeWanted, attributeName);

								}
							});
					$("#thatList").on('click', '.toggleAttButton', function() {
						$(".attributeBox").each(function(index) {
							if ($(this).hasClass("hidden")) {
								$(this).removeClass("hidden");
							} else {
								$(this).addClass("hidden");
							}
						});
						$(".toggleAttButton").each(function(index) {
							if ($("span", this).hasClass("glyphicon-menu-right")) {
								$("span", this).removeClass("glyphicon-menu-right");
								$("span", this).addClass("glyphicon-menu-left");
							} else {
								$("span", this).removeClass("glyphicon-menu-left");
								$("span", this).addClass("glyphicon-menu-right");
							}
						});
						toggled = !toggled;
					});

					$(window)
							.scroll(
									function() {
										if ($(window).scrollTop()
												+ $(window).height() == $(
												document).height()) {
											if (isFinal == 0) {
												if (!working) {
													$(".spinner").removeClass(
															"hidden");
													working = true;
													console
															.log('window scrolled');
													console
															.log('last delivered:');
													console.log(last_delivered);
													last_delivered = requestItems(
															listID,
															attributeWanted,
															sortedList,
															last_delivered,
															(last_delivered + number_to_fetch),
															toggled);
												}
											} else {
												console.log("out of items");
											}
										}
									});

					$("#newShowdownButton").click(function() {
						createCookie("listId", listID, 2);
						createCookie("currentCategory", listID, 2);
						createCookie("categoryName", listName, 2);
						createCookie("isList", "true", 2);
						window.location.href = "/";
					});

					sortedList = requestInitial(listID, attributeWanted,
							attributeName);
				})

var toggled = false;