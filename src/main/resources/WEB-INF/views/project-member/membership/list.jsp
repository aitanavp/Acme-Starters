<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="project-member.membership.list.member" path="projectMember" width="80%"/>
</acme:list>

<jstl:if test="${draftMode == true && canManageMembers == true}">
	<acme:button code="project-member.membership.list.button.add" action="/project-member/membership/create?projectId=${projectId}"/>
	<acme:button code="project-member.membership.list.button.delete" action="/project-member/membership/delete?projectId=${projectId}"/>
</jstl:if>
<acme:button code="project-member.membership.list.button.project" action="/project-member/project/show?id=${projectId}"/>