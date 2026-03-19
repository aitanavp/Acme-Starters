<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.campaign.list.ticker" path="ticker" width="10%"/>
	<acme:list-column code="any.campaign.list.name" path="name" width="30%"/>
	<acme:list-column code="any.campaign.list.description" path="description" width="70%"/>
	<acme:list-column code="any.campaign.list.name" path="name" width="10%"/>
	<acme:list-column code="any.campaign.list.description" path="description" width="30%"/>
	<acme:list-column code="any.campaign.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="any.campaign.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="any.campaign.list.moreInfo" path="moreInfo" width="10%"/>
	<acme:list-column code="any.campaign.list.draftMode" path="draftMode" width="5%"/>
	<acme:list-column code="any.campaign.list.monthsActive" path="monthsActive" width="5%"/>
	<acme:list-column code="any.campaign.list.effort" path="effort" width="5%"/>
</acme:list>


