package skillcheck.service;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// FIXME Step-5-1: 「EmployeeBean, ResponseBean, ConstMessage, ConstSQL, EmployeeDao, SC5Exception, Logger」をインポートしなさい。
// [ここへ記述]

/**
 * 社員情報管理サービス
 * <pre>
 * 当該クラスで提供する機能
 * ・社員情報検索（部分一致・全件）
 * </pre>
 *
 * @author y.sato
 * @since 2019/01/02
 */
public final class EmployeeManagementService extends BaseService implements EmployeeDao {

    /**
     * コンストラクタ
     */
    public EmployeeManagementService() {
        super();
    }

    @Override
    public ResponseBean getEmployeeData(final ExecuteCase eCase) throws MVCException {
        Logger.logStart(new Throwable());

        this.responseBean = this.executeDBAccess(eCase, new ArrayList<>(0));

        Logger.logEnd(new Throwable());
        return this.responseBean;
    }

    @Override
    public ResponseBean getEmployeeData(final ExecuteCase eCase, EmployeeBean pEmployeeBean) throws MVCException {
        Logger.logStart(new Throwable());

        this.responseBean = this.executeDBAccess(eCase, Arrays.asList(pEmployeeBean));

        Logger.logEnd(new Throwable());
        return this.responseBean;
    }

    /**
     * 社員情報DBアクセス処理
     *
     * @param eCase <pre>処理パターン: {@link ExecuteCase}</pre>
     * @param pEmployeeBeanList <pre>入力情報の社員情報Bean配列</pre>
     * @return responseBean <pre>レスポンスデータ（社員情報データ、メッセージ）</pre>
     * @throws MVCException
     */
    private ResponseBean executeDBAccess(final ExecuteCase eCase, final List<EmployeeBean> pEmployeeBeanList) throws MVCException {
        Logger.logStart(new Throwable());
        Logger.log(new Throwable(), "ExecuteCase = " + eCase.toString());

        // jsp側での余計な判定回避ため、nullの可能性を潰す
        List<EmployeeBean> empResultList = new ArrayList<>(0);

        try {
            // FIXME Step-5-2: executeGetConnection()内を修正
            // MEMO: Windowsは【control】、Macは【command】を押下しながらクリックすることでメソッド定義へジャンプ可能
            super.executeGetConnection();

            // 共通処理: 社員情報テーブルへのアクセス
            // FIXME Step-5-3: SELECT文の実行(5-1, 5-2)
            // Tips: executeSelectQueryメソッド内を修正してください。
            this.executeSelectQuery(eCase, pEmployeeBeanList);

            // SQL実行結果よりメタデータを取得
            final ResultSetMetaData meta = resultSet.getMetaData();
            Logger.log(new Throwable(), "meta = " + meta);

            // 抽出結果を社員情報Beanへセット
            while (this.resultSet.next()) {
                // メタデータよりカラム名を表示: 取得内容のヒント
                int i = 0;
                while (++i < meta.getColumnCount()) {
                    Logger.log(new Throwable(), meta.getColumnName(i) + ": " + this.resultSet.getString(meta.getColumnName(i)));
                }

                // FIXME Step-5-8: SQLの抽出結果（resultSet）の各カラムデータを該当する社員情報Beanへセットしなさい。
                // Tips1: セット項目: 社員番号、パスワード、名前、メールアドレス、プログラミング言語、コメント
                // Tips2: 正解パターンは複数あり
                EmployeeBean employeeBean = new EmployeeBean(
                        this.resultSet.getString("empId"),
                        this.resultSet.getString("password"),
                        this.resultSet.getString("name"),
                        this.resultSet.getString("mail"),
                        this.resultSet.getString("programingLanguage"),
                        this.resultSet.getString("comment"));

                // 社員情報リストへ追加
                empResultList.add(employeeBean);

                employeeBean = null;
            }
        } catch (SQLException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_SQL);
            super.executeRollback();
        } catch (NullPointerException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_NULL);
        } finally {
            Logger.log(new Throwable(), "reqStatus  = " + this.reqStatus);
            Logger.log(new Throwable(), "reqMessage = " + this.reqMessage);

            if (this.reqStatus == 0) {
                switch (eCase) {
                case FIND_ALL:
                case FIND_BY_EMPID:
                case FIND_BY_EMPID_WITH_LIKE:
                    // MEMO: findFirst().orElse(null)は、リストの最初の1件を取得、無ければ、「null」を返す
                    EmployeeBean employeeBean = empResultList.stream().findFirst().orElse(null);
                    final int count = Objects.isNull(employeeBean) ? 0 : empResultList.size();
                    this.reqMessage = String.format(ConstMessage.SUCCECSS_RECORD_COUNT, count);
                    employeeBean = null;
                    break;
                default:
                    break;
                }
            }

            // レスポンスデータに社員情報、リクエストステータス、メッセージをセット
            this.responseBean.setEmplyeeBeanList(empResultList);
            this.responseBean.setRequestStaus(this.reqStatus);
            this.responseBean.setMessage(this.reqMessage);

            super.executeCloseSQL();

            empResultList = null;

            if (this.reqStatus == 2) throw new MVCException(this.responseBean);
        }

        Logger.logEnd(new Throwable());
        return this.responseBean;
    }

    /**
     * 社員情報検索SQL実行処理
     *
     * @param eCase <pre>{@link ExecuteCase}</pre>
     * @param pEmployeeBeanList <pre>検索条件となる社員情報Beanリスト</pre>
     * @return resultSet <pre>SQL取得結果</pre>
     * @throws MVCException
     */
    private void executeSelectQuery(final ExecuteCase eCase, final List<EmployeeBean> pEmployeeBeanList)
            throws MVCException {
        Logger.logStart(new Throwable());

        // クエリ構築用変数
        // MEMO: 初期容量を指定しておくとパフォーマンスがよくなる
        StringBuilder sbQuery = new StringBuilder(ConstSQL.SELECT_BASE.length());

        // 検索の条件値となる社員番号取得用変数
        EmployeeBean emp = null;

        // クエリ用文字列を連結させて作成
        sbQuery.append(ConstSQL.SELECT_BASE);

        try {
            // 「全件検索」以外の場合は、条件クエリを追加する
            switch (eCase) {
            case FIND_ALL:
                sbQuery.append(ConstSQL.SELECT_BY_DELETE_FLG_ZERO);
                this.resultSet = this.connection.createStatement().executeQuery(sbQuery.toString());
                Logger.log(new Throwable(), "SQL: " +  sbQuery.toString());
                break;
            case FIND_BY_EMPID:

                // FIXME Step-5-4: pEmployeeBeanListの「1件目の要素のみ」から社員情報を取得しなさい。
                // Tips1: ループ文を使用すること（正解は複数パターンあります）
                // Tips2: 格納先はローカル変数のempとすること
                // [ここへ記述]

                if (Objects.nonNull(emp)) {
                    Logger.log(new Throwable(), "pEmployeeBeanList[0].empId = " + emp.getEmpId());

                    sbQuery.append(ConstSQL.SELECT_BY_EMPID);

                    // FIXME Step-5-5: 以下の手順に沿って適当な処理を記述しなさい。
                    // 1. 上記で構築したSELECT文を引数にして、connectionよりプリペアードステートメントオブジェクトを作成
                    // 2. 1で作成したオブジェクトをpreparedStatementへ格納
                    // Tips: sbQueryは、sbQuery.toString()でStringへ変換
                    // [ここへ記述]

                    // LIKEを使用するため、パラメータを編集
                    final String empId = ExecuteCase.FIND_BY_EMPID_WITH_LIKE.equals(eCase)
                            ? ("%" + emp.getEmpId() + "%")
                            : emp.getEmpId();

                    // FIXME Step-5-6: preparedStatementに適切なパラメーターをセットしなさい。
                    // Tips: パラメータをセットするインデックスに注意
                    // [ここへ記述]

                    // FIXME Step-5-7: preparedStatementよりSQL(SELECT文)を実行し、resultSetへ結果を格納しなさい。
                    // [ここへ記述]

                    Logger.log(new Throwable(), "SQL: " +  this.preparedStatement.toString());
                }
                break;
            default:
                // NOP
                break;
            }
        } catch (SQLException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_SQL);
        } catch (IndexOutOfBoundsException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_INDEX);
        } catch (NullPointerException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_NULL);
        } finally {
            sbQuery = null;
            emp = null;
            if (this.reqStatus == 2) throw new MVCException(this.responseBean);
        }

        Logger.logEnd(new Throwable());
    }

}
