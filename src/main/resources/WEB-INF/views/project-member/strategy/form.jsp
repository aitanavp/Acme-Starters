<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.strategy.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.strategy.form.name" path="name"/>
	<acme:form-textarea code="project-member.strategy.form.description" path="description"/>
	<acme:form-moment code="project-member.strategy.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.strategy.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.strategy.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.strategy.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.strategy.form.expectedPercentage" path="expectedPercentage" readonly="true"/>

</acme:form>
