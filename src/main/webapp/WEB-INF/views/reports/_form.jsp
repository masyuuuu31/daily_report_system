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

<input type="hidden" name="${AttributeConst.REP_APPROVAL.getValue()}"
    value="${report.approval}" />
<input type="hidden" name="${AttributeConst.REP_ID.getValue()}"
    value="${report.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}"
    value="${_token}" />

<c:choose>
    <c:when test="${login_employee.position != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()}">
        <!-- Button trigger modal -->
        <button type="button" class="btn btn-light btn-outline-dark"
            data-bs-toggle="modal" data-bs-target="#staticBackdrop">登録</button>

        <!-- Modal -->
        <div class="modal fade" id="staticBackdrop" data-bs-backdrop="static"
            data-bs-keyboard="false" tabindex="-1"
            aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">承認者一覧</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                        <select class="form-select form-select-sm"
                            aria-label=".form-select-sm example" name="${AttributeConst.EMPLOYEE_SUPERIOR.getValue()}" id="${AttributeConst.EMPLOYEE_SUPERIOR.getValue()}">
                            <option selected>承認者を選択してください</option>
                            <c:forEach var="superior" items="${superior_list}">
                            <option value="${superior.id}">${superior.name}</option>
                            </c:forEach>
                        </select>

                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary"
                            data-bs-dismiss="modal">戻る</button>
                        <button type="submit" class="btn btn-light btn-outline-dark">申請</button>
                    </div>
                </div>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <button type="submit" class="btn btn-light btn-outline-dark">登録</button>
    </c:otherwise>

</c:choose>

