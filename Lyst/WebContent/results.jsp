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
<script src="jquery.validate.min.js"></script>
<script src="homescripts.js"></script>
<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="style.css">
<link href='https://fonts.googleapis.com/css?family=Black+Ops+One'
	rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Open+Sans:700'
	rel='stylesheet' type='text/css'>
<link rel="stylesheet" type="text/css" href="vsStyle.css">
<link rel="stylesheet" type="text/css" href="resultsStyle.css">
<script src="april3.js"></script>
<script src="sliderScript.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
	<div class="container-fluid">
		<div class="row">
			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12 nopadding">
				<nav class="navbar navbar-default" role="navigation">
				<div class="row">
					<div class="hidden-xs col-sm-3 col-md-3 col-lg-3">
						<img class="imageDoBoys2" src="imageservlet/Drawing(5).png"></img>
					</div>
					<div class="col-xs-3 hidden-sm hidden-md hidden-lg">
						<img class="imageDoBoys" src="imageservlet/Drawing(9).png"></img>
					</div>
					<div class="col-xs-6 hidden-sm hidden-md hidden-lg">
						<img class="imageDoBoys2 center-block"
							src="imageservlet/Drawing(10).png"></img>
					</div>
					<div class="hidden-xs col-sm-6 col-md-6 col-lg-6 parent">
						<input class="searchbar child" placeholder="Search..." />
					</div>
					<div class="col-xs-3 col-sm-3 col-md-3 col-lg-3">
						<img class="imageDoBoys pull-right"
							src="imageservlet/Drawing(7).png"></img>
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
		<div id="contextButtons" class="row">
			<div id="topLeftButton"
				class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<a id="quitButton" type="button"
					class="btn btn-circle btn-xl center-block" href="home.jsp"> <span
					class="glyphicon glyphicon-eye-close"></span>
				</a>
			</div>
			<div class="hidden-xs hidden-sm col-md-2 col-lg-2"></div>
			<div id="topRightButton"
				class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<button type="button" id="fullList"
					class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-list-alt"></span>
				</button>
			</div>
		</div>
		<div id="labels" class="row row-buffer">
			<div class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<span><h4 class="centerAlign">New Showdown</h4></span><input
					type="hidden" id="CurrentCategory" value="Something"> <input
					type="hidden" id="isCategoryList" value="false">

			</div>
			<div class="hidden-xs hidden-sm col-md-2 col-lg-2"></div>
			<div class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<h4 class="centerAlign">Full List</h4>
			</div>
		</div>
		<div class="row">
			<div class=" col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<div class="positionRelativeContainer nopadding">
					<c:if test="${sessionScope.winningSide eq 'left'}">
					<span class="label label-success bannerOverlay">Winner</span>
					</c:if>
					<div class="numberCircle">
						<p id="ratingNumber">81</p>
					</div>
					<div id="leftPic">
						<img id="thesauce"
							class="img-responsive img-circle center-block resultImage"
							src="${sessionScope.leftItem.picPath}">
					</div>
				</div>
			</div>
			<div id="centerContent"
				class="hidden-xs hidden-sm col-md-2 col-lg-2 centerAlign">
				<div id="attributePhrases">
					<div class="row row-buffer">
						<h3 id="attributeTitle" class="centerAlign">
							<c:if test="${sessionScope.winningSide eq 'left'}">
								<c:out value="${sessionScope.leftItem.name}"></c:out> wins!
							</c:if>
							<c:if test="${sessionScope.winningSide eq 'right'}">
								<c:out value="${sessionScope.rightItem.name}"></c:out> wins!
							</c:if>
							<c:if test="${sessionScope.winningSide eq 'tie'}">
								Its a tie!
							</c:if>
						</h3>
					</div>
					<div class="row row-buffer">
						<button type="button" class="btn btn-primary">Scoring</button>
					</div>
				</div>
			</div>
			<div class="col-xs-6 col-sm-6 col-md-5 col-lg-5 nopadding">
				<div class="positionRelativeContainer nopadding">
				<c:if test="${sessionScope.winningSide eq 'right'}">
					<span class="label label-success bannerOverlay">Winner</span>
					</c:if>
					<div class="numberCircle bannerOverlay">
						<p id="ratingNumber">85</p>
					</div>
					<div id="rightPic">
						<img id="thesauce"
							class="img-responsive img-circle center-block resultImage"
							src="${sessionScope.rightItem.picPath}">
					</div>
				</div>
			</div>
		</div>
		<div class="row" id="bottomRow">
			<div id="leftName"
				class="hidden-xs hidden-sm col-md-5 col-lg-5 centerAlign">
				<h3>
					<c:out value="${sessionScope.leftItem.name}"></c:out>
				</h3>
			</div>
			<div id="leftName2"
				class="col-xs-6 col-sm-6 hidden-md hidden-lg centerAlign">
				<h3 class="verticalAlign">
					<c:out value="${sessionScope.leftItem.name}"></c:out>
				</h3>
			</div>
			<div class="hidden-xs hidden-sm col-md-2 col-lg-2"></div>
			<div id="rightName"
				class="hidden-xs hidden-sm col-md-5 col-lg-5 centerAlign">
				<h3>
					<c:out value="${sessionScope.rightItem.name}"></c:out>
				</h3>
			</div>
			<div id="rightName2"
				class="col-xs-6 col-sm-6 hidden-md hidden-lg centerAlign">
				<h3>
					<c:out value="${sessionScope.rightItem.name}"></c:out>
				</h3>
			</div>
		</div>

		<div class="container-fluid">

			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tile green center-block">
							<div class="row nopadding centerAlign">
								<h3 class="nopadding">Shooting</h3>
							</div>
							<div class="row nopadding">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Lebron James</p>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Steph Curry</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">71</h3>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">99</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										World <span class="floatRight" style="width: 0%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										<span class="floatLeft" style="width: 27%"></span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										You <span class="floatRight" style="width: 0%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										<span class="floatLeft" style="width: 80%"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tile green center-block">
							<div class="row nopadding centerAlign">
								<h3 class="nopadding">Passing</h3>
							</div>
							<div class="row nopadding">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Lebron James</p>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Steph Curry</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">98</h3>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">88</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										<span class="floatRight" style="width: 10%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										World<span class="floatLeft" style="width: 0%"></span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										<span class="floatRight" style="width: 40%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										You<span class="floatLeft" style="width: 0%"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tile red center-block">
							<div class="row nopadding centerAlign">
								<h3 class="nopadding">IQ</h3>
							</div>
							<div class="row nopadding">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Lebron James</p>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Steph Curry</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">95</h3>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">96</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										World <span class="floatRight" style="width: 0%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										<span class="floatLeft" style="width: 1%"></span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										<span class="floatRight" style="width: 20%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										You<span class="floatLeft" style="width: 0%"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tile green center-block">
							<div class="row nopadding centerAlign">
								<h3 class="nopadding">Handles</h3>
							</div>
							<div class="row nopadding">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Lebron James</p>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Steph Curry</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">95</h3>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">95</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										World <span class="floatRight" style="width: 0%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										<span class="floatLeft" style="width: 0%"></span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										You <span class="floatRight" style="width: 0%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										<span class="floatLeft" style="width: 20%"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

			<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
				<div class="row">
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<div class="tile green center-block">
							<div class="row nopadding centerAlign">
								<h3 class="nopadding">Rebounding</h3>
							</div>
							<div class="row nopadding">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Lebron James</p>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<p class="itemName">Steph Curry</p>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">79</h3>
								</div>
								<div class="col-xs-6 col-sm-6 col-md-6 col-lg-6">
									<h3 class="title">33</h3>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										<span class="floatRight" style="width: 48%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar blue stripes">
										World<span class="floatLeft" style="width: 0%"></span>
									</div>
								</div>
							</div>
							<div class="row">
								<div class="col-xs-1 col-sm-1 col-md-1 col-lg-1 nopadding"></div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										<span class="floatRight" style="width: 60%"></span>
									</div>
								</div>
								<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5 nopadding">
									<div class="progress-bar pink stripes">
										You <span class="floatLeft" style="width: 0%"></span>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>

		</div>
	</div>
</body>
</html>
