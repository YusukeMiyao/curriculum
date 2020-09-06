package skillcheck.constant;

/**
 * ・メッセージ関連の定数
 *
 * @author y.sato
 * @since 2019/01/02
 *
 */
public final class ConstMessage {

    /** ログイン成功 */
    public static final String SUCCESS_LOGIN = "ログインに成功しました。";
    /** 社員登録完了 */
    public static final String SUCCECSS_INSERT = "社員情報を登録しました。";
    /** 社員情報更新完了 */
    public static final String SUCCECSS_UPDATE_EMP_INFO = "社員情報を更新しました。";
    /** 社員情報削除完了 */
    public static final String SUCCECSS_UPDATE_DELETE_FLG = "選択された社員情報を削除しました。";
    /** 照会結果なし */
    public static final String SUCCECSS_RECORD_COUNT = "照会結果は%d件です。";
    /** セッションタイムアウト */
    public static final String ERROR_SESSION_INVALID = "セッションがタイムアウトしました。<br>再ログインしてください。";
    /** 社員情報が未存在 */
    public static final String ERROR_UNMATCH_USER = "該当する社員情報がありません。";
    /** パスワードが不一致 */
    public static final String ERROR_UNMATCH_PASSWORD = "パスワードに誤りがあります。";
    /** 登録済み社員番号あり */
    public static final String ERROR_EXISTS_EMPID = "入力された社員番号はすでに登録済みです。";
    /** 不明なリクエスト */
    public static final String ERROR_UNKNOWN_REQUEST = "不明なリクエストが実行されました。";

    /* 例外処理: Tips用*/
    /** セッション情報の設定取得誤り */
    public static final String EXCEPTION_NO_SESSION = "セッション情報がありません！&#010;セッションの設定・取得処理を確認しましょう！";
    /** 不明なリクエスト */
    public static final String EXCEPTION_ARGUMENT = "呼び出しているメソッドに指定外の引数（要素や型）が指定されている可能性があります！&#010;メソッドの引数を確認しましょう！";
    /** JDBC接続関連の誤り */
    public static final String EXCEPTION_JDBC = "JDBC関連の例外です！&#010;postgresqlのjarファイルは追加されているか、ビルド・パスは追加されているかを確認しましょう！";
    /** SQL関連の誤り */
    public static final String EXCEPTION_SQL = "SQL関連の例外です！&#010;preparedStatementやresultSetの使い方に誤りが無いか確認しましょう！";
    /** DBのレコード誤り or 未存在 */
    public static final String EXCEPTION_DB = "社員情報テーブルのレコードが削除済み、または、未存在です！&#010;DBのレコードを確認しましょう！";
    /** 範囲外の要素指定 */
    public static final String EXCEPTION_INDEX = "配列やList使用時のインデックスに誤りがあります！&#010;存在しない要素にアクセスしていないか確認しましょう！";
    /** ヌルポ */
    public static final String EXCEPTION_NULL = "ヌルポです！&#010;NULLの可能性がある変数が存在します！&#010;メソッドを呼び出している変数の値を確認しましょう！";

}