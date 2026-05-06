<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:form-select code="project-member.membership.form.projectMember" path="projectMember" choices="${projectMemberChoices}"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="project-member.membership.form.button.add" action="/project-member/membership/create?projectId=${projectId}"/>
		</jstl:when>
		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="project-member.membership.form.button.delete" action="/project-member/membership/delete?projectId=${projectId}"/>
		</jstl:when>
	</jstl:choose>

	<acme:button code="project-member.membership.form.button.back" action="/project-member/membership/list?projectId=${projectId}"/>
</acme:form>