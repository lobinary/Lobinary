/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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
 * A Formatter provides support for formatting LogRecords.
 * <p>
 * Typically each logging Handler will have a Formatter associated
 * with it.  The Formatter takes a LogRecord and converts it to
 * a string.
 * <p>
 * Some formatters (such as the XMLFormatter) need to wrap head
 * and tail strings around a set of formatted records. The getHeader
 * and getTail methods can be used to obtain these strings.
 *
 * <p>
 *  Formatter提供对格式化LogRecords的支持。
 * <p>
 *  通常每个日志处理程序将有一个与它相关联的格式化程序。 Formatter采用LogRecord并将其转换为字符串。
 * <p>
 *  一些格式化器(例如XMLFormatter)需要在一组格式化记录周围包装头和尾字符串。 getHeader和getTail方法可以用来获取这些字符串。
 * 
 * 
 * @since 1.4
 */

public abstract class Formatter {

    /**
     * Construct a new formatter.
     * <p>
     *  构造一个新的格式化程序。
     * 
     */
    protected Formatter() {
    }

    /**
     * Format the given log record and return the formatted string.
     * <p>
     * The resulting formatted String will normally include a
     * localized and formatted version of the LogRecord's message field.
     * It is recommended to use the {@link Formatter#formatMessage}
     * convenience method to localize and format the message field.
     *
     * <p>
     *  格式化给定的日志记录并返回格式化的字符串。
     * <p>
     *  生成的格式化String通常包括LogRecord消息字段的本地化和格式化版本。建议使用{@link Formatter#formatMessage}便利方法来本地化和格式化消息字段。
     * 
     * 
     * @param record the log record to be formatted.
     * @return the formatted log record
     */
    public abstract String format(LogRecord record);


    /**
     * Return the header string for a set of formatted records.
     * <p>
     * This base class returns an empty string, but this may be
     * overridden by subclasses.
     *
     * <p>
     *  返回一组格式化记录的头字符串。
     * <p>
     *  这个基类返回一个空字符串,但这可能被子类覆盖。
     * 
     * 
     * @param   h  The target handler (can be null)
     * @return  header string
     */
    public String getHead(Handler h) {
        return "";
    }

    /**
     * Return the tail string for a set of formatted records.
     * <p>
     * This base class returns an empty string, but this may be
     * overridden by subclasses.
     *
     * <p>
     *  返回一组格式化记录的尾部字符串。
     * <p>
     *  这个基类返回一个空字符串,但这可能被子类覆盖。
     * 
     * 
     * @param   h  The target handler (can be null)
     * @return  tail string
     */
    public String getTail(Handler h) {
        return "";
    }


    /**
     * Localize and format the message string from a log record.  This
     * method is provided as a convenience for Formatter subclasses to
     * use when they are performing formatting.
     * <p>
     * The message string is first localized to a format string using
     * the record's ResourceBundle.  (If there is no ResourceBundle,
     * or if the message key is not found, then the key is used as the
     * format string.)  The format String uses java.text style
     * formatting.
     * <ul>
     * <li>If there are no parameters, no formatter is used.
     * <li>Otherwise, if the string contains "{0" then
     *     java.text.MessageFormat  is used to format the string.
     * <li>Otherwise no formatting is performed.
     * </ul>
     * <p>
     *
     * <p>
     *  本地化和格式化日志记录中的消息字符串。提供此方法是为了方便格式化子类在执行格式化时使用。
     * <p>
     * 消息字符串首先使用记录的ResourceBundle本地化为格式字符串。 (如果没有ResourceBundle,或者没有找到消息键,则该键用作格式字符串。
     * )格式String使用java.text样式格式。
     * <ul>
     * 
     * @param  record  the log record containing the raw message
     * @return   a localized and formatted message
     */
    public synchronized String formatMessage(LogRecord record) {
        String format = record.getMessage();
        java.util.ResourceBundle catalog = record.getResourceBundle();
        if (catalog != null) {
            try {
                format = catalog.getString(record.getMessage());
            } catch (java.util.MissingResourceException ex) {
                // Drop through.  Use record message as format
                format = record.getMessage();
            }
        }
        // Do the formatting.
        try {
            Object parameters[] = record.getParameters();
            if (parameters == null || parameters.length == 0) {
                // No parameters.  Just return format string.
                return format;
            }
            // Is it a java.text style format?
            // Ideally we could match with
            // Pattern.compile("\\{\\d").matcher(format).find())
            // However the cost is 14% higher, so we cheaply check for
            // 1 of the first 4 parameters
            if (format.indexOf("{0") >= 0 || format.indexOf("{1") >=0 ||
                        format.indexOf("{2") >=0|| format.indexOf("{3") >=0) {
                return java.text.MessageFormat.format(format, parameters);
            }
            return format;

        } catch (Exception ex) {
            // Formatting failed: use localized format string.
            return format;
        }
    }
}
