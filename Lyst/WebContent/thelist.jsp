<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<title><c:out value="${requestScope.listName}" /></title>


<link
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
	rel="stylesheet">


<!-- jQuery library -->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<!-- Latest compiled JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js"></script>

<script src="https://code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<script src="/JS/lyst_page.js"></script>
<link
	href="https://code.jquery.com/ui/1.10.4/themes/flick/jquery-ui.css"
	rel="stylesheet">
<link href="/thelistStyle.css" rel="stylesheet">
<link href="/vsStylePurple.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="/style.css">
<meta name="viewport" content="width=device-width, initial-scale=1">
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
	<div class="container-fluid">
		<div class="row rowPadding well well-sm">
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2">
				<a href="/bro/lists" data-toggle="tooltip" data-placement="bottom"
					title="View Lists"><img class="img-responsive topButton"
					src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/listLogo.png"></a>
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-4 col-sm-4 col-md-4 col-lg-4">
				<img class="theLogo center-block"
					src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/logo.png">
			</div>
			<div class="col-xs-1 col-sm-2 col-md-2 col-lg-2"></div>
			<div class="col-xs-3 col-sm-2 col-md-2 col-lg-2 pull-right">
				<a href="/" type="button" id="rateMenuButton" data-toggle="tooltip"
					data-placement="bottom" title="Rate!"><img
					class="img-responsive topButton pull-right"
					src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/Icons/vsLogo.png">
				</a>
			</div>

		</div>
	</div>
	<div class="container-fluid">
		<div class="row rowBuffs">
			<div class="col-xs-4 col-sm-4 col-md-4">
				<button id="newShowdownButton" type="button"
					class="btn btn-circle btn-xl center-block">
					<span class="glyphicon glyphicon-tasks"></span>
				</button>
				<h4 class="centerAlign">
					Rate
					<c:out value="${requestScope.listName}" />
				</h4>
			</div>
			<div class="col-xs-6 col-sm-4 col-md-4">
				<p class="listTitle centerAlign">
					<c:out value="${requestScope.listName}" />
				</p>
				<p id="listDescriptor" class="centerAlign"></p>
			</div>
			<div class="col-xs-2 col-sm-4 col-md-4"></div>
		</div>
		<input type="hidden" id="listId" value="${requestScope.listId}">



		<div id="thatList">
			<c:forEach var="i" items="${requestScope.lystItems}" varStatus="loop">
			<c:choose>
			<c:when test="${loop.index%2==0}">
			<div class="row rowPadding itemInList">
			</c:when>
			<c:otherwise>
			<div class="row rowPadding itemInList grayBackground">
			</c:otherwise>
			</c:choose>
			<c:if test="${i.attributes.size() <= 5 }"><div class="hidden-xs col-sm-1 col-md-1"></div></c:if>
			<c:if test="${i.attributes.size() <= 4 }"><div class="hidden-xs col-sm-1 col-md-1"></div></c:if>
			<c:if test="${i.attributes.size() <= 3 }"><div class="hidden-xs col-sm-1 col-md-1"></div></c:if>
					<div class="col-xs-12 col-sm-2 col-md-2 itemBox">
						<div class="col-xs-2 col-sm-2 col-md-1">
							<p class="numberText">
								<c:out value="${i.getSelectedAttributeRanking()}" />
							</p>
						</div>
						<div class="col-xs-10 col-sm-10 col-md-11">
							<a
								href="/bro/<c:out value="${i.getListUrl()}" />/<c:out value="${i.getNameUrl()}" />"><img
								class="img-circle center-block listItemImage"
								src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/<c:out value="${i.picPath}" />">
							</a>
						</div>
					</div>
					<div class="col-xs-2 hidden-sm hidden-md hidden-lg"></div>
					<div class="col-xs-10 col-sm-2 col-md-2 itemBox">
						<a
							href="/bro/<c:out value="${i.getListUrl()}" />/<c:out value="${i.getNameUrl()}" />">
							<h3 class="itemName">
								<c:out value="${i.name}" />
							</h3>
						</a>
					</div>
					<c:choose>
						<c:when test="${requestScope.toggled eq 'false'}">
							<c:forEach var="j" items="${i.attributes}">
								<c:if test="${j.attributeNumber == 6 && i.attributes.size() !=7}">
									<div class="hidden-xs col-sm-1 col-md-1">
										<button type="button"
					class="btn btn-circle btn-xl toggleAttButton center-block" onclick="this.blur();">
					<span class="glyphicon glyphicon-menu-right"></span>
				</button>
									</div>
								</c:if>
								<c:choose>
									<c:when test="${j.attributeNumber < 6 || i.attributes.size() ==7}">
										<div
											id="attributeNumber<c:out value="${j.attributeNumber}" />"
											class="hidden-xs col-sm-1 col-md-1 attributeBox">
											<div id="attributeNameText" class="attributeName">
												<c:out value="${j.name}" />
											</div>
											<span class="redRating center-block"><p
													class="ratingNumber">
													<c:out value="${j.rating}" />
												</p></span>
										</div>
									</c:when>
									<c:otherwise>
										<div
											id="attributeNumber<c:out value="${j.attributeNumber}" />"
											class="hidden-xs col-sm-1 col-md-1 attributeBox hidden">
											<div id="attributeNameText" class="attributeName">
												<c:out value="${j.name}" />
											</div>
											<span class="redRating center-block"><p
													class="ratingNumber">
													<c:out value="${j.rating}" />
												</p></span>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:when>

						<c:otherwise>
							<c:forEach var="j" items="${i.attributes}">
								<c:if test="${j.attributeNumber == 6}">
									<div class="hidden-xs col-sm-1 col-md-1">
										<button type="button" id="vsButton"
					class="btn btn-circle btn-xl toggleAttButton center-block" onclick="this.blur();">
					<span class="glyphicon glyphicon-menu-left"></span>
				</button>
									</div>
								</c:if>
								<c:choose>
									<c:when test="${j.attributeNumber < 6}">
										<div
											id="attributeNumber<c:out value="${j.attributeNumber}" />"
											class="hidden-xs col-sm-1 col-md-1 attributeBox hidden">
											<div id="attributeNameText" class="attributeName">
												<c:out value="${j.name}" />
											</div>
											<span class="redRating center-block"><p
													class="ratingNumber">
													<c:out value="${j.rating}" />
												</p></span>
										</div>
									</c:when>
									<c:otherwise>
										<div
											id="attributeNumber<c:out value="${j.attributeNumber}" />"
											class="hidden-xs col-sm-1 col-md-1 attributeBox">
											<div id="attributeNameText" class="attributeName">
												<c:out value="${j.name}" />
											</div>
											<span class="redRating center-block"><p
													class="ratingNumber">
													<c:out value="${j.rating}" />
												</p></span>
										</div>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>

					</c:choose>
						<c:if test="${i.attributes.size() <= 4 }"><div class="col-xs-2 hidden-sm hidden-md hidden-lg"></div></c:if>
			<c:if test="${i.attributes.size() <= 3 }"><div class="col-xs-2 hidden-sm hidden-md hidden-lg"></div></c:if>
					<c:forEach var="j" items="${i.attributes}">
					<div id="attributeNumber<c:out value="${j.attributeNumber}" />"
						class="col-xs-2 hidden-sm hidden-md hidden-lg attributeBoxMini">
						<div id="attributeNameText" class="attributeName">
							<c:out value="${j.name}" />
						</div>
						<span class="redRating center-block"><p
								class="ratingNumber">
								<c:out value="${j.rating}" />
							</p></span>
					</div>
					</c:forEach>
				</div>
			</c:forEach>
		</div>

		<div id="spinnerbody" class="container-fluid main-body">
			<div class="spinner hidden">
				<div class="rect1"></div>
				<div class="rect2"></div>
				<div class="rect3"></div>
				<div class="rect4"></div>
				<div class="rect5"></div>
			</div>
		</div>
	</div>
</body>
</html>