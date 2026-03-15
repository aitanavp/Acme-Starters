<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="auditor.auditSection.list.name" path="name" width="20%"/>
	<acme:list-column code="auditor.auditSection.list.notes" path="notes" width="50%"/>
	<acme:list-column code="auditor.auditSection.list.hours" path="hours" width="10%"/>
	<acme:list-column code="auditor.auditSection.list.kind" path="kind" width="20%"/>
</acme:list>

<jstl:if test="${draftMode == true}">
	<acme:button code="auditor.auditSection.list.button.create" action="/auditor/audit-section/create?auditReportId=${auditReportId}"/>
</jstl:if>
<acme:button code="auditor.auditSection.list.button.auditReport" action="/auditor/audit-report/show?id=${auditReportId}"/>