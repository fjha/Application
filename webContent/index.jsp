<%@ page isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false" %>
<html>
<body>

	<h2>How to iterate list on JSP in Spring MVC</h2>

	<p><b>Simple List:</b><p>
	
	${lists}
	
	<p><b>Iterated List:</b><p>

	<ol>
		<c:forEach var="emp" items="${lists}">
		
			<li>${emp.id}</li>
            <li>${emp.title}</li>
            <li>${emp.releaseDate}</li>
            <li>${emp.genre}</li>
           
			
		</c:forEach>
	</ol>

</body>
</html>