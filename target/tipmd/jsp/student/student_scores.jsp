<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>学生成绩</title>

    <!-- Bootstrap core CSS -->
    <link href="http://cdn.bootcss.com/bootstrap/3.3.2/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <link href="<c:url value="/static/css/dashboard.css"/>" rel="stylesheet">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    

</head>
<body>
	
	<div class="container">
	<div align="center" style="font-size: 16px;">
		姓名:&nbsp;&nbsp;&nbsp;${student.name}&nbsp;&nbsp;&nbsp;
		性别:&nbsp;&nbsp;&nbsp;
		<c:choose>
          <c:when test="${student.sex == 'Male'}">男</c:when>
          <c:when test="${student.sex == 'Female'}">女</c:when>
          <c:otherwise>未知</c:otherwise>
         </c:choose>&nbsp;&nbsp;&nbsp;
		生日:&nbsp;&nbsp;&nbsp;<fmt:formatDate value="${student.birthday}" pattern="yyyy-MM-dd"/>
	</div>
    <table class="table">
      <thead>  
        <tr>  
          <th>科目</th>  
          <th>学分</th>
          <th>考分</th>  
        </tr>  
      </thead>  
      <tbody>
      	<c:choose>
      	<c:when test="${fn:length(scoreList) > 0}">
      	<c:forEach var="sc" items="${scoreList}">  
        <tr>  
          <td>${sc.course.name}</td>  
          <td>${sc.course.credit}</td> 
          <td>${sc.points}</td> 
        </tr>
        </c:forEach>
        </c:when>
        <c:otherwise>
        <tr>
        	<td colspan="3">暂时没有成绩</td>
        </tr>
        </c:otherwise>
        </c:choose>
      </tbody>  
    </table>  
 </div>
	
	<!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
    <script src="http://cdn.bootcss.com/jquery/1.11.2/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <script src="<c:url value="/static/js/docs.min.js"/>"></script>
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<c:url value="/static/js/ie10-viewport-bug-workaround.js"/>"></script>
</body>
</html>