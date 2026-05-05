<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.strategy.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.strategy.form.name" path="name"/>
	<acme:form-textarea code="project-member.strategy.form.description" path="description"/>
	<acme:form-moment code="project-member.strategy.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.strategy.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.strategy.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.strategy.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.strategy.form.expectedPercentage" path="expectedPercentage" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="project-member.strategy.form.button.tactics" action="/project-member/tactic/list?strategyId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="project-member.strategy.form.button.tactics" action="/project-member/tactic/list?strategyId=${id}"/>
			<acme:submit code="project-member.strategy.form.button.update" action="/project-member/strategy/update"/>
			<acme:submit code="project-member.strategy.form.button.delete" action="/project-member/strategy/delete"/>
			<acme:submit code="project-member.strategy.form.button.publish" action="/project-member/strategy/publish"/>
		</jstl:when>
		
	</jstl:choose>
</acme:form>
