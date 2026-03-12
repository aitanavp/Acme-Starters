<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="fundraiser.tactic.form.name" path="name"/>
	<acme:form-textarea code="fundraiser.tactic.form.description" path="description"/>
	<acme:form-textbox code="fundraiser.tactic.form.expectedPercentage" path="expectedPercentage"/>
	<acme:form-select code="fundraiser.tactic.form.kind" path="kind" choices="${TacticKind}"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="fundraiser.tactic.form.button.update" action="/fundraiser/tactic/update?id=${id}"/>
			<acme:submit code="fundraiser.tactic.form.button.delete" action="/fundraiser/tactic/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="fundraiser.tactic.form.button.create" action="/fundraiser/tactic/create?strategyId=${strategyId}"/>
		</jstl:when>		
	</jstl:choose>

	<acme:button code="fundraiser.tactic.form.button.strategy" action="/fundraiser/strategy/show?id=${strategyId}"/>
</acme:form>
