<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.donation.list.name" path="name"/>
	<acme:list-column code="any.donation.list.notes" path="notes"/>
	<acme:form-textbox code="any.donation.form.money" path="money"/>
	<acme:form-textbox code="any.donation.form.kind" path="kind"/>
	
</acme:list>