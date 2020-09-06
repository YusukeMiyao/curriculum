package skillcheck.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import skillcheck.bean.ResponseBean;
import skillcheck.constant.ConstMessage;
import skillcheck.constant.ConstSQL;
import skillcheck.exception.MVCException;
import skillcheck.logger.Logger;

/**
 * サービス: 親クラス（abstract）
 *
 * @author y.sato
 * @since 2019/01/02
 */
public abstract class BaseService {

    /* フィールド変数の定義 */
    /** ・レスポンスデータ（社員情報の取得結果、レスポンスメッセージ）格納用変数 */
    protected ResponseBean responseBean;

    /** 0: リクエスト成功 / 1: 何かしらのエラー / 2: Exception */
    protected int reqStatus;
    protected String reqMessage;

    protected Connection connection;
    protected ResultSet resultSet;
    protected PreparedStatement preparedStatement;

    /**
     * コンストラクタ
     */
    public BaseService() {
        Logger.logStart(new Throwable());
        this.responseBean = new ResponseBean();
        this.connection = null;
        this.resultSet = null;
        this.preparedStatement = null;
        this.reqStatus = 0;
        this.reqMessage = "";
        Logger.logEnd(new Throwable());
    }

    /**
     * JDBC接続処理
     */
    protected void executeGetConnection() throws MVCException {
        Logger.logStart(new Throwable());

        try {
            // FIXME Step-5-2: 以下のStep-5-2に適切な定数を記述しなさい。
            // Tips: ConstSQLより適切な定数を参照

            // FIXME Step-5-2: postgresqlのドライバー名
            Class.forName("[ここへ記述]");

            // FIXME Step-5-2: DBへ接続するための初期設定（引数すべてに記述すること）
            this.connection = DriverManager.getConnection(
                    "",
                    "",
                    "");

            // オートコミットOFF
            this.connection.setAutoCommit(false);

        } catch (ClassNotFoundException e) {
            this.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_JDBC);
        } catch (SQLException e) {
            this.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_SQL);
        } finally {
            if (this.reqStatus == 2) throw new MVCException(this.responseBean);
        }

        Logger.logEnd(new Throwable());
    }

    /**
     * コミット処理
     */
    protected void executeCommit() {
        Logger.logStart(new Throwable());

        try {
            this.connection.commit();
        } catch (SQLException e) {
            Logger.log(e);
            this.executeRollback();
        }

        Logger.logEnd(new Throwable());
    }

    /**
     * ロールバック処理
     */
    protected void executeRollback() {
        Logger.logStart(new Throwable());

        try {
            this.connection.rollback();
        } catch (SQLException e) {
            Logger.log(e);
        }

        Logger.logEnd(new Throwable());
    }

    /**
     * SQLオブジェクトのクローズ処理
     */
    protected void executeCloseSQL() {
        Logger.logStart(new Throwable());

        try {
            // 「else」が不要な場合には「{}」を省いた書き方も可能
            if (Objects.nonNull(this.resultSet)) this.resultSet.close();
            if (Objects.nonNull(this.preparedStatement)) this.preparedStatement.close();
            if (Objects.nonNull(this.connection)) this.connection.close();
        } catch (SQLException e) {
            Logger.log(e);
        }

        Logger.logEnd(new Throwable());
    }

    /**
     * 例外処理時のリクエストステータス・メッセージ情報のセット
     * <pre>{@code reqStatus = 2 (Exception発生)}をデフォルトで設定</pre>
     *
     * @param exception 発生した例外
     * @param message 例外発生時の原因・解決補助用メッセージ
     */
    protected void executeSetExceptionInfo(final Exception e, final String message) {
        this.reqStatus = 2;
        this.reqMessage = message;
        Logger.log(e);
    }

}
