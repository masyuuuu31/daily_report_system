package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Report;

/**
 * 日報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */

public class ReportConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    public static Report toModel(ReportView rv) {

        Integer approvalValue = null;

        if (rv.getApproval() != null) {

            switch (rv.getApproval()) {

            case 0:
                approvalValue = JpaConst.REP_APPLICATION;
                break;

            case 1:
                approvalValue = JpaConst.REP_APPROVAL_DONE;
                break;

            case 2:
                approvalValue = JpaConst.REP_APPROVAL_REJECT;
                break;

            default:
                System.out.println("値を取得できませんでした。"); //デバック用出力
            }
        }

        return new Report(
                rv.getId(),
                EmployeeConverter.toModel(rv.getEmployee()),
                rv.getReportDate(),
                rv.getTitle(),
                rv.getContent(),
                approvalValue,
                rv.getCreatedAt(),
                rv.getUpdatedAt());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static ReportView toView(Report r) {

        if (r == null) {
            return null;
        }

        Integer approvalValue = null;

        if (r.getApproval() != null) {

            switch (r.getApproval()) {

            case 0:
                approvalValue = AttributeConst.REP_APPLICATION.getIntegerValue();
                break;

            case 1:
                approvalValue = AttributeConst.REP_APPROVAL_DONE.getIntegerValue();
                break;

            case 2:
                approvalValue = AttributeConst.REP_APPROVAL_REJECT.getIntegerValue();
                break;

            default:
                System.out.println("値を取得できませんでした。"); //デバック用出力
            }
        }

        return new ReportView(
                r.getId(),
                EmployeeConverter.toView(r.getEmployee()),
                r.getReportDate(),
                r.getTitle(),
                r.getContent(),
                approvalValue,
                r.getCreatedAt(),
                r.getUpdatedAt());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ReportView> toViewList(List<Report> list) {
        List<ReportView> rvs = new ArrayList<>();

        for (Report r : list) {
            rvs.add(toView(r));
        }

        return rvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Report r, ReportView rv) {
        r.setId(rv.getId());
        r.setEmployee(EmployeeConverter.toModel(rv.getEmployee()));
        r.setReportDate(rv.getReportDate());
        r.setTitle(rv.getTitle());
        r.setContent(rv.getContent());
        r.setCreatedAt(rv.getCreatedAt());
        r.setUpdatedAt(rv.getUpdatedAt());
    }
}
