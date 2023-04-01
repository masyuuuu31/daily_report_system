<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<label for="${AttributeConst.EMP_CODE.getValue()}">社員番号</label>
<br>
<input type="text" name="${AttributeConst.EMP_CODE.getValue()}"
    id="${AttributeConst.EMP_CODE.getValue()}" value="${employee.code}" />
<br>
<br>

<label for="${AttributeConst.EMP_NAME.getValue()}">氏名</label>
<br>
<input type="text" name="${AttributeConst.EMP_NAME.getValue()}"
    id="${AttributeConst.EMP_NAME.getValue()}" value="${employee.name}" />
<br>
<br>

<label for="${AttributeConst.EMP_PASS.getValue()}">パスワード</label>
<br>
<input type="password" name="${AttributeConst.EMP_PASS.getValue()}"
    id="${AttributeConst.EMP_PASS.getValue()}" value="${employee.password}" />
<br>
<br>


<label for="${AttributeConst.EMP_DEP.getValue()}">部署</label>
<br>
<select name="${AttributeConst.EMP_DEP.getValue()}"
    id="${AttributeConst.EMP_DEP.getValue()}">
    <option value="${AttributeConst.DEP_SALES.getIntegerValue()}"
        <c:if test="${employee.department == AttributeConst.DEP_SALES.getIntegerValue()}"> selected</c:if>>営業部</option>
    <option value="${AttributeConst.DEP_HUMAN_RESOURCES.getIntegerValue()}"
        <c:if test="${employee.department == AttributeConst.DEP_HUMAN_RESOURCES.getIntegerValue()}"> selected</c:if>>人事部</option>
    <option value="${AttributeConst.DEP_INFORMATION_SYSTEMS.getIntegerValue()}"
        <c:if test="${employee.department == AttributeConst.DEP_INFORMATION_SYSTEMS.getIntegerValue()}"> selected</c:if>>情報システム部</option>
    <option value="${AttributeConst.DEP_GENERAL.getIntegerValue()}"
        <c:if test="${employee.department == AttributeConst.DEP_GENERAL.getIntegerValue()}"> selected</c:if>>総務部</option>
    <option value="${AttributeConst.DEP_ACCOUNTING.getIntegerValue()}"
        <c:if test="${employee.department == AttributeConst.DEP_ACCOUNTING.getIntegerValue()}"> selected</c:if>>経理部</option>
</select>
<br>
<br>

<label for="${AttributeConst.EMP_DIV.getValue()}">所属ユニット</label>
<br>
<select name="${AttributeConst.EMP_DIV.getValue()}"
    id="${AttributeConst.EMP_DIV.getValue()}">
    <option value="${AttributeConst.DEP_DIV_FIRST.getIntegerValue()}"
        <c:if test="${employee.division == AttributeConst.DEP_DIV_FIRST.getIntegerValue()}"> selected</c:if>>第1ユニット</option>
    <option value="${AttributeConst.DEP_DIV_SECOND.getIntegerValue()}"
        <c:if test="${employee.division == AttributeConst.DEP_DIV_SECOND.getIntegerValue()}"> selected</c:if>>第2ユニット</option>
    <option value="${AttributeConst.DEP_DIV_THIRD.getIntegerValue()}"
        <c:if test="${employee.division == AttributeConst.DEP_DIV_THIRD.getIntegerValue()}"> selected</c:if>>第3ユニット</option>
    <option value="${AttributeConst.DEP_DIV_FOURTH.getIntegerValue()}"
        <c:if test="${employee.division == AttributeConst.DEP_DIV_FOURTH.getIntegerValue()}"> selected</c:if>>第4ユニット</option>
    <option value="${AttributeConst.DEP_DIV_FIFTH.getIntegerValue()}"
        <c:if test="${employee.division == AttributeConst.DEP_DIV_FIFTH.getIntegerValue()}"> selected</c:if>>第5ユニット</option>
</select>
<br>
<br>

<label for="${AttributeConst.EMP_POSITION.getValue()}">役職</label>
<br>
<select name="${AttributeConst.EMP_POSITION.getValue()}"
    id="${AttributeConst.EMP_POSITION.getValue()}">
    <option value="${AttributeConst.DEP_POS_NORMAL.getIntegerValue()}"
        <c:if test="${employee.position == AttributeConst.DEP_POS_NORMAL.getIntegerValue()}"> selected</c:if>>一般社員</option>
    <option value="${AttributeConst.DEP_POS_CHIEF.getIntegerValue()}"
        <c:if test="${employee.position == AttributeConst.DEP_POS_CHIEF.getIntegerValue()}"> selected</c:if>>主任</option>
    <option value="${AttributeConst.DEP_POS_MANAGER.getIntegerValue()}"
        <c:if test="${employee.position == AttributeConst.DEP_POS_MANAGER.getIntegerValue()}"> selected</c:if>>課長</option>
    <option value="${AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()}"
        <c:if test="${employee.position == AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()}"> selected</c:if>>部長</option>
</select>
<br>
<br>


<label for="${AttributeConst.EMP_ADMIN_FLG.getValue()}">権限</label>
<br>
<select name="${AttributeConst.EMP_ADMIN_FLG.getValue()}"
    id="${AttributeConst.EMP_ADMIN_FLG.getValue()}">
    <option value="${AttributeConst.ROLE_GENERAL.getIntegerValue()}"
        <c:if test="${employee.adminFlag == AttributeConst.ROLE_GENERAL.getIntegerValue()}"> selected</c:if>>一般</option>
    <option value="${AttributeConst.ROLE_ADMIN.getIntegerValue()}"
        <c:if test="${employee.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}"> selected</c:if>>管理者</option>
</select>
<br>
<br>

<input type="hidden" name="${AttributeConst.EMP_ID.getValue()}"
    value="${employee.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}"
    value="${_token}" />
<button type="submit" class="btn btn-light btn-outline-dark">投稿</button>