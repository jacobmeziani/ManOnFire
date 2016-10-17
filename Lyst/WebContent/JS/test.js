(function ( $ ) {
 
    $.fn.dynameter = function ( options, attributeName ) {
        var defaults = {
            label: 'DynaMeter',
            value: 50,
            min: 0,
            max: 100,
            regions: {  // Value-keys and color-refs. E.g., value:
						// 'normal'|'warn|'error', etc.
                0: 'normal'
            }
        };

        var settings = $.extend({}, defaults, options);

        settings._range = settings.max - settings.min;
        settings._clrRef0 = 'normal';
        settings._clrRef1 = 'warn';
        settings._clrRef2 = 'error';

        this.changeValue =  function ( myVal, leftDude, rightDude )  { 
            var $this = $(this);
            var myMin = $this.data('dm-min');
            var myMax = $this.data('dm-max');
            var myRange = $this.data('dm-range');
            // Update value text.
            var winner;
            var phraseThatPays;
            if(myVal>=95 && myVal<=105){
            	phraseThatPays = "Even";
            	winner = "";
            }
            else if(myVal>=70 && myVal<=94){
            	phraseThatPays = "Slight Edge: ";
            	winner = leftDude;
            }
            else if(myVal>=30 && myVal<=69){
            	phraseThatPays = "Edge: ";
            	winner = leftDude;
            }
            else if(myVal>=0 && myVal<=29){
            	phraseThatPays = "Major Edge: ";
            	winner = leftDude;
            }
            else if(myVal>=106 && myVal<=130){
            	phraseThatPays = "Slight Edge: ";
            	winner = rightDude;
            }
            else if(myVal>=131 && myVal<=170){
            	phraseThatPays = "Edge: ";
            	winner = rightDude;
            }
            else if(myVal>=171 && myVal<=200){
            	phraseThatPays = "Major Edge: ";
            	winner = rightDude;
            }
            $this.find('.dm-innerDiv .dm-valueP').html(phraseThatPays+winner);
            // Ensure value is in-range.
            if (myVal < myMin) {
                myVal = myMin;
            }
            if (myVal > myMax) {
                myVal = myMax;
            }
            // Rotate mask div.
            var myRelVal = myVal - myMin;
            var myDeg = myRelVal / myRange * 180;
            $this.find('.dm-maskDiv').css({
                '-webkit-transform': 'rotate(' + myDeg + 'deg)',
                '-moz-transform': 'rotate(' + myDeg + 'deg)',
                '-ms-transform': 'rotate(' + myDeg + 'deg)',
                'border-radius': 'rotate(' + myDeg + 'deg)'
            });
            // Set/update dm-value attr.
            $this.data('dm-value', myVal);
            // console.log('[dynameter.changeValue] Method called. myVal = ' +
			// myVal);
        };

        // Greenify the collection based on the settings variable.
        return this.each(function () {
            var $this = $(this);  // Div that's getting DynaMeter-ized.

            function updateValue (myVal) {
                var myMin = $this.data('dm-min');
                var myRange = $this.data('dm-range');
                // Update value text.
                $this.find('.dm-innerDiv .dm-valueP').html(attributeName);
                // Rotate mask div.
                var myRelVal = myVal - myMin;
                var myDeg = myRelVal / myRange * 180;
                $this.find('.dm-maskDiv').css({
                    '-webkit-transform': 'rotate(' + myDeg + 'deg)',
                    '-ms-transform': 'rotate(' + myDeg + 'deg)',
                    '-moz-transform': 'rotate(' + myDeg + 'deg)',
                    'border-radius': 'rotate(' + myDeg + 'deg)'
                });
                // Set/update dm-value attr.
                $this.data('dm-value', myVal);
            }

            // Initialize once.
            if (!$this.hasClass('dm-wrapperDiv')) {
                // Skip init if settings are invalid.
                if (settings.value < settings.min ||
                    settings.value > settings.max ||
                    settings.min >= settings.max) {
                    throw new Error("DynaMeter initialization failed -- invalid value/min/max settings.");
                }
                var currClrRef;
                for (var key in settings.regions) {
                    currClrRef = settings.regions[key];
                    if (currClrRef != settings._clrRef0 &&
                        currClrRef != settings._clrRef1 &&
                        currClrRef != settings._clrRef2) {
                        throw new Error("DynaMeter initialization failed -- invalid region color-key.");
                    }
                    if (key < settings.min || key > settings.max) {
                        throw new Error("DynaMeter initialization failed -- invalid region value.");
                    }
                }

                $this.addClass('dm-wrapperDiv');
                $this.data('dm-id', ('dm-' + new Date().getTime()));
                $this.data('dm-min', settings.min);
                $this.data('dm-max', settings.max);
                $this.data('dm-range', settings.max - settings.min);

                $this.html('');
                $this.append('<div class="dm-maskDiv"></div>');
                $this.append('<div class="dm-innerDiv"></div>');

                var $bgDiv = $this.find('div.dm-bgDiv');
                var $maskDiv = $this.find('div.dm-maskDiv');
                var $innerDiv = $this.find('div.dm-innerDiv');

                $innerDiv.append('<p class="dm-valueP">' + settings.value + '</p>');

                var $valueP = $this.find('p.dm-valueP');

                var getAngleFromValue = function (myVal) {
                    // Returns angle for canvas arc color-stops.
                    if (myVal < settings.min || myVal > settings.max) {
                        // console.log('[dynameter.getAngleFromValue] ERROR:
						// myValue is outside value range!');
                        return null;
                    }
                    return parseInt((myVal - $this.data('dm-min')) / $this.data('dm-range') * 180);
                };

                // Color stops for indicator color-bands [[angle,
				// color-reference],...].
                var colorStops = [];  // Color-ref by angle, based on
										// settings.regions.
                var keyIdx = 0;
                for (var key2 in settings.regions) {
                    // If there's no min-value starting region, prepend one
					// using 'normal' as color-ref.
                    if (keyIdx === 0 && key2 > settings.min) {
                        colorStops.push([getAngleFromValue(settings.min), 'normal']);
                        keyIdx++;
                        // If starting region is still "normal" w/
						// non-min-value, skip this key.
                        if (settings.regions[key2] == 'normal') {
                            continue;
                        }
                    }
                    colorStops.push([getAngleFromValue(key2), settings.regions[key2]]);
                    keyIdx++;
                }
                var colorStopsLength = colorStops.length;

                // Create and rotate color-bands for indicator background.
                for (var i = 0; i < colorStopsLength; i++) {
                    var myAngle = colorStops[i][0];
                    var myClrRef = colorStops[i][1];
                    $(document.createElement('div'))
                        .addClass('dm-bgClrDiv ' + myClrRef)
                        .css({
                            '-webkit-transform': 'rotate(' + myAngle + 'deg)',
                            '-moz-transform': 'rotate(' + myAngle + 'deg)',
                            '-ms-transform': 'rotate(' + myAngle + 'deg)',
                            'transform': 'rotate(' + myAngle + 'deg)',
                            'zIndex': i + 1
                        })
                        .prependTo($this);
                }

                console.log('[dynameter] div#' + $this.attr('id') + ' initialized.');

            }

            updateValue(settings.value);

        });


 
    };
 
}( jQuery ));

function SetupSliders(){
	$(".gauge-class").each(function(){
		var myFuelGauge;
	var attributeName = $(this).attr("id"); 
  myFuelGauge = $(this).dynameter({
    width: 300,
    value: 100,
    min: 0.0,
    max: 200.0,
    unit: '',
    regions: { // Value-keys and color-refs
      0: 'error',
      .5: 'warn',
      1.5: 'normal'
    }
  },"Even");
  
	$(".slider")

	// activate the slider with options
	.slider({
		min : 0,
		max : 200,
		value : 100
	})

	// and whenever the slider changes, we update the
	.on("slide", function(e, ui) {
		var attributeChanging = $(this).attr("data-attributeName");
		var leftItemName = $(this).attr("data-leftItemName");
		var rightItemName = $(this).attr("data-rightItemName");
		$("#gauge-"+attributeChanging).dynameter().changeValue((ui.value).toFixed(1),leftItemName,rightItemName);
	});
	
	$('.ui-slider-label').on('touchstart', function(e) { 
		var attributeChanging = $(this).attr("data-attributeName");
		var leftItemName = $(this).attr("data-leftItemName");
		var rightItemName = $(this).attr("data-rightItemName");
		$("#gauge-"+attributeChanging).dynameter().changeValue((ui.value).toFixed(1),leftItemName,rightItemName);
	});
});
}

 $(document).ready(function() {
	 SetupSliders();
 
 });
