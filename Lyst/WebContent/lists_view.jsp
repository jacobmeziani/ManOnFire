<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Lysts!!!</title>
 <link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	
<link rel = "stylesheet" href = "Stylesheets/lists.css">
<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>
	
<script src = "JS/lists.js"></script>	

<meta name="viewport" content="width=device-width, initial-scale=1">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

</head>
<body>
<!-- <script id = "item-template" type "text/template">
<div class="item-desktop" id="tester">
<div class="row">
	<div class="col-md-3">picpath</div>
	<div class="col-md-6">
		<div class="list-name-description">
		{{list_name}}	</div>
		<div class="current-leader-description">
			Current Leader: {{curent_leader}}
		</div>
	</div>
	<div class="col-md-3 category-name-description">Category: {{category}}</div>

</div>


</div>







</script>
 -->
	<button id = "clicker">CLICK ME!</button>
<div class="container-fluid" id="main-body">

<ul class="desk hidden" id="top-nav">
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item final"><a class="showmethemoney"> Everything </a></li>
<li class="menu-item children"><a class="showmethemoney testclass">Entertainment</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney">Up</a></li>
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item final all_class"><a class="showmethemoney"> All Entertainment</a></li>
<li class="menu-item children"><a class="showmethemoney">Movies</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney">Up</a></li>
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item list-item final all_class"><a class="showmethemoney all_class"> All Movies</a></li>
<li class="list-item menu-item final"><a database-id="6" class="showmethemoney">Best Formula 1 Tracks</a></li>
</ul></li>
<li class="menu-item children"><a class="showmethemoney">TV Shows</a>
<ul class="hidden" style="left: 200px; top: 60px;">
<li class="final upmenu hide"><a class="showmethemoney">Up</a></li>
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item list-item final all_class"><a class="showmethemoney all_class"> All TV Shows</a></li>
<li class="list-item menu-item final"><a database-id="3" class="showmethemoney">Actors</a></li>
<li class="list-item menu-item final"><a database-id="5" class="showmethemoney">Fast Food</a></li>
<li class="list-item menu-item final"><a database-id="4" class="showmethemoney">Coaches</a></li>
<li class="list-item menu-item final"><a database-id="2" class="showmethemoney">Rappers</a></li>
<li class="list-item menu-item final"><a database-id="1" class="showmethemoney">NFL Players</a></li>
</ul></li>
</ul></li>
<li class="menu-item children"><a class="showmethemoney">Sciences</a>
<ul class="hidden" style="left: 200px; top: 60px;">
<li class="final upmenu hide"><a class="showmethemoney">Up</a></li>
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item list-item final all_class"><a class="showmethemoney all_class"> All Sciences</a></li>
<li class="list-item menu-item final"><a database-id="12" class="showmethemoney">Best Animals on Earth</a></li>
</ul></li>
<li class="menu-item children"><a class="showmethemoney testclass">Sports</a>
<ul class="hidden" style="left: 200px; top: 90px;">
<li class="final upmenu hide"><a class="showmethemoney">Up</a></li>
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item final all_class"><a class="showmethemoney"> All Sports</a></li>
<li class="menu-item children"><a class="showmethemoney">American Sports</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney">Up</a></li>
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item list-item final all_class"><a class="showmethemoney all_class"> All American Sports</a></li>
<li class="list-item menu-item final"><a database-id="7" class="showmethemoney">Best College Stadiums</a></li>
</ul></li>
<li class="menu-item children"><a class="showmethemoney">Foreign Sports</a>
<ul class="hidden" style="left: 200px; top: 60px;">
<li class="final upmenu hide"><a class="showmethemoney">Up</a></li>
<li class="final closemenu hide"><a class="showmethemoney">Close</a></li>
<li class="menu-item list-item final all_class"><a class="showmethemoney all_class"> All Foreign Sports</a></li>
<li class="list-item menu-item final"><a database-id="13" class="showmethemoney">Most Amazing cities in the World</a></li>
<li class="list-item menu-item final"><a database-id="11" class="showmethemoney">null</a></li>
<li class="list-item menu-item final"><a database-id="9" class="showmethemoney">Best Action Shows on HBO</a></li>
<li class="list-item menu-item final"><a database-id="8" class="showmethemoney">Best Rap Albums from the 1990's</a></li>
<li class="list-item menu-item final"><a database-id="10" class="showmethemoney">Most Ratchet Cities in the World</a></li>
</ul></li>
</ul></li>
</ul>


</div>

	</div>
<div class="container-fluid">
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