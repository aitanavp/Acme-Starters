<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:choose>
    <jstl:when test="${acme:anyOf(_command,'show|create|update|delete|publish')}">
        <acme:form>
            <acme:form-textbox code="manager.project.form.titlestr" path="title"/>
            <acme:form-textbox code="manager.project.form.keywords" path="keywords"/>
            <acme:form-textarea code="manager.project.form.description" path="description"/>
            <acme:form-moment code="manager.project.form.kickOffMoment" path="kickOffMoment"/>
            <acme:form-moment code="manager.project.form.closeOutMoment" path="closeOutMoment"/>
            <acme:form-double code="manager.project.form.effort" path="effort" readonly="true"/>

            <jstl:choose>
                <jstl:when test="${_command == 'create'}">
                    <acme:submit code="manager.project.form.button.create" action="/manager/project/create"/>
                </jstl:when>
                <jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
                    <acme:button code="manager.project.form.button.members" action="/manager/project/members?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.inventions" action="/manager/invention/list?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.campaigns" action="/manager/campaign/list?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.strategies" action="/manager/strategy/list?projectId=${id}"/>
                    <acme:submit code="manager.project.form.button.update" action="/manager/project/update?id=${id}"/>
                    <acme:submit code="manager.project.form.button.delete" action="/manager/project/delete?id=${id}"/>
                    <acme:submit code="manager.project.form.button.publish" action="/manager/project/publish?id=${id}"/>
                </jstl:when>
                <jstl:otherwise>
                    <acme:button code="manager.project.form.button.members" action="/manager/project/members?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.sponsorships" action="/manager/sponsorship/list?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.audit-reports" action="/manager/audit-report/list?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.inventions" action="/manager/invention/list?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.campaigns" action="/manager/campaign/list?projectId=${id}"/>
                    <acme:button code="manager.project.form.button.strategies" action="/manager/strategy/list?projectId=${id}"/>
                </jstl:otherwise>
            </jstl:choose>
        </acme:form>
    </jstl:when>

    <jstl:when test="${_command == 'members'}">
        <acme:list navigable="false">
            <acme:list-column code="manager.project.form.members.members" path="projectMember" width="80%"/>
        </acme:list>
        <jstl:if test="${draftMode == true && canManageMembers == true}">
            <acme:button code="manager.project.form.button.add-members" action="/manager/project/add-members?projectId=${projectId}"/>
            <acme:button code="manager.project.form.button.delete-members" action="/manager/project/delete-members?projectId=${projectId}"/>
        </jstl:if>
    </jstl:when>
    
    <jstl:when test="${_command == 'add-members'}">
        <acme:form>
            <acme:form-select code="manager.project.form.membership.projectMember" path="projectMember" choices="${projectMemberChoices}"/>
            <acme:submit code="manager.project.form.membership.button.add" action="/manager/project/add-members?projectId=${projectId}"/>
        </acme:form>
    </jstl:when>
    
    <jstl:when test="${_command == 'delete-members'}">
        <acme:form>
            <acme:form-select code="manager.project.form.membership.projectMember" path="projectMember" choices="${projectMemberChoices}"/>
            <acme:submit code="manager.project.form.membership.button.delete" action="/manager/project/delete-members?projectId=${projectId}"/>
        </acme:form>
    </jstl:when>
</jstl:choose>
