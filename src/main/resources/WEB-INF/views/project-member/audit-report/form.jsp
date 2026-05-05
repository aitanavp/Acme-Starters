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

</acme:form>