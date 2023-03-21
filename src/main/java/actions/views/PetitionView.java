package actions.views;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 申請情報について画面の入力値・出力値を扱うViewモデル
 *
 */
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class PetitionView {

    /**
     * id
     */
    private Integer id;

    /**
     * 承認者
     */
    private EmployeeView sendTo;

    /**
     * 申請者
     */
    private EmployeeView sendFrom;

    /**
     * 申請日報データ
     */
    private ReportView report;

    /**
     * 既読状況（0 : 未読、1 : 既読）
     */
    private Integer read;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

}
