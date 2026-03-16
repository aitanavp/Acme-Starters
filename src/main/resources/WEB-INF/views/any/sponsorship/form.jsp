<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-textbox code="any.sponsorship.form.name" path="name"/>
	<acme:form-textarea code="any.sponsorship.form.description" path="description"/>
	<acme:form-moment code="any.sponsorship.form.startMoment" path="startMoment"/>
	<acme:form-moment code="any.sponsorship.form.endMoment" path="endMoment"/>
	<acme:form-url code="any.sponsorship.form.moreInfo" path="moreInfo"/>
	<acme:form-textbox code="any.sponsorship.form.monthsActive" path="monthsActive"/>
	<acme:form-textbox code="any.sponsorship.form.totalMoney" path="totalMoney"/>
	
	<acme:button code="any.sponsorship.form.button.donations" action="/any/donation/list?sponsorshipId=${id}" />
	<acme:button code="any.sponsorship.form.button.sponsor" action="/any/sponsor/show?id=${sponsorId}" />
</acme:form>