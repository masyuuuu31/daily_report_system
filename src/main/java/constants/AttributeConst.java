package constants;

/**
 * 画面の項目値等を定義するEnumクラス
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の従業員
    LOGIN_EMP("login_employee"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //従業員管理
    EMPLOYEE("employee"),
    EMPLOYEES("employees"),
    EMP_COUNT("employees_count"),
    EMP_ID("id"),
    EMP_CODE("code"),
    EMP_PASS("password"),
    EMP_NAME("name"),
    EMP_DEP("department"),
    EMP_DIV("division"),
    EMP_POSITION("position"),
    EMP_ADMIN_FLG("admin_flag"),

    //部署名
    DEP_SALES(1),
    DEP_HUMAN_RESOURCES(2),
    DEP_INFORMATION_SYSTEMS(3),
    DEP_GENERAL(4),
    DEP_ACCOUNTING(5),

    //所属グループ
    DEP_DIV_FIRST(1),
    DEP_DIV_SECOND(2),
    DEP_DIV_THIRD(3),
    DEP_DIV_FOURTH(4),
    DEP_DIV_FIFTH(5),

    //役職
    DEP_POS_NORMAL(1),
    DEP_POS_MANAGER(2),
    DEP_POS_GENERAL_MANAGER(3),

    //管理者フラグ
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //承認フラグ
    REP_APPROVAL_TRUE(1),
    REP_APPROVAL_FALSE(0),

    //日報管理
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_DATE("report_date"),
    REP_TITLE("title"),
    REP_CONTENT("content_msg"),
    REP_APPROVAL("approval");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}