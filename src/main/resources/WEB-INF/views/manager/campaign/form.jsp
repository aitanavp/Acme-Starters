<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.campaign.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.campaign.form.name" path="name"/>
	<acme:form-textarea code="project-member.campaign.form.description" path="description"/>
	<acme:form-moment code="project-member.campaign.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.campaign.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.campaign.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.campaign.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.campaign.form.effort" path="effort" readonly="true"/>

</acme:form>