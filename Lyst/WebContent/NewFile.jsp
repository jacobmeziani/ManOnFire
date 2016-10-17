<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="ISO-8859-1">
<title>LystHub</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<!-- jQuery library -->
<script async="" src="https://www.google-analytics.com/analytics.js"></script><script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script src="react/react.js"></script>
    <script src="react/react-dom.js"></script>
    <script src="https://unpkg.com/babel-core@5.8.38/browser.min.js"></script>
<script src="https://code.jquery.com/jquery-2.1.1.js"></script>
<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<link rel="stylesheet" href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css">
	<script type="text/javascript" src="//cdnjs.cloudflare.com/ajax/libs/jqueryui-touch-punch/0.2.3/jquery.ui.touch-punch.min.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" type="text/css" href="Stylesheets/navbar.css">
<link href="https://fonts.googleapis.com/css?family=Black+Ops+One" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:700" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="Stylesheets/theme.css">
<link rel="stylesheet" type="text/css" href="Stylesheets/categorymenu.css">
<script src="JS/menu-builder.js"></script>
<script src="JS/slider.js"></script>
<link rel="stylesheet" href="Stylesheets/test.css">
<link rel="stylesheet" href="Stylesheets/folding-cube.css">
<script src="JS/test.js"></script>
<script src="JS/test2.js" type="text/babel"></script>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','https://www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-83915306-1', 'auto');
  ga('send', 'pageview');

</script>
</head>
<body>
<div class= "container-fluid">
	<div class="container-fluid topNav">
		<div class="row rowPadding well well-sm">
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2">
				<a href="/bro/lists" data-toggle="tooltip" data-placement="bottom" title="View Lysts"><img class="img-responsive topButton" src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/listLogo.png"></a>
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
				<img class="theLogo center-block" src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/logo.png">
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2 pull-right">
				<a href="/" type="button" id="rateMenuButton" data-toggle="tooltip" data-placement="bottom" title="Rate!"><img class="img-responsive topButton pull-right" src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/vsLogo.png">
				</a>
			</div>

		</div>
	</div>
	<div class="container-fluid" id="main-body">
		<div id="contextButtons" class="row">
			<div id="topLeftButton" class=" hidden-xs col-sm-6 col-md-6 col-lg-6 nopadding">
				<button id="clicker" type="button" class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-th"></span>
				</button>
				<div class=" col-xs-5 col-sm-5 col-md-5 col-lg-5"></div>
						<div class=" col-xs-2 col-sm-2 col-md-2 col-lg-2">
										<ul id="top-nav" class="desk center-block hidden">
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item final"><a class="showmethemoney finalNode">Everything</a></li>
<li class="menu-item children"><a class="showmethemoney testclass">Business</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item final all_class"><a class="showmethemoney finalNode"> All Business</a></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">Career</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All Career</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="2">Jobs</a></li>
</ul></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">Companies</a>
<ul class="hidden" style="left: 200px; top: 60px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All Companies</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="8">Airlines</a></li>
</ul></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">People</a>
<ul class="hidden" style="left: 200px; top: 90px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All People</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="4">Fashion Designers</a></li>
</ul></li>
</ul></li>
<li class="menu-item children"><a class="showmethemoney testclass">Entertainment</a>
<ul class="hidden" style="left: 200px; top: 60px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item final all_class"><a class="showmethemoney finalNode"> All Entertainment</a></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">Characters</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All Characters</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="0">Villains</a></li>
</ul></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">Movies</a>
<ul class="hidden" style="left: 200px; top: 60px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All Movies</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="1">Actors</a></li>
</ul></li>
</ul></li>
<li class="menu-item children"><a class="showmethemoney testclass">Living</a>
<ul class="hidden" style="left: 200px; top: 90px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item final all_class"><a class="showmethemoney finalNode"> All Living</a></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">Food and Drink</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All Food and Drink</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="6">Food</a></li>
</ul></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">Pets</a>
<ul class="hidden" style="left: 200px; top: 60px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All Pets</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="5">Dogs</a></li>
</ul></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">Places</a>
<ul class="hidden" style="left: 200px; top: 90px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All Places</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="9">Universities</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="3">Cities</a></li>
</ul></li>
</ul></li>
<li class="menu-item children"><a class="showmethemoney testclass">Sports</a>
<ul class="hidden" style="left: 200px; top: 120px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item final all_class"><a class="showmethemoney finalNode"> All Sports</a></li>
<li class="menu-item children finalcategory"><a class="showmethemoney ">NBA</a>
<ul class="hidden" style="left: 200px; top: 30px;">
<li class="final upmenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-arrow-left"></span>Back</a></li>
<li class="final closemenu hide"><a class="showmethemoney"><span class="glyphicon glyphicon-remove"></span>Close</a></li>
<li class="menu-item list-item final finalcategory all_class"><a class="showmethemoney all_class finalNode"> All NBA</a></li>
<li class="list-item menu-item final"><a class="showmethemoney finalNode" database-id="7">NBA Players</a></li>
</ul></li>
</ul></li>
</ul>

										</div>
			</div>
			<div id="topRightButton" class=" hidden-xs col-sm-6 col-md-6 col-lg-6 nopadding">
						<button type="button" id="randomButton" class="btn btn-circle btn-xl center-block" onclick="this.blur();">
					<span class="glyphicon glyphicon-repeat"></span>
				</button>
			</div>
		</div>
		<div id="labels" class="row row-buffer">
			<div class=" hidden-xs col-sm-6 col-md-6 col-lg-6 nopadding">
				<span><h4 id="showCategory" class="centerAlign">Everything</h4></span>
				<input id="currentCategory" value="Everything" type="hidden"> <input id="isCategoryList" value="false" type="hidden">

			</div>
			<div class=" hidden-xs col-sm-6 col-md-6 col-lg-6 nopadding">
				<h4 class="centerAlign">Random</h4>
			</div>
		</div>
		<div id="leggo"> 
<!-- 				<div className="sk-folding-cube"> -->
<!-- 	    		  <div className="sk-cube1 sk-cube"></div> -->
<!-- 	    		  <div className="sk-cube2 sk-cube"></div> -->
<!-- 	    		  <div className="sk-cube4 sk-cube"></div> -->
<!-- 	    		  <div className="sk-cube3 sk-cube"></div> -->
<!-- 	    		</div></div> -->
 </div>
<!-- 			<div class="col-xs-12 col-sm-6 col-md-4"> -->
<!-- 		    		<div class= "centerSauce"> -->
<!-- 		    			<div class="attributeTitle centerAlign"> -->
<!-- 		    				Dunking -->
<!-- 		    			</div> -->
<!-- 						<div class="rating-gauge-padding"> -->
<!-- 							<div id="gauge-Dunking" class="gauge-class"> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="slider-padding"> -->
<!-- 						<div class="col-xs-6 col-sm-6 col-md-6"> -->
<!-- 						<img class="img-responsive img-circle" style="margin-left:-60px; margin-top:-20px; width:40px; height:40px;" src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/NbaPlayers/kevinlove.jpg"/> -->
<!-- 						</div> -->
<!-- 						<div class="col-xs-6 col-sm-6 col-md-6"> -->
<!-- 						<img class="img-responsive img-circle" style="margin-left:100px; margin-top:-20px; ; width:40px; height:40px;" src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/NbaPlayers/rudygobert.jpg"> -->
<!-- 						</div> -->
<!-- 							<div class="slider" id="circles-slider" data-leftItemName="Kevin Love" data-rightItemName="Rudy Gobert" data-attributeName="Dunking"> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
	<div class="col-xs-12 hidden-sm hidden-md hidden-lg">
<div class="navbar navbar-default navbar-fixed-bottom">
		<div id="contextButtons" class="row">
			<div id="topLeftButton" class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<button id="clicker" type="button" class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-th"></span>
				</button>
				</div>
			<div id="middleButton" class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<button type="button" id="randomButton" class="btn btn-circle btn-xl center-block" onclick="this.blur();">
					<span class="glyphicon glyphicon-repeat"></span>
				</button>
			</div>
			<div id="topRightButton" class=" col-xs-4 col-sm-4 col-md-4 col-lg-4 nopadding">
				<button type="button" id="vsButton" class="btn btn-circle btn-xl center-block" onclick="this.blur();">
					<span class="glyphicon glyphicon-globe"></span>
				</button>
			</div>
		</div>
</div>
</div>
</div>
</body>

</html>