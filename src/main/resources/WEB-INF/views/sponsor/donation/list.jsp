<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="sponsor.donation.list.name" path="name" width="20%"/>
	<acme:list-column code="sponsor.donation.list.notes" path="notes" width="50%"/>
	<acme:list-column code="sponsor.donation.list.money" path="money" width="10%"/>
	<acme:list-column code="sponsor.donation.list.kind" path="kind" width="20%"/>
</acme:list>

<jstl:if test="${draftMode == true}">
	<acme:button code="sponsor.donation.list.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
</jstl:if>
<acme:button code="sponsor.donation.list.button.sponsorship" action="/sponsor/sponsorship/show?id=${sponsorshipId}"/>