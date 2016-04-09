<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="jquery.validate.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	<link rel="stylesheet" type="text/css" href="testCss.css">
	<script src="testScripts.js"></script>
	<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">
</head>
<body>
	<div class="container">
	<div id="myCard" class="flip-container">
	<div class="flipper">
		<div class="front">
			<img id="thesauce" class="img-responsive center-block" src="imageservlet/dicaps.png">
		</div>
		<div class="back">
			<img id="thesauce" class="img-responsive center-block" src="imageservlet/leb.png">
		</div>
	</div>
</div>
<a type="button" id="randomButton"
					class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-repeat"></span>
				</a>
</div>
</body>
</html>