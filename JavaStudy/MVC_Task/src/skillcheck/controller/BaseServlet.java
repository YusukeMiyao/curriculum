package skillcheck.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import skillcheck.bean.EmployeeBean;
import skillcheck.bean.ResponseBean;
import skillcheck.constant.ConstMessage;
import skillcheck.dao.EmployeeDao.ExecuteCase;
import skillcheck.exception.MVCException;
import skillcheck.logger.Logger;
import skillcheck.service.EmployeeManagementService;
import skillcheck.util.PasswordHashUtil;

/**
 * サーブレット: 親クラス
 *
 * @author y.sato
 * @since 2019/01/02
 */
public abstract class BaseServlet extends HttpServlet {

    /* 定数 */
    private static final String CONST_SESSION_KEY_FOR_LOGIN = "login";

    // FIXME Step-3: 定数定義
    // FIXME Step-3-1: リクエスト判別用のボタンの属性名を記述しなさい。
    protected static final String CONST_ELEMENT_NAME_REQUEST = "ここへ記述";
    protected static final String CONST_REQUST_KEY_FOR_SENDER = "sender";
    protected static final String CONST_REQUST_KEY_FOR_REDIRECT = "redirect";
    protected static final String CONST_REQUST_KEY_FOR_RESPONSE_BEAN = "responseBean";

    /** ・リクエスト対象（リクエスト&レスポンスを渡す先）のjspファイル */
    protected static final String CONST_DESTINATION_LOGIN_JSP = "/MVC_Task/login.jsp";
    // FIXME Step-3-2: 実行結果表示用のjspファイルのパスを記述しなさい。
    protected static final String CONST_DESTINATION_RESULT_JSP = "";

    /* フィールド変数の定義 */
    /** フォーワード先 */
    protected String destinationTarget;
    /** レスポンス情報 */
    protected ResponseBean responseBean;
    /** 社員情報サービス */
    protected EmployeeManagementService ems;

    /**
     * コンストラクタ
     */
    public BaseServlet() {
        Logger.logStart(new Throwable());
        // レスポンスデータはnullの可能性を潰す
        responseBean = new ResponseBean();
        destinationTarget = null;
        Logger.logEnd(new Throwable());
    }

    /**
     * セッションのバリデーションチェック
     *
     * @param request
     * @param response
     * @return セッションあり/なし（true/false）
     * @throws IOException
     * @throws MVCException
     */
    protected boolean validateSession(HttpServletRequest request, HttpServletResponse response) throws IOException, MVCException {
        Logger.logStart(new Throwable());

        boolean hasSession = false;

        // セッションを取得
        HttpSession session = request.getSession(false);

        if (Objects.isNull(session) || session.isNew()) {
            Logger.log(new Throwable(), "セッションなし or New: リダイレクト");
            this.setRedirectInfo(false, session, request, response);
        } else {
            Logger.log(new Throwable(), "セッションあり");

            try {
                // セッション情報を取得
                final Object loginSession = (Object) session.getAttribute(CONST_SESSION_KEY_FOR_LOGIN);
                if (Objects.isNull(loginSession)) {
                    // ここに到達した場合は、セッション情報の設定方法を確認
                    Logger.log(new Throwable(), "セッション: ログイン情報なし");
                    this.setRedirectInfo(false, session, request, response);
                } else {
                    hasSession = true;
                }
            } catch (IllegalStateException e) {
                Logger.log(e);
                // Sessionのattributeに値が未設定でnullであるような場合に発生
                this.destinationTarget = request.getParameter(CONST_REQUST_KEY_FOR_SENDER);
                this.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_NO_SESSION, 0);
                throw new MVCException(this.responseBean, e.getCause());
            }
        }

        Logger.logEnd(new Throwable());
        return hasSession;
    }

    /**
     * リダイレクト情報の設定
     *
     * @param isLogout
     * @param session <pre>セッション情報</pre>
     * @param request
     * @param response
     * @throws IOException
     */
    protected void setRedirectInfo(final boolean isLogout, HttpSession session, HttpServletRequest request, HttpServletResponse response) throws IOException {

        // セション情報として登録
        session = request.getSession(true);

        // セッションタイムアウト時のみメッセージをセット
        if (!isLogout) session.setAttribute(CONST_REQUST_KEY_FOR_REDIRECT, ConstMessage.ERROR_SESSION_INVALID);

        // セッション情報（ログイン状態）が未存在の場合、ログイン画面へリダイレクト
        response.sendRedirect(CONST_DESTINATION_LOGIN_JSP);
    }

    /**
     * ログインのバリデーションチェック
     * @param request
     * @return
     * @throws IOException
     */
    protected boolean validateLogin(final HttpServletRequest request) throws IOException {
        Logger.logStart(new Throwable());

        final HttpSession session = request.getSession(true);

        EmployeeBean resEmployeeBean = null;
        String message = "";
        boolean isLoginError = false;

        final String reqEmpId = request.getParameter("empId").trim();
        final String reqPassword = request.getParameter("password").trim();

        try {
            // FIXME Step-3-3: 社員情報管理サービスより、社員情報を取得する処理を呼び出しなさい。
            // Tips1: 社員情報管理サービスはインスタンスが生成済みのものを利用すること
            // Tips2: 完全一致検索の社員情報取得を呼び出すこと
            // Tips3: 第二引数の渡し方に注意すること
            // ←ここへ記述

            // 最初の1件を取得
            resEmployeeBean = responseBean.getEmplyeeBeanList().stream().findFirst().orElse(null);

            if (Objects.nonNull(resEmployeeBean)) {
                // パスワードチェック
                final String hashPassword = PasswordHashUtil.getSafetyPassword(reqPassword, reqEmpId);
                if (resEmployeeBean.getPassword().equals(hashPassword)) {
                    // ログイン成功
                    this.destinationTarget = CONST_DESTINATION_RESULT_JSP;
                    message = ConstMessage.SUCCESS_LOGIN;
                    // 社員番号をログインセッション情報として登録
                    session.setAttribute(CONST_SESSION_KEY_FOR_LOGIN, resEmployeeBean.getEmpId());
                } else {
                    // パスワード不一致
                    message = ConstMessage.ERROR_UNMATCH_PASSWORD;
                }
            } else {
                // ログイン時に該当する社員情報が未存在
                message = ConstMessage.ERROR_UNMATCH_USER;
            }
            this.responseBean.setMessage(message);

        } catch (NullPointerException e) {
            this.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_NULL, 0);
        } catch (MVCException e) {
            Logger.log(e);
            this.responseBean = e.getResponseBean();
        } finally {
            if (Objects.isNull(this.destinationTarget)) {
                isLoginError = true;
                // リダイレクトで返すため、セッション情報にレスポンス情報をセットする
                session.setAttribute(CONST_REQUST_KEY_FOR_RESPONSE_BEAN, this.responseBean);
            }
        }
        Logger.log(new Throwable(), "isLoginError = " + isLoginError);

        Logger.logEnd(new Throwable());
        return isLoginError;
    }

    /**
     * 例外処理時のリクエストステータス・メッセージ情報のセット
     *
     * @param e <pre>発生した例外: {@code ◯◯Exception}が対象</pre>
     * @param message <pre>例外発生時の原因・解決補助用メッセージ</pre>
     * @param listSize <pre>指定サイズで ResponseBeanのEmplyeeBeanListを初期化（未指定の場合は{@code -1}）</pre>
     */
    protected void executeSetExceptionInfo(final Exception e, final String message, final int listSize) {
        Logger.log(e);
        this.responseBean.setRequestStaus(2);
        this.responseBean.setMessage(message);
        if (listSize < 0) return;
        this.responseBean.setEmplyeeBeanList(new ArrayList<>(listSize));
    }

}
