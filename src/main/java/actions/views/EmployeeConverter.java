package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Employee;

/**
 * 従業員データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class EmployeeConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param ev EmployeeViewのインスタンス
     * @return Employeeのインスタンス
     */
    public static Employee toModel(EmployeeView ev) {

        Integer departmentValue;
        Integer divisionValue;
        Integer positionValue;

        switch (ev.getDepartment()) {

        case 1:
            departmentValue = JpaConst.DEP_SALES;

        case 2:
            departmentValue = JpaConst.DEP_HUMAN_RESOURCES;

        case 3:
            departmentValue = JpaConst.DEP_INFORMATION_SYSTEMS;

        case 4:
            departmentValue = JpaConst.DEP_GENERAL;

        case 5:
            departmentValue = JpaConst.DEP_ACCOUNTING;

        default:
            departmentValue = null;
        }

        switch (ev.getDivision()) {

        case 1:
            divisionValue = JpaConst.DEP_DIV_FIRST;

        case 2:
            divisionValue = JpaConst.DEP_DIV_SECOND;

        case 3:
            divisionValue = JpaConst.DEP_DIV_THIRD;

        case 4:
            divisionValue = JpaConst.DEP_DIV_FOURTH;

        case 5:
            divisionValue = JpaConst.DEP_DIV_FIFTH;

        default:
            divisionValue = null;
        }

        switch (ev.getPosition()) {

        case 1:
            positionValue = JpaConst.DEP_POS_NORMAL;

        case 2:
            positionValue = JpaConst.DEP_POS_MANAGER;

        case 3:
            positionValue = JpaConst.DEP_POS_GENERAL_MANAGER;

        default:
            positionValue = null;
        }

        return new Employee(
                ev.getId(),
                ev.getCode(),
                ev.getName(),
                ev.getPassword(),
                departmentValue,
                divisionValue,
                positionValue,
                ev.getAdminFlag() == null
                        ? null
                        : ev.getAdminFlag() == AttributeConst.ROLE_ADMIN.getIntegerValue()
                                ? JpaConst.ROLE_ADMIN
                                : JpaConst.ROLE_GENERAL,
                ev.getCreatedAt(),
                ev.getUpdatedAt(),
                ev.getDeleteFlag() == null
                        ? null
                        : ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                ? JpaConst.EMP_DEL_TRUE
                                : JpaConst.EMP_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Employeeのインスタンス
     * @return EmployeeViewのインスタンス
     */
    public static EmployeeView toView(Employee e) {

        if(e == null) {
            return null;
        }

        Integer departmentValue;
        Integer divisionValue;
        Integer positionValue;

        switch (e.getDepartment()) {

        case 1:
            departmentValue = AttributeConst.DEP_SALES.getIntegerValue();

        case 2:
            departmentValue = AttributeConst.DEP_HUMAN_RESOURCES.getIntegerValue();

        case 3:
            departmentValue = AttributeConst.DEP_INFORMATION_SYSTEMS.getIntegerValue();

        case 4:
            departmentValue = AttributeConst.DEP_GENERAL.getIntegerValue();

        case 5:
            departmentValue = AttributeConst.DEP_ACCOUNTING.getIntegerValue();

        default:
            departmentValue = null;
        }

        switch (e.getDivision()) {

        case 1:
            divisionValue = AttributeConst.DEP_DIV_FIRST.getIntegerValue();

        case 2:
            divisionValue = AttributeConst.DEP_DIV_SECOND.getIntegerValue();

        case 3:
            divisionValue = AttributeConst.DEP_DIV_THIRD.getIntegerValue();

        case 4:
            divisionValue = AttributeConst.DEP_DIV_FOURTH.getIntegerValue();

        case 5:
            divisionValue = AttributeConst.DEP_DIV_FIFTH.getIntegerValue();

        default:
            divisionValue = null;
        }

        switch (e.getPosition()) {

        case 1:
            positionValue = AttributeConst.DEP_POS_NORMAL.getIntegerValue();

        case 2:
            positionValue = AttributeConst.DEP_POS_MANAGER.getIntegerValue();

        case 3:
            positionValue = AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue();

        default:
            positionValue = null;
        }

        return new EmployeeView(
                e.getId(),
                e.getCode(),
                e.getName(),
                e.getPassword(),
                departmentValue,
                divisionValue,
                positionValue,
                e.getAdminFlag() == null
                        ? null
                        : e.getAdminFlag() == JpaConst.ROLE_ADMIN
                                ? AttributeConst.ROLE_ADMIN.getIntegerValue()
                                : AttributeConst.ROLE_GENERAL.getIntegerValue(),
                e.getCreatedAt(),
                e.getUpdatedAt(),
                e.getDeleteFlag() == null
                        ? null
                        : e.getDeleteFlag() == JpaConst.EMP_DEL_TRUE
                                ? AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                : AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<EmployeeView> toViewList(List<Employee> list) {
        List<EmployeeView> evs = new ArrayList<>();

        for (Employee e : list) {
            evs.add(toView(e));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Employee e, EmployeeView ev) {
        e.setId(ev.getId());
        e.setCode(ev.getCode());
        e.setName(ev.getName());
        e.setPassword(ev.getPassword());
        e.setDepartment(ev.getDepartment());
        e.setDivision(ev.getDivision());
        e.setPosition(ev.getPosition());
        e.setAdminFlag(ev.getAdminFlag());
        e.setCreatedAt(ev.getCreatedAt());
        e.setUpdatedAt(ev.getUpdatedAt());
        e.setDeleteFlag(ev.getDeleteFlag());

    }

}