package skillcheck.bean;

import java.util.List;

/**
 * レスポンス情報Bean（モデル）
 *
 * @author y.sato
 * @since 2019/01/02
 */
public final class ResponseBean {

    /** ・メッセージ */
    private String _message;

    /** ・リクエストステータス */
    private int _requestStatus;

    /** ・社員情報データリスト */
    private List<EmployeeBean> _employeeBeanList;

    /**
     * コンストラクタ
     */
    public ResponseBean() {
    }

    /**
     * コンストラクタ
     */
    public ResponseBean(String message, int requestStatus, List<EmployeeBean> employeeBeanList) {
        this._message = message;
        this._requestStatus = requestStatus;
        this._employeeBeanList = employeeBeanList;
    }

    /**
     * @param message <pre>セットするStringクラスのメッセージ</pre>
     */
    public void setMessage(String message) {
        this._message = message;
    }

    /** @return Stringクラスのメッセージ */
    public String getMessage() {
        return _message;
    }

    /**
     * @param message <pre>セットするint型のメッセージ</pre>
     */
    public void setRequestStaus(int requestStatus) {
        this._requestStatus = requestStatus;
    }

    /**
     * @return int型のメッセージ
     */
    public int getRequestStaus() {
        return _requestStatus;
    }

    /**
     * @param empBean <pre>セットするEmplpoyeeBean型のデータ</pre>
     */
    public void setEmplyeeBeanList(List<EmployeeBean> empBeanList) {
        this._employeeBeanList = empBeanList;
    }

    /** @return 社員情報データ */
    public List<EmployeeBean> getEmplyeeBeanList() {
        return _employeeBeanList;
    }

}
