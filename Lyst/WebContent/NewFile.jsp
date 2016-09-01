<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lysts!!!</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">


<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>


<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css">
<link rel="stylesheet" type="text/css" href="style.css">
<link rel="stylesheet" type="text/css" href="vsStyle.css">
<script src="testScripts.js"></script>
<link rel="stylesheet" href="testCss.css">
<link rel="stylesheet" href = "Stylesheets/MenuStyle.css">

<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

</head>
<body>
	<div class="container-fluid">
		<div class="row rowPadding well well-sm">
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2">
				<a data-toggle="tooltip" data-placement="bottom" title="View Lists"
					><img
					class="img-responsive topButton"
					src="/Lyst/imageservlet/Villains/bowser.png"></a>
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
				<img class="theLogo center-block" src="/Lyst/imageservlet/Icons/logo.png">
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2 pull-right">
				<a id="rateMenuButton" data-toggle="tooltip" data-placement="bottom" title="Rate!"
					><img
					class="img-responsive topButton pull-right"
					src="/Lyst/imageservlet/Villains/ganondorf.png"> </a>
			</div>

		</div>
	</div>
<!-- 	<nav class="navbar navbar-default"> -->
<!-- 	<div class="container"> -->
<!-- 		<div class="btn-group" role="group"> -->
<!-- 			<button type="button" id="categoryselect" class="btn"> -->
<!-- 				<span id="showCategory">Everything</span> -->
<!-- 			</button> -->
<%-- 			${sessionScope.CategoryHTML} --%>
<!-- 			<div class = "menubackground hidden"></div> -->
<!-- 			<button type="button" id="filterbutton" class="btn"> <span class = "caret"></span></button> -->

<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	</nav> -->
<div id="topLeftButton" class=" col-xs-12 col-sm-12 col-md-12 col-lg-12 nopadding">
				<button id="categoryselect" type="button"
					class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-th"></span></button>
					<span id="showCategory">Everything</span>
			${sessionScope.CategoryHTML}
			</div>
	<div class="container-fluid main-body" id = "main-body">
</div><div class="container-fluid main-body" id = "spinnerbody">
 
		<div class="spinner">
			<div class="rect1"></div>
			<div class="rect2"></div>
			<div class="rect3"></div>
			<div class="rect4"></div>
			<div class="rect5"></div>
		</div>
	</div>



</body>
</html>