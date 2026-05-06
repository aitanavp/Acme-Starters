<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.total-number-of-projects"/>
		</th>
		<td>
			<acme:print value="${totalNumberOfProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.deviation-of-projects"/>
		</th>
		<td>
			<acme:print value="${deviationOfProjects}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.min-effort"/>
		</th>
		<td>
			<acme:print value="${minEffort}"/>
		</td>
	</tr>	
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.max-effort"/>
		</th>
		<td>
			<acme:print value="${maxEffort}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.average-effort"/>
		</th>
		<td>
			<acme:print value="${averageEffort}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.dashboard.form.label.deviation-effort"/>
		</th>
		<td>
			<acme:print value="${deviationEffort}"/>
		</td>
	</tr>
</table>
