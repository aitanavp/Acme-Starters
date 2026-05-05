<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="project-member.invention.form.ticker" path="ticker"/>
	<acme:form-textbox code="project-member.invention.form.name" path="name"/>
	<acme:form-textarea code="project-member.invention.form.description" path="description"/>
	<acme:form-moment code="project-member.invention.form.startMoment" path="startMoment"/>
	<acme:form-moment code="project-member.invention.form.endMoment" path="endMoment"/>
	<acme:form-url code="project-member.invention.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="project-member.invention.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="project-member.invention.form.cost" path="cost" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="project-member.invention.form.button.parts" action="/project-member/part/list?inventionId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="project-member.invention.form.button.parts" action="/project-member/part/list?inventionId=${id}"/>
			<acme:submit code="project-member.invention.form.button.update" action="/project-member/invention/update"/>
			<acme:submit code="project-member.invention.form.button.delete" action="/project-member/invention/delete"/>
			<acme:submit code="project-member.invention.form.button.publish" action="/project-member/invention/publish"/>
		</jstl:when>	
	</jstl:choose>
</acme:form>