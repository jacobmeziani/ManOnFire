<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>LystHub</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="homescripts.js"></script>
<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css">
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jqueryui-touch-punch/0.2.3/jquery.ui.touch-punch.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="style.css">
<link href='https://fonts.googleapis.com/css?family=Black+Ops+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Open+Sans:700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="vsStylePurple.css">
<link rel="stylesheet" type="text/css" href="Stylesheets/MenuStyle.css">
<link rel="stylesheet" type="text/css" href="sliderStyle.css">
<script src="april3.js"></script>
<script src="sliderScript.js"></script>
<script src="menuScript.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
	<div class="container-fluid topNav">
		<div class="row rowPadding well well-sm">
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2">
				<a href="/bro/lists" data-toggle="tooltip" data-placement="bottom" title="View Lists"><img
					class="img-responsive topButton"
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
	<div class="container-fluid" id="main-body">
		<div id="contextButtons" class="row">
			<div id="topLeftButton"
				class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<button id="clicker" type="button"
					class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-th"></span>
				</button>
				<div class=" col-xs-5 col-sm-5 col-md-5 col-lg-5"></div>
						<div class=" col-xs-2 col-sm-2 col-md-2 col-lg-2">
										${requestScope.CategoryHTML}
										</div>
			</div>
			<div id="middleButton"
				class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<button type="button" id="randomButton"
					class="btn btn-circle btn-xl center-block" onclick="this.blur();">
					<span class="glyphicon glyphicon-repeat"></span>
				</button>
			</div>
			<div id="topRightButton"
				class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<button type="button" id="vsButton"
					class="btn btn-circle btn-xl center-block" onclick="this.blur();">
					<span class="glyphicon glyphicon-sunglasses"></span>
				</button>
			</div>
		</div>
		<div id="labels" class="row row-buffer">
			<div class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<span><h4 id="showCategory" class="centerAlign">${requestScope.categoryName}</h4></span>
				<input type="hidden" id="currentCategory"
					value="${requestScope.currentCategory}"> <input
					type="hidden" id="isCategoryList" value="${requestScope.isList}">

			</div>
			<div class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<h4 class="centerAlign">Random</h4>
			</div>
			<div class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<h4 class="centerAlign">Start!</h4>
			</div>
		</div>
		<div class="row">
			<div id="listNameContent"
				class="col-xs-12 col-sm-12 col-md-12 col-lg-12 centerAlign">
				<div id="list">
					<h3 id="listNameH3">
						<c:out value="${requestScope.leftItem.belongingList}"></c:out>
					</h3>
				</div>
			</div>
			</div>
		<div class="row">
			<div class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<div id="leftPic" class="testing">
					<img class="img-responsive img-circle center-block vsImage "
						src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/<c:out value="${requestScope.leftItem.picPath}"/>"
						onload="imgLoaded(this)">
				</div>
			</div>
			<div id="centerContent"
				class="hidden-xs hidden-sm col-md-2 col-lg-2 centerAlign">
				<div class="row">
						<h1>VS</h1>
				</div>
			</div>
			<div class="col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<div id="rightPic" class="testing">
					<img class="img-responsive img-circle center-block vsImage"
						src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/<c:out value="${requestScope.rightItem.picPath}"/>"
						onload="imgLoaded(this)">
				</div>
			</div>
		</div>
		<div class="row" id="bottomRow">
			<div id="leftName"
				class="hidden-xs hidden-sm col-md-5 col-lg-5 centerAlign">
				<h3 id="leftNameH3">
					<c:out value="${requestScope.leftItem.name}"></c:out>
				</h3>
			</div>
			<div id="leftName2" class="col-xs-4 col-sm-4 hidden-md hidden-lg">
				<h3 class="pull-right verticalAlign">
					<c:out value="${requestScope.leftItem.name}"></c:out>
				</h3>
			</div>
			<div id="miniVs" class="col-xs-4 col-sm-4 hidden-md hidden-lg centerAlign">
					<h1>VS</h1>
			</div>
			<div class="hidden-xs hidden-sm col-md-2 col-lg-2"></div>
			<div id="rightName"
				class="hidden-xs hidden-sm col-md-5 col-lg-5 centerAlign">
				<h3 id ="rightNameH3">
					<c:out value="${requestScope.rightItem.name}"></c:out>
				</h3>
			</div>
			<div id="rightName2" class="col-xs-4 col-sm-4 hidden-md hidden-lg">
				<h3 class="pull-left">
					<c:out value="${requestScope.rightItem.name}"></c:out>
				</h3>
			</div>
		</div>
	</div>
</body>
</html>
