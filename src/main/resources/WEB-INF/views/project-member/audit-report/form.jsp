<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.auditReport.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.auditReport.form.name" path="name"/>
	<acme:form-textarea code="project-member.auditReport.form.description" path="description"/>
	<acme:form-moment code="project-member.auditReport.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.auditReport.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.auditReport.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.auditReport.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.auditReport.form.hours" path="hours" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="project-member.auditReport.form.button.auditSections" action="/project-member/audit-section/list?auditReportId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="project-member.auditReport.form.button.auditSections" action="/project-member/audit-section/list?auditReportId=${id}"/>
			<acme:submit code="project-member.auditReport.form.button.update" action="/project-member/audit-report/update"/>
			<acme:submit code="project-member.auditReport.form.button.delete" action="/project-member/audit-report/delete"/>
			<acme:submit code="project-member.auditReport.form.button.publish" action="/project-member/audit-report/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="project-member.auditReport.form.button.create" action="/project-member/audit-report/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>