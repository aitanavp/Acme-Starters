<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="project-member.auditReport.list.ticker" path="ticker" width="10%"/>
	<acme:list-column code="project-member.auditReport.list.name" path="name" width="15%"/>
	<acme:list-column code="project-member.auditReport.list.description" path="description" width="25%"/>
	<acme:list-column code="project-member.auditReport.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="project-member.auditReport.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="project-member.auditReport.list.monthsActive" path="monthsActive" width="10%"/>
	<acme:list-column code="project-member.auditReport.list.hours" path="hours" width="10%"/>
	<acme:list-column code="project-member.auditReport.list.draftMode" path="draftMode" width="5%"/>
</acme:list>

<acme:button code="project-member.auditReport.list.button.create" action="/project-member/audit-report/create"/>