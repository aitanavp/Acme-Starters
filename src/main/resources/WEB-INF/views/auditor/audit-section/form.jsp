<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="auditor.auditSection.form.name" path="name"/>
	<acme:form-textarea code="auditor.auditSection.form.notes" path="notes"/>
	<acme:form-textbox code="auditor.auditSection.form.hours" path="hours"/>
	<acme:form-select code="auditor.auditSection.form.kind" path="kind" choices="${SectionKind}"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="auditor.auditSection.form.button.update" action="/auditor/audit-section/update?id=${id}"/>
			<acme:submit code="auditor.auditSection.form.button.delete" action="/auditor/audit-section/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditSection.form.button.create" action="/auditor/audit-section/create?auditReportId=${auditReportId}"/>
		</jstl:when>		
	</jstl:choose>

	<acme:button code="auditor.auditSection.form.button.auditReport" action="/auditor/audit-report/show?id=${auditReportId}"/>
</acme:form>