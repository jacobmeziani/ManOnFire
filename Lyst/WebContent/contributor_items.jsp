<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LystDuel Contributor Import Tool</title>

<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">

<link rel = "stylesheet" href = "Stylesheets/contributor_items.css">

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
<script src = "JS/contributor_items.js"></script>
<script src = "JS/js.cookie.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

</head>
<body>
	<div class="container-fluid">
		<div class="well">
			<span class="pull-right"> <span
				class="glyphicon glyphicon-user"></span><span id="userid">${sessionScope.username}</span></span>
		</div>
	</div>
	<div class="container-fluid">
		<ul class="nav nav-tabs">
			<li role="presentation"><a href="contributor_home.jsp">Lyst generation</a></li>
			<li role="presentation" class = "active"><a href="contributor_items.jsp">Items Import</a></li>
		</ul>
	</div>

	<div class="container-fluid">
		<div id="mainbody">

			<div class="dropdown">
				<button class="btn btn-default dropdown-toggle" type="button"
					id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true"
					aria-expanded="true">
					Select List <span class="caret"></span>
				</button>
				<ul class="dropdown-menu" id = "listselect" aria-labelledby="dropdownMenu1">
				</ul>
			</div>
			<span>Current List: </span><span id = "selectedlist"></span>
			<input type = "hidden" id = "selectedid"> 
			<br />
				<a id="add_row" class="btn btn-default pull-left">Add Row</a><a id='delete_row' class="pull-left btn btn-default">Delete Row</a>
	<a id = "savebutton" class = "btn btn-success pull-right">Save Progress</a>
	<a id = "submitbutton" class = "btn btn-success pull-right">Submit!</a>
		

			<table class="table table-bordered table-hover" id="item_table">
				<thead>
					<tr>
						<th class="text-center">#</th>
						<th class="text-center">List Item</th>
						<th class = "text-center">Picture Path</th>
					</tr>
				</thead>
				<tbody>

				</tbody>
			</table>


		</div>

	</div>




</body>
</html>