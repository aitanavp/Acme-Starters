<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="sponsor.donation.form.name" path="name"/>
	<acme:form-textarea code="sponsor.donation.form.notes" path="notes"/>
	<acme:form-money code="sponsor.donation.form.money" path="money"/>
	<acme:form-select code="sponsor.donation.form.kind" path="kind" choices="${DonationKind}"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="sponsor.donation.form.button.update" action="/sponsor/donation/update?id=${id}"/>
			<acme:submit code="sponsor.donation.form.button.delete" action="/sponsor/donation/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="sponsor.donation.form.button.create" action="/sponsor/donation/create?sponsorshipId=${sponsorshipId}"/>
		</jstl:when>		
	</jstl:choose>

	<acme:button code="sponsor.donation.form.button.sponsorship" action="/sponsor/sponsorship/show?id=${sponsorshipId}"/>
</acme:form>