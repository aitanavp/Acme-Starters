<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.donation.form.name" path="name"/>
	<acme:form-textarea code="any.donation.form.notes" path="notes"/>
	<acme:form-money code="any.donation.form.money" path="money"/>
	<acme:form-textbox code="any.donation.form.kind" path="kind"/>
</acme:form>