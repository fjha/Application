<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.ArrayList" %>
<html>
<body>

	<h2>How to iterate list on JSP in Spring MVC</h2>
 
	<p><b>Simple List:</b><p>
	
	${lists}
	
	<p><b>Iterated List:</b><p>

	<ol>
		<c:forEach var="list" items="${lists}">
		 <li> $list</li>
          
		</c:forEach>
	</ol>

</body>
</html>