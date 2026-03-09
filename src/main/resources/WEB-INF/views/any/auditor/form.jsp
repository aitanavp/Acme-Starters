<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.auditor.form.firm" path="firm"/>
	<acme:form-textbox code="any.auditor.form.highlights" path="highlights"/>
	<acme:form-textbox code="any.auditor.form.solicitor" path="solicitor"/>
</acme:form>