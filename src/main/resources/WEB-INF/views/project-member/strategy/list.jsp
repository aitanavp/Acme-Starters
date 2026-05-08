<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="project-member.strategy.list.ticker" path="ticker" width="10%"/>
	<acme:list-column code="project-member.strategy.list.name" path="name" width="15%"/>
	<acme:list-column code="project-member.strategy.list.description" path="description" width="25%"/>
	<acme:list-column code="project-member.strategy.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="project-member.strategy.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="project-member.strategy.list.monthsActive" path="monthsActive" width="10%"/>
	<acme:list-column code="project-member.strategy.list.expectedPercentage" path="expectedPercentage" width="10%"/>
	<acme:list-column code="project-member.strategy.list.draftMode" path="draftMode" width="5%"/>
</acme:list>

<acme:button code="project-member.strategy.list.button.create" action="/project-member/strategy/create?projectId=${projectId}"/>