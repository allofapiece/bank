<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>


<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<c:set var="fullField" value="client.field.${param.field}"/>

<div class="form-group<c:if test="${errors[fullField] ne null}"> invalid</c:if>">
    <label for="${fn:replace(param.field, '.', '-')}-field"><fmt:message key="client.field.${param.field}.label" /></label>
    <input type="<c:out value="${param.type}" default="text" />" value="<c:choose><c:when test="${param.value ne null and param.value ne ''}">${param.value}</c:when><c:when test="${param.value eq null or param.value eq ''}"><fmt:message key="client.field.${param.field}.label" /></c:when></c:choose>"
    name="${fn:replace(param.field, '.', '-')}" class="form-control" id="${fn:replace(param.field, '.', '-')}-field" aria-describedby="${fn:replace(param.field, '.', '-')}Help" placeholder="<fmt:message key="client.field.${param.field}.placeholder" />">
    <c:if test="${errors[fullField] ne null}">
        <c:forEach items="${errors[fullField]}" var="error">
            <div class="invalid-feedback"><fmt:message key="${fullField}.${error}.message" /></div>
        </c:forEach>
    </c:if>
</div>

