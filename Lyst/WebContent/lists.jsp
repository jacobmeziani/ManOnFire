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

<script src="JS/lists.js"></script>
<link rel="stylesheet" href="Stylesheets/lists.css">
<link rel="stylesheet" href = "Stylesheets/MenuStyle.css">

<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

</head>
<body>
	<nav class="navbar navbar-default navbar-fixed-top">
	<div class="container">
		<div class="btn-group" role="group">
			<button type="button" id="categoryselect" class="btn">
				<span id="showCategory">Everything</span>
			</button>
			${sessionScope.CategoryHTML}
			<div class = "menubackground hidden"></div>
			<button type="button" id="filterbutton" class="btn"> <span class = "caret"></span></button>

		</div>
	</div>
	</nav>
	<div class="container-fluid main-body" id = "main-body">

		<!-- <div class="col-md-6">
			<div class="item-desktop">
				<img class="img-responsive" src="imageservlet/dicaps.png">
				<div class="show-info">
					<span>Current Category: Sports</span> <span>Current Leader:
						poster on curry</span>
				</div>
				<div class="showlist-desktop">Lebron's Best Dunks of 2015</div>

			</div>
		</div>
		<div class="col-md-6">
			<div class="item-desktop">
				<img class="img-responsive" src="imageservlet/dicaps.png">
				<div class="show-info">
					<span>Current Category: Sports</span> <span>Current Leader:
						poster on curry</span>
				</div>
				<div class="showlist-desktop">Lebron's Best Dunks of 2015</div>

			</div>
		</div>
		<div class="col-md-6">
			<div class="item-desktop">
				<img class="img-responsive" src="imageservlet/dicaps.png">
				<div class="show-info">
					<span>Current Category: Sports</span> <span>Current Leader:
						poster on curry</span>
				</div>
				<div class="showlist-desktop">Lebron's Best Dunks of 2015</div>

			</div>
		</div>
 -->	</div>
 <div class="container-fluid main-body" id = "spinnerbody">
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