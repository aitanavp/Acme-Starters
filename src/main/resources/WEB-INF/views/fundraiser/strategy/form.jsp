<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="fundraiser.strategy.form.ticker" path="ticker"/>
	<acme:form-textbox code="fundraiser.strategy.form.name" path="name"/>
	<acme:form-textarea code="fundraiser.strategy.form.description" path="description"/>
	<acme:form-moment code="fundraiser.strategy.form.startMoment" path="startMoment"/>
	<acme:form-moment code="fundraiser.strategy.form.endMoment" path="endMoment"/>
	<acme:form-url code="fundraiser.strategy.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="fundraiser.strategy.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="fundraiser.strategy.form.expectedPercentage" path="expectedPercentage" readonly="true"/>
	<acme:form-checkbox code="fundraiser.strategy.form.draftMode" path="draftMode" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="fundraiser.strategy.form.button.tactics" action="/fundraiser/tactic/list?strategyId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="fundraiser.strategy.form.button.tactics" action="/fundraiser/tactic/list?strategyId=${id}"/>
			<acme:submit code="fundraiser.strategy.form.button.update" action="/fundraiser/strategy/update"/>
			<acme:submit code="fundraiser.strategy.form.button.delete" action="/fundraiser/strategy/delete"/>
			<acme:submit code="fundraiser.strategy.form.button.publish" action="/fundraiser/strategy/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="fundraiser.strategy.form.button.create" action="/fundraiser/strategy/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>
