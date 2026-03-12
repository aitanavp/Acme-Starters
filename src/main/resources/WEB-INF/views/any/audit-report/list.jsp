<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.audit-report.list.name" path="name" width="30%"/>
	<acme:list-column code="any.audit-report.list.description" path="description" width="70%"/>
	<acme:list-column code="any.audit-report.list.name" path="name" width="10%"/>
	<acme:list-column code="any.audit-report.list.description" path="description" width="35%"/>
	<acme:list-column code="any.audit-report.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="any.audit-report.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="any.audit-report.list.moreInfo" path="moreInfo" width="10%"/>
	<acme:list-column code="any.audit-report.list.monthsActive" path="monthsActive" width="5%"/>
	<acme:list-column code="any.audit-report.list.hours" path="hours" width="15%"/>
</acme:list>


