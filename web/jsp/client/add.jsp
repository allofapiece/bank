<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="UTF-8" %>

<c:import url="/jsp/template/header.jsp"/>

<c:if test="${sessionScope.language ne null}">
    <fmt:setLocale value="${sessionScope.language}" />
</c:if>
<fmt:setBundle basename="localization.local" />

<div id="content-title" style="margin-top: 15px;">
    <h2><fmt:message key="${not empty title ? title : 'title.client.add'}" /></h2>
    <hr>
</div>
<div class="row">
    <div class="col-6">
        <form action="<c:url value="/fc?command=add-client"/>" id="add-client-form" method="POST">
            <h4><fmt:message key="title.client.add" /></h4>
            <c:import url="/jsp/template/form-errors.jsp"/>
            
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="name"/>
                <c:param name="value" value="${name}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="surname"/>
                <c:param name="value" value="${surname}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="patronymic"/>
                <c:param name="value" value="${patronymic}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="email"/>
                <c:param name="value" value="${email}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="birthday"/>
                <c:param name="value" value="${birthday}"/>
                <c:param name="type" value="datetime-local"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="birthplace"/>
                <c:param name="value" value="${birthplace}"/>
            </c:import>
            <div class="form-group<c:if test="${errors['client.field.living.place.status'] ne null}"> invalid</c:if>">
                <label for="living-place-field"><fmt:message key="client.field.living.place.label" /></label>
                <select name="living-place" class="form-control" id="living-place-field" aria-describedby="marital-statusHelp">
                    <c:forEach items="${livingPlaceSelect}" var="livingPlace">
                        <option value="${livingPlace}"><fmt:message key="client.field.living.place.${fn:toLowerCase(fn:replace(livingPlace, ' ', '.'))}.label" /></option>
                    </c:forEach>
                </select>
                <c:if test="${errors['client.field.living.place'] ne null}">
                    <c:forEach items="${errors['client.field.living.place']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="client.field.living.place.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="living.address"/>
                <c:param name="value" value="${livingAddress}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="phone.home"/>
                <c:param name="value" value="${homePhone}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="phone.mobile"/>
                <c:param name="value" value="${mobilePhone}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="work.place"/>
                <c:param name="value" value="${workPlace}"/>
            </c:import>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="work.post"/>
                <c:param name="value" value="${workPost}"/>
            </c:import>
            <div class="form-group<c:if test="${errors['client.field.marital.status'] ne null}"> invalid</c:if>">
                <label for="marital-status-field"><fmt:message key="client.field.marital.status.label" /></label>
                <select name="marital-status" class="form-control" id="marital-status-field" aria-describedby="marital-statusHelp">
                    <c:forEach items="${maritalStatusSelect}" var="maritalStatus">
                        <option value="${maritalStatus}"><fmt:message key="client.field.marital.status.${fn:toLowerCase(fn:replace(maritalStatus, ' ', '.'))}.label" /></option>
                    </c:forEach>
                </select>
                <small id="typeHelp" class="form-text text-muted"><fmt:message key="client.field.marital.status.help" /></small>
                <c:if test="${errors['client.field.marital.status'] ne null}">
                    <c:forEach items="${errors['client.field.marital.status']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="client.field.marital.status.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['client.field.nationality'] ne null}"> invalid</c:if>">
                <label for="nationality-field"><fmt:message key="client.field.nationality.label" /></label>
                <select name="nationality" class="form-control" id="nationality-field" aria-describedby="nationalityHelp">
                    <c:forEach items="${nationalitySelect}" var="nationality">
                        <option value="${nationality}"><fmt:message key="client.field.nationality.${fn:toLowerCase(fn:replace(nationality, ' ', '.'))}.label" /></option>
                    </c:forEach>
                </select>
                <c:if test="${errors['client.field.nationality'] ne null}">
                    <c:forEach items="${errors['client.field.nationality']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="client.field.nationality.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['client.field.disability'] ne null}"> invalid</c:if>">
                <label for="disability-field"><fmt:message key="client.field.disability.label" /></label>
                <select name="disability" class="form-control" id="disability-field" aria-describedby="disabilityHelp">
                    <c:forEach items="${disabilitySelect}" var="disability">
                        <option value="${disability}"><fmt:message key="client.field.disability.${fn:toLowerCase(fn:replace(disability, ' ', '.'))}.label" /></option>
                    </c:forEach>
                </select>
                <c:if test="${errors['client.field.disability'] ne null}">
                    <c:forEach items="${errors['client.field.disability']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="client.field.disability.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['client.field.is.retiree'] ne null}"> invalid</c:if>">
                <label><fmt:message key="client.field.is.retiree.label" /></label>
                <div class="radio">
                    <label for="retiree-yes-field"><input type="radio" name="retiree" value="true" class="form-control" id="retiree-yes-field" aria-describedby="retireeHelp"><fmt:message key="client.field.is.retiree.yes.label" /></label>
                </div>
                <div class="radio">
                    <label for="retiree-no-field">
                        <input type="radio" name="retiree" value="false" class="form-control" id="retiree-no-field" aria-describedby="retireeHelp">
                        <fmt:message key="client.field.is.retiree.no.label" /></label>
                </div>
                <c:if test="${errors['client.field.is.retiree'] ne null}">
                    <c:forEach items="${errors['client.field.is.retiree']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="client.field.is.retiree.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <div class="form-group<c:if test="${errors['client.field.is.liable.for.military.service'] ne null}"> invalid</c:if>">
                <label><fmt:message key="client.field.is.liable.for.military.service.label" /></label>
                <div class="radio">
                    <label for="military-yes-field"><input type="radio" name="military" value="true" class="form-control" id="military-yes-field" aria-describedby="militaryHelp"><fmt:message key="client.field.is.liable.for.military.service.yes.label" /></label>
                </div>
                <div class="radio">
                    <label for="military-no-field">
                        <input type="radio" name="military" value="false" class="form-control" id="military-no-field" aria-describedby="militaryHelp">
                        <fmt:message key="client.field.is.liable.for.military.service.no.label" /></label>
                </div>
                <c:if test="${errors['client.field.is.liable.for.military.service'] ne null}">
                    <c:forEach items="${errors['client.field.is.liable.for.military.service']}" var="error">
                        <div class="invalid-feedback"><fmt:message key="client.field.is.liable.for.military.service.${error}.message" /></div>
                    </c:forEach>
                </c:if>
            </div>
            <c:import url="/jsp/template/field.jsp">
                <c:param name="field" value="monthly.earnings"/>
                <c:param name="value" value="${monthlyEarnings}"/>
                <c:param name="type" value="number"/>
            </c:import>
            <button type="submit" class="btn btn-primary"><fmt:message key="client.submit.add.label" /></button>
        </form>
    </div>


    <div class="col-6 additional">
        <h4><fmt:message key="title.client.add.passport" /></h4>
        <div class="form-group<c:if test="${errors['client.field.passport.series'] ne null}"> invalid</c:if>">
            <label for="passport-series-field"><fmt:message key="client.field.passport.series.label" /></label>
            <input type="text" form="add-client-form" name="passport-series" class="form-control" id="passport-series-field" aria-describedby="passport-seriesHelp" placeholder="<fmt:message key="client.field.passport.series.placeholder" />">
            <c:if test="${errors['client.field.passport.series'] ne null}">
                <c:forEach items="${errors['client.field.passport.series']}" var="error">
                    <div class="invalid-feedback"><fmt:message key="client.field.passport.series.${error}.message" /></div>
                </c:forEach>
            </c:if>
        </div>
        <div class="form-group<c:if test="${errors['client.field.passport.number'] ne null}"> invalid</c:if>">
            <label for="passport-number-field"><fmt:message key="client.field.passport.number.label" /></label>
            <input type="text" form="add-client-form" name="passport-number" class="form-control" id="passport-number-field" aria-describedby="passport-numberHelp" placeholder="<fmt:message key="client.field.passport.number.placeholder" />">
            <c:if test="${errors['client.field.passport.number'] ne null}">
                <c:forEach items="${errors['client.field.passport.number']}" var="error">
                    <div class="invalid-feedback"><fmt:message key="client.field.passport.number.${error}.message" /></div>
                </c:forEach>
            </c:if>
        </div>
        <div class="form-group<c:if test="${errors['client.field.passport.given.place'] ne null}"> invalid</c:if>">
            <label for="passport-given-place-field"><fmt:message key="client.field.passport.given.place.label" /></label>
            <input type="text" form="add-client-form" name="passport-given-place" class="form-control" id="passport-given-place-field" aria-describedby="passport-given-placeHelp" placeholder="<fmt:message key="client.field.passport.given.place.placeholder" />">
            <c:if test="${errors['client.field.passport.given.place'] ne null}">
                <c:forEach items="${errors['client.field.passport.given.place']}" var="error">
                    <div class="invalid-feedback"><fmt:message key="client.field.passport.given.place.${error}.message" /></div>
                </c:forEach>
            </c:if>
        </div>
        <div class="form-group<c:if test="${errors['client.field.passport.given.date'] ne null}"> invalid</c:if>">
            <label for="passport-given-date-field"><fmt:message key="client.field.passport.given.date.label" /></label>
            <input type="datetime-local" form="add-client-form" name="passport-given-date" class="form-control" id="passport-given-date-field" aria-describedby="passport-given-dateHelp" placeholder="<fmt:message key="client.field.passport.given.date.placeholder" />">
            <c:if test="${errors['client.field.passport.given.date'] ne null}">
                <c:forEach items="${errors['client.field.passport.given.date']}" var="error">
                    <div class="invalid-feedback"><fmt:message key="client.field.passport.given.date.${error}.message" /></div>
                </c:forEach>
            </c:if>
        </div>
        <div class="form-group<c:if test="${errors['client.field.identify.number'] ne null}"> invalid</c:if>">
            <label for="identify-number-field"><fmt:message key="client.field.identify.number.label" /></label>
            <input type="text" form="add-client-form" name="identify-number" class="form-control" id="identify-number-field" aria-describedby="identify-numberHelp" placeholder="<fmt:message key="client.field.identify.number.placeholder" />">
            <c:if test="${errors['client.field.identify.number'] ne null}">
                <c:forEach items="${errors['client.field.identify.number']}" var="error">
                    <div class="invalid-feedback"><fmt:message key="client.field.identify.number.${error}.message" /></div>
                </c:forEach>
            </c:if>
        </div>
    </div>
</div>

<script src="${context}/js/clients.js"></script>

<c:import url="/jsp/template/footer.jsp"/>