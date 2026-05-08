<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:choose>
	<jstl:when test="${_command == 'create'}">
		<acme:form>
			<acme:form-select code="project-member.invention.form.invention" path="inventionId" choices="${inventionChoices}"/>
			<acme:submit code="project-member.invention.form.button.create" action="/project-member/invention/create?projectId=${projectId}"/>
		</acme:form>
	</jstl:when>
	<jstl:otherwise>
		<acme:form>
			<acme:form-textbox code="project-member.invention.form.ticker" path="ticker"/>
			<acme:form-textbox code="project-member.invention.form.name" path="name"/>
			<acme:form-textarea code="project-member.invention.form.description" path="description"/>
			<acme:form-moment code="project-member.invention.form.startMoment" path="startMoment"/>
			<acme:form-moment code="project-member.invention.form.endMoment" path="endMoment"/>
			<acme:form-url code="project-member.invention.form.moreInfo" path="moreInfo"/>
			<acme:form-double code="project-member.invention.form.monthsActive" path="monthsActive" readonly="true"/>
			<acme:form-double code="project-member.invention.form.cost" path="cost" readonly="true"/>
		</acme:form>
	</jstl:otherwise>
</jstl:choose>