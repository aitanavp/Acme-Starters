<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.part.list.name" path="name" width="20%"/>
	<acme:list-column code="any.part.list.description" path="description" width="50%"/>
	<acme:list-column code="any.part.list.cost" path="cost" width="10%"/>
	<acme:list-column code="any.part.list.kind" path="kind" width="20%"/>
	
</acme:list>


