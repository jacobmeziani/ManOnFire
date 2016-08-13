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
				<img id="thesauce" src="${sessionScope.leftItem.picPath}"
					class="img-responsive img-circle center-block vsImage"></img>
			</div>
			<div id="list">
					<h3 id="test2">
						<c:out value="${sessionScope.leftItem.belongingList}"></c:out>
					</h3>
			</div>
			<div id="rightPic">
				<img src="${sessionScope.rightItem.picPath}"
					class="img-responsive img-circle center-block vsImage"></img>
			</div>
			<div id="leftName">
				<h3>
					<c:out value="${sessionScope.leftItem.name}"></c:out>
				</h3>
			</div>
			<div id="rightName">
				<h3>
					<c:out value="${sessionScope.rightItem.name}"></c:out>
				</h3>
			</div>
			
			<div id="rightName2">
				<h3 class="pull-left">
					<c:out value="${sessionScope.rightItem.name}"></c:out>
				</h3>
			</div>
			
			<div id="leftName2">
				<h3 class="pull-right verticalAlign">
					<c:out value="${sessionScope.leftItem.name}"></c:out>
				</h3>
			</div>
</body>
</html>