<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.milestone.list.title2" path="title" width="20%"/>
	<acme:list-column code="any.milestone.list.achievements" path="achievements" width="50%"/>
	<acme:list-column code="any.milestone.list.effort" path="effort" width="10%"/>
	<acme:list-column code="any.milestone.list.kind" path="kind" width="20%"/>
	
</acme:list>


