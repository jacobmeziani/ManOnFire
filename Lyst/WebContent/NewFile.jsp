<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<title>Insert title here</title>


<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">


<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>

<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link
	href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css"
	rel="stylesheet">

<link href="testCss.css" rel="stylesheet">
<link href="vsStyle.css" rel="stylesheet">
<!-- <script src="JS/lyst_page.js"></script> -->
<link rel="stylesheet" type="text/css" href="style.css">
<meta name="viewport" content="width=device-width, initial-scale=1">





</head>
<body>
	<div class="container-fluid">
		<div class="row rowPadding well well-sm">
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2">
				<a href="/bro/lists" data-toggle="tooltip" data-placement="bottom"
					title="View Lists"><img class="img-responsive topButton"
					src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/listLogo.png"></a>
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
				<img class="theLogo center-block"
					src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/logo.png">
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2 pull-right">
				<a href="/" type="button" id="rateMenuButton" data-toggle="tooltip"
					data-placement="bottom" title="Rate!"><img
					class="img-responsive topButton pull-right"
					src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/vsLogo.png">
				</a>
			</div>

		</div>
	</div>
	<div class="container-fluid">
		<div class="row rowBuffs">
			<div class="col-xs-4 col-sm-4 col-md-4">
				<a href="/" id="newShowdownButton" type="button"
					class="btn btn-circle btn-xl center-block"> <span
					class="glyphicon glyphicon-eye-close aTag"></span>
				</a>
				<h4 class="centerAlign">Rate Items</h4>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-4">
				<p class="listTitle centerAlign">Villains</p>
			</div>
			<div class="col-xs-2 col-sm-4 col-md-4"></div>
		</div>
		<input type="hidden" id="isCategoryList"
			value="${requestScope.isList}">
		<div class="row rowPadding itemInList">
			<div class="col-xs-12 col-sm-2 col-md-2 itemBox">
				<div class="col-xs-2 col-sm-2 col-md-1">
					<p class="numberText">1</p>
				</div>
				<div class="col-xs-10 col-sm-10 col-md-11">
					<a href="www.espn.com"><img
						class="img-circle center-block listItemImage"
						src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Villains/bane.jpg">
					</a>
				</div>
			</div>
			<div class="col-xs-2 hidden-sm hidden-md hidden-lg"></div>
			<div class="col-xs-10 col-sm-2 col-md-2 itemBox">
				<a href="www.espn.com">
					<h3 class="itemName">Bane Sauce</h3>
				</a>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Overall</div>
				<span class="redRating center-block"><p class="ratingNumber">88</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Evil</div>
				<span class="redRating center-block"><p class="ratingNumber">85</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Fighting</div>
				<span class="redRating center-block"><p class="ratingNumber">91</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Charisma</div>
				<span class="redRating center-block"><p class="ratingNumber">92</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Intimidating</div>
				<span class="redRating center-block"><p class="ratingNumber">100</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Iconic</div>
				<span class="redRating center-block"><p class="ratingNumber">77</p></span>
			</div>

		</div>
		<div class="row rowPadding itemInList">
			<div class="col-xs-12 col-sm-2 col-md-2 itemBox">
				<div class="col-xs-2 col-sm-2 col-md-1">
					<p class="numberText">103</p>
				</div>
				<div class="col-xs-10 col-sm-10 col-md-11">
					<img class="img-circle center-block listItemImage"
						src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Villains/bane.jpg">
				</div>
			</div>
			<div class="col-xs-2 hidden-sm hidden-md hidden-lg"></div>
			<div class="col-xs-10 col-sm-2 col-md-2 itemBox">
				<h3 class="itemName">Bane Sauce</h3>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Overall</div>
				<span class="redRating center-block"><p class="ratingNumber">88</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Evil</div>
				<span class="redRating center-block"><p class="ratingNumber">85</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Fighting</div>
				<span class="redRating center-block"><p class="ratingNumber">91</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Charisma</div>
				<span class="redRating center-block"><p class="ratingNumber">92</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Intimidating</div>
				<span class="redRating center-block"><p class="ratingNumber">100</p></span>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 attributeBox">
				<div class="attributeName">Iconic</div>
				<span class="redRating center-block"><p class="ratingNumber">77</p></span>
			</div>

		</div>

		<div id="spinnerbody" class="container-fluid main-body">
			<div class="spinner hidden">
				<div class="rect1"></div>
				<div class="rect2"></div>
				<div class="rect3"></div>
				<div class="rect4"></div>
				<div class="rect5"></div>
			</div>
		</div>
	</div>
</body>
</html>