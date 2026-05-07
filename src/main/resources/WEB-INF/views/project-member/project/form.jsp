<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:choose>
	<jstl:when test="${_command == 'show'}">
		<acme:form>
			<acme:form-textbox code="project-member.project.form.titlestr" path="title"/>
			<acme:form-textbox code="project-member.project.form.keywords" path="keywords"/>
			<acme:form-textarea code="project-member.project.form.description" path="description"/>
			<acme:form-moment code="project-member.project.form.kickOffMoment" path="kickOffMoment"/>
			<acme:form-moment code="project-member.project.form.closeOutMoment" path="closeOutMoment"/>
			<acme:form-url code="project-member.project.form.draftMode" path="draftMode"/>
			<acme:form-double code="project-member.project.form.effort" path="effort" readonly="true"/>
			
			<jstl:choose>	
				<jstl:when test="${draftMode == true}">
					<acme:button code="project-member.project.form.button.members" action="/project-member/project/members?projectId=${id}"/>
					<acme:button code="project-member.project.form.button.inventions" action="/project-member/invention/list?projectId=${id}"/>
					<acme:button code="project-member.project.form.button.campaigns" action="/project-member/campaign/list?projectId=${id}"/>
					<acme:button code="project-member.project.form.button.strategies" action="/project-member/strategy/list?projectId=${id}"/>	
			 	</jstl:when>
				<jstl:otherwise>
					<acme:button code="project-member.project.form.button.members" action="/project-member/project/members?projectId=${id}"/>
					<acme:button code="project-member.project.form.button.sponsorships" action="/project-member/sponsorship/list?projectId=${id}"/>
					<acme:button code="project-member.project.form.button.audit-reports" action="/project-member/audit-report/list?projectId=${id}"/>			
					<acme:button code="project-member.project.form.button.inventions" action="/project-member/invention/list?projectId=${id}"/>
					<acme:button code="project-member.project.form.button.campaigns" action="/project-member/campaign/list?projectId=${id}"/>
					<acme:button code="project-member.project.form.button.strategies" action="/project-member/strategy/list?projectId=${id}"/>			
				</jstl:otherwise>
			</jstl:choose>
		</acme:form>
	</jstl:when>
	<jstl:when test="${_command == 'members'}">
		<acme:list>
			<acme:list-column code="project-member.project.form.members.member" path="projectMember" width="80%"/>
		</acme:list>
		<jstl:if test="${draftMode == true && canManageMembers == true}">
			<acme:button code="project-member.project.form.button.add-members" action="/project-member/project/add-members?projectId=${projectId}"/>
			<acme:button code="project-member.project.form.button.delete-members" action="/project-member/project/delete-members?projectId=${projectId}"/>
		</jstl:if>
	</jstl:when>
	<jstl:when test="${_command == 'add-members'}">
		<acme:form>
			<acme:form-select code="project-member.membership.form.projectMember" path="projectMember" choices="${projectMemberChoices}"/>
			<acme:submit code="project-member.membership.form.button.add" action="/project-member/project/add-members?projectId=${projectId}"/>
		</acme:form>
	</jstl:when>
	<jstl:when test="${_command == 'delete-members'}">
		<acme:form>
			<acme:form-select code="project-member.membership.form.projectMember" path="projectMember" choices="${projectMemberChoices}"/>
			<acme:submit code="project-member.membership.form.button.delete" action="/project-member/project/delete-members?projectId=${projectId}"/>
		</acme:form>
	</jstl:when>
</jstl:choose>