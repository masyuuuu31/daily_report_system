package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.PetitionView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.JpaConst;
import models.Report;
import models.validators.ReportValidator;

/**
 * 日報テーブルの操作に関わる処理を行うクラス
 */

public class ReportService extends ServiceBase {

    /**
     * 指定した従業員が作成した日報データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param employee 従業員
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getMinePerPage(EmployeeView employee, int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL_MINE, Report.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * 指定した従業員が作成した日報データの件数を取得し、返却する
     * @param employee
     * @return 日報データの件数
     */
    public long countAllMine(EmployeeView employee) {

        long count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_ALL_MINE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, EmployeeConverter.toModel(employee))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する日報データを取得し、ReportViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getAllPerPage(int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_ALL, Report.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return ReportConverter.toViewList(reports);
    }

    /**
     * 日報テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT, Long.class)
                .getSingleResult();
        return reports_count;
    }

    /**
     * 所属部署を条件に指定されたページ数の一覧画面に表示する日報データを取得し、ReportViewのリストで返却する
     * @param department 所属部署
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getPerPageByDepartment(Integer department, int page) {

        List<Report> reports = em.createNamedQuery(JpaConst.Q_REP_GET_BY_DEPARTMENT_ALL, Report.class)
                .setParameter(JpaConst.JPQL_PARM_DEPARTMENT, department)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return ReportConverter.toViewList(reports);
    }

    /**
     * 所属部署を条件に日報テーブルのデータの件数を取得し、返却する
     * @param department 所属部署
     * @return データの件数
     */
    public long countByDepartment(Integer department) {
        long reports_count = (long) em.createNamedQuery(JpaConst.Q_REP_COUNT_BY_DEPARTMENT, Long.class)
                .setParameter(JpaConst.JPQL_PARM_DEPARTMENT, department)
                .getSingleResult();
        return reports_count;
    }

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ReportView findOne(int id) {
        return ReportConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された日報の登録内容を元にデータを1件作成し、日報テーブルに登録する
     * @param rv 日報の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ReportView rv) {
        List<String> errors = ReportValidator.validate(rv);
        if (errors.size() == 0) {

            if (rv.getEmployee().getPosition() != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {
                rv.setApproval(AttributeConst.REP_APPLICATION.getIntegerValue()); //役職が部長以外の場合、申請中に設定
            } else {
                rv.setApproval(AttributeConst.REP_APPROVAL_DONE.getIntegerValue()); //役職が役職が部長の場合、承認済みに設定
            }

            LocalDateTime ldt = LocalDateTime.now();
            rv.setCreatedAt(ldt);
            rv.setUpdatedAt(ldt);
            createInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーが無ければ0件のリスト）
        return errors;
    }

    /**
     * 画面から入力された日報の登録内容を元に、日報データを更新する
     * @param rv 日報の更新内容
     * @param pv 申請データ
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(ReportView rv, PetitionView pv) {

        //バリデーションを行う
        List<String> errors = ReportValidator.validate(rv);

        if (errors.size() == 0) {

            //更新日時を現在日時に設定
            LocalDateTime ldt = LocalDateTime.now();
            rv.setUpdatedAt(ldt);

            updateInternal(rv);

            //申請データが存在する場合、申請テーブルを更新する
            if (pv != null) {
                //更新済みのrvを取得
                ReportView savedRv = findOne(rv.getId());
                pv.setReport(savedRv);
                new PetitionService().update(pv, true);
            }

        }
        //バリデーションで発生したエラーを返却（エラーが無ければ0件のリスト）
        return errors;
    }

    /**
     * 申請データ更新時に日報データを更新する（承認状況、更新日時）
     * @param pv 申請データ
     */
    public void update(PetitionView pv) {

        //更新日時を現在日時に設定
        LocalDateTime ldt = LocalDateTime.now();
        pv.getReport().setUpdatedAt(ldt);
        updateInternal(pv.getReport());
    }

    /**
     * idを条件にデータを一件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Report findOneInternal(int id) {
        return em.find(Report.class, id);
    }

    /**
     * 日報データを1件登録する
     * @param rv 日報データ
     */
    private void createInternal(ReportView rv) {

        em.getTransaction().begin();
        Report r = ReportConverter.toModel(rv);
        em.persist(r);
        em.getTransaction().commit();

        //承認者が設定されている場合申請データを登録する
        if (rv.getApprover() != null) {
            new PetitionService().create(r);
            System.out.println("create done");
        }
    }

    /**
     * 日報データを更新する
     * @param rv 日報データ
     */
    private void updateInternal(ReportView rv) {

        em.getTransaction().begin();
        Report r = findOneInternal(rv.getId());
        ReportConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();

    }
}
