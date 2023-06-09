package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "daily_report_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数


    //従業員テーブル
    String TABLE_EMP = "employees"; //テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //社員番号
    String EMP_COL_NAME = "name"; //氏名
    String EMP_COL_PASS = "password"; //パスワード
    String EMP_COL_DEP = "department"; //所属部署
    String EMP_COL_DIV = "division"; //所属グループ・課
    String EMP_COL_POSITION = "position"; //役職
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String EMP_COL_CREATED_AT = "created_at"; //登録日時
    String EMP_COL_UPDATED_AT = "updated_at"; //更新日時
    String EMP_COL_DELETE_FLAG = "delete_flag"; //削除フラグ

    //部署コード
    int DEP_SALES =1; //営業部
    int DEP_HUMAN_RESOURCES = 2; //人事部
    int DEP_INFORMATION_SYSTEMS = 3; //情報システム部
    int DEP_GENERAL = 4; //総務部
    int DEP_ACCOUNTING = 5; //経理部

    //所属ユニットコード
    int DEP_DIV_FIRST = 1; //第1ユニット
    int DEP_DIV_SECOND = 2; //第2ユニット
    int DEP_DIV_THIRD = 3; //第3ユニット
    int DEP_DIV_FOURTH = 4; //第4ユニット
    int DEP_DIV_FIFTH = 5; //第5ユニット

    //役職コード
    int DEP_POS_NORMAL = 1; //一般社員
    int DEP_POS_CHIEF = 2; //主任
    int DEP_POS_MANAGER = 3; //課長
    int DEP_POS_GENERAL_MANAGER = 4; //部長

    //フラグ(0, 1)
    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int EMP_DEL_TRUE = 1; //削除フラグON(削除済み)
    int EMP_DEL_FALSE = 0; //削除フラグOFF(現役)

    //日報テーブル
    String TABLE_REP = "reports"; //テーブル名
    //日報テーブルカラム
    String REP_COL_ID = "id"; //id
    String REP_COL_EMP = "employee_id"; //日報を作成した従業員のid
    String REP_COL_REP_DATE = "report_date"; //いつの日報かを示す日付
    String REP_COL_TITLE = "title"; //日報のタイトル
    String REP_COL_CONTENT = "content"; //日報の内容
    String REP_COL_APPROVAL = "approval"; //承認状況
    String REP_COL_APPROVER = "approver"; //承認者
    String REP_COL_CREATED_AT = "created_at"; //登録日時
    String REP_COL_UPDATED_AT = "updated_at"; //更新日時

    //申請フラグ
    int REP_APPLICATION = 0; //申請中
    int REP_APPROVAL_DONE = 1; //承認済み
    int REP_APPROVAL_REJECT = 2; //承認拒否

    //申請テーブル
    String TABLE_PET = "petitions"; //テーブル名
    //申請テーブルカラム
    String PET_COL_ID = "id"; //id
    String PET_COL_SEND_TO = "send_to"; //承認者
    String PET_COL_SEND_FROM = "send_from"; //申請者
    String PET_COL_REP = "report_id"; //申請日報データ
    String PET_COL_CREATED_AT = "created_at"; //登録日時
    String PET_COL_UPDATED_AT = "updated_at"; //更新日時

    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_REP = "report"; //日報
    String ENTITY_PET = "petition"; //申請

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_EMPLOYEE = "employee"; //従業員
    String JPQL_PARM_DEPARTMENT = "department"; //部署
    String JPQL_PARM_DIVISION = "division"; //ユニット
    String JPQL_PARM_REPORT = "report"; //日報

    //NamedQueryの nameとquery
    //従業員についてのNamedQuery
    //全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll";
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e ORDER BY e.id DESC";
    //全ての従業員の件数を取得する
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";
    //同じ部署の従業員をidの降順に取得する
    String Q_EMP_GET_BY_DEPARTMENT_ALL = ENTITY_EMP + ".getByDepartmentAll";
    String Q_EMP_GET_BY_DEPARTMENT_ALL_DEF = "SELECT e FROM Employee AS e WHERE e.department = :" + JPQL_PARM_DEPARTMENT + " ORDER BY e.id DESC";
    //同じ部署の従業員の件数を取得する
    String Q_EMP_COUNT_BY_DEPARTMENT = ENTITY_EMP + ".countByDepartment";
    String Q_EMP_COUNT_BY_DEPARTMENT_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.department = :" + JPQL_PARM_DEPARTMENT;
    //社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD;
    //指定した社員番号を保持する従業員の件数を取得する
    String Q_EMP_COUNT_REGISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_REGISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE;


    //承認者の一覧を取得する
    String Q_EMP_GET_ALL_SUPERIOR = ENTITY_EMP + ".getAllSuperior";
    String Q_EMP_GET_ALL_SUPERIOR_DEF = "SELECT e FROM Employee AS e WHERE e.department = :" + JPQL_PARM_DEPARTMENT + " AND (e.position = 4 OR (e.position BETWEEN 2 AND 3 AND e.division = :" + JPQL_PARM_DIVISION + ")) ORDER BY e.id DESC";
    //部長のみ
    String Q_EMP_GET_ALL_GM = ENTITY_EMP + ".getAllGM";
    String Q_EMP_GET_ALL_GM_DEF = "SELECT e FROM Employee AS e WHERE e.position = 4 AND e.department = :" + JPQL_PARM_DEPARTMENT + " ORDER BY e.id DESC";
    //課長
    String Q_EMP_GET_ALL_GM_AND_MANAGER = ENTITY_EMP + ".getAllGMAndManager";
    String Q_EMP_GET_ALL_GM_AND_MANAGER_DEF = "SELECT e FROM Employee AS e WHERE e.department = :" + JPQL_PARM_DEPARTMENT + " AND (e.position = 4 OR (e.position = 3 AND e.division = :" + JPQL_PARM_DIVISION + ")) ORDER BY e.id DESC";


    //日報についてのNamedQuery
    //承認済みの全ての日報をidの降順に取得する
    String Q_REP_GET_ALL = ENTITY_REP + ".getAll";
    String Q_REP_GET_ALL_DEF = "SELECT r FROM Report AS r WHERE r.approval = 1 ORDER BY r.id DESC";
    //承認済みの全ての日報の件数を取得する
    String Q_REP_COUNT = ENTITY_REP + ".count";
    String Q_REP_COUNT_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.approval = 1";
    //承認済み、且つ同じ部署の従業員が作成した日報をidの降順に取得する
    String Q_REP_GET_BY_DEPARTMENT_ALL = ENTITY_REP + ".getByDepartmentAll";
    String Q_REP_GET_BY_DEPARTMENT_ALL_DEF = "SELECT r FROM Report AS r WHERE r.approval = 1 AND r.employee.department = :" + JPQL_PARM_DEPARTMENT + " ORDER BY r.id DESC";
    //承認済み、且つ同じ部署の従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_BY_DEPARTMENT = ENTITY_REP + ".countByDepartment";
    String Q_REP_COUNT_BY_DEPARTMENT_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.approval = 1 AND r.employee.department = :" + JPQL_PARM_DEPARTMENT ;
    //指定した従業員が作成した日報を全件idの降順で取得する
    String Q_REP_GET_ALL_MINE = ENTITY_REP + ".getAllMine";
    String Q_REP_GET_ALL_MINE_DEF = "SELECT r FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE + " ORDER BY r.id DESC";
    //指定した従業員が作成した日報の件数を取得する
    String Q_REP_COUNT_ALL_MINE = ENTITY_REP + ".countAllMine";
    String Q_REP_COUNT_ALL_MINE_DEF = "SELECT COUNT(r) FROM Report AS r WHERE r.employee = :" + JPQL_PARM_EMPLOYEE;
    //承認依頼中の日報データを表示する
    String Q_PET_GET_ALL = ENTITY_PET + ".getAll";
    String Q_PET_GET_ALL_DEF = "SELECT p FROM Petition AS p WHERE p.sendTo = :" + JPQL_PARM_EMPLOYEE + " AND p.report.approval = 0 ORDER BY p.id DESC";
    //承認依頼中の日報件数を取得する
    String Q_PET_COUNT_ALL = ENTITY_PET + ".countAll";
    String Q_PET_COUNT_ALL_DEF = "SELECT COUNT(p) FROM Petition AS p WHERE p.sendTo = :" + JPQL_PARM_EMPLOYEE + " AND p.report.approval = 0 ";
    //指定した日報を含む申請データを取得する
    String Q_PET_GET_BY_REPORT = ENTITY_PET + ".getByReport";
    String Q_PET_GET_BY_REPORT_DEF = "SELECT p FROM Petition AS p WHERE p.report = :" + JPQL_PARM_REPORT;

}
