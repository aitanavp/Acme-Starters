<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.audit-report.form.name" path="name"/>
	<acme:form-textbox code="any.audit-report.form.description" path="description"/>
	<acme:form-textbox code="any.audit-report.form.startMoment" path="startMoment"/>
	<acme:form-textbox code="any.audit-report.form.endMoment" path="endMoment"/>
	<acme:form-textbox code="any.audit-report.form.moreInfo" path="moreInfo"/>
	
	<acme:button code="any.audit-report.form.button.auditSections" action="/any/audit-section/list?auditReportId=${id}" />
	<acme:button code="any.audit-report.form.button.auditor" action="/any/auditor/show?id=${auditorId}" />
</acme:form>