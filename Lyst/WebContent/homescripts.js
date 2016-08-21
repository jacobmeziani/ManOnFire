var attributes;
var attributeSize;
var leftItem;
var rightItem;
var stringPackage;
var currentAttributeIndex = 0;
var descriptors;
var scorePositions;
var currentSelection = 5;
var sliderInit;

$(document).ready(function() {

	$("#randomButton").click(function() {
		$("#leftPic").fadeOut("slow");
		$("#rightPic").fadeOut("slow");
		$("#leftName").fadeOut("slow");
		$("#rightName").fadeOut("slow");
		$("#rightName2").fadeOut("slow");
		$("#leftName2").fadeOut("slow");
		$("#vsButtonMobile").fadeOut("slow");
		var category = $("#currentCategory").attr("value");
		var list = $("#isCategoryList").attr("value");
		$.get("bro", {
			"action" : "newRandom",
			"currentCategory" : category,
			"isList" : list
		}, function(html) {
			var parsed = $('<div/>').append(html);
			$("#leftPic").html(parsed.find("#leftPic"));
			$("#leftPic").fadeIn("slow");
			$("#leftName").html(parsed.find("#leftName"));
			$("#leftName").fadeIn("slow");
			$("#list").html(parsed.find("#list"));
			$("#rightPic").html(parsed.find("#rightPic"));
			$("#rightPic").fadeIn("slow");
			$("#rightName").html(parsed.find("#rightName"));
			$("#rightName").fadeIn("slow");
			$("#rightName2").html(parsed.find("#rightName2"));
			$("#leftName2").html(parsed.find("#leftName2"));
			$("#rightName2").fadeIn("slow");
			$("#leftName2").fadeIn("slow");
			$("#vsButtonMobile").fadeIn("slow");
		});
	})

	$("#vsButton").click(function() {
		goToVsScreen();
	})
	$("#vsButtonMobile").click(function() {
		goToVsScreen();
	})

});

function goToVsScreen() {
	$.get("bro", {
		"action" : "vs"
	}, function(json) {
		sliderInit = false;
		var obj = JSON.parse(json);
		attributes = obj.attributes;
		leftItem = obj.leftItem;
		rightItem = obj.rightItem;
		stringPackage = obj.stringPackages;
		attributeSize = attributes.length;
		scorePositions = new Array();
		for (i = 0; i < attributeSize; i++) {
			scorePositions[i] = 5;
		}
		$.get("bro", {
			"action" : "sliderDisplay"
		}, function(html) {
			var parsed = $('<div/>').append(html);

			$("#bottomRow").html(parsed.find("#sliderRow"));
			$("#centerContent").html(parsed.find("#attributePhrases"));
			$("#contextButtons").html(parsed.find("#sliderTopButtons"));
			$("#labels").html(parsed.find("#sliderTopButtonsLabels"));
			$("#attributeTitle").html(attributes[currentAttributeIndex].name);
			sliderFunc(leftItem, rightItem, attributes);
			$("#descriptor").html(descriptors[5]);
			$("#descriptorMobile").html(descriptors[5]);
			$("#nextAttributeButton").click(function() {
				nextAttribute();
			});
			$("#previousAttributeButton").click(function() {
				previousAttribute();
			});
			$("#quitButton").click(function() {
				$(this).attr("disabled", "disabled");
				$.get("bro", {
					"action" : "navigatingBack"
				}, function(html) {
					window.location.href = 'home.jsp';
				});
			})
			$("#submitRatingsButton").click(function() {
				$(this).attr("disabled", "disabled");
				scorePositions[currentAttributeIndex] = currentSelection;
				$.get("bro", {
					"action" : "results",
					"scores" : scorePositions
				}, function(data) {
					window.location.href = 'results.jsp';
				});
			});
		});
	});
}

function sliderFunc(leftItem, rightItem, attributes) {

	// set up an array to hold the months
	var numbers = [ "+5", "+4", "+3", "+2", "+1", "0", "+1", "+2", "+3", "+4",
			"+5" ];
	descriptors = getCurrentListOfPhrases(leftItem, rightItem,
			attributes[currentAttributeIndex].name,
			stringPackage[currentAttributeIndex]);

	$(".slider")

	// activate the slider with options
	.slider({
		min : 0,
		max : numbers.length - 1,
		value : currentSelection
	})

	// add pips with the labels set to "months"
	.slider("pips", {
		rest : "label",
		labels : numbers,
		animate : 400
	})

	// and whenever the slider changes, lets echo out the month
	.on("slidechange", function(e, ui) {
		currentSelection = ui.value;
		$("#descriptor").html(descriptors[currentSelection]);
		$("#descriptorMobile").html(descriptors[currentSelection]);
	});

	if (!sliderInit) {
		$("#rightButton").click(function() {
			if (currentSelection == 10) {
			} else {
				currentSelection++;
				$(".slider").slider('value', currentSelection);
			}
		})

		$("#leftButton").click(function() {
			if (currentSelection == 0) {
			} else {
				currentSelection--;
				$(".slider").slider('value', currentSelection);
			}
		})
		$("#rightButtonMini").click(function() {
			if (currentSelection == 10) {
			} else {
				currentSelection++;
				$(".slider").slider('value', currentSelection);
			}
		})

		$("#leftButtonMini").click(function() {
			if (currentSelection == 0) {
			} else {
				currentSelection--;
				$(".slider").slider('value', currentSelection);
			}
		})
	}
	sliderInit = true;

}

function getCurrentListOfPhrases(leftItem, rightItem, attribute, packageNumber) {
	var phrases;
	if (packageNumber == 0) {
		phrases = [
				leftItem + " is so much better at " + attribute + " than "
						+ rightItem + ", its not even funny.",
				leftItem + " is miles better at " + attribute + " than "
						+ rightItem,
				leftItem + " is much better at " + attribute + " than "
						+ rightItem,
				leftItem + " is better at " + attribute + " than " + rightItem,
				leftItem + " is slightly better at " + attribute + " than "
						+ rightItem,
				leftItem + " and " + rightItem + " are exactly the same at "
						+ attribute,
				rightItem + " is slightly better at " + attribute + " than "
						+ leftItem,
				rightItem + " is better at " + attribute + " than " + leftItem,
				rightItem + " is much better at " + attribute + " than "
						+ leftItem,
				rightItem + " is miles better at " + attribute + " than "
						+ leftItem,
				rightItem + " is so much better at " + attribute + " than "
						+ leftItem + ", its not even funny.", ];
	} else if (packageNumber == 1) {
		phrases = [
				leftItem + " is so much more " + attribute + " than "
						+ rightItem + ", its not even funny.",
				leftItem + " is leaps and bounds more " + attribute + " than "
						+ rightItem,
				leftItem + " is much more " + attribute + " than "
						+ rightItem,
				leftItem + " is more " + attribute + " than " + rightItem,
				leftItem + " is slightly more " + attribute + " than "
						+ rightItem,
				leftItem + " is exactly as " + attribute + " as " + rightItem,
				rightItem + " is slightly more " + attribute + " than "
						+ leftItem,
				rightItem + " is more " + attribute + " than " + leftItem,
				rightItem + " is much more " + attribute + " than "
						+ leftItem,
				rightItem + " is leaps and bounds more " + attribute + " than "
						+ leftItem,
				rightItem + " is so much more " + attribute + " than "
						+ leftItem + ", its not even funny.", ];
	}
	else if (packageNumber == 2) {
		phrases = [
				leftItem + " has so much more " + attribute + " than "
						+ rightItem + ", its not even funny.",
				leftItem + " has tons more " + attribute + " than "
						+ rightItem,
				leftItem + " has much more " + attribute + " than "
						+ rightItem,
				leftItem + " has more " + attribute + " than " + rightItem,
				leftItem + " has slightly more " + attribute + " than "
						+ rightItem,
				leftItem + " has exactly the same " + attribute + " as " + rightItem,
				rightItem + " has slightly more " + attribute + " than "
						+ leftItem,
				rightItem + " has more " + attribute + " than " + leftItem,
				rightItem + " has much more " + attribute + " than "
						+ leftItem,
				rightItem + " has tons more " + attribute + " than "
						+ leftItem,
				rightItem + " has so much more " + attribute + " than "
						+ leftItem + ", its not even funny.", ];
	}
	else {
		phrases = [ "Lebron is the best", "Lebron is amazing",
				"Lebron is great", "Lebron is good", "Lebron is kinda good",
				"Samesies", "Curry is kinda good", "Curry is good",
				"Curry is great", "Curry is amazing", "Curry is the best" ];
	}
	return phrases;
}

function nextAttribute() {
	if (currentAttributeIndex == 0) {
		$("#quitText").toggleClass('hidden');
		$("#backText").toggleClass('hidden');
		$("#previousAttributeButton").toggleClass('hidden');
		$("#quitButton").toggleClass('hidden');
	}
	scorePositions[currentAttributeIndex] = currentSelection;
	currentAttributeIndex++;
	$("#attributeTitle").html(attributes[currentAttributeIndex].name);
	currentSelection = scorePositions[currentAttributeIndex];
	sliderFunc(leftItem, rightItem, attributes);
	$("#descriptor").html(descriptors[currentSelection]);
	if (currentAttributeIndex == (attributeSize - 1)) {
		$("#accurateText").toggleClass('hidden');
		$("#submitText").toggleClass('hidden');
		$("#nextAttributeButton").toggleClass('hidden');
		$("#submitRatingsButton").toggleClass('hidden');
	}
}

function previousAttribute() {
	if (currentAttributeIndex == (attributeSize - 1)) {
		$("#accurateText").toggleClass('hidden');
		$("#submitText").toggleClass('hidden');
		$("#nextAttributeButton").toggleClass('hidden');
		$("#submitRatingsButton").toggleClass('hidden');
	}
	currentAttributeIndex--;
	$("#attributeTitle").html(attributes[currentAttributeIndex].name);
	currentSelection = scorePositions[currentAttributeIndex];
	sliderFunc(leftItem, rightItem, attributes);
	$("#descriptor").html(descriptors[currentSelection]);
	if (currentAttributeIndex == 0) {
		$("#quitText").toggleClass('hidden');
		$("#backText").toggleClass('hidden');
		$("#previousAttributeButton").toggleClass('hidden');
		$("#quitButton").toggleClass('hidden');
	}
}