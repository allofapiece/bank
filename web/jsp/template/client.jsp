<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<tr>
    <td>${param.id}</td>
    <td>${param.name}</td>
    <td>${param.surname}</td>
    <td>${param.patronymic}</td>
    <td>${param.passportSeries}</td>
    <td>${param.passportNumber}</td>
    <td>${param.identifyNumber}</td>
    <td><a href=""><fmt:message key="client.inscription.show.action.update" /></a></td>
    <td><a href=""><fmt:message key="client.inscription.show.action.delete" /></a></td>
</tr>