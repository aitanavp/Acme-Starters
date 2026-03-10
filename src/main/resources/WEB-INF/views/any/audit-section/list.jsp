<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.audit-section.list.name" path="name" width="20%"/>
	<acme:list-column code="any.audit-section.list.notes" path="notes" width="50%"/>
	<acme:list-column code="any.audit-section.list.hours" path="hours" width="10%"/>
	<acme:list-column code="any.audit-section.list.kind" path="kind" width="20%"/>
</acme:list>


