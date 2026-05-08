<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>

	<acme:form-select
		code="project-member.invention.form.label.invention"
		path="invention"
		choices="${inventionChoices}"/>

	<acme:submit
		code="project-member.invention.form.button.create"
		action="/project-member/invention/create?projectId=${projectId}"/>

</acme:form>