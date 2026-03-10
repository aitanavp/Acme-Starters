<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.strategy.list.name" path="name" width="10%"/>
	<acme:list-column code="any.strategy.list.description" path="description" width="35%"/>
	<acme:list-column code="any.strategy.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="any.strategy.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="any.strategy.list.moreInfo" path="moreInfo" width="10%"/>
	<acme:list-column code="any.strategy.list.monthsActive" path="monthsActive" width="5%"/>
	<acme:list-column code="any.strategy.list.expectedPercentage" path="expectedPercentage" width="15%"/>
</acme:list>


