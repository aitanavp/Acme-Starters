<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.invention.list.name" path="name" width="30%"/>
	<acme:list-column code="any.invention.list.description" path="description" width="70%"/>
</acme:list>


