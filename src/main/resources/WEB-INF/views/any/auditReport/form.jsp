<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.auditReport.form.name" path="name"/>
	<acme:form-textbox code="any.auditReport.form.description" path="description"/>
	<acme:form-textbox code="any.auditReport.form.startMoment" path="startMoment"/>
	<acme:form-textbox code="any.auditReport.form.endMoment" path="endMoment"/>
	<acme:form-textbox code="any.auditReport.form.moreInfo" path="moreInfo"/>
	
	<acme:button code="any.auditReport.form.button.auditSections" action="/any/auditSection/list?auditReportId=${id}" />
	<acme:button code="any.auditReport.form.button.auditor" action="/any/auditor/show?id=${auditorId}" />
</acme:form>