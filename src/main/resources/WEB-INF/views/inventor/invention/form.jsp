<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form> 
	<acme:form-textbox code="inventor.invention.form.ticker" path="ticker"/>
	<acme:form-textbox code="inventor.invention.form.name" path="name"/>
	<acme:form-textarea code="inventor.invention.form.description" path="description"/>
	<acme:form-moment code="inventor.invention.form.startMoment" path="startMoment"/>
	<acme:form-moment code="inventor.invention.form.endMoment" path="endMoment"/>
	<acme:form-url code="inventor.invention.form.moreInfo" path="moreInfo"/>
	<jstl:if test="${_command != 'create'}">
		<acme:form-double code="inventor.invention.form.monthsActive" path="monthsActive" readonly="true"/>
		<acme:form-double code="inventor.invention.form.cost" path="cost" readonly="true"/>
		<acme:form-checkbox code="inventor.invention.form.draftMode" path="draftMode" readonly="true"/>
	</jstl:if>

	<jstl:choose>	 
		<jstl:when test="${_command == 'show' && draftMode == false}">
			<acme:button code="inventor.invention.form.button.parts" action="/inventor/part/list?inventionId=${id}"/>			
		</jstl:when>
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:button code="inventor.invention.form.button.parts" action="/inventor/part/list?inventionId=${id}"/>
			<acme:submit code="inventor.invention.form.button.update" action="/inventor/invention/update"/>
			<acme:submit code="inventor.invention.form.button.delete" action="/inventor/invention/delete"/>
			<acme:submit code="inventor.invention.form.button.publish" action="/inventor/invention/publish"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="inventor.invention.form.button.create" action="/inventor/invention/create"/>
		</jstl:when>		
	</jstl:choose>
</acme:form>