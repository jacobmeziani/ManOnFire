<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
</head>
<body>
			<div id="leftPic">
				<img id="thesauce" src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/<c:out value="${requestScope.leftItem.picPath}"/>"
					class="img-responsive img-circle center-block vsImage" onload="imgLoaded(this)"></img>
			</div>
			<div id="list">
					<h3 id="listNameH3">
						<c:out value="${requestScope.leftItem.belongingList}"></c:out>
					</h3>
			</div>
			<div id="rightPic">
				<img src="https://s3-us-west-2.amazonaws.com/elasticbeanstalk-us-west-2-119295481920/Images/<c:out value="${requestScope.rightItem.picPath}"/>"
					class="img-responsive img-circle center-block vsImage" onload="imgLoaded(this)"></img>
			</div>
			<div id="leftName">
				<h3 id="leftNameH3">
					<c:out value="${requestScope.leftItem.name}"></c:out>
				</h3>
			</div>
			<div id="rightName">
				<h3 id="rightNameH3">
					<c:out value="${requestScope.rightItem.name}"></c:out>
				</h3>
			</div>
			
			<div id="rightName2">
				<h3 class="pull-left">
					<c:out value="${requestScope.rightItem.name}"></c:out>
				</h3>
			</div>
			
			<div id="leftName2">
				<h3 class="pull-right verticalAlign">
					<c:out value="${requestScope.leftItem.name}"></c:out>
				</h3>
			</div>
</body>
</html>