<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.sponsorship.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.sponsorship.form.name" path="name"/>
	<acme:form-textbox code="project-member.sponsorship.form.description" path="description"/>
	<acme:form-moment code="project-member.sponsorship.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.sponsorship.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.sponsorship.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.sponsorship.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.sponsorship.form.totalMoney" path="totalMoney" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="project-member.sponsorship.form.button.donations" action="/project-member/donation/list?sponsorshipId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="project-member.sponsorship.form.button.donations" action="/project-member/donation/list?sponsorshipId=${id}"/>
			<acme:submit code="project-member.sponsorship.form.button.update" action="/project-member/sponsorship/update"/>
			<acme:submit code="project-member.sponsorship.form.button.delete" action="/project-member/sponsorship/delete"/>
			<acme:submit code="project-member.sponsorship.form.button.publish" action="/project-member/sponsorship/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="project-member.sponsorship.form.button.create" action="/project-member/sponsorship/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>