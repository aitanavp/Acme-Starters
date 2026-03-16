<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.sponsorship.list.ticker" path="ticker" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.name" path="name" width="15%"/>
	<acme:list-column code="sponsor.sponsorship.list.description" path="description" width="25%"/>
	<acme:list-column code="sponsor.sponsorship.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="sponsor.sponsorship.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.monthsActive" path="monthsActive" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.totalMoney" path="totalMoney" width="10%"/>
	<acme:list-column code="sponsor.sponsorship.list.draftMode" path="draftMode" width="5%"/>
</acme:list>

<acme:button code="sponsor.sponsorship.list.button.create" action="/sponsor/sponsorship/create"/>