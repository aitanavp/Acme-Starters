<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.tactic.form.name" path="name"/>
	<acme:form-textarea code="any.tactic.form.description" path="description"/>
	<acme:form-textbox code="any.tactic.form.expectedPercentage" path="expectedPercentage"/>
	<acme:form-textbox code="any.tactic.form.kind" path="kind"/>
</acme:form>