
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="spokesperson.milestone.list.title" path="title" width="20%"/>
	<acme:list-column code="spokesperson.milestone.list.achievements" path="achievements" width="50%"/>
	<acme:list-column code="spokesperson.milestone.list.effort" path="effort" width="10%"/>
	<acme:list-column code="spokesperson.milestone.list.kind" path="kind" width="20%"/>
</acme:list>

<jstl:if test="${draftMode == true}">
	<acme:button code="spokesperson.milestone.list.button.create" action="/spokesperson/milestone/create?campaignId=${campaignId}"/>
</jstl:if>
<acme:button code="spokesperson.milestone.list.button.campaign" action="/spokesperson/campaign/show?id=${campaignId}"/>

