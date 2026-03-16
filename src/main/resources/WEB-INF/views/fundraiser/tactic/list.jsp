<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="fundraiser.tactic.list.name" path="name" width="20%"/>
	<acme:list-column code="fundraiser.tactic.list.description" path="description" width="50%"/>
	<acme:list-column code="fundraiser.tactic.list.expectedPercentage" path="expectedPercentage" width="10%"/>
	<acme:list-column code="fundraiser.tactic.list.kind" path="kind" width="20%"/>
</acme:list>

<jstl:if test="${draftMode == true}">
	<acme:button code="fundraiser.tactic.list.button.create" action="/fundraiser/tactic/create?strategyId=${strategyId}"/>
</jstl:if>
<acme:button code="fundraiser.tactic.list.button.strategy" action="/fundraiser/strategy/show?id=${strategyId}"/>
