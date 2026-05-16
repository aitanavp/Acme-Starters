<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.sponsorship.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.sponsorship.form.name" path="name"/>
	<acme:form-textbox code="project-member.sponsorship.form.description" path="description"/>
	<acme:form-moment code="project-member.sponsorship.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.sponsorship.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.sponsorship.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.sponsorship.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.sponsorship.form.totalMoney" path="totalMoney" readonly="true"/>

</acme:form>