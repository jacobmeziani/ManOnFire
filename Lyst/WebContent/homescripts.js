	var currentAttributeIndex = 0;
	var descriptors;

$(document).ready(function() {
	var attributes;
	var attributeSize;
	var leftItem;
	var rightItem;
	$("#myCardRight").toggleClass("flip");
	$("#myCard").toggleClass("flip");
	$("#randomButton").click(function() {
		$("#myCard").toggleClass("flip");
		$("#myCardRight").toggleClass("flip");
		$.get("bro", function(html) {
			var parsed = $('<div/>').append(html);

			$("#leftPic").html(parsed.find("#leftPic"));
			$("#leftName").html(parsed.find("#leftName"));
			$("#list").html(parsed.find("#list"));
			$("#rightPic").html(parsed.find("#rightPic"));
			$("#rightName").html(parsed.find("#rightName"));
			$("#rightName2").html(parsed.find("#rightName2"));
			$("#leftName2").html(parsed.find("#leftName2"));
			$("#myCard").toggleClass("flip");
			$("#myCardRight").toggleClass("flip");
		});
	})

	$("#vsButton").click(function() {
		$.get("bro", "vs=true", function(json) {
			var obj = JSON.parse(json);
			attributes = obj.attributes;
			leftItem = obj.leftItem;
			rightItem = obj.rightItem;
			attributeSize = attributes.length;
			alert(leftItem + " y " + rightItem);
			$.get("bro", "vsDisplay=true", function(html) {
				var parsed = $('<div/>').append(html);

				$("#bottomRow").html(parsed.find("#sliderRow"));
				$("#centerContent").html(parsed.find("#attributePhrases"));
				$("#attributeTitle").html(attributes[0]);
				sliderFunc(leftItem, rightItem, attributes);
				$("#descriptor").html(descriptors[5]);
			});
		});
	})

});

function sliderFunc(leftItem, rightItem, attributes) {

	// set up an array to hold the months
	var numbers = [ "+5", "+4", "+3", "+2", "+1", "0", "+1", "+2", "+3", "+4",
			"+5" ];
	descriptors = getCurrentListOfPhrases(leftItem, rightItem,
			attributes[currentAttributeIndex], 0);
	var currentSelection = 5;

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
	});

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
	} else {
		phrases = [ "Lebron is the best", "Lebron is amazing",
				"Lebron is great", "Lebron is good", "Lebron is kinda good",
				"Samesies", "Curry is kinda good", "Curry is good",
				"Curry is great", "Curry is amazing", "Curry is the best" ];
	}
	return phrases;
}