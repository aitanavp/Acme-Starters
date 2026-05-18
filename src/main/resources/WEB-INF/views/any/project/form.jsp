<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
    <acme:form-textbox code="any.project.form.titlestr" path="title"/>
    <acme:form-textbox code="any.project.form.keywords" path="keywords"/>
    <acme:form-textarea code="any.project.form.description" path="description"/>
    <acme:form-moment code="any.project.form.kickOffMoment" path="kickOffMoment"/>
    <acme:form-moment code="any.project.form.closeOutMoment" path="closeOutMoment"/>
    <acme:form-double code="any.project.form.effort" path="effort" readonly="true"/>
</acme:form>