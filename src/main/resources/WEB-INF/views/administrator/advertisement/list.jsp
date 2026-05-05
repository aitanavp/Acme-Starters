<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="administrator.advertisement.list.slogan" path="slogan" width="30%"/>
	<acme:list-column code="administrator.advertisement.list.picture" path="picture" width="30%"/>
	<acme:list-column code="administrator.advertisement.list.target" path="target" width="30%"/>
	<acme:list-column code="administrator.advertisement.list.draftMode" path="draftMode" width="10%"/>
</acme:list>

<acme:button code="administrator.advertisement.list.button.create" action="/administrator/advertisement/create"/>