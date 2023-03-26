<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.AttributeConst"%>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br>
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" />
            <br>
        </c:forEach>
    </div>
</c:if>
<fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd"
    var="reportDay" type="date" />
<label for="${AttributeConst.REP_DATE.getValue()}">日付</label>
<br>
<input type="date" name="${AttributeConst.REP_DATE.getValue()}"
    id="${AttributeConst.REP_DATE.getValue()}"
    value="<fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' />" />
<br>
<br>

<label>氏名</label>
<br>
<c:out value="${sessionScope.login_employee.name}" />
<br>
<br>

<label for="${AttributeConst.REP_TITLE.getValue()}">タイトル</label>
<br>
<input type="text" name="${AttributeConst.REP_TITLE.getValue()}"
    id="${AttributeConst.REP_TITLE.getValue()}" value="${report.title}" />
<br>
<br>

<label for="${AttributeConst.REP_CONTENT.getValue()}">内容</label>
<br>
<textarea name="${AttributeConst.REP_CONTENT.getValue()}"
    id="${AttributeConst.REP_CONTENT.getValue()}" rows="10" cols="50">${report.content}</textarea>
<br>

<c:if
    test="${login_employee.position != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()}">
    <label for="${AttributeConst.EMPLOYEE_SUPERIOR.getValue()}">承認者</label>
    <br>
    <select name="${AttributeConst.EMPLOYEE_SUPERIOR.getValue()}" id="${AttributeConst.EMPLOYEE_SUPERIOR.getValue()}" >
        <c:forEach var="superior" items="${superior_list}">
            <option value="${superior.id}"
            <c:if test="${report.approver.id == superior.id}"> selected</c:if>>${superior.name}</option>
        </c:forEach>
    </select>
</c:if>
<br>
<br>
<button type="submit" class="btn btn-light btn-outline-dark">登録</button>


<input type="hidden" name="${AttributeConst.REP_ID.getValue()}"
    value="${report.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}"
    value="${_token}" />

