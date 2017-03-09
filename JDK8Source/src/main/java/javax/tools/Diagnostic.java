/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
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

package javax.tools;

import java.util.Locale;

/**
 * Interface for diagnostics from tools.  A diagnostic usually reports
 * a problem at a specific position in a source file.  However, not
 * all diagnostics are associated with a position or a file.
 *
 * <p>A position is a zero-based character offset from the beginning of
 * a file.  Negative values (except {@link #NOPOS}) are not valid
 * positions.
 *
 * <p>Line and column numbers begin at 1.  Negative values (except
 * {@link #NOPOS}) and 0 are not valid line or column numbers.
 *
 * <p>
 *  工具诊断接口。诊断通常会报告源文件中特定位置的问题。但是,并非所有诊断都与位置或文件相关联。
 * 
 *  <p>位置是从文件开头开始的从零开始的字符偏移。负值({@link #NOPOS}除外)不是有效位置。
 * 
 *  <p>行号和列号从1开始。负值({@link #NOPOS}除外)和0不是有效的行号或列号。
 * 
 * 
 * @param <S> the type of source object used by this diagnostic
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface Diagnostic<S> {

    /**
     * Kinds of diagnostics, for example, error or warning.
     *
     * The kind of a diagnostic can be used to determine how the
     * diagnostic should be presented to the user. For example,
     * errors might be colored red or prefixed with the word "Error",
     * while warnings might be colored yellow or prefixed with the
     * word "Warning". There is no requirement that the Kind
     * should imply any inherent semantic meaning to the message
     * of the diagnostic: for example, a tool might provide an
     * option to report all warnings as errors.
     * <p>
     *  诊断类型,例如错误或警告。
     * 
     *  诊断的种类可以用于确定如何向用户呈现诊断。例如,错误可能是红色的或带有"错误"一词的前缀,而警告可能是黄色的或带有"警告"一词的前缀。
     * 没有要求该类应当意味着诊断消息的任何固有语义含义：例如,工具可以提供将所有警告作为错误报告的选项。
     * 
     */
    enum Kind {
        /**
         * Problem which prevents the tool's normal completion.
         * <p>
         *  阻止工具正常完成的问题。
         * 
         */
        ERROR,
        /**
         * Problem which does not usually prevent the tool from
         * completing normally.
         * <p>
         *  通常不会阻止工具正常完成的问题。
         * 
         */
        WARNING,
        /**
         * Problem similar to a warning, but is mandated by the tool's
         * specification.  For example, the Java&trade; Language
         * Specification mandates warnings on certain
         * unchecked operations and the use of deprecated methods.
         * <p>
         *  问题类似于警告,但是由工具的规范强制。例如,Java&trade;语言规范要求对某些未经检查的操作发出警告,并使用过时的方法。
         * 
         */
        MANDATORY_WARNING,
        /**
         * Informative message from the tool.
         * <p>
         *  来自工具的信息性消息。
         * 
         */
        NOTE,
        /**
         * Diagnostic which does not fit within the other kinds.
         * <p>
         * 诊断不适合其他类型。
         * 
         */
        OTHER,
    }

    /**
     * Used to signal that no position is available.
     * <p>
     *  用于表示没有位置可用。
     * 
     */
    public final static long NOPOS = -1;

    /**
     * Gets the kind of this diagnostic, for example, error or
     * warning.
     * <p>
     *  获取此诊断的类型,例如,错误或警告。
     * 
     * 
     * @return the kind of this diagnostic
     */
    Kind getKind();

    /**
     * Gets the source object associated with this diagnostic.
     *
     * <p>
     *  获取与此诊断相关联的源对象。
     * 
     * 
     * @return the source object associated with this diagnostic.
     * {@code null} if no source object is associated with the
     * diagnostic.
     */
    S getSource();

    /**
     * Gets a character offset from the beginning of the source object
     * associated with this diagnostic that indicates the location of
     * the problem.  In addition, the following must be true:
     *
     * <p>{@code getStartPostion() <= getPosition()}
     * <p>{@code getPosition() <= getEndPosition()}
     *
     * <p>
     *  从与此诊断相关联的源对象的开头获取指示问题位置的字符偏移量。此外,以下必须是真的：
     * 
     *  <p> {@ code getStartPostion()<= getPosition()} <p> {@ code getPosition()<= getEndPosition()}
     * 
     * 
     * @return character offset from beginning of source; {@link
     * #NOPOS} if {@link #getSource()} would return {@code null} or if
     * no location is suitable
     */
    long getPosition();

    /**
     * Gets the character offset from the beginning of the file
     * associated with this diagnostic that indicates the start of the
     * problem.
     *
     * <p>
     *  从与此诊断相关联的文件开头获取指示问题开始的字符偏移量。
     * 
     * 
     * @return offset from beginning of file; {@link #NOPOS} if and
     * only if {@link #getPosition()} returns {@link #NOPOS}
     */
    long getStartPosition();

    /**
     * Gets the character offset from the beginning of the file
     * associated with this diagnostic that indicates the end of the
     * problem.
     *
     * <p>
     *  从与此诊断相关联的文件的开头获取指示问题结束的字符偏移量。
     * 
     * 
     * @return offset from beginning of file; {@link #NOPOS} if and
     * only if {@link #getPosition()} returns {@link #NOPOS}
     */
    long getEndPosition();

    /**
     * Gets the line number of the character offset returned by
     * {@linkplain #getPosition()}.
     *
     * <p>
     *  获取{@linkplain #getPosition()}返回的字符偏移的行号。
     * 
     * 
     * @return a line number or {@link #NOPOS} if and only if {@link
     * #getPosition()} returns {@link #NOPOS}
     */
    long getLineNumber();

    /**
     * Gets the column number of the character offset returned by
     * {@linkplain #getPosition()}.
     *
     * <p>
     *  获取{@linkplain #getPosition()}返回的字符偏移的列编号。
     * 
     * 
     * @return a column number or {@link #NOPOS} if and only if {@link
     * #getPosition()} returns {@link #NOPOS}
     */
    long getColumnNumber();

    /**
     * Gets a diagnostic code indicating the type of diagnostic.  The
     * code is implementation-dependent and might be {@code null}.
     *
     * <p>
     *  获取指示诊断类型的诊断代码。代码是实现相关的,可能是{@code null}。
     * 
     * 
     * @return a diagnostic code
     */
    String getCode();

    /**
     * Gets a localized message for the given locale.  The actual
     * message is implementation-dependent.  If the locale is {@code
     * null} use the default locale.
     *
     * <p>
     *  获取给定语言环境的本地化消息。实际消息是实现相关的。如果语言环境是{@code null},则使用默认语言环境。
     * 
     * @param locale a locale; might be {@code null}
     * @return a localized message
     */
    String getMessage(Locale locale);
}
