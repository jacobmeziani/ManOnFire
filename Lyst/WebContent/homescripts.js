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
		$("#leftPic").fadeOut( "slow");
		$("#rightPic").fadeOut( "slow");
		$("#leftName").fadeOut( "slow");
		$("#rightName").fadeOut( "slow");
		$("#rightName2").fadeOut( "slow" );
		$("#leftName2").fadeOut( "slow" );
		$("#mobileVs").fadeOut( "slow" );
		$.get("bro", function(html) {
			var parsed = $('<div/>').append(html);
			$("#leftPic").html(parsed.find("#leftPic"));
			$("#leftPic").fadeIn( "slow");
			$("#leftName").html(parsed.find("#leftName"));
			$("#leftName").fadeIn( "slow" );
			$("#list").html(parsed.find("#list"));
			$("#rightPic").html(parsed.find("#rightPic"));
			$("#rightPic").fadeIn( "slow" );
			$("#rightName").html(parsed.find("#rightName"));
			$("#rightName").fadeIn( "slow" );
			$("#rightName2").html(parsed.find("#rightName2"));
			$("#leftName2").html(parsed.find("#leftName2"));
			$("#rightName2").fadeIn( "slow" );
			$("#leftName2").fadeIn( "slow" );
			$("#mobileVs").fadeIn( "slow" );
		});
	})

	$("#vsButton").click(function() {
		$.get("bro", "vs=true", function(json) {
			sliderInit = false;
			var obj = JSON.parse(json);
			attributes = obj.attributes;
			leftItem = obj.leftItem;
			rightItem = obj.rightItem;
			stringPackage = obj.stringPackages;
			attributeSize = attributes.length;
			scorePositions = new Array();
			for (i=0;i<attributeSize;i++){
				scorePositions[i]=5;
			}
			$.get("bro", "vsDisplay=true", function(html) {
				var parsed = $('<div/>').append(html);

				$("#bottomRow").html(parsed.find("#sliderRow"));
				$("#centerContent").html(parsed.find("#attributePhrases"));
				$("#contextButtons").html(parsed.find("#sliderTopButtons"));
				$("#labels").html(parsed.find("#sliderTopButtonsLabels"));
				$("#attributeTitle").html(attributes[currentAttributeIndex]);
				sliderFunc(leftItem, rightItem, attributes);
				$("#descriptor").html(descriptors[5]);
				$("#nextAttributeButton").click(function(){
					nextAttribute();
				});
				$("#previousAttributeButton").click(function(){
					previousAttribute();
				});
				$("#quitButton").click(function(){
					$.get( "bro", "initial=true", function( data ) {
						var newDoc = document.open("text/html", "replace");
						newDoc.write(data);
						newDoc.close();
						});
				});
			});
		});
	})

});

function sliderFunc(leftItem, rightItem, attributes) {
	
	// set up an array to hold the months
	var numbers = [ "+5", "+4", "+3", "+2", "+1", "0", "+1", "+2", "+3", "+4",
			"+5" ];
	descriptors = getCurrentListOfPhrases(leftItem, rightItem,
			attributes[currentAttributeIndex], stringPackage[currentAttributeIndex]);

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
	
	if(!sliderInit){
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
	} else {
		phrases = [ "Lebron is the best", "Lebron is amazing",
				"Lebron is great", "Lebron is good", "Lebron is kinda good",
				"Samesies", "Curry is kinda good", "Curry is good",
				"Curry is great", "Curry is amazing", "Curry is the best" ];
	}
	return phrases;
}

function nextAttribute()
{
	if(currentAttributeIndex==0){
		$("#quitText").toggleClass('hidden');
		$("#backText").toggleClass('hidden');
		$("#previousAttributeButton").toggleClass('hidden');
		$("#quitButton").toggleClass('hidden');
	}
	scorePositions[currentAttributeIndex]= currentSelection;
	currentAttributeIndex++;
	$("#attributeTitle").html(attributes[currentAttributeIndex]);
	currentSelection = scorePositions[currentAttributeIndex];
	sliderFunc(leftItem, rightItem, attributes);
	$("#descriptor").html(descriptors[currentSelection]);
	if(currentAttributeIndex==(attributeSize-1)){
		$("#accurateText").toggleClass('hidden');
		$("#submitText").toggleClass('hidden');
		$("#nextAttributeButton").toggleClass('hidden');
		$("#submitRatingsButton").toggleClass('hidden');
	}
}

function previousAttribute(){
	if(currentAttributeIndex==(attributeSize-1)){
		$("#accurateText").toggleClass('hidden');
		$("#submitText").toggleClass('hidden');
		$("#nextAttributeButton").toggleClass('hidden');
		$("#submitRatingsButton").toggleClass('hidden');
	}
	currentAttributeIndex--;
	$("#attributeTitle").html(attributes[currentAttributeIndex]);
	currentSelection = scorePositions[currentAttributeIndex];
	sliderFunc(leftItem, rightItem, attributes);
	$("#descriptor").html(descriptors[currentSelection]);
	if(currentAttributeIndex==0){
		$("#quitText").toggleClass('hidden');
		$("#backText").toggleClass('hidden');
		$("#previousAttributeButton").toggleClass('hidden');
		$("#quitButton").toggleClass('hidden');
	}
}