package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Petition;

/**
 * 申請情報データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */

public class PetitionConverter {

    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param pv PetitionViewのインスタンス
     * @return Petitionのインスタンス
     */
    public static Petition toModel(PetitionView pv) {

        return new Petition(
                pv.getId(),
                EmployeeConverter.toModel(pv.getSendTo()),
                EmployeeConverter.toModel(pv.getSendFrom()),
                ReportConverter.toModel(pv.getReport()),
                pv.getCreatedAt(),
                pv.getUpdatedAt()
                );
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param p Petitionのインスタンス
     * @return PetitionViewのインスタンス
     */
    public static PetitionView toView(Petition p) {

        if (p == null) {
            return null;
        }

        return new PetitionView(
                p.getId(),
                EmployeeConverter.toView(p.getSendTo()),
                EmployeeConverter.toView(p.getSendFrom()),
                ReportConverter.toView(p.getReport()),
                p.getCreatedAt(),
                p.getUpdatedAt()
                );
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<PetitionView> toViewList(List<Petition> list) {
        List<PetitionView> pvs = new ArrayList<>();

        for (Petition p : list) {
            pvs.add(toView(p));
        }
        return pvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param p DTOモデル（コピー先）
     * @param pv Viewモデル（コピー元）
     */
    public static void copyViewToModel(Petition p, PetitionView pv) {
        p.setId(pv.getId());
        p.setSendTo(EmployeeConverter.toModel(pv.getSendTo()));
        p.setSendFrom(EmployeeConverter.toModel(pv.getSendFrom()));
        p.setReport(ReportConverter.toModel(pv.getReport()));
        p.setCreatedAt(pv.getCreatedAt());
        p.setUpdatedAt(pv.getUpdatedAt());
    }
}
