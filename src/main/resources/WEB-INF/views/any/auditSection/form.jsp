<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.auditSection.form.name" path="name"/>
	<acme:form-textbox code="any.auditSection.form.notes" path="notes"/>
	<acme:form-textbox code="any.auditSection.form.hours" path="hours"/>
	<acme:form-textbox code="any.auditSection.form.kind" path="kind"/>
</acme:form>