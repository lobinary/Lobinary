/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2004, 2010, Oracle and/or its affiliates. All rights reserved.
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

package java.util;

/**
 * FomattableFlags are passed to the {@link Formattable#formatTo
 * Formattable.formatTo()} method and modify the output format for {@linkplain
 * Formattable Formattables}.  Implementations of {@link Formattable} are
 * responsible for interpreting and validating any flags.
 *
 * <p>
 *  FomattableFlags传递到{@link Formattable#formatTo Formattable.formatTo()}方法,并修改{@linkplain Formattable Formattables}
 * 的输出格式。
 *  {@link Formattable}的实现负责解释和验证任何标志。
 * 
 * 
 * @since  1.5
 */
public class FormattableFlags {

    // Explicit instantiation of this class is prohibited.
    private FormattableFlags() {}

    /**
     * Left-justifies the output.  Spaces (<tt>'&#92;u0020'</tt>) will be added
     * at the end of the converted value as required to fill the minimum width
     * of the field.  If this flag is not set then the output will be
     * right-justified.
     *
     * <p> This flag corresponds to <tt>'-'</tt> (<tt>'&#92;u002d'</tt>) in
     * the format specifier.
     * <p>
     *  左对齐输出。将根据需要在转换值的末尾添加空格(<tt>'\ u0020'</tt>),以填充字段的最小宽度。如果此标志未设置,则输出将右对齐。
     * 
     *  <p>此标志对应于格式说明符中的<tt>' - '</tt>(<tt>'\ u002d'</tt>)。
     * 
     */
    public static final int LEFT_JUSTIFY = 1<<0; // '-'

    /**
     * Converts the output to upper case according to the rules of the
     * {@linkplain java.util.Locale locale} given during creation of the
     * <tt>formatter</tt> argument of the {@link Formattable#formatTo
     * formatTo()} method.  The output should be equivalent the following
     * invocation of {@link String#toUpperCase(java.util.Locale)}
     *
     * <pre>
     *     out.toUpperCase() </pre>
     *
     * <p> This flag corresponds to <tt>'S'</tt> (<tt>'&#92;u0053'</tt>) in
     * the format specifier.
     * <p>
     *  在创建{@link Formattable#formatTo formatTo()}方法的<tt> formatter </tt>参数期间,根据{@linkplain java.util.Locale locale}
     * 的规则将输出转换为大写。
     * 输出应该等效于以下调用{@link String#toUpperCase(java.util.Locale)}。
     * 
     * <pre>
     *  out.toUpperCase()</pre>
     * 
     */
    public static final int UPPERCASE = 1<<1;    // 'S'

    /**
     * Requires the output to use an alternate form.  The definition of the
     * form is specified by the <tt>Formattable</tt>.
     *
     * <p> This flag corresponds to <tt>'#'</tt> (<tt>'&#92;u0023'</tt>) in
     * the format specifier.
     * <p>
     *  <p>此标记对应于格式说明符中的<tt>'S'</tt>(<tt>'\ u0053'</tt>)。
     * 
     */
    public static final int ALTERNATE = 1<<2;    // '#'
}
