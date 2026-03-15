<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="inventor.invention.list.ticker" path="ticker" width="10%"/>
	<acme:list-column code="inventor.invention.list.name" path="name" width="10%"/>
	<acme:list-column code="inventor.invention.list.description" path="description" width="30%"/>
	<acme:list-column code="inventor.invention.list.startMoment" path="startMoment" width="15%"/>
	<acme:list-column code="inventor.invention.list.endMoment" path="endMoment" width="10%"/>
	<acme:list-column code="inventor.invention.list.moreInfo" path="moreInfo" width="10%"/>
	<acme:list-column code="inventor.invention.list.draftMode" path="draftMode" width="5%"/>
	<acme:list-column code="inventor.invention.list.monthsActive" path="monthsActive" width="5%"/>
	<acme:list-column code="inventor.invention.list.cost" path="cost" width="5"/>
</acme:list>

<acme:button code="inventor.invention.list.button.create" action="/inventor/invention/create"/>