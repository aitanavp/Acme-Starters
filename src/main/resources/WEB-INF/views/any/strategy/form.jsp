<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.strategy.form.name" path="name"/>
	<acme:form-textbox code="any.strategy.form.description" path="description"/>
	<acme:form-textbox code="any.strategy.form.startMoment" path="startMoment"/>
	<acme:form-textbox code="any.strategy.form.endMoment" path="endMoment"/>
	<acme:form-textbox code="any.strategy.form.moreInfo" path="moreInfo"/>
	<acme:form-textbox code="any.strategy.form.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="any.strategy.form.expectedPercentage" path="expectedPercentage"/>
	
	<acme:button code="any.strategy.form.button.tactics" action="/any/tactic/list?strategyId=${id}" />
	<acme:button code="any.strategy.form.button.fundraiser" action="/any/fundraiser/show?id=${fundraiserId}" />
</acme:form>