<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML>
<html>
  <head>
    
    <title>下载文件显示页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
   <!-- 遍历Map集合 -->
   <c:forEach var="me" items="${fileNameMap}">
   	 <c:url value="/servlet/DownLoadServlet" var="downurl">
   	 	<c:param name="filename" value="${me.key}"></c:param>
   	 </c:url>
   	 ${me.value}<a href="${downurl}">下载</a>
   	 <br/>
   </c:forEach>
  </body>
</html>
