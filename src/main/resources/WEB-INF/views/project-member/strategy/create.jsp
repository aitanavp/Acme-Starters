<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-select
		code="project-member.strategy.form.label.strategy"
		path="strategy"
		choices="${strategyChoices}"/>

	<acme:submit
		code="project-member.strategy.form.button.create"
		action="/project-member/strategy/create?projectId=${projectId}"/>

</acme:form>