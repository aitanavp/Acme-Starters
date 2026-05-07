<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="project-member.sponsorship.list.ticker" path="ticker" width="10%"/>
	<acme:list-column code="project-member.sponsorship.list.name" path="name" width="15%"/>
	<acme:list-column code="project-member.sponsorship.list.description" path="description" width="25%"/>
	<acme:list-column code="project-member.sponsorship.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="project-member.sponsorship.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="project-member.sponsorship.list.monthsActive" path="monthsActive" width="10%"/>
	<acme:list-column code="project-member.sponsorship.list.totalMoney" path="totalMoney" width="10%"/>
	<acme:list-column code="project-member.sponsorship.list.draftMode" path="draftMode" width="5%"/>
</acme:list>
