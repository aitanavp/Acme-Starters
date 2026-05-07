<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
    <acme:form-textbox code="project-member.project.form.titlestr" path="title"/>
    <acme:form-textbox code="project-member.project.form.keywords" path="keywords"/>
    <acme:form-textarea code="project-member.project.form.description" path="description"/>
    <acme:form-moment code="project-member.project.form.kickOffMoment" path="kickOffMoment"/>
    <acme:form-moment code="project-member.project.form.closeOutMoment" path="closeOutMoment"/>
    <acme:form-url code="project-member.project.form.draftMode" path="draftMode"/>
    <acme:form-double code="project-member.project.form.effort" path="effort" readonly="true"/>

    <jstl:choose>    
        <jstl:when test="${_command == 'show' && draftMode == true}">
            <acme:button code="project-member.project.form.button.inventions" action="/project-member/invention/list?projectId=${id}"/>
            <acme:button code="project-member.project.form.button.campaigns" action="/project-member/campaign/list?projectId=${id}"/>
            <acme:button code="project-member.project.form.button.strategies" action="/project-member/strategy/list?projectId=${id}"/>    
         </jstl:when>
        <jstl:when test="${_command == 'show' && draftMode == false}">
            <acme:button code="project-member.project.form.button.sponsorships" action="/project-member/sponsorship/list?projectId=${id}"/>
            <acme:button code="project-member.project.form.button.audit-reports" action="/project-member/audit-report/list?projectId=${id}"/>            
            <acme:button code="project-member.project.form.button.inventions" action="/project-member/invention/list?projectId=${id}"/>
            <acme:button code="project-member.project.form.button.campaigns" action="/project-member/campaign/list?projectId=${id}"/>
            <acme:button code="project-member.project.form.button.strategies" action="/project-member/strategy/list?projectId=${id}"/>            
        </jstl:when>
    </jstl:choose>
</acme:form>
