$(document).ready(function() {

	// set up an array to hold the months
	var months = ["+5", "+4", "+3", "+2", "+1", "0", "+1", "+2", "+3", "+4", "+5"];
	var stephs = ["Lebron is the best", "Lebron is amazing", "Lebron is great", "Lebron is good", "Lebron is kinda good", "Samesies",
	              "Curry is kinda good","Curry is good","Curry is great","Curry is amazing","Curry is the best" ]
	var currentSelection = 5;

	$(".slider")
	                    
	    // activate the slider with options
	    .slider({ 
	        min: 0, 
	        max: months.length-1, 
	        value: currentSelection 
	    })
	                    
	    // add pips with the labels set to "months"
	    .slider("pips", {
	        rest: "label",
	        labels: months,
	        animate: 400
	    })
	                    
	    // and whenever the slider changes, lets echo out the month
	    .on("slidechange", function(e,ui) {
	        currentSelection = ui.value;
	    	$("#descriptor").html(stephs[currentSelection]);	        
	    });
	    
	    	$("#rightButton").click(function() {
	    		if(currentSelection==10){}
	    		else{
	    		currentSelection++;
	    		$(".slider").slider('value',currentSelection);
	    		}
	})
	
		$("#leftButton").click(function() {
			if(currentSelection==0){}
			else{
			currentSelection--;
    		$(".slider").slider('value',currentSelection);
			}
	})
	
});

                    
