<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.inventor.form.bio" path="bio"/>
	<acme:form-textbox code="any.inventor.form.keyWords" path="keyWords"/>
	<acme:form-textbox code="any.inventor.form.licensed" path="licensed"/>
</acme:form>