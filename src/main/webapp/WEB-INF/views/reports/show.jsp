<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdxDep" value="${ForwardConst.CMD_INDEX_DEP.getValue()}" />
<c:set var="commIdxAll" value="${ForwardConst.CMD_INDEX_ALL.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>日報　詳細ページ</h2>

        <table>
            <tr>
                <th>氏名</th>
                <td><c:out value="${report.employee.name}" /></td>
            <tr>
            <tr>
                <th>日付</th>
                <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd"
                    var="reportDay" type="date" />
                <td><fmt:formatDate value="${reportDay}" pattern="yyyy-MM-dd" /></td>
            </tr>
            <tr>
                <th>内容</th>
                <td><pre><c:out value="${report.content}" /></pre></td>
            </tr>
            <tr>
                <th>登録日時</th>
                <fmt:parseDate value="${report.createdAt}"
                    pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                <td><fmt:formatDate value="${createDay}"
                        pattern="yyyy-MM-dd HH:mm:ss" /></td>
            </tr>
            <tr>
                <th>更新日時</th>
                <fmt:parseDate value="${report.updatedAt}"
                    pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                <td><fmt:formatDate value="${updateDay}"
                        pattern="yyyy-MM-dd HH:mm:ss" /></td>
            </tr>
        </table>

        <c:if test="${sessionScope.login_employee.id == report.employee.id}" >
            <c:if test="${sessionScope.login_employee.position == AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()
                 or report.approval != AttributeConst.REP_APPROVAL_DONE.getIntegerValue()}">
                <p>
                    <a href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この日報を編集する</a>
                </p>
            </c:if>
        </c:if>

        <c:choose>
            <c:when test="${sessionScope.view_select == AttributeConst.VIEW_GET_ALL.getIntegerValue()}">
                <p>
                    <a href="<c:url value='?action=${actRep}&command=${commIdxAll}' />">一覧に戻る</a>
                </p>
            </c:when>
            <c:otherwise>
                <p>
                    <a href="<c:url value='?action=${actRep}&command=${commIdxDep}' />">一覧に戻る</a>
                </p>
            </c:otherwise>
        </c:choose>

    </c:param>
</c:import>