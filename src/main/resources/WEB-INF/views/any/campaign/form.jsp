<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.campaign.form.name" path="name"/>
	<acme:form-textbox code="any.campaign.form.description" path="description"/>
	<acme:form-textbox code="any.campaign.form.startMoment" path="startMoment"/>
	<acme:form-textbox code="any.campaign.form.endMoment" path="endMoment"/>
	<acme:form-textbox code="any.campaign.form.moreInfo" path="moreInfo"/>
	
	<acme:button code="any.campaign.form.button.parts" action="/any/milestone/list?campaignId=${id}" />
	<acme:button code="any.campaign.form.button.inventor" action="/any/spokesperson/show?id=${spokespersonId}" />
</acme:form>