<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.sponsorship.list.name" path="name" width="10%"/>
	<acme:list-column code="any.sponsorship.list.description" path="description" width="35%"/>
	<acme:list-column code="any.sponsorship.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="any.sponsorship.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="any.sponsorship.list.moreInfo" path="moreInfo" width="10%"/>
	<acme:list-column code="any.sponsorship.list.monthsActive" path="monthsActive" width="5%"/>
	<acme:list-column code="any.sponsorship.list.totalMoney" path="totalMoney" width="15%"/>
</acme:list>

