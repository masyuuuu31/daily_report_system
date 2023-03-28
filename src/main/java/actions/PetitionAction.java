package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.PetitionView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.PetitionService;

/**
 * 申請データに関する処理を行うActionクラス
 *
 */
public class PetitionAction extends ActionBase {

    private PetitionService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new PetitionService();

        //メソッドを実行
        invoke();
        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        if (checkSuperior()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //指定されたページ数の一覧画面に表示する申請データを取得
            int page = getPage();
            List<PetitionView> petitions = service.getAllPetitionList(loginEmployee, page);

            //申請データの件数を取得
            long petitionsCount = service.countAllPetition(loginEmployee);

            putRequestScope(AttributeConst.PETITIONS, petitions);
            putRequestScope(AttributeConst.PET_COUNT, petitionsCount);
            putRequestScope(AttributeConst.PAGE, page);
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE);

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_PET_INDEX);
        }
    }

    /**
     * 詳細/編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        if (checkSuperior()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView loginEmployee = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //idを条件に申請データを取得する
            PetitionView pv = service.findOne(toNumber(getRequestParam(AttributeConst.PET_ID)));

            if (pv == null || pv.getSendTo().getId() != loginEmployee.getId()) {
                //該当の申請データが存在しない、または、
                //ログインしている従業員が申請データの承認者ではない場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);

            } else {
                putRequestScope(AttributeConst.PETITION, pv); //取得した申請データ
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

                //詳細画面を表示
                forward(ForwardConst.FW_PET_SHOW);
            }
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

            //idを条件に申請データを取得する
            PetitionView pv = service.findOne(toNumber(getRequestParam(AttributeConst.PET_ID)));

            //日報の承認状況を変更する（承認, 再提出）
            int approval = Integer.parseInt(getRequestParam(AttributeConst.REP_APPROVAL));
            pv.getReport().setApproval(approval);
            service.update(pv, false);

            //セッションに更新完了のフラッシュメッセージを設定
            if (approval == AttributeConst.REP_APPROVAL_DONE.getIntegerValue()) {
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_APPROVED.getMessage());
            } else {
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REJECTED.getMessage());
            }

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_PET, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * ログイン中の従業員が上長クラスかどうかチェックし、一般社員の場合はエラー画面を表示
     * true: 上長クラス false: 一般社員
     * @throws ServletException
     * @throws IOException
     */
    private boolean checkSuperior() throws ServletException, IOException {

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //一般社員の場合はエラー画面を表示
        if (ev.getPosition() == AttributeConst.DEP_POS_NORMAL.getIntegerValue()) {

            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;

        } else {

            return true;
        }
    }

}
