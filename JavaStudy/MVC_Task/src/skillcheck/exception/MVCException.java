package skillcheck.exception;

import skillcheck.bean.ResponseBean;

/**
 * カスタムException
 *
 * @author y.sato
 * @since 2019/01/01
 *
 */
public class MVCException extends Exception {

    private static final long serialVersionUID = 1L;

    private ResponseBean _responseBean;

    /**
     * コンストラクタ
     *
     * @param responseBean
     */
    public MVCException(final ResponseBean responseBean) {
        super();
        this._responseBean = responseBean;
    }

    /**
     * コンストラクタ
     *
     * @param responseBean
     * @param throwable
     */
   public MVCException(final ResponseBean responseBean, Throwable throwable) {
       super(throwable);
       this._responseBean = responseBean;
   }

    /**
     * @return responseBean
     */
    public ResponseBean getResponseBean() {
        return _responseBean;
    }

    /**
     * @param responseBean セットする responseBean
     */
    public void setResponseBean(ResponseBean responseBean) {
        this._responseBean = responseBean;
    }

}
