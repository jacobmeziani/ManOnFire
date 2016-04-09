<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <title>top bar testing</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
  <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
  <link rel="stylesheet" type="text/css" href="style.css">
</head>

<body>

<div class="container-fluid">
	<div class ="well">
		<div class ="row">
			<div class= "col-md-2">
				<img src="images/logo.png" class=img-thumbnail" alt="logo" width="71" height="40">
			</div>
			<div class= "col-md-6">
				<div class = "input-group">
					<input type="text" class="form-control" placeholder="Search..." aria-label = "search_term">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">All<span class="caret"></span></button>
						<ul class="dropdown-menu dropdown-menu-right">
							<li><a href="...">Categories</a></li>
							<li><a href="...">Lysts</a></li>
							<li><a href="...">Items</a></li>
						</ul>
					</div> 				
				</div>
			</div>
			<div class = "col-md-2">
					<button type="button" class="btn btn-default dropdown-toggle" aria-label="categories" ariahaspopup="true" data-toggle="dropdown">
  						<span class="glyphicon glyphicon-th" aria-hidden="true"></span>
  						<span class="carat"></span>
					</button>
				<div class="dropdown-menu">
					<ul class="col-md-5">
						<li><button type="button" class="btn btn-default">Sports</button></li>
						<li><button type="button" class="btn btn-default">Entertainment</button></li>
					</ul>
					<ul class="col-md-5">
						<li><button type="button" class="btn btn-default">Nudes</button></li>
						<li><button type="button" class="btn btn-default">Pornos</button></li>
					</ul>
				</div>
			</div>
			<div class= "col-md-2">
			</div>
	
		</div>
	</div>
</div>
 



<body>
	<div class="container-fluid">
		<div class = "row">
			<div class = "col-md-2">
					<div class=navbar-header">
			<a class="navbar-brand" href="index.jsp"> <img alt="logo" src="images/logo.png" height="30px"></a>
			</div>
			</div>
			<div class= "col-md-6">
			<div class = "navbar-form" role = "search"> -->
			<div class = "input-group">
					<input type="text" class="form-control" placeholder="Search..." aria-label = "search_term">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default ">Submit</button>
					</div> 				
				</div>
			</div>
			</div>
			<div class="col-md-2">
				<button type="button" class="btn btn-default navbar-btn">Show Lysts</button>
			</div>
		</div>
	</div>
</nav>
<!-- 		
		<div class=navbar-header">
			<a class="navbar-brand" href="/"><img alt="logo" src="images/logo.png" height="30px"></a>
		</div>
		<form class = "navbar-form" role="search">
		
			<div class="form-group">
				<input type="text" class="form-control wider_search" placeholder="Search for lysts, items, or categories..." name="search_term">
			</div>
			<button type ="submit" class="btn btn-default">Search!</button>
		</form>
		 -->
		
		
		
	












	</div>
</nav>






<nav class="navbar navbar-default">
	<div class = "container">
		<div class ="row">
			<div class= "col-md-2">
				<div class=navbar-header">
					<a class="navbar-brand" href="/"><img alt="logo" src="images/logo.png" height="30px"></a>		
			</div>
			</div>
			<div class= "col-md-6">
				<div class = "input-group">
					<input type="text" class="form-control" placeholder="Search..." aria-label = "search_term">
					<div class="input-group-btn">
						<button type="button" class="btn btn-default ">Submit</button>
					</div> 				
				</div>
			</div>
			<div class = "col-md-2">
					<button type="button" class="btn btn-default dropdown-toggle" aria-label="categories" ariahaspopup="true" data-toggle="dropdown">
  						<span class="glyphicon glyphicon-th" aria-hidden="true"></span>
  						<span class="carat"></span>
					</button>
				<div class="dropdown-menu">
					<ul class="col-md-5">
						<li><button type="button" class="btn btn-default">Sports</button></li>
						<li><button type="button" class="btn btn-default">Entertainment</button></li>
					</ul>
					<ul class="col-md-5">
						<li><button type="button" class="btn btn-default">Nudes</button></li>
						<li><button type="button" class="btn btn-default">Pornos</button></li>
					</ul>
				</div>
			</div>
			<div class= "col-md-2">
				<button type="button" class="btn btn-default pull-right">About us!</button>
			</div>
		</div>
	</div>
</nav>



</body>
</html>