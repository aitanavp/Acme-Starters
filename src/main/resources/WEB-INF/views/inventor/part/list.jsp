
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.part.list.name" path="name" width="20%"/>
	<acme:list-column code="inventor.part.list.description" path="description" width="50%"/>
	<acme:list-column code="inventor.part.list.cost" path="cost" width="10%"/>
	<acme:list-column code="inventor.part.list.kind" path="kind" width="20%"/>
</acme:list>

<jstl:if test="${draftMode == true}">
	<acme:button code="inventor.part.list.button.create" action="/inventor/part/create?inventionId=${inventionId}"/>
</jstl:if>
<acme:button code="inventor.part.list.button.invention" action="/inventor/part/show?id=${inventionId}"/>