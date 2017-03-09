/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2001, 2004, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */


package java.util.logging;

/**
 * ErrorManager objects can be attached to Handlers to process
 * any error that occurs on a Handler during Logging.
 * <p>
 * When processing logging output, if a Handler encounters problems
 * then rather than throwing an Exception back to the issuer of
 * the logging call (who is unlikely to be interested) the Handler
 * should call its associated ErrorManager.
 * <p>
 *  ErrorManager对象可以附加到处理程序以处理在记录期间在处理程序上发生的任何错误。
 * <p>
 *  当处理日志输出时,如果处理器遇到问题,而不是抛出一个异常回记录调用的发布者(谁不太可能感兴趣),处理程序应该调用其关联的ErrorManager。
 * 
 */

public class ErrorManager {
   private boolean reported = false;

    /*
     * We declare standard error codes for important categories of errors.
     * <p>
     *  我们为重要的错误类别声明标准错误代码。
     * 
     */

    /**
     * GENERIC_FAILURE is used for failure that don't fit
     * into one of the other categories.
     * <p>
     *  GENERIC_FAILURE用于不适合其他类别的故障。
     * 
     */
    public final static int GENERIC_FAILURE = 0;
    /**
     * WRITE_FAILURE is used when a write to an output stream fails.
     * <p>
     *  对输出流的写入失败时,使用WRITE_FAILURE。
     * 
     */
    public final static int WRITE_FAILURE = 1;
    /**
     * FLUSH_FAILURE is used when a flush to an output stream fails.
     * <p>
     *  当对输出流的刷新失败时,使用FLUSH_FAILURE。
     * 
     */
    public final static int FLUSH_FAILURE = 2;
    /**
     * CLOSE_FAILURE is used when a close of an output stream fails.
     * <p>
     *  当输出流的关闭失败时使用CLOSE_FAILURE。
     * 
     */
    public final static int CLOSE_FAILURE = 3;
    /**
     * OPEN_FAILURE is used when an open of an output stream fails.
     * <p>
     *  当输出流的打开失败时,使用OPEN_FAILURE。
     * 
     */
    public final static int OPEN_FAILURE = 4;
    /**
     * FORMAT_FAILURE is used when formatting fails for any reason.
     * <p>
     *  如果格式因任何原因失败,则使用FORMAT_FAILURE。
     * 
     */
    public final static int FORMAT_FAILURE = 5;

    /**
     * The error method is called when a Handler failure occurs.
     * <p>
     * This method may be overridden in subclasses.  The default
     * behavior in this base class is that the first call is
     * reported to System.err, and subsequent calls are ignored.
     *
     * <p>
     *  当处理程序发生故障时调用错误方法。
     * <p>
     * 
     * @param msg    a descriptive string (may be null)
     * @param ex     an exception (may be null)
     * @param code   an error code defined in ErrorManager
     */
    public synchronized void error(String msg, Exception ex, int code) {
        if (reported) {
            // We only report the first error, to avoid clogging
            // the screen.
            return;
        }
        reported = true;
        String text = "java.util.logging.ErrorManager: " + code;
        if (msg != null) {
            text = text + ": " + msg;
        }
        System.err.println(text);
        if (ex != null) {
            ex.printStackTrace();
        }
    }
}
