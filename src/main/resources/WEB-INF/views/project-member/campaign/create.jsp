<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-select
		code="project-member.campaign.form.label.campaign"
		path="campaign"
		choices="${campaignChoices}"/>

	<acme:submit
		code="project-member.campaign.form.button.create"
		action="/project-member/campaign/create?projectId=${projectId}"/>

</acme:form>