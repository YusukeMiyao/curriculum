package skillcheck.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import skillcheck.bean.ResponseBean;
import skillcheck.constant.ConstMessage;
import skillcheck.exception.MVCException;
import skillcheck.logger.Logger;
import skillcheck.service.EmployeeManagementService;
import skillcheck.util.RequestTypeUtil;
import skillcheck.util.RequestTypeUtil.RequestType;

/**
 * 社員情報管理コントローラー（メインサーブレット）
 *
 * @author y.sato
 * @since 2019/01/02
 */
public final class EmployeeManagementController extends BaseServlet {

    /**
     * コンストラクタ
     */
    public EmployeeManagementController() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger.logStart(new Throwable());

        RequestType requestType = null;

        // FIXME Step-4-1: 社員情報管理サービスのインスタンスを生成しなさい。
        // Tips: 定義済みフィールド変数を使用
        // [ここへ記述]

        boolean hasSession = false;

        try {
            // セッションチェック
            hasSession = super.validateSession(request, response);
            Logger.log(new Throwable(), "hasSession = " + hasSession);

            // RequestTypeに未存在のキー値が指定された場合は例外発生
            requestType = this.getRequestType(request);
            Logger.log(new Throwable(), "RequestType = " + requestType.toString());

            // セッション切れの場合は早期離脱（finallyは実行される）
            if (!hasSession) return;

            switch (requestType) {
            case LOGOUT:
                HttpSession session = request.getSession(false);
                if (Objects.nonNull(session)) session.invalidate();
                hasSession = false;
                super.setRedirectInfo(true, session, request, response);
                break;
            default:
                // NOP
                break;
            }
        } catch (NullPointerException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_NULL,  0);
        } catch (MVCException e) {
            this.responseBean = e.getResponseBean();
            Logger.log(e);
        } finally {

            Logger.log(new Throwable(), "reqStatus  = " + this.responseBean.getRequestStaus());
            Logger.log(new Throwable(), "reqMessage = " + this.responseBean.getMessage());

            request.setAttribute(CONST_REQUST_KEY_FOR_RESPONSE_BEAN, this.responseBean);

            Logger.log(new Throwable(), "遷移先 = " + this.destinationTarget);

            if (hasSession) {
                this.getServletContext().getRequestDispatcher(this.destinationTarget).forward(request, response);
            }
            this.ems = null;
            this.responseBean = null;
        }

        Logger.logEnd(new Throwable());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger.logStart(new Throwable());

        // DBの文字コード設定と合わせる
        request.setCharacterEncoding("utf-8");

        this.responseBean = new ResponseBean();

        RequestType requestType = null;
        destinationTarget = null;

        List<String> reqEmpIdList = null;
        boolean hasSession = true;
        boolean isLoginError = false;

        /* 関数型インターフェース（ラムダ式）- START */
        // MEMO: Functionは、apply(引数)で処理を実行

        // リクエストより社員番号を取得（※削除時は複数の可能性あり）: 関数型インターフェース（ラムダ式）
        Function<HttpServletRequest, List<String>> rmdGetEmpIdList = (rmdRequest) -> {
            // FIXME Step-4-2: 各jspよりPOSTで送信されたリクエストパラメーターの社員番号を取得しなさい。
            // Tips: jsp側のname属性と一致させること
            final String pEmpId = "[ここへ記述]";
            return Arrays.asList(pEmpId);
        };
        /* 関数型インターフェース（ラムダ式）- END */

        try {
            requestType = this.getRequestType(request);
            Logger.log(new Throwable(), "RequestType = " + requestType.toString());

            // ログイン以外の場合
            if (!RequestType.LOGIN.equals(requestType)) {
                // 認証チェック
                hasSession = super.validateSession(request, response);
                Logger.log(new Throwable(), "hasSession = " + hasSession);
                // セッション切れの場合は早期離脱（finallyは実行される）
                if (!hasSession) return;
            }

            // FIXME Step-4-3: 社員情報管理サービスのインスタンス変数を生成しなさい。
            // Tips: 定義済みフィールド変数を使用
            // [ここへ記述]

            reqEmpIdList = rmdGetEmpIdList.apply(request);
            reqEmpIdList.forEach(id -> Logger.log(new Throwable(), "reqEmpId = " + id));

            // リクエストごとに条件分岐させる
            switch (requestType) {
            case LOGIN:
                Logger.log(new Throwable(), "--- ログイン ---");
                // ログインエラーの場合はリダイレクトする
                isLoginError = super.validateLogin(request);
                break;
            default:
                // MEMO: LOGOUTは対象外のため、default扱いとする
                break;
            }
        } catch (NullPointerException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_NULL, -1);
        } catch (MVCException e) {
            this.responseBean = e.getResponseBean();
            Logger.log(e);
        } finally {

            Logger.log(new Throwable(), "reqStatus  = " + this.responseBean.getRequestStaus());
            Logger.log(new Throwable(), "reqMessage = " + this.responseBean.getMessage());

            // FIXME Step-4-4: 取得結果（ResponseBean）をjspへ渡すための処理を記述しなさい。
            // Tips1: リクエストへレスポンス情報をセット
            // Tips2: キー名は「CONST_REQUST_KEY_FOR_RESPONSE_BEAN」使用
            // [ここへ記述]

            Logger.log(new Throwable(), "遷移先 = " + this.destinationTarget);

            // ログイン成功、かつ、セッションが存在、かつ、フォワード先が設定済みの場合のみフォワード
            if (!isLoginError && hasSession) {
                this.getServletContext().getRequestDispatcher(this.destinationTarget).forward(request, response);
            } else {
                response.sendRedirect(CONST_DESTINATION_LOGIN_JSP);
            }

            this.ems = null;
            this.responseBean = null;
        }

        Logger.logEnd(new Throwable());
    }

    /**
     * リクエストタイプを取得
     *
     * @param requestTypeNames リクエストパラメーターより取得したパラメーター名
     * @return リクエストタイプ（{@link RequestType}）
     * @throws MVCException
     */
    private RequestType getRequestType(final HttpServletRequest request) throws MVCException {
        Logger.logStart(new Throwable());

        final String requestTypeName = request.getParameter(CONST_ELEMENT_NAME_REQUEST);
        Logger.log(new Throwable(), "requestTypeName = " + requestTypeName);

        RequestType requestType = null;

        if (Objects.isNull(this.responseBean)) this.responseBean = new ResponseBean();

        try {
            if (Objects.isNull(requestTypeName)) {
                // この処理に到達してしまう場合は、リクエスト時に押下したボタン（疑似ボタン）の「name」、「value」をチェック
                this.responseBean.setRequestStaus(1);
                this.responseBean.setEmplyeeBeanList(new ArrayList<>(0));
                this.responseBean.setMessage(ConstMessage.ERROR_UNKNOWN_REQUEST);
            } else {
                // RequestTypeに未存在のキー値が指定された場合は例外発生
                requestType = RequestTypeUtil.RequestType.keyValueOf(requestTypeName);
            }
        } catch (IllegalArgumentException e) {
            super.executeSetExceptionInfo(e, ConstMessage.EXCEPTION_ARGUMENT, 0);
        } finally {
            Logger.log(new Throwable(), "this.responseBean = " + this.responseBean);

            // 何らかの理由でrequestTypeがnull、または、リクエストステータス > 0（エラーあり）の場合
            if (Objects.isNull(requestType) || this.responseBean.getRequestStaus() > 0) {
                // リクエスト対象の画面へ戻す
                this.destinationTarget = request.getParameter(CONST_REQUST_KEY_FOR_SENDER);
                throw new MVCException(this.responseBean);
            }
        }

        Logger.logEnd(new Throwable());
        return requestType;
    }

}