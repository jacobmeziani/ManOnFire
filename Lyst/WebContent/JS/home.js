var attributes;
var attributeSize;
var stringPackage;
var currentAttributeIndex = 0;
var descriptors;
var scorePositions;
var currentSelection = 5;
var sliderInit;
var leftItemProps;
var rightItemProps;
var homeWorking = false;

$(document).ready(function() {
	$("#randomButton").click(function() {
		if(!homeWorking){
			homeWorking = true;
	newMatchup();
		}
	})

	$("#vsButton").click(function() {
		if(!homeWorking){
			homeWorking = true;
		goToVsScreen();
		}
	})

});

function goToVsScreen() {
	var listName = $.trim($("#listNameH3").text());
	var leftItemName = $.trim($("#leftNameH3").text());
	var rightItemName = $.trim($("#rightNameH3").text());
	$.get("bro", {
		"action" : "vs",
		"listName" : listName,
		"leftItemName" : leftItemName,
		"rightItemName" : rightItemName
	}, function(json) {
		sliderInit = false;
		var obj = JSON.parse(json);
		attributes = obj.attributes;
		leftItemProps = obj.leftItemProps;
		rightItemProps = obj.rightItemProps;
		stringPackage = obj.stringPackages;
		attributeSize = attributes.length;
		scorePositions = new Array();
		for (i = 0; i < attributeSize; i++) {
			scorePositions[i] = 5;
		}
		$.get("bro", {
			"action" : "sliderDisplay"
		}, function(html) {
			$("#listNameH3").empty();
			var parsed = $('<div/>').append(html);

			$("#bottomRow").html(parsed.find("#sliderRow"));
			$("#centerContent").html(parsed.find("#attributePhrases"));
			$("#contextButtons").html(parsed.find("#sliderTopButtons"));
			$("#labels").html(parsed.find("#sliderTopButtonsLabels"));
			$("#attributeTitle").html(attributes[currentAttributeIndex].name);
			$("#attributeMobileTitle").html(attributes[currentAttributeIndex].name);
			sliderFunc(leftItemProps[0], rightItemProps[0], attributes);
			$("#descriptor").html(descriptors[5]);
			$("#descriptorMobile").html(descriptors[5]);
			$("#nextAttributeButton").click(function() {
				nextAttribute();
			});
			$("#previousAttributeButton").click(function() {
				previousAttribute();
			});
			$("#submitRatingsButton").click(function() {
				$(this).attr("disabled", "disabled");
				scorePositions[currentAttributeIndex] = currentSelection;
				$.get("bro", {
					"action" : "results",
					"scores" : scorePositions,
					"leftItemProps" : leftItemProps,
					"rightItemProps" : rightItemProps
				}, function(data) {
					var newDoc = document.open("text/html", "replace");
					newDoc.write(data);
					newDoc.close();
				});
			});
			window.scrollTo(0,document.body.scrollHeight);
			homeWorking = false;
		});
	});
}

function sliderFunc(leftItem, rightItem, attributes) {

	// set up an array to hold the months
	var emoji =twemoji.parse(twemoji.convert.fromCodePoint('1f4aa'));
	var numbers = [emoji, emoji,emoji,emoji,emoji,"0",emoji,emoji,emoji,emoji,emoji];
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
	
	$('.ui-slider-label').on('touchstart', function(e) { 
		currentSelection = parseInt($(this).attr("data-value"));
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
	else if (packageNumber == 3) {
		phrases = [
				leftItem + " has so much better " + attribute + " than "
						+ rightItem + ", its not even funny.",
				leftItem + " has tons better " + attribute + " than "
						+ rightItem,
				leftItem + " has much better " + attribute + " than "
						+ rightItem,
				leftItem + " has better " + attribute + " than " + rightItem,
				leftItem + " has slightly better " + attribute + " than "
						+ rightItem,
				leftItem + " has exactly the same quality " + attribute + " as " + rightItem,
				rightItem + " has slightly better " + attribute + " than "
						+ leftItem,
				rightItem + " has better " + attribute + " than " + leftItem,
				rightItem + " has much better " + attribute + " than "
						+ leftItem,
				rightItem + " has tons better " + attribute + " than "
						+ leftItem,
				rightItem + " has so much better " + attribute + " than "
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
	$("#attributeMobileTitle").html(attributes[currentAttributeIndex].name);
	currentSelection = scorePositions[currentAttributeIndex];
	sliderFunc(leftItemProps[0], rightItemProps[0], attributes);
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
	$("#attributeMobileTitle").html(attributes[currentAttributeIndex].name);
	currentSelection = scorePositions[currentAttributeIndex];
	sliderFunc(leftItemProps[0], rightItemProps[0], attributes);
	$("#descriptor").html(descriptors[currentSelection]);
	if (currentAttributeIndex == 0) {
		$("#quitText").toggleClass('hidden');
		$("#backText").toggleClass('hidden');
		$("#previousAttributeButton").toggleClass('hidden');
		$("#quitButton").toggleClass('hidden');
	}
}

function imgLoaded(img){
    var $img = $(img);
 
    $img.addClass('loaded');
};

function newMatchup(){
	$("#rightPic").fadeOut("slow");
	$("#leftName").fadeOut("slow");
	$("#rightName").fadeOut("slow");
	$("#rightName2").fadeOut("slow");
	$("#leftName2").fadeOut("slow");
	$("#miniVs").fadeOut("slow");
	$("#leftPic").fadeOut("slow",function(){
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
		$("#miniVs").fadeIn("slow");
		homeWorking = false;
	});
	});
}