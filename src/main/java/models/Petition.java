package models;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 申請データのDTOモデル
 *
 */

@Table(name = JpaConst.TABLE_PET)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_PET_GET_ALL,
            query = JpaConst.Q_PET_GET_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_PET_COUNT_ALL,
            query = JpaConst.Q_PET_COUNT_ALL_DEF),
    @NamedQuery(
            name = JpaConst.Q_PET_GET_BY_REPORT,
            query = JpaConst.Q_PET_GET_BY_REPORT_DEF)
})
@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する
@Entity
public class Petition {

    /**
     * id
     */
    @Id
    @Column(name = JpaConst.PET_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 承認者
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.PET_COL_SEND_TO, nullable = false)
    private Employee sendTo;

    /**
     * 申請者
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.PET_COL_SEND_FROM, nullable = false)
    private Employee sendFrom;

    /**
     * 申請日報データ
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.PET_COL_REP, nullable = false)
    private Report report;

    /**
     * 登録日時
     */
    @Column(name = JpaConst.REP_COL_CREATED_AT, nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = JpaConst.REP_COL_UPDATED_AT, nullable = false)
    private LocalDateTime updatedAt;

}
