package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.PetitionView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.EmployeeService;
import services.PetitionService;
import services.ReportService;

/**
 * 日報に関する処理を行うActionクラス
 *
 */

public class ReportAction extends ActionBase {

    private ReportService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * ログインしている従業員と同じ部署の日報データを取得しindexを呼び出す
     * @throws ServletException
     * @throws IOException
     */
    public void indexDep() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();

        //ログイン中の従業員を取得
        EmployeeView ev = getSessionScope(AttributeConst.LOGIN_EMP);

        putSessionScope(AttributeConst.VIEW_SELECT, AttributeConst.VIEW_GET_DEPARTMENT.getIntegerValue());

        //承認済みの日報データを取得
        List<ReportView> reports = service.getPerPageByDepartment(ev.getDepartment(), page);
        //日報データの件数を取得
        long reportsCount = service.countByDepartment(ev.getDepartment());

        index(reports, reportsCount, page);

        }

    /**
     * 全ての日報データを取得しindexを呼び出す
     * @throws ServletException
     * @throws IOException
     */
    public void indexAll() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();

        putSessionScope(AttributeConst.VIEW_SELECT, AttributeConst.VIEW_GET_ALL.getIntegerValue());

        //承認済みの日報データを取得
        List<ReportView> reports = service.getAllPerPage(page);
        //日報データの件数を取得
        long reportsCount = service.countAll();

        index(reports, reportsCount, page);

        }


    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @return IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //日報情報の空インスタンスに、日報の日付⇔今日の日付を設定する
        ReportView rv = new ReportView();
        rv.setReportDate(LocalDate.now());
        putRequestScope(AttributeConst.REPORT, rv); //日付のみ設定済みの日報インスタンス

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //ログイン中の従業員が部長以外の場合は、承認者のリストをリクエストスコープにセットする
        if (ev.getPosition() != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {
            List<EmployeeView> superiorList = new EmployeeService().getSuperiorEmp(ev);
            putRequestScope(AttributeConst.EMPLOYEE_SUPERIORS, superiorList);
        }

        //新規登録画面を表示
        forward(ForwardConst.FW_REP_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //日報の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.REP_DATE) == null
                    || getRequestParam(AttributeConst.REP_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.REP_DATE));
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            EmployeeView approver = null; //承認者

            //ログイン中の従業員が部長以外の場合は、承認者をパラメーターから取得する
            if (ev.getPosition() != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {

                approver = new EmployeeService()
                        .findOne(Integer.parseInt(getRequestParam(AttributeConst.EMPLOYEE_SUPERIOR)));
            }

            //パラメータの値をもとに日報情報のインスタンスを作成する
            ReportView rv = new ReportView(
                    null,
                    ev, //ログインしている従業員を、日報作成者として登録する
                    day,
                    getRequestParam(AttributeConst.REP_TITLE),
                    getRequestParam(AttributeConst.REP_CONTENT),
                    null,
                    approver,
                    null,
                    null);

            //日報情報登録
            List<String> errors = service.create(rv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策
                putRequestScope(AttributeConst.REPORT, rv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //ログイン中の従業員が部長以外の場合は、承認者のリストをリクエストスコープにセットする
                if (ev.getPosition() != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {
                    List<EmployeeView> superiorList = new EmployeeService().getSuperiorEmp(ev);
                    putRequestScope(AttributeConst.EMPLOYEE_SUPERIORS, superiorList);
                }

                //新規登録画面を再表示
                forward(ForwardConst.FW_REP_NEW);
            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                if (ev.getPosition() == AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());
                } else {
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_APPLIED.getMessage());
                }

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX_DEP);
            }
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に日報データを取得する
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        if (rv == null) {
            //該当の日報データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, rv); //取得した日報データ

            //詳細画面を表示
            forward(ForwardConst.FW_REP_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に日報データを取得する
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //日報データが承認済み以外の場合は編集可能
        if (ev.getPosition() == AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()
                || rv.getApproval() != AttributeConst.REP_APPROVAL_DONE.getIntegerValue()) {

            if (rv == null || ev.getId() != rv.getEmployee().getId()) {
                //該当の日報データが存在しない、または
                //ログインしている従業員が日報の作成者ではない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
            } else {

                //ログイン中の従業員が部長以外の場合は、承認者のリストをリクエストスコープにセットする
                if (ev.getPosition() != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {
                    List<EmployeeView> superiorList = new EmployeeService().getSuperiorEmp(ev);
                    putRequestScope(AttributeConst.EMPLOYEE_SUPERIORS, superiorList);
                }

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv); //取得した日報データ

                //編集画面を表示
                forward(ForwardConst.FW_REP_EDIT);
            }

        } else {
            //日報データが承認済みの場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
        }
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に日報データを取得する
            ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //入力された日報内容を設定する
            rv.setReportDate(toLocalDate(getRequestParam(AttributeConst.REP_DATE)));
            rv.setTitle(getRequestParam(AttributeConst.REP_TITLE));
            rv.setContent(getRequestParam(AttributeConst.REP_CONTENT));


            PetitionView pv = null; //申請データ

            //ログイン中の従業員が部長以外の場合は、承認者をパラメーターから取得する
            if (ev.getPosition() != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {

                EmployeeView approver = new EmployeeService()
                        .findOne(Integer.parseInt(getRequestParam(AttributeConst.EMPLOYEE_SUPERIOR)));
                rv.setApprover(approver);

                //承認状況が再提出の場合は、申請中に変更する
                if(rv.getApproval() == AttributeConst.REP_APPROVAL_REJECT.getIntegerValue()) {
                    rv.setApproval(AttributeConst.REP_APPLICATION.getIntegerValue());
                }

                pv = new PetitionService().findByReport(rv);
            }

            //日報データを更新する
            List<String> errors = service.update(rv, pv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //ログイン中の従業員が部長以外の場合は、承認者のリストをリクエストスコープにセットする
                if (ev.getPosition() != AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {
                    List<EmployeeView> superiorList = new EmployeeService().getSuperiorEmp(ev);
                    putRequestScope(AttributeConst.EMPLOYEE_SUPERIORS, superiorList);
                }

                //編集画面を再表示
                forward(ForwardConst.FW_REP_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                if (ev.getPosition() == AttributeConst.DEP_POS_GENERAL_MANAGER.getIntegerValue()) {
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());
                } else {
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_REAPPLIED.getMessage());
                }

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX_DEP);
            }
        }
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    private void index(List<ReportView> reports, long reportsCount, int page) throws ServletException, IOException {

        putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, reportsCount); //全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_REP_INDEX);
        }


}
