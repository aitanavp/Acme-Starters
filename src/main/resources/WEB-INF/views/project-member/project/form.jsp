<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="projectMember.project.form.ticker" path="ticker"/>
	<acme:form-textbox code="projectMember.project.form.name" path="name"/>
	<acme:form-textarea code="projectMember.project.form.description" path="description"/>
	<acme:form-moment code="projectMember.project.form.startMoment" path="startMoment"/>
	<acme:form-moment code="projectMember.project.form.endMoment" path="endMoment"/>
	<acme:form-url code="projectMember.project.form.moreInfo" path="moreInfo"/>
	<acme:form-double code="projectMember.project.form.monthsActive" path="monthsActive" readonly="true"/>
	<acme:form-double code="projectMember.project.form.cost" path="cost" readonly="true"/>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="projectMember.project.form.button.parts" action="/project-member/part/list?projectId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="projectMember.project.form.button.parts" action="/project-member/part/list?projectId=${id}"/>
			<acme:submit code="projectMember.project.form.button.update" action="/project-member/project/update"/>
			<acme:submit code="projectMember.project.form.button.delete" action="/project-member/project/delete"/>
			<acme:submit code="projectMember.project.form.button.publish" action="/project-member/project/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="projectMember.project.form.button.create" action="/project-member/project/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>