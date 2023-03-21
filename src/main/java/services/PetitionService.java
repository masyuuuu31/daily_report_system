package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.PetitionConverter;
import actions.views.PetitionView;
import constants.AttributeConst;
import constants.JpaConst;
import models.Petition;

/**
 * 申請データのテーブル操作に関わる処理を行うクラス
 */

public class PetitionService extends ServiceBase {

    /**
     * 承認依頼中の日報データを、指定されたページ数の一覧画面に表示する分取得しPetitionViewのリストで返却する
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
     * 承認依頼中の日報データの件数を取得し、返却する
     * @param employee
     * @return 日報データの件数
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
     * 画面から入力された日報の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param rv 日報の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public void create(PetitionView pv) {

            //登録、更新日時を現在日時に設定
            LocalDateTime ldt = LocalDateTime.now();
            pv.setCreatedAt(ldt);
            pv.setUpdatedAt(ldt);
            createInternal(pv);
        }

    /**
     * 承認依頼中の日報データを既読に更新
     * @param rv 日報の更新内容
     */
    public void update(PetitionView pv) {

            pv.setRead(AttributeConst.PET_READ_TRUE.getIntegerValue()); //既読に更新

            //更新日時を現在日時に設定
            LocalDateTime ldt = LocalDateTime.now();
            pv.setUpdatedAt(ldt);

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
     * 承認依頼中の日報データを1件登録する
     * @param pv 承認依頼中の日報データ
     */
    private void createInternal(PetitionView pv) {

        em.getTransaction().begin();
        em.persist(PetitionConverter.toModel(pv));
        em.getTransaction().commit();
    }

    /**
     * 承認依頼中の日報データを更新する
     * @param pv 承認依頼中の日報データ
     */
    private void updateInternal(PetitionView pv) {

        em.getTransaction().begin();
        Petition p = findOneInternal(pv.getId());
        PetitionConverter.copyViewToModel(p, pv);
        em.getTransaction().commit();
    }

}
