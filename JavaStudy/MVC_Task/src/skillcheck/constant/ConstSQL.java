package skillcheck.constant;

/**
 * JDBC、SQL関連の定数
 *
 * @author y.sato
 * @since 2019/01/02
 *
 */
public final class ConstSQL {

    /** ドライバーのクラス名 */
    public static final String JDBC_POSTGRES_DRIVER = "org.postgresql.Driver";
    /** JDMC接続先情報 */
    // MEMO: PostgreSQLを複数バージョン使用している場合は、ポート番号も指定
    // TODO: 接続先のポート番号は個々で異なるため、pgAdminのPostgreSQLより、接続先設定を参照してXXXXへ記述すること。
    public static final String JDBC_CONNECTION = "jdbc:postgresql://localhost:5434/lesson_db?useUnicode=true&characterEncoding=utf8";
    /** ユーザー名 */
    public static final String JDBC_POSTGRES_USER = "postgres";
    /** パスワード */
    public static final String JDBC_POSTGRES_PASS = "postgres";

    // FIXME Step-2: 以下のSELECT文、INSERT文、UPDATE文を記述しなさい
    // Tips: 更新値や条件値には「?」を使用してください

    // FIXME Step-2-1: [SQL-SELECT] 社員情報テーブルより、作成日時、更新日時、削除フラグを除いたカラムを取得するクエリを作成しなさい。
    /** 社員情報一覧取得用クエリ: 取得カラム + 取得元テーブル */
    public static final String SELECT_BASE = "ここへ記述";
    /** 社員情報一覧取得用クエリ: 削除されていない社員情報を社員番号順に取得 */
    public static final String SELECT_BY_DELETE_FLG_ZERO = " WHERE deleteFlg = '0' ORDER BY empId";
    /** 社員番号を条件とするクエリ: 完全一致 */
    public static final String SELECT_BY_EMPID = " WHERE empId = ? AND deleteFlg = '0'";

    /** プリペアードステートメントで使用するクエリの条件値用プレースホルダー */
    public static final String CONST_PLACEHOLDER_FOR_BIND_PARAM = "?";
}
