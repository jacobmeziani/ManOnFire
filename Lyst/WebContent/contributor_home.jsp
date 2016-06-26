<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<title>LystDuel Contributor Import Tool</title>

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
<script src = "JS/contributor_import.js"></script>
<script src = "JS/js.cookie.js"></script>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
#mainbody {
	width: 100%;
	border-left: 1px solid;
	border-right: 1px solid;
	border-bottom: 1px solid;
	border-color: #ddd;
	padding: 10px;
	padding-bottom: 40px;
}

</style>
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
			<li role="presentation" class="active"><a href="#">Lyst generation</a></li>
			<li role="presentation"><a href="contributor_items.jsp">Items Import</a></li>
		</ul>
	</div>

	<div class="container-fluid">
		<div id="mainbody">
			<h1>Welcome to LystDuel's Contributor Page</h1>
			<h3>Please fill in the information below. Click submit to finalize the list</h3>
			<h3><i>*Switching between tabs will erase the information on this page</i></h3>
			<br/>
			<div class = "col-xs-6">
				<label>List Name</label>
				<input type = "text" class = "form-control" name = "list_name" id = "list_name" placeholder = "Sloths in Movies">
			</div>
			<br/>
			<br/>
			<br/>
			<div class="col-xs-12"></div>
			

				<table class = "table table-bordered table-hover" id = "att_table">
					<thead>
						<tr>
							<th class = "text-center"> #
							</th>
							<th class = "text-center">Attribute</th>
						</tr>
					</thead>
					<tbody>
						<tr id = "entry1">
							<td>1</td>
							<td>
								<input type = "text" name = "attribute1" id = "attribute1" class= "form-control" placeholder = "Attribute">
							</td>
						</tr>
					</tbody>
				</table>
			
	<a id="add_row" class="btn btn-default pull-left">Add Row</a><a id='delete_row' class="pull-left btn btn-default">Delete Row</a>
	<a id = "submitbutton" class = "btn btn-success pull-right">Submit!</a>
		
		</div>


	</div>



</body>

</html>