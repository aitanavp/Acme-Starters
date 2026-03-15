<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="auditor.auditReport.form.ticker" path="ticker"/>
	<acme:form-textbox code="auditor.auditReport.form.name" path="name"/>
	<acme:form-textbox code="auditor.auditReport.form.description" path="description"/>
	<acme:form-moment code="auditor.auditReport.form.startMoment" path="startMoment"/>
	<acme:form-moment code="auditor.auditReport.form.endMoment" path="endMoment"/>
	<acme:form-url code="auditor.auditReport.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="auditor.auditReport.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="auditor.auditReport.form.hours" path="hours" readonly="true"/>
	<acme:form-textbox code="auditor.auditReport.form.draftMode" path="draftMode" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="auditor.auditReport.form.button.auditSections" action="/auditor/audit-section/list?auditReportId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="auditor.auditReport.form.button.auditSections" action="/auditor/audit-section/list?auditReportId=${id}"/>
			<acme:submit code="auditor.auditReport.form.button.update" action="/auditor/audit-report/update"/>
			<acme:submit code="auditor.auditReport.form.button.delete" action="/auditor/audit-report/delete"/>
			<acme:submit code="auditor.auditReport.form.button.publish" action="/auditor/audit-report/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="auditor.auditReport.form.button.create" action="/auditor/audit-report/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>