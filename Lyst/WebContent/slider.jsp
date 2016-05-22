<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<link rel="stylesheet" type="text/css" href="testCss.css">
<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="vsStyle.css">
<link rel="stylesheet" type="text/css" href="sliderStyle.css">
<script src="testScripts.js"></script>
<script src="testScripts2.js"></script>
</head>
<body>
	<div id="sliderTopButtons" class="row">
		<div id="topLeftButton"
			class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
			<button id="previousAttributeButton" type="button"
				class="hidden btn btn-circle btn-xl center-block">
				<span class="glyphicon glyphicon-circle-arrow-left"></span>
			</button>
			<button id="quitButton" type="button"
				class="btn btn-circle btn-xl center-block">
				<span class="glyphicon glyphicon-off"></span>
			</button>
		</div>
		<div class="hidden-xs hidden-sm col-md-2 col-lg-2"></div>
		<div id="topRightButton"
			class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
			<button type="button" id="nextAttributeButton"
				class="btn btn-circle btn-xl center-block">
				<span class="glyphicon glyphicon-thumbs-up"></span>
			</button>
			<button type="button" id="submitRatingsButton"
				class="hidden btn btn-circle btn-xl center-block">
				<span class="glyphicon glyphicon-flag"></span>
			</button>
		</div>
	</div>
	<div id="sliderTopButtonsLabels">
		<div class="row row-buffer">
			<div class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<h4 id="quitText" class="centerAlign">Quit</h4>
				<h4 id="backText" class="hidden centerAlign">Back</h4>

			</div>
			<div class="hidden-xs hidden-sm col-md-2 col-lg-2"></div>
			<div class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<h4 id="accurateText" class="centerAlign">Accurate!</h4>
				<h4 id="submitText" class="hidden centerAlign">Submit!</h4>
			</div>
		</div>
	</div>
	<div id="attributePhrases">
		<div class="row row-buffer">
			<h3 class="centerAlign" id="attributeTitle">Defense</h3>

		</div>
		<div class="row row-buffer">
			<p class="centerAlign" id="descriptor">Lebron James is much
				better at passing than Steph Curry</p>
		</div>
	</div>
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12" id="sliderRow">
		<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
			<button type="button" id="leftButton"
				class="btn btn-circle btn-xl center-block">
				<span class="glyphicon glyphicon-chevron-left"></span>
			</button>
		</div>
		<div class="col-xs-10 col-sm-10 col-md-10 col-lg-10">
			<div class="slider" id="circles-slider"></div>
		</div>
		<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1">
			<button type="button" id="rightButton"
				class="btn btn-circle btn-xl center-block">
				<span class="glyphicon glyphicon-chevron-right"></span>
			</button>
		</div>
	</div>
</body>
</html>