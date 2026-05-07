<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

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
			<acme:submit code="manager.project.form.button.update" action="/manager/project/update?id=${id}"/>
			<acme:submit code="manager.project.form.button.delete" action="/manager/project/delete?id=${id}"/>
			<acme:submit code="manager.project.form.button.publish" action="/manager/project/publish?id=${id}"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
