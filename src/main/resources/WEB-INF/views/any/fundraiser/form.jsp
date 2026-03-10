<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.fundraiser.form.bank" path="bank"/>
	<acme:form-textbox code="any.fundraiser.form.statement" path="statement"/>
	<acme:form-textbox code="any.fundraiser.form.agent" path="agent"/>
</acme:form>