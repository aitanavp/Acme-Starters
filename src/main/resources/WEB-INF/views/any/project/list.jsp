<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
    <acme:list-column code="any.project.list.titlestr" path="title" width="10%"/>
    <acme:list-column code="any.project.list.keywords" path="keywords" width="10%"/>
    <acme:list-column code="any.project.list.description" path="description" width="30%"/>
    <acme:list-column code="any.project.list.kickOffMoment" path="kickOffMoment" width="15%"/>
    <acme:list-column code="any.project.list.closeOutMoment" path="closeOutMoment" width="10%"/>
    <acme:list-column code="any.project.list.effort" path="effort" width="5%"/>
</acme:list>

