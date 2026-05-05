<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.campaign.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.campaign.form.name" path="name"/>
	<acme:form-textarea code="project-member.campaign.form.description" path="description"/>
	<acme:form-moment code="project-member.campaign.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.campaign.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.campaign.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.campaign.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.campaign.form.effort" path="effort" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="project-member.campaign.form.button.milestones" action="/project-member/milestone/list?campaignId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="project-member.campaign.form.button.milestones" action="/project-member/milestone/list?campaignId=${id}"/>
			<acme:submit code="project-member.campaign.form.button.update" action="/project-member/campaign/update"/>
			<acme:submit code="project-member.campaign.form.button.delete" action="/project-member/campaign/delete"/>
			<acme:submit code="project-member.campaign.form.button.publish" action="/project-member/campaign/publish"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>