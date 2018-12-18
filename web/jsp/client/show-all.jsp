<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div id="content-title" style="margin-top: 15px;">
    <h2><fmt:message key="title.client.show.all" /></h2>
    <hr>
</div>
<div class="row" style="margin-top: 15px;">
    <div class="container-fluid">
        <a class="btn btn-primary" href="<c:url value="/fc?command=add-client"/>"><fmt:message key="client.submit.add.label" /></a>
        <div class="table-responsive">
            <table class="table">
                <thead>
                    <tr>
                        <td>id</td>
                        <td><fmt:message key="client.field.name.label" /></td>
                        <td><fmt:message key="client.field.surname.label" /></td>
                        <td><fmt:message key="client.field.patronymic.label" /></td>
                        <td><fmt:message key="client.field.passport.series.label" /></td>
                        <td><fmt:message key="client.field.passport.number.label" /></td>
                        <td><fmt:message key="client.field.identify.number.label" /></td>
                    </tr>
                </thead>
                <c:forEach items="${clients}" var="client">
                        <tr>
                            <td>${client.id}</td>
                            <td>${client.name}</td>
                            <td>${client.surname}</td>
                            <td>${client.patronymic}</td>
                            <td>${client.passportSeries}</td>
                            <td>${client.passportNumber}</td>
                            <td>${client.identifyNumber}</td>
                            <td><a href="<c:url value="/fc?command=client-update&id=${client.id}"/> "><fmt:message key="client.inscription.show.action.update" /></a></td>
                            <td><a href="<c:url value="/fc?command=client-delete&id=${client.id}"/> "><fmt:message key="client.inscription.show.action.delete" /></a></td>
                        </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>

<script src="${context}/js/lots.js"></script>

<c:import url="/jsp/template/footer.jsp"/>