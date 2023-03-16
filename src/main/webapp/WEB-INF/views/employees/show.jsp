<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst"%>
<%@ page import="constants.AttributeConst"%>

<c:set var="actEmp" value="${ForwardConst.ACT_EMP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>id : ${employee.id}の従業員情報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>社員番号</th>
                    <td><c:out value="${employee.code}" /></td>
                <tr>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${employee.name}" /></td>
                </tr>
                <tr>
                    <th>所属部署</th>
                    <td><c:choose>
                            <c:when
                                test="${employee.department == AttributeConst.DEP_SALES.getIntegerValue()}">営業部</c:when>
                            <c:when
                                test="${employee.department == AttributeConst.DEP_HUMAN_RESOURCES.getIntegerValue()}">人事部</c:when>
                            <c:when
                                test="${employee.department == AttributeConst.DEP_INFORMATION_SYSTEMS.getIntegerValue()}">情報システム部</c:when>
                            <c:when
                                test="${employee.department == AttributeConst.DEP_GENERAL.getIntegerValue()}">総務部</c:when>
                            <c:when
                                test="${employee.department == AttributeConst.DEP_ACCOUNTING.getIntegerValue()}">経理部</c:when>
                        </c:choose></td>
                </tr>
                <tr>
                    <th>所属ユニット</th>
                    <td><c:choose>
                            <c:when
                                test="${employee.division == AttributeConst.DEP_DIV_FIRST.getIntegerValue()}">第1ユニット</c:when>
                            <c:when
                                test="${employee.division == AttributeConst.DEP_DIV_SECOND.getIntegerValue()}">第2ユニット</c:when>
                            <c:when
                                test="${employee.division == AttributeConst.DEP_DIV_THIRD.getIntegerValue()}">第3ユニット</c:when>
                            <c:when
                                test="${employee.division == AttributeConst.DEP_DIV_FOURTH.getIntegerValue()}">第4ユニット</c:when>
                            <c:when
                                test="${employee.division == AttributeConst.DEP_DIV_FIFTH.getIntegerValue()}">第5ユニット</c:when>
                        </c:choose>
                </tr>
                <tr>
                    <th>役職</th>
                    <td><c:choose>
                            <c:when
                                test="${employee.position == AttributeConst.DEP_POS_NORMAL.getIntegerValue()}">一般社員</c:when>
                            <c:when
                                test="${employee.position == AttributeConst.DEP_POS_CHIEF.getIntegerValue()}">主任</c:when>
                            <c:when
                                test="${employee.position == AttributeConst.DEP_POS_MANAGER.getIntegerValue()}">課長</c:when>
                            <c:when
                                test="${employee.position == AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()}">部長</c:when>
                        </c:choose></td>
                </tr>
                <tr>
                    <th>権限</th>
                    <td><c:choose>
                            <c:when
                                test="${employee.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}">管理者</c:when>
                            <c:otherwise>一般</c:otherwise>
                        </c:choose></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${employee.createdAt}"
                        pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${employee.updatedAt}"
                        pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}"
                            pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a
                href="<c:url value='?action=${actEmp}&command=${commEdit}&id=${employee.id}' />">この従業員情報を編集する</a>
        </p>

        <p>
            <a href="<c:url value='?action=${actEmp}&command=${commIdx}' />">一覧に戻る</a>
        </p>

    </c:param>
</c:import>
