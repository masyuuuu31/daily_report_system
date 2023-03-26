<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
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
            <c:if
                test="${login_employee.position != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()}">
                <tr>
                    <th>承認状況</th>
                    <td><c:choose>
                            <c:when
                                test="${report.approval == AttributeConst.REP_APPLICATION.getIntegerValue()}">申請中</c:when>
                            <c:when
                                test="${report.approval == AttributeConst.REP_APPROVAL_DONE.getIntegerValue()}">承認済み</c:when>
                            <c:when
                                test="${report.approval == AttributeConst.REP_APPROVAL_REJECT.getIntegerValue()}">要再提出</c:when>
                        </c:choose></td>
                </tr>
            </c:if>
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

        <p>
            <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>