package skillcheck.logger;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class Logger {

    /**
     * ログ情報: 開始リテラル
     */
    private static final String LITERAL_START = "START";
    /**
     * ログ情報: 終了リテラル
     */
    private static final String LITERAL_END = "END";

    /**
     * 日付フォーマット
     */
    private static final String DATE_FORMAT = "yyyy/MM/dd hh:mm:ss.SSS";

    /**
     * ログ: START（メッセージなし）
     *
     * @param t <pre>スタックトレース情報</pre>
     * @param obj <pre>出力したいメッセージや値など</pre>
     */
    public static void logStart(Throwable t) {
        log(t, LITERAL_START);
    }

    /**
     * ログ: END（メッセージなし）
     *
     * @param t <pre>スタックトレース情報</pre>
     * @param obj <pre>出力したいメッセージや値など</pre>
     */
    public static void logEnd(Throwable t) {
        log(t, LITERAL_END);
    }

    /**
     * ログ: メッセージあり
     *
     * @param t <pre>スタックトレース情報</pre>
     * @param obj <pre>出力したいメッセージや値など</pre>
     */
    public static void log(Throwable t, Object obj) {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        StackTraceElement element = t.getStackTrace()[0];
        System.out.println(
                "[MEMORY:" + String.valueOf(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) + "]" +
                "[" + sdf.format(Calendar.getInstance().getTime()) + "] " +
                "[" + element.getClassName() + "] " +
                "[" + element.getMethodName() + "] " +
                "[" + element.getLineNumber() + "] " + obj);
    }

    /**
     * ログ: 例外発生時専用
     *
     * @param e
     */
    public static void log(final Exception e) {
        System.out.println("■ 例外発生: " +
                "[" + e.getStackTrace()[0].getClassName() +
                "] [" + e.getStackTrace()[0].getMethodName() +
                "] [" + e.getStackTrace()[0].getLineNumber() + "]");
    }
}