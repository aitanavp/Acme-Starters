<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.spokesperson.form.cv" path="cv"/>
	<acme:form-textbox code="any.spokesperson.form.achievements" path="achievements"/>
	<acme:form-textbox code="any.spokesperson.form.licensed" path="licensed"/>
</acme:form>