package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.PetitionConverter;
import actions.views.PetitionView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.JpaConst;
import models.Petition;
import models.Report;

/**
 * 申請データのテーブル操作に関わる処理を行うクラス
 */

public class PetitionService extends ServiceBase {

    /**
     * 申請データを、指定されたページ数の一覧画面に表示する分取得しPetitionViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<PetitionView> getAllPetitionList(EmployeeView employee, int page) {

        List<Petition> petitions = em.createNamedQuery(JpaConst.Q_PET_GET_ALL, Petition.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return PetitionConverter.toViewList(petitions);
    }

    /**
     * 申請データの件数を取得し、返却する
     * @param employee
     * @return 申請データの件数
     */
    public long countAllPetition(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_PET_COUNT_ALL, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * idを条件に取得したデータをPetitionViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public PetitionView findOne(int id) {
        return PetitionConverter.toView(findOneInternal(id));
    }

    /**
     * reportを条件に取得したデータをPetitionViewのインスタンスで返却する
     * @param report
     * @return 取得データのインスタンス
     */
    public PetitionView findByReport(ReportView report) {
        Petition pv = null;
        try {
            pv = em.createNamedQuery(JpaConst.Q_PET_GET_BY_REPORT, Petition.class)
                    .setParameter(JpaConst.JPQL_PARM_REPORT, ReportConverter.toModel(report))
                    .getSingleResult();
        } catch (NoResultException e) {
        }
        return PetitionConverter.toView(pv);
    }

    /**
     * 画面から入力された日報の登録内容を元に申請データを1件作成し、申請テーブルに登録する
     * @param rv 画面から入力された日報データ
     */
    public void create(Report r) {

        //登録、更新日時を現在日時に設定

            PetitionView pv = new PetitionView(
                    null,
                    EmployeeConverter.toView(r.getApprover()), //承認者
                    EmployeeConverter.toView(r.getEmployee()), //申請者
                    ReportConverter.toView(r), //日報データ
                    AttributeConst.PET_READ_FALSE.getIntegerValue(), //初期状態は未読
                    LocalDateTime.now(),
                    LocalDateTime.now()
                    );

            createInternal(pv);
        }

    /**
     * 申請データを更新
     * @param pv 申請データの更新内容
     * @param frs 呼び出し元判定フラグ（True / False）
     */
    public void update(PetitionView pv, boolean frs) {

            //更新日時を現在日時に設定
            LocalDateTime ldt = LocalDateTime.now();
            pv.setUpdatedAt(ldt);

            //PetitionAction update()から呼び出された場合は、ReportService.update(pv)を呼び出す
            if (frs == false) {
                new ReportService().update(pv);
                pv.setReadStatus(AttributeConst.PET_READ_TRUE.getIntegerValue()); //既読に更新
            } else {
                //日報が再申請された場合、承認者を変更する
                pv.setSendTo(pv.getReport().getApprover());
                pv.setReadStatus(AttributeConst.PET_READ_FALSE.getIntegerValue()); //未読に更新
            }

            updateInternal(pv);
        }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Petition findOneInternal(int id) {
        return em.find(Petition.class, id);
    }

    /**
     * 申請データを1件登録する
     * @param pv 申請データ
     */
    private void createInternal(PetitionView pv) {

        em.getTransaction().begin();
        em.persist(PetitionConverter.toModel(pv));
        em.getTransaction().commit();
    }

    /**
     * 申請データを更新する
     * @param pv 申請データ
     */
    private void updateInternal(PetitionView pv) {

        em.getTransaction().begin();
        Petition p = findOneInternal(pv.getId());
        PetitionConverter.copyViewToModel(p, pv);
        em.getTransaction().commit();
    }

}
