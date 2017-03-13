/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2013, Oracle and/or its affiliates. All rights reserved.
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

/*
 * Portions Copyright IBM Corporation, 2001. All Rights Reserved.
 * <p>
 *  部分版权所有IBM Corporation,2001保留所有权利
 * 
 */
package java.math;

/**
 * Specifies a <i>rounding behavior</i> for numerical operations
 * capable of discarding precision. Each rounding mode indicates how
 * the least significant returned digit of a rounded result is to be
 * calculated.  If fewer digits are returned than the digits needed to
 * represent the exact numerical result, the discarded digits will be
 * referred to as the <i>discarded fraction</i> regardless the digits'
 * contribution to the value of the number.  In other words,
 * considered as a numerical value, the discarded fraction could have
 * an absolute value greater than one.
 *
 * <p>Each rounding mode description includes a table listing how
 * different two-digit decimal values would round to a one digit
 * decimal value under the rounding mode in question.  The result
 * column in the tables could be gotten by creating a
 * {@code BigDecimal} number with the specified value, forming a
 * {@link MathContext} object with the proper settings
 * ({@code precision} set to {@code 1}, and the
 * {@code roundingMode} set to the rounding mode in question), and
 * calling {@link BigDecimal#round round} on this number with the
 * proper {@code MathContext}.  A summary table showing the results
 * of these rounding operations for all rounding modes appears below.
 *
 *<table border>
 * <caption><b>Summary of Rounding Operations Under Different Rounding Modes</b></caption>
 * <tr><th></th><th colspan=8>Result of rounding input to one digit with the given
 *                           rounding mode</th>
 * <tr valign=top>
 * <th>Input Number</th>         <th>{@code UP}</th>
 *                                           <th>{@code DOWN}</th>
 *                                                        <th>{@code CEILING}</th>
 *                                                                       <th>{@code FLOOR}</th>
 *                                                                                    <th>{@code HALF_UP}</th>
 *                                                                                                   <th>{@code HALF_DOWN}</th>
 *                                                                                                                    <th>{@code HALF_EVEN}</th>
 *                                                                                                                                     <th>{@code UNNECESSARY}</th>
 *
 * <tr align=right><td>5.5</td>  <td>6</td>  <td>5</td>    <td>6</td>    <td>5</td>  <td>6</td>      <td>5</td>       <td>6</td>       <td>throw {@code ArithmeticException}</td>
 * <tr align=right><td>2.5</td>  <td>3</td>  <td>2</td>    <td>3</td>    <td>2</td>  <td>3</td>      <td>2</td>       <td>2</td>       <td>throw {@code ArithmeticException}</td>
 * <tr align=right><td>1.6</td>  <td>2</td>  <td>1</td>    <td>2</td>    <td>1</td>  <td>2</td>      <td>2</td>       <td>2</td>       <td>throw {@code ArithmeticException}</td>
 * <tr align=right><td>1.1</td>  <td>2</td>  <td>1</td>    <td>2</td>    <td>1</td>  <td>1</td>      <td>1</td>       <td>1</td>       <td>throw {@code ArithmeticException}</td>
 * <tr align=right><td>1.0</td>  <td>1</td>  <td>1</td>    <td>1</td>    <td>1</td>  <td>1</td>      <td>1</td>       <td>1</td>       <td>1</td>
 * <tr align=right><td>-1.0</td> <td>-1</td> <td>-1</td>   <td>-1</td>   <td>-1</td> <td>-1</td>     <td>-1</td>      <td>-1</td>      <td>-1</td>
 * <tr align=right><td>-1.1</td> <td>-2</td> <td>-1</td>   <td>-1</td>   <td>-2</td> <td>-1</td>     <td>-1</td>      <td>-1</td>      <td>throw {@code ArithmeticException}</td>
 * <tr align=right><td>-1.6</td> <td>-2</td> <td>-1</td>   <td>-1</td>   <td>-2</td> <td>-2</td>     <td>-2</td>      <td>-2</td>      <td>throw {@code ArithmeticException}</td>
 * <tr align=right><td>-2.5</td> <td>-3</td> <td>-2</td>   <td>-2</td>   <td>-3</td> <td>-3</td>     <td>-2</td>      <td>-2</td>      <td>throw {@code ArithmeticException}</td>
 * <tr align=right><td>-5.5</td> <td>-6</td> <td>-5</td>   <td>-5</td>   <td>-6</td> <td>-6</td>     <td>-5</td>      <td>-6</td>      <td>throw {@code ArithmeticException}</td>
 *</table>
 *
 *
 * <p>This {@code enum} is intended to replace the integer-based
 * enumeration of rounding mode constants in {@link BigDecimal}
 * ({@link BigDecimal#ROUND_UP}, {@link BigDecimal#ROUND_DOWN},
 * etc. ).
 *
 * <p>
 *  指定能够丢弃精度的数字运算的<i>舍入行为</i>每个舍入模式指示如何计算舍入结果的最低有效返回数字如果返回的数字少于表示精确数字所需的数字丢弃的数字将被称为丢弃的部分,而不管数字对数字的值的贡献。
 * 换句话说,被认为是数值,丢弃的部分可以具有更大的绝对值比一个。
 * 
 * <p>每个舍入模式描述包括一个表,列出了在舍入模式下不同的两位十进制值如何舍入到一位十进制值。
 * 可以通过创建一个{@code BigDecimal}数字来获得表中的结果列({@code precision}设置为{@code 1},{@code roundingMode}设置为所讨论的舍入模式)形
 * 成一个{@link MathContext}对象,然后调用{ @link BigDecimal#round round}使用适当的{@code MathContext}显示下面所有舍入模式的这些舍入操作
 * 的结果的摘要表如下所示。
 * <p>每个舍入模式描述包括一个表,列出了在舍入模式下不同的两位十进制值如何舍入到一位十进制值。
 * 
 * table border>
 * <caption> <b>不同舍入模式下舍入操作的摘要</b> </caption> <tr> <th> </th> <th colspan = 8> </th>
 * <tr valign=top>
 *  <th>输入号码</th> <th> {@ code UP} </th> <th> {@ code DOWN} </th> <th> {@ code CEILING} </th> <th> {@代码FLOOR}
 *  </th> <th> {@ code HALF_UP} </th> <th> {@ code HALF_DOWN} </th> <th> {@ code HALF_EVEN} </th> <th> {@ code UNNECESSARY }
 *  </th>。
 * 
 * <tr align = right> <td> 55 </td> <td> 6 </td> <td> 5 </td> <td> 6 </td> <td> 5 </td> <td> 6 </td> <td>
 *  5 </td> <td> 6 </td> <td> throw {@code ArithmeticException} </td> <tr align = right> <td> 3 </td> <td>
 *  2 </td> <td> 3 </td> <td> 2 </td> <td> 3 </td> <td> 2 </td> <td> / td> <td> throw {@code ArithmeticException}
 *  </td> <tr align = right> <td> 16 </td> <td> 2 </td> <td> 1 </td> <td> 2 </td> <td> 1 </td> <td> 2 </td>
 *  <td> 2 </td> <td> 2 </td> <td> throw {@code ArithmeticException} </td> tr align = right> <td> 11 </td>
 *  <td> 2 </td> <td> 1 </td> <td> 2 </td> <td> 1 </td> <td> 1 < / td> <td> 1 </td> <td> 1 </td> <td> th
 * row {@code ArithmeticException} </td> <tr align = right> <td> 10 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD>
 *  1 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD> 1 < / TD> <TD> 1 </TD> <TD> 1 </TD> <TR ALIGN =右> <TD> -10 </TD>
 *  <TD> 1 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD> -1 </TD>
 *  <TR ALIGN =右> <TD> -11 </TD> <TD> -2 </TD> <TD> 1 </TD> <TD> 1 </TD> <TD > -2 </TD> <TD> 1 </TD> <TD>
 *  1 </TD> <TD> 1 </TD> <TD> {@code抛出ArithmeticException,} </TD> <TR对齐=右> <TD> -16 </TD> <TD> -2 </TD> 
 * <TD> 1 </TD> <TD> 1 </TD> <TD> -2 </TD> <TD > -2 </TD> <TD> -2 </TD> <TD> -2 </TD> <TD> {@code抛出ArithmeticException,}
 *  </TD> <TR ALIGN =右> <TD> -25 </TD> <TD> -3 </TD> <TD> -2 </TD> <TD> -2 </TD> <TD> -3 </TD> <TD> -3 </TD>
 *  <TD> -2 </TD> <TD> -2 </TD> <TD> {@code抛出ArithmeticException,} </TD> <TR ALIGN =右> <TD> -55 </TD> <TD>
 *   - 6 </TD> <TD> -5 </TD> <TD> -5 </TD> <TD> -6 </TD> <TD> -6 </TD> <TD> -5 </TD> <TD> -6 </TD> <TD> 
 * {@code抛出ArithmeticException,} </TD>。
 * /table>
 * 
 * <p>此{@code enum}旨在替换{@link BigDecimal}({@link BigDecimal#ROUND_UP},{@link BigDecimal#ROUND_DOWN}等)中的舍
 * 入模式常数的基于整数的枚举,。
 * 
 * 
 * @see     BigDecimal
 * @see     MathContext
 * @author  Josh Bloch
 * @author  Mike Cowlishaw
 * @author  Joseph D. Darcy
 * @since 1.5
 */
public enum RoundingMode {

        /**
         * Rounding mode to round away from zero.  Always increments the
         * digit prior to a non-zero discarded fraction.  Note that this
         * rounding mode never decreases the magnitude of the calculated
         * value.
         *
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode UP Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code UP} rounding
         *<tr align=right><td>5.5</td>  <td>6</td>
         *<tr align=right><td>2.5</td>  <td>3</td>
         *<tr align=right><td>1.6</td>  <td>2</td>
         *<tr align=right><td>1.1</td>  <td>2</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>-2</td>
         *<tr align=right><td>-1.6</td> <td>-2</td>
         *<tr align=right><td>-2.5</td> <td>-3</td>
         *<tr align=right><td>-5.5</td> <td>-6</td>
         *</table>
         * <p>
         *  用于从零舍入的舍入模式始终在非零丢弃比例之前递增数字注意,此舍入模式不会减小计算值的大小
         * 
         *  p>示例：
         * table border>
         * <caption> <b>舍入模式UP示例</b> </caption> tr valign = top> <th>输入数字</th> <th>使用{@code UP} tr align = right
         * > <td> 55 </td> <td> 6 </td> tr align = right> <td> 25 </td> <td> 3 </td> tr align = right> <td> 16 </td>
         *  <td> 2 </td> tr align = right> <td> 11 </td> <td> 2 </td> tr align = right> <td> 10 </td> <td> 1 </td>
         *  tr align = right> <td> -10 </td> <td> -1 </td> tr align = right> <td> -11 </td> <td> -2 </td > tr al
         * ign = right> <td> -16 </td> <td> -2 </td> tr align = right> <td> -25 </td> <td> -3 </td> tr align =右>
         *  <td> -55 </td> <td> -6 </td>。
         * /table>
         */
    UP(BigDecimal.ROUND_UP),

        /**
         * Rounding mode to round towards zero.  Never increments the digit
         * prior to a discarded fraction (i.e., truncates).  Note that this
         * rounding mode never increases the magnitude of the calculated value.
         *
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode DOWN Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code DOWN} rounding
         *<tr align=right><td>5.5</td>  <td>5</td>
         *<tr align=right><td>2.5</td>  <td>2</td>
         *<tr align=right><td>1.6</td>  <td>1</td>
         *<tr align=right><td>1.1</td>  <td>1</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>-1</td>
         *<tr align=right><td>-1.6</td> <td>-1</td>
         *<tr align=right><td>-2.5</td> <td>-2</td>
         *<tr align=right><td>-5.5</td> <td>-5</td>
         *</table>
         * <p>
         *  舍入模式向舍入到零从不在舍弃部分之前递增数字(即截断)注意,此舍入模式从不增加计算值的大小
         * 
         *  p>示例：
         * table border>
         * <caption> <b>舍入模式DOWN示例</b> </caption> tr valign = top> <th>输入数字</th> <th>使用{@code DOWN} tr align = r
         * ight> <td> 55 </td> <td> 5 </td> tr align = right> <td> 25 </td> <td> 2 </td> tr align = right> <td> 
         * 16 </td> <td> 1 </td> tr align = right> <td> 11 </td> <td> 1 </td> tr align = right> <td> 10 </td> <td>
         *  1 </td> tr align = right> <td> -10 </td> <td> -1 </td> tr align = right> <td> -11 </td> <td> -1 </td >
         *  tr align = right> <td> -16 </td> <td> -1 </td> tr align = right> <td> -25 </td> <td> -2 </td> tr ali
         * gn =右> <td> -55 </td> <td> -5 </td>。
         * /table>
         */
    DOWN(BigDecimal.ROUND_DOWN),

        /**
         * Rounding mode to round towards positive infinity.  If the
         * result is positive, behaves as for {@code RoundingMode.UP};
         * if negative, behaves as for {@code RoundingMode.DOWN}.  Note
         * that this rounding mode never decreases the calculated value.
         *
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode CEILING Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code CEILING} rounding
         *<tr align=right><td>5.5</td>  <td>6</td>
         *<tr align=right><td>2.5</td>  <td>3</td>
         *<tr align=right><td>1.6</td>  <td>2</td>
         *<tr align=right><td>1.1</td>  <td>2</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>-1</td>
         *<tr align=right><td>-1.6</td> <td>-1</td>
         *<tr align=right><td>-2.5</td> <td>-2</td>
         *<tr align=right><td>-5.5</td> <td>-5</td>
         *</table>
         * <p>
         *  舍入模式向正无穷大舍入如果结果为正,则表现为{@code RoundingModeUP};如果为负,表现为{@code RoundingModeDOWN}注意,此舍入模式不会减少计算的值
         * 
         * p>示例：
         * table border>
         *  <caption> <b>舍入模式CEILING示例</b> </caption> tr valign = top> <th>输入数字</th> <th>输入四舍五入为一位数字<br> with {@code CEILING}
         *  tr align = right> <td> 55 </td> <td> 6 </td> tr align = right> <td> 25 </td> <td> 3 </td> tr align =
         *  right> <td> 16 </td> <td> 2 </td> tr align = right> <td> 11 </td> <td> 2 </td> tr align = right> <td>
         *  10 </td> <td> 1 </td> tr align = right> <td> -10 </td> <td> -1 </td> tr align = right> <td> -11 </td>
         *  <td> -1 </td > tr align = right> <td> -16 </td> <td> -1 </td> tr align = right> <td> -25 </td> <td> 
         * -2 </td> tr align =右> <td> -55 </td> <td> -5 </td>。
         * /table>
         */
    CEILING(BigDecimal.ROUND_CEILING),

        /**
         * Rounding mode to round towards negative infinity.  If the
         * result is positive, behave as for {@code RoundingMode.DOWN};
         * if negative, behave as for {@code RoundingMode.UP}.  Note that
         * this rounding mode never increases the calculated value.
         *
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode FLOOR Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code FLOOR} rounding
         *<tr align=right><td>5.5</td>  <td>5</td>
         *<tr align=right><td>2.5</td>  <td>2</td>
         *<tr align=right><td>1.6</td>  <td>1</td>
         *<tr align=right><td>1.1</td>  <td>1</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>-2</td>
         *<tr align=right><td>-1.6</td> <td>-2</td>
         *<tr align=right><td>-2.5</td> <td>-3</td>
         *<tr align=right><td>-5.5</td> <td>-6</td>
         *</table>
         * <p>
         * 舍入模式向负无穷大舍入如果结果为正,则表示为{@code RoundingModeDOWN};如果为负,表现为{@code RoundingModeUP}注意,此舍入模式不会增加计算的值
         * 
         *  p>示例：
         * table border>
         * <caption> <b>舍入模式FLOOR示例</b> </caption> tr valign = top> <th>输入数字</th> <th>使用{@code FLOOR} tr align =
         *  right> <td> 55 </td> <td> 5 </td> tr align = right> <td> 25 </td> <td> 2 </td> tr align = right> <td>
         *  16 </td> <td> 1 </td> tr align = right> <td> 11 </td> <td> 1 </td> tr align = right> <td> 10 </td> <td>
         *  1 </td> tr align = right> <td> -10 </td> <td> -1 </td> tr align = right> <td> -11 </td> <td> -2 </td >
         *  tr align = right> <td> -16 </td> <td> -2 </td> tr align = right> <td> -25 </td> <td> -3 </td> tr ali
         * gn =右> <td> -55 </td> <td> -6 </td>。
         * /table>
         */
    FLOOR(BigDecimal.ROUND_FLOOR),

        /**
         * Rounding mode to round towards {@literal "nearest neighbor"}
         * unless both neighbors are equidistant, in which case round up.
         * Behaves as for {@code RoundingMode.UP} if the discarded
         * fraction is &ge; 0.5; otherwise, behaves as for
         * {@code RoundingMode.DOWN}.  Note that this is the rounding
         * mode commonly taught at school.
         *
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode HALF_UP Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code HALF_UP} rounding
         *<tr align=right><td>5.5</td>  <td>6</td>
         *<tr align=right><td>2.5</td>  <td>3</td>
         *<tr align=right><td>1.6</td>  <td>2</td>
         *<tr align=right><td>1.1</td>  <td>1</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>-1</td>
         *<tr align=right><td>-1.6</td> <td>-2</td>
         *<tr align=right><td>-2.5</td> <td>-3</td>
         *<tr align=right><td>-5.5</td> <td>-6</td>
         *</table>
         * <p>
         * 除非两个邻居都是等距的,在这种情况下向上舍入为{@code RoundingModeUP},如果丢弃的比特是&gt;,则舍入模式向{@literal"最近邻" 05;否则,表现为{@code RoundingModeDOWN}
         * 注意,这是在学校通常教授的舍入模式。
         * 
         *  p>示例：
         * table border>
         * <caption> <b>舍入模式HALF_UP示例</b> </caption> tr valign = top> <th>输入数字</th> <th>使用{@code HALF_UP} tr ali
         * gn = right> <td> 55 </td> <td> 6 </td> tr align = right> <td> 25 </td> <td> 3 </td> tr align = right>
         *  <td> 16 </td> <td> 2 </td> tr align = right> <td> 11 </td> <td> 1 </td> tr align = right> <td> 10 </td>
         *  <td> 1 </td> tr align = right> <td> -10 </td> <td> -1 </td> tr align = right> <td> -11 </td> <td> -1
         *  </td > tr align = right> <td> -16 </td> <td> -2 </td> tr align = right> <td> -25 </td> <td> -3 </td>
         *  tr align =右> <td> -55 </td> <td> -6 </td>。
         * /table>
         */
    HALF_UP(BigDecimal.ROUND_HALF_UP),

        /**
         * Rounding mode to round towards {@literal "nearest neighbor"}
         * unless both neighbors are equidistant, in which case round
         * down.  Behaves as for {@code RoundingMode.UP} if the discarded
         * fraction is &gt; 0.5; otherwise, behaves as for
         * {@code RoundingMode.DOWN}.
         *
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode HALF_DOWN Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code HALF_DOWN} rounding
         *<tr align=right><td>5.5</td>  <td>5</td>
         *<tr align=right><td>2.5</td>  <td>2</td>
         *<tr align=right><td>1.6</td>  <td>2</td>
         *<tr align=right><td>1.1</td>  <td>1</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>-1</td>
         *<tr align=right><td>-1.6</td> <td>-2</td>
         *<tr align=right><td>-2.5</td> <td>-2</td>
         *<tr align=right><td>-5.5</td> <td>-5</td>
         *</table>
         * <p>
         * 除非两个邻居都是等距的,在这种情况下向下舍入为{@code RoundingModeUP},如果丢弃的分数> 0,则舍入模式向{@literal"最近邻" 05;否则,表现为{@code RoundingModeDOWN}
         * 。
         * 
         *  p>示例：
         * table border>
         * <caption> <b>舍入模式HALF_DOWN示例</b> </caption> tr valign = top> <th>输入数字</th> <th>使用{@code HALF_DOWN} tr
         *  align = right> <td> 55 </td> <td> 5 </td> tr align = right> <td> 25 </td> <td> 2 </td> tr align = ri
         * ght> <td> 16 </td> <td> 2 </td> tr align = right> <td> 11 </td> <td> 1 </td> tr align = right> <td> 1
         * 0 </td> <td> 1 </td> tr align = right> <td> -10 </td> <td> -1 </td> tr align = right> <td> -11 </td> 
         * <td> -1 </td > tr align = right> <td> -16 </td> <td> -2 </td> tr align = right> <td> -25 </td> <td> -
         * 2 </td> tr align =右> <td> -55 </td> <td> -5 </td>。
         * /table>
         */
    HALF_DOWN(BigDecimal.ROUND_HALF_DOWN),

        /**
         * Rounding mode to round towards the {@literal "nearest neighbor"}
         * unless both neighbors are equidistant, in which case, round
         * towards the even neighbor.  Behaves as for
         * {@code RoundingMode.HALF_UP} if the digit to the left of the
         * discarded fraction is odd; behaves as for
         * {@code RoundingMode.HALF_DOWN} if it's even.  Note that this
         * is the rounding mode that statistically minimizes cumulative
         * error when applied repeatedly over a sequence of calculations.
         * It is sometimes known as {@literal "Banker's rounding,"} and is
         * chiefly used in the USA.  This rounding mode is analogous to
         * the rounding policy used for {@code float} and {@code double}
         * arithmetic in Java.
         *
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode HALF_EVEN Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code HALF_EVEN} rounding
         *<tr align=right><td>5.5</td>  <td>6</td>
         *<tr align=right><td>2.5</td>  <td>2</td>
         *<tr align=right><td>1.6</td>  <td>2</td>
         *<tr align=right><td>1.1</td>  <td>1</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>-1</td>
         *<tr align=right><td>-1.6</td> <td>-2</td>
         *<tr align=right><td>-2.5</td> <td>-2</td>
         *<tr align=right><td>-5.5</td> <td>-6</td>
         *</table>
         * <p>
         * 除非两个邻居是等距的,否则舍入模式向{@literal"最近邻"}舍入,在这种情况下,如果丢弃的部分的左边的数字是奇数,则向着{@code RoundingModeHALF_UP}行为与{@code RoundingModeHALF_DOWN}
         * 如果是偶数注意,这是统计上最小化累积误差的一个计算序列重复应用的舍入模式它有时被称为{@literal"银行家的舍入,"},主要用于美国此舍入模式类似于用于Java中的{@code float}和{@code double}
         * 算术的舍入策略。
         * 
         *  p>示例：
         * table border>
         * <caption> <b>舍入模式HALF_EVEN范例</b> </caption> tr valign = top> <th>输入数字</th> <th>使用{@code HALF_EVEN}舍入t
         * r align = right> <td> 55 </td> <td> 6 </td> tr align = right> <td> 25 </td> <td> 2 </td> tr align = r
         * ight> <td> 16 </td> <td> 2 </td> tr align = right> <td> 11 </td> <td> 1 </td> tr align = right> <td> 
         * 10 </td> <td> 1 </td> tr align = right> <td> -10 </td> <td> -1 </td> tr align = right> <td> -11 </td>
         *  <td> -1 </td > tr align = right> <td> -16 </td> <td> -2 </td> tr align = right> <td> -25 </td> <td> 
         * -2 </td> tr align =右> <td> -55 </td> <td> -6 </td>。
         * /table>
         */
    HALF_EVEN(BigDecimal.ROUND_HALF_EVEN),

        /**
         * Rounding mode to assert that the requested operation has an exact
         * result, hence no rounding is necessary.  If this rounding mode is
         * specified on an operation that yields an inexact result, an
         * {@code ArithmeticException} is thrown.
         *<p>Example:
         *<table border>
         * <caption><b>Rounding mode UNNECESSARY Examples</b></caption>
         *<tr valign=top><th>Input Number</th>
         *    <th>Input rounded to one digit<br> with {@code UNNECESSARY} rounding
         *<tr align=right><td>5.5</td>  <td>throw {@code ArithmeticException}</td>
         *<tr align=right><td>2.5</td>  <td>throw {@code ArithmeticException}</td>
         *<tr align=right><td>1.6</td>  <td>throw {@code ArithmeticException}</td>
         *<tr align=right><td>1.1</td>  <td>throw {@code ArithmeticException}</td>
         *<tr align=right><td>1.0</td>  <td>1</td>
         *<tr align=right><td>-1.0</td> <td>-1</td>
         *<tr align=right><td>-1.1</td> <td>throw {@code ArithmeticException}</td>
         *<tr align=right><td>-1.6</td> <td>throw {@code ArithmeticException}</td>
         *<tr align=right><td>-2.5</td> <td>throw {@code ArithmeticException}</td>
         *<tr align=right><td>-5.5</td> <td>throw {@code ArithmeticException}</td>
         *</table>
         * <p>
         * 舍入模式断言所请求的操作具有确切的结果,因此不需要舍入如果对产生不精确结果的操作指定此舍入模式,则会抛出{@code ArithmeticException} p>示例：
         * table border>
         * <caption> <b>舍入模式UNNECESSARY示例</b> </caption> tr valign = top> <th>输入数字</th> <th>使用{@code UNNECESSARY}
         *  tr align = right> <td> 55 </td> <td> 55 </td> <td>抛出{@code ArithmeticException} </td> td> tr align =
         *  right> <td> 16 </td> <td> 16 </td> <td> throw {@code ArithmeticException} </td> tr align = right> <td>
         *  10 </td> <td> 1 </td> tr align = right> <td> -10 </td> <td> -1 </td> = right> <td> -11 </td> <td> th
         * row {@code ArithmeticException} </td> td> tr align = right> <td> -25 </td> <td> throw {@code ArithmeticException}
         *  </td> tr align = right> <td> -55 </td> <td> throw {@code ArithmeticException} </td>。
         */
    UNNECESSARY(BigDecimal.ROUND_UNNECESSARY);

    // Corresponding BigDecimal rounding constant
    final int oldMode;

    /**
     * Constructor
     *
     * <p>
     * /table>
     * 
     * @param oldMode The {@code BigDecimal} constant corresponding to
     *        this mode
     */
    private RoundingMode(int oldMode) {
        this.oldMode = oldMode;
    }

    /**
     * Returns the {@code RoundingMode} object corresponding to a
     * legacy integer rounding mode constant in {@link BigDecimal}.
     *
     * <p>
     * 构造函数
     * 
     * 
     * @param  rm legacy integer rounding mode to convert
     * @return {@code RoundingMode} corresponding to the given integer.
     * @throws IllegalArgumentException integer is out of range
     */
    public static RoundingMode valueOf(int rm) {
        switch(rm) {

        case BigDecimal.ROUND_UP:
            return UP;

        case BigDecimal.ROUND_DOWN:
            return DOWN;

        case BigDecimal.ROUND_CEILING:
            return CEILING;

        case BigDecimal.ROUND_FLOOR:
            return FLOOR;

        case BigDecimal.ROUND_HALF_UP:
            return HALF_UP;

        case BigDecimal.ROUND_HALF_DOWN:
            return HALF_DOWN;

        case BigDecimal.ROUND_HALF_EVEN:
            return HALF_EVEN;

        case BigDecimal.ROUND_UNNECESSARY:
            return UNNECESSARY;

        default:
            throw new IllegalArgumentException("argument out of range");
        }
    }
}
