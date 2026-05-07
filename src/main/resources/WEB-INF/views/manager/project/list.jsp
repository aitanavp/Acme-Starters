<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="manager.project.list.titlestr" path="title" width="10%"/>
    <acme:list-column code="manager.project.list.keywords" path="keywords" width="10%"/>
    <acme:list-column code="manager.project.list.description" path="description" width="30%"/>
    <acme:list-column code="manager.project.list.kickOffMoment" path="kickOffMoment" width="15%"/>
    <acme:list-column code="manager.project.list.closeOutMoment" path="closeOutMoment" width="10%"/>
    <acme:list-column code="manager.project.list.draftMode" path="draftMode" width="10%"/>
    <acme:list-column code="manager.project.list.effort" path="effort" width="5%"/>
</acme:list>

<acme:button code="manager.project.list.button.create" action="/manager/project/create"/>