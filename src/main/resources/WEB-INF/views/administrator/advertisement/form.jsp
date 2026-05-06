<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="administrator.advertisement.form.slogan" path="slogan"/>
	<acme:form-url code="administrator.advertisement.form.picture" path="picture"/>
	<acme:form-url code="administrator.advertisement.form.target" path="target"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="administrator.advertisement.form.button.create" action="/administrator/advertisement/create"/>
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete')}">
			<acme:submit code="administrator.advertisement.form.button.update" action="/administrator/advertisement/update?id=${id}"/>
			<acme:submit code="administrator.advertisement.form.button.delete" action="/administrator/advertisement/delete?id=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>