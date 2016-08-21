<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>Lyst</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="/Lyst/jquery.validate.min.js"></script>
<script src="/Lyst/homescripts.js"></script>
<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="/Lyst/style.css">
<link href='https://fonts.googleapis.com/css?family=Black+Ops+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Open+Sans:700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="/Lyst/vsStyle.css">
<link rel="stylesheet" type="text/css" href="/Lyst/listitemStyle.css">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 nopadding">
				<nav class="navbar navbar-default" role="navigation">
				<div class="row">
					<div class="hidden-xs col-sm-3 col-md-3 col-lg-3">
						<img class="imageDoBoys2" src="/Lyst/imageservlet/Drawing(5).png"></img>
					</div>
					<div class="col-xs-3 hidden-sm hidden-md hidden-lg">
						<img class="imageDoBoys" src="/Lyst/imageservlet/Drawing(9).png"></img>
					</div>
					<div class="col-xs-6 hidden-sm hidden-md hidden-lg">
						<img class="imageDoBoys2 center-block"
							src="/Lyst/imageservlet/Drawing(10).png"></img>
					</div>
					<div class="hidden-xs col-sm-6 col-md-6 col-lg-6 parent">
						<input class="searchbar child" placeholder="Search..." />
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
						<img class="imageDoBoys pull-right"
							src="/Lyst/imageservlet/Drawing(7).png"></img>
					</div>
					<div class="col-xs-12 hidden-sm hidden-md hidden-lg">
						<input class="searchbar" placeholder="Search..." />
					</div>
				</div>
				</nav>
			</div>
		</div>
	</div>
	<div class="container-fluid" id="main-body">
		<div class="row">
			<div class="col-xs-6 col-sm-6 col-md-4 col-lg-4">
				<h3 class="centerAlign"><c:out value="${sessionScope.currentItem.name}"></c:out></h3>
				<p class="centerAlign listText"><c:out value="${sessionScope.currentItem.belongingList}"></c:out></p>
			</div>
			<div class="hidden-xs hidden-sm col-md-4 col-lg-4">
				<img class="img-circle center-block detailImage"
					src="/Lyst/imageservlet/<c:out value="${sessionScope.currentItem.picPath}"/>">
			</div>
			<div class="col-xs-6 col-sm-6 col-md-4 col-lg-4">
				<button type="button" id="fullList"
					class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-list-alt"></span>
				</button>
				<h4 class="centerAlign">View List</h4>
			</div>
		</div>
		<div class="row">
			<div class="hidden-lg hidden-md col-sm-12 col-xs-12">
				<img class="img-circle center-block detailImage"
					src="/Lyst/imageservlet/<c:out value="${sessionScope.currentItem.picPath}"/>">
			</div>
		</div>
		<div class="row">
			<div class="col-xs-8 col-sm-8 col-md-8 col-lg-8"></div>
			<div class="hidden-xs col-sm-1 col-md-1 col-lg-1">
				<p class="centerAlign attributeText">Rating</p>
			</div>
			<div class="hidden-xs col-sm-1 col-md-1 col-lg-1">
				<p class="centerAlign attributeText">Ranking</p>
			</div>
		</div>
		<c:forEach var="i" items="${sessionScope.itemAttributes}">
		<div class="row rowPadding">
			<div class="hidden-xs col-sm-2 col-md-2 col-lg-2">
			</div>
			<div class="col-xs-4 col-sm-2 col-md-2 col-lg-2">
				<p class="centerAlign attributeText"><c:out value="${i.name}"/></p>
			</div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<div class="progress-bar green stripes">
					<span class="floatLeft" style="width: <c:out value="${i.rating}"/>%"></span>
				</div>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
				<p class="centerAlign ratingNumber"><c:out value="${i.rating}"/></p>
			</div>
			<div class="col-xs-2 col-sm-1 col-md-1 col-lg-1">
				<p class="centerAlign ratingNumber"><c:out value="${i.getRankingString()}"/></p>
			</div>
		</div>
</c:forEach>
	</div>
</body>
</html>
