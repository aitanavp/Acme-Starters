
<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="spokesperson.milestone.form.title" path="title"/>
	<acme:form-textarea code="spokesperson.milestone.form.achievements" path="achievements"/>
	<acme:form-double code="spokesperson.milestone.form.effort" path="effort"/>
	<acme:form-select code="spokesperson.milestone.form.kind" path="kind" choices="${MilestoneKind}"/>

	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete') && draftMode == true}">
			<acme:submit code="spokesperson.milestone.form.button.update" action="/spokesperson/milestone/update?id=${id}"/>
			<acme:submit code="spokesperson.milestone.form.button.delete" action="/spokesperson/milestone/delete?id=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="spokesperson.milestone.form.button.create" action="/spokesperson/milestone/create?campaignId=${campaignId}"/>
		</jstl:when>		
	</jstl:choose>

	<acme:button code="spokesperson.milestone.form.button.campaign" action="/spokesperson/campaign/show?id=${campaignId}"/>
</acme:form>

