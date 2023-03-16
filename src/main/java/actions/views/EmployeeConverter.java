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

        return new Employee(ev.getId(), ev.getCode(), ev.getName(), ev.getPassword(),
                ev.getDepartment() == null ? null
                        : ev.getDepartment() == AttributeConst.DEP_SALES.getIntegerValue() ? JpaConst.DEP_SALES
                                : ev.getDepartment() == AttributeConst.DEP_HUMAN_RESOURCES.getIntegerValue()
                                        ? JpaConst.DEP_HUMAN_RESOURCES
                                        : ev.getDepartment() == AttributeConst.DEP_INFORMATION_SYSTEMS.getIntegerValue()
                                                ? JpaConst.DEP_INFORMATION_SYSTEMS
                                                : ev.getDepartment() == AttributeConst.DEP_GENERAL.getIntegerValue()
                                                        ? JpaConst.DEP_GENERAL
                                                        : JpaConst.DEP_ACCOUNTING,
                ev.getDivision() == null ? null
                        : ev.getDivision() == AttributeConst.DEP_DIV_FIRST.getIntegerValue() ? JpaConst.DEP_DIV_FIRST
                                : ev.getDivision() == AttributeConst.DEP_DIV_SECOND.getIntegerValue()
                                        ? JpaConst.DEP_DIV_SECOND
                                        : ev.getDivision() == AttributeConst.DEP_DIV_THIRD.getIntegerValue()
                                                ? JpaConst.DEP_DIV_THIRD
                                                : ev.getDivision() == AttributeConst.DEP_DIV_FOURTH.getIntegerValue()
                                                        ? JpaConst.DEP_DIV_FOURTH
                                                        : JpaConst.DEP_DIV_FIFTH,
                ev.getPosition() == null ? null
                        : ev.getPosition() == AttributeConst.DEP_POS_NORMAL.getIntegerValue() ? JpaConst.DEP_POS_NORMAL
                                : ev.getPosition() == AttributeConst.DEP_POS_CHIEF.getIntegerValue()
                                        ? JpaConst.DEP_POS_CHIEF
                                        : ev.getPosition() == AttributeConst.DEP_POS_MANAGER.getIntegerValue()
                                                ? JpaConst.DEP_POS_MANAGER
                                                : JpaConst.DEP_POS_GENERAL_MANAGER,
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

        return new EmployeeView(
                e.getId(),
                e.getCode(),
                e.getName(),
                e.getPassword(),
                e.getDepartment() == null ? null
                        : e.getDepartment() == JpaConst.DEP_SALES ? AttributeConst.DEP_SALES.getIntegerValue()
                                : e.getDepartment() == JpaConst.DEP_HUMAN_RESOURCES
                                        ? AttributeConst.DEP_HUMAN_RESOURCES.getIntegerValue()
                                        : e.getDepartment() == JpaConst.DEP_INFORMATION_SYSTEMS
                                                ? AttributeConst.DEP_INFORMATION_SYSTEMS.getIntegerValue()
                                                : e.getDepartment() == JpaConst.DEP_GENERAL
                                                        ? AttributeConst.DEP_GENERAL.getIntegerValue()
                                                        : AttributeConst.DEP_ACCOUNTING.getIntegerValue(),
                e.getDivision() == null ? null
                        : e.getDivision() == JpaConst.DEP_DIV_FIRST ? AttributeConst.DEP_DIV_FIRST.getIntegerValue()
                                : e.getDivision() == JpaConst.DEP_DIV_SECOND
                                        ? AttributeConst.DEP_DIV_SECOND.getIntegerValue()
                                        : e.getDivision() == JpaConst.DEP_DIV_THIRD
                                                ? AttributeConst.DEP_DIV_THIRD.getIntegerValue()
                                                : e.getDivision() == JpaConst.DEP_DIV_FOURTH
                                                        ? AttributeConst.DEP_DIV_FOURTH.getIntegerValue()
                                                        : AttributeConst.DEP_DIV_FIFTH.getIntegerValue(),
                e.getPosition() == null ? null
                        : e.getPosition() == JpaConst.DEP_POS_NORMAL ? AttributeConst.DEP_POS_NORMAL.getIntegerValue()
                                : e.getPosition() == JpaConst.DEP_POS_CHIEF
                                        ? AttributeConst.DEP_POS_CHIEF.getIntegerValue()
                                        : e.getPosition() == JpaConst.DEP_POS_MANAGER
                                                ? AttributeConst.DEP_POS_MANAGER.getIntegerValue()
                                                : AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue(),
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