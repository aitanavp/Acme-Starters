<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.campaign.form.name" path="name"/>
	<acme:form-textarea code="any.campaign.form.description" path="description"/>
	<acme:form-moment code="any.campaign.form.startMoment" path="startMoment"/>
	<acme:form-moment code="any.campaign.form.endMoment" path="endMoment"/>
	<acme:form-url code="any.campaign.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="any.campaign.form.monthsActive" path="monthsActive"/>
	<acme:form-double code="any.campaign.form.effort" path="effort"/>
	
	<acme:button code="any.campaign.form.button.milestones" action="/any/milestone/list?campaignId=${id}" />
	<acme:button code="any.campaign.form.button.spokesperson" action="/any/spokesperson/show?id=${spokespersonId}" />
</acme:form>