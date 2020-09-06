package skillcheck.util;

public final class RequestTypeUtil {

    /**
     * 列挙型: リクエストパターン
     *
     * <pre>
     * {@code LOGIN ログイン}
     * {@code LOGOUT ログアウト}
     * </pre>
     */
    public enum RequestType {
        LOGIN("login"),
        LOGOUT("logout");
        private final String requestName;
        private RequestType(final String requestName) {
            this.requestName = requestName;
        }

        /**
         * 指定パラメーターによる逆引き初期化
         * <pre>※パラメーターがStringの場合は、既存の「valueOf」と重複するため独自のメソッド名にしておく</pre>
         * @param key キー名
         */
        public static RequestType keyValueOf(final String requestName) throws IllegalArgumentException {
            // 特にネストする必要が無いので1行で
            for (final RequestType rt : values()) if (rt.requestName.equals(requestName)) return rt;
            throw new IllegalArgumentException("指定された要素値は、RequestTypeに存在しません！: " + requestName);
        }
    }

}
