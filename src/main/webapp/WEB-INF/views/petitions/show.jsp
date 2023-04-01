<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actPet" value="${ForwardConst.ACT_PET.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>申請　詳細ページ</h2>

        <table>
            <tr>
                <th>氏名</th>
                <td><c:out value="${petition.report.employee.name}" /></td>
            <tr>
            <tr>
                <th>日付</th>
                <fmt:parseDate value="${petition.report.reportDate}"
                    pattern="yyyy-MM-dd" var="reportDay" type="date" />
                <td><fmt:formatDate value="${reportDay}" pattern="yyyy-MM-dd" /></td>
            </tr>
            <tr>
                <th>内容</th>
                <td><pre><c:out value="${petition.report.content}" /></pre></td>
            </tr>
            <tr>
                <th>登録日時</th>
                <fmt:parseDate value="${petition.report.createdAt}"
                    pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                <td><fmt:formatDate value="${createDay}"
                        pattern="yyyy-MM-dd HH:mm:ss" /></td>
            </tr>
            <tr>
                <th>更新日時</th>
                <fmt:parseDate value="${petition.report.updatedAt}"
                    pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                <td><fmt:formatDate value="${updateDay}"
                        pattern="yyyy-MM-dd HH:mm:ss" /></td>
            </tr>
        </table>
        <br>

        <form method="POST" action="<c:url value='?action=${actPet}&command=${commUpd}' />">
            <button type="submit" class="btn btn-light btn-outline-dark"
                name = "${AttributeConst.REP_APPROVAL.getValue()}"
                value="${AttributeConst.REP_APPROVAL_DONE.getIntegerValue()}">承認</button>
            <button type="submit" class="btn btn-light btn-outline-dark"
                name="${AttributeConst.REP_APPROVAL.getValue()}"
                value="${AttributeConst.REP_APPROVAL_REJECT.getIntegerValue()}">再提出</button>

            <input type="hidden" name="${AttributeConst.PET_ID.getValue()}"value="${petition.id}" />
            <input type="hidden" name="${AttributeConst.TOKEN.getValue()}"value="${_token}" />
        </form>
        <br>

        <p>
            <a href="<c:url value='?action=${actPet}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>