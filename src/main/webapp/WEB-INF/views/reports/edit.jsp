<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst"%>

<c:set var="commIdxDep" value="${ForwardConst.CMD_INDEX_DEP.getValue()}" />
<c:set var="commIdxAll" value="${ForwardConst.CMD_INDEX_ALL.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commUpd" value="${ForwardConst.CMD_UPDATE.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <h2>日報　編集ページ</h2>
        <form method="POST" action="<c:url value='?action=${actRep}&command=${commUpd}' />">
            <c:import url="edit_form.jsp"/>
        </form>

        <c:choose>
            <c:when test="${sessionScope.view_select == AttributeConst.VIEW_GET_DEPARTMENT.getIntegerValue()}">
                <p>
                    <a href="<c:url value='?action=${actRep}&command=${commIdxDep}' />">一覧に戻る</a>
                </p>
            </c:when>
            <c:otherwise>
                <p>
                    <a href="<c:url value='?action=${actRep}&command=${commIdxAll}' />">一覧に戻る</a>
                </p>
            </c:otherwise>
        </c:choose>

    </c:param>
</c:import>