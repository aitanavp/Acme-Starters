<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.milestone.form.title2" path="title"/>
	<acme:form-textbox code="any.milestone.form.achievements" path="achievements"/>
	<acme:form-textbox code="any.milestone.form.effort" path="effort"/>
	<acme:form-textbox code="any.milestone.form.kind" path="kind"/>
</acme:form>