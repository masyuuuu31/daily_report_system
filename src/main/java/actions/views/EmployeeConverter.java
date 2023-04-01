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

        Integer department = null;
        Integer division = null;
        Integer position = null;

        if (ev.getDepartment() != null) {

            switch (ev.getDepartment()) {

            case 1:
                department = JpaConst.DEP_SALES;
                break;

            case 2:
                department = JpaConst.DEP_HUMAN_RESOURCES;
                break;

            case 3:
                department = JpaConst.DEP_INFORMATION_SYSTEMS;
                break;

            case 4:
                department = JpaConst.DEP_GENERAL;
                break;

            case 5:
                department = JpaConst.DEP_ACCOUNTING;
                break;

            }
        }

        if (ev.getDivision() != null) {

            switch (ev.getDivision()) {

            case 1:
                division = JpaConst.DEP_DIV_FIRST;
                break;

            case 2:
                division = JpaConst.DEP_DIV_SECOND;
                break;

            case 3:
                division = JpaConst.DEP_DIV_THIRD;
                break;

            case 4:
                division = JpaConst.DEP_DIV_FOURTH;
                break;

            case 5:
                division = JpaConst.DEP_DIV_FIFTH;
                break;

            }
        }

        if (ev.getPosition() != null) {

            switch (ev.getPosition()) {

            case 1:
                position = JpaConst.DEP_POS_NORMAL;
                break;

            case 2:
                position = JpaConst.DEP_POS_CHIEF;
                break;

            case 3:
                position = JpaConst.DEP_POS_MANAGER;
                break;

            case 4:
                position = JpaConst.DEP_POS_GENERAL_MANAGER;
                break;

            }
        }

        return new Employee(ev.getId(), ev.getCode(), ev.getName(), ev.getPassword(),
                department,
                division,
                position,
                ev.getAdminFlag() == null ? null
                        : ev.getAdminFlag() == AttributeConst.ROLE_ADMIN.getIntegerValue() ? JpaConst.ROLE_ADMIN
                                : JpaConst.ROLE_GENERAL,
                ev.getCreatedAt(), ev.getUpdatedAt(),
                ev.getDeleteFlag() == null ? null
                        : ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue() ? JpaConst.EMP_DEL_TRUE
                                : JpaConst.EMP_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param e Employeeのインスタンス
     * @return EmployeeViewのインスタンス
     */
    public static EmployeeView toView(Employee e) {

        if (e == null) {
            return null;
        }

        Integer department = null;
        Integer division = null;
        Integer position = null;

        if (e.getDepartment() != null) {

            switch (e.getDepartment()) {

            case 1:
                department = AttributeConst.DEP_SALES.getIntegerValue();
                break;

            case 2:
                department = AttributeConst.DEP_HUMAN_RESOURCES.getIntegerValue();
                break;

            case 3:
                department = AttributeConst.DEP_INFORMATION_SYSTEMS.getIntegerValue();
                break;

            case 4:
                department = AttributeConst.DEP_GENERAL.getIntegerValue();
                break;

            case 5:
                department = AttributeConst.DEP_ACCOUNTING.getIntegerValue();
                break;

            }
        }

        if (e.getDivision() != null) {

            switch (e.getDivision()) {

            case 1:
                division = AttributeConst.DEP_DIV_FIRST.getIntegerValue();
                break;

            case 2:
                division = AttributeConst.DEP_DIV_SECOND.getIntegerValue();
                break;

            case 3:
                division = AttributeConst.DEP_DIV_THIRD.getIntegerValue();
                break;

            case 4:
                division = AttributeConst.DEP_DIV_FOURTH.getIntegerValue();
                break;

            case 5:
                division = AttributeConst.DEP_DIV_FIFTH.getIntegerValue();
                break;

            }
        }

        if (e.getPosition() != null) {

            switch (e.getPosition()) {

            case 1:
                position = AttributeConst.DEP_POS_NORMAL.getIntegerValue();
                break;

            case 2:
                position = AttributeConst.DEP_POS_CHIEF.getIntegerValue();
                break;

            case 3:
                position = AttributeConst.DEP_POS_MANAGER.getIntegerValue();
                break;

            case 4:
                position = AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue();
                break;

            }
        }

        return new EmployeeView(
                e.getId(),
                e.getCode(),
                e.getName(),
                e.getPassword(),
                department,
                division,
                position,
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