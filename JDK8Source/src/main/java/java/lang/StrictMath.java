/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.lang;
import java.util.Random;
import sun.misc.DoubleConsts;

/**
 * The class {@code StrictMath} contains methods for performing basic
 * numeric operations such as the elementary exponential, logarithm,
 * square root, and trigonometric functions.
 *
 * <p>To help ensure portability of Java programs, the definitions of
 * some of the numeric functions in this package require that they
 * produce the same results as certain published algorithms. These
 * algorithms are available from the well-known network library
 * {@code netlib} as the package "Freely Distributable Math
 * Library," <a
 * href="ftp://ftp.netlib.org/fdlibm.tar">{@code fdlibm}</a>. These
 * algorithms, which are written in the C programming language, are
 * then to be understood as executed with all floating-point
 * operations following the rules of Java floating-point arithmetic.
 *
 * <p>The Java math library is defined with respect to
 * {@code fdlibm} version 5.3. Where {@code fdlibm} provides
 * more than one definition for a function (such as
 * {@code acos}), use the "IEEE 754 core function" version
 * (residing in a file whose name begins with the letter
 * {@code e}).  The methods which require {@code fdlibm}
 * semantics are {@code sin}, {@code cos}, {@code tan},
 * {@code asin}, {@code acos}, {@code atan},
 * {@code exp}, {@code log}, {@code log10},
 * {@code cbrt}, {@code atan2}, {@code pow},
 * {@code sinh}, {@code cosh}, {@code tanh},
 * {@code hypot}, {@code expm1}, and {@code log1p}.
 *
 * <p>
 * The platform uses signed two's complement integer arithmetic with
 * int and long primitive types.  The developer should choose
 * the primitive type to ensure that arithmetic operations consistently
 * produce correct results, which in some cases means the operations
 * will not overflow the range of values of the computation.
 * The best practice is to choose the primitive type and algorithm to avoid
 * overflow. In cases where the size is {@code int} or {@code long} and
 * overflow errors need to be detected, the methods {@code addExact},
 * {@code subtractExact}, {@code multiplyExact}, and {@code toIntExact}
 * throw an {@code ArithmeticException} when the results overflow.
 * For other arithmetic operations such as divide, absolute value,
 * increment, decrement, and negation overflow occurs only with
 * a specific minimum or maximum value and should be checked against
 * the minimum or maximum as appropriate.
 *
 * <p>
 *  类{@code StrictMath}包含用于执行基本数值操作的方法,例如基本指数,对数,平方根和三角函数
 * 
 * <p>为了确保Java程序的可移植性,此包中的一些数字函数的定义要求它们产生与某些已发布算法相同的结果。
 * 这些算法可从众所周知的网络库{@code netlib}获得封装"Freely Distributable Math Library",<a href=\"ftp://ftpnetliborg/fdlibmtar\">
 *  {@code fdlibm} </a>这些用C编程语言编写的算法被理解为使用遵循Java浮点运算规则的所有浮点运算执行。
 * <p>为了确保Java程序的可移植性,此包中的一些数字函数的定义要求它们产生与某些已发布算法相同的结果。
 * 
 * <p> Java数学库是根据{@code fdlibm} version 53定义的其中{@code fdlibm}为函数(例如{@code acos})提供多个定义,请使用"IEEE 754核心函数"
 * 版本(驻留在以字母{@code e}开头的文件中)需要{@code fdlibm}语义的方法是{@code sin},{@code cos},{@code tan},{@ code asin},{@code acos}
 * ,{@code atan},{@code exp},{@code log},{@code log10},{@code cbrt},{@code atan2},{@code pow },{@code sinh}
 * ,{@code cosh},{@code tanh},{@code hypot},{@code expm1}和{@code log1p}。
 * 
 * <p>
 * 平台使用带有int和长原语类型的带符号二进制补码整数算法开发人员应该选择原语类型,以确保算术运算一致地产生正确的结果,在某些情况下意味着操作不会溢出计算值的范围。
 * 实践是选择原始类型和算法以避免溢出在大小为{@code int}或{@code long}且需要检测溢出错误的情况下,方法{@code addExact},{@code subtractExact} ,
 * {@code multiplyExact}和{@code toIntExact}在结果溢出时抛出{@code ArithmeticException}对于除法,绝对值,增量,减量和取反溢出等其他算术运算
 * ,只发生在特定的最小值或最大值,并应根据最小值或最大值进行检查。
 * 平台使用带有int和长原语类型的带符号二进制补码整数算法开发人员应该选择原语类型,以确保算术运算一致地产生正确的结果,在某些情况下意味着操作不会溢出计算值的范围。
 * 
 * 
 * @author  unascribed
 * @author  Joseph D. Darcy
 * @since   1.3
 */

public final class StrictMath {

    /**
     * Don't let anyone instantiate this class.
     * <p>
     * 不要让任何人实例化这个类
     * 
     */
    private StrictMath() {}

    /**
     * The {@code double} value that is closer than any other to
     * <i>e</i>, the base of the natural logarithms.
     * <p>
     *  {@code double}值比任何其他值更接近<i> e </i>,即自然对数的底数
     * 
     */
    public static final double E = 2.7182818284590452354;

    /**
     * The {@code double} value that is closer than any other to
     * <i>pi</i>, the ratio of the circumference of a circle to its
     * diameter.
     * <p>
     *  {@code double}值比任何其他值更接近<i> pi </i>,圆的圆周与其直径的比值
     * 
     */
    public static final double PI = 3.14159265358979323846;

    /**
     * Returns the trigonometric sine of an angle. Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the
     * result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * <p>
     *  返回角度的三角正弦特殊情况：<ul> <li>如果参数为NaN或无穷大,则结果为NaN <li>如果参数为零,则结果为具有相同符号的零参数</ul>
     * 
     * 
     * @param   a   an angle, in radians.
     * @return  the sine of the argument.
     */
    public static native double sin(double a);

    /**
     * Returns the trigonometric cosine of an angle. Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the
     * result is NaN.</ul>
     *
     * <p>
     *  返回角度的三角形余弦特殊情况：<ul> <li>如果参数为NaN或无穷大,则结果为NaN </ul>
     * 
     * 
     * @param   a   an angle, in radians.
     * @return  the cosine of the argument.
     */
    public static native double cos(double a);

    /**
     * Returns the trigonometric tangent of an angle. Special cases:
     * <ul><li>If the argument is NaN or an infinity, then the result
     * is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * <p>
     * 返回角度的三角正切特殊情况：<ul> <li>如果参数为NaN或无穷大,则结果为NaN <li>如果参数为零,则结果为具有相同符号的零参数</ul>
     * 
     * 
     * @param   a   an angle, in radians.
     * @return  the tangent of the argument.
     */
    public static native double tan(double a);

    /**
     * Returns the arc sine of a value; the returned angle is in the
     * range -<i>pi</i>/2 through <i>pi</i>/2.  Special cases:
     * <ul><li>If the argument is NaN or its absolute value is greater
     * than 1, then the result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * <p>
     *  返回值的反正弦值;返回的角度在-π/ 2到π/ 2范围内。
     * 特殊情况：<ul> <li>如果参数是NaN或其绝对值大于1,则结果为NaN <li>如果参数为零,则结果为具有与参数相同符号的零</ul>。
     * 
     * 
     * @param   a   the value whose arc sine is to be returned.
     * @return  the arc sine of the argument.
     */
    public static native double asin(double a);

    /**
     * Returns the arc cosine of a value; the returned angle is in the
     * range 0.0 through <i>pi</i>.  Special case:
     * <ul><li>If the argument is NaN or its absolute value is greater
     * than 1, then the result is NaN.</ul>
     *
     * <p>
     *  返回值的反余弦值;返回的角度在范围00到特殊情况：<ul> <li>如果参数是NaN或其绝对值大于1,则结果是NaN </ul>
     * 
     * 
     * @param   a   the value whose arc cosine is to be returned.
     * @return  the arc cosine of the argument.
     */
    public static native double acos(double a);

    /**
     * Returns the arc tangent of a value; the returned angle is in the
     * range -<i>pi</i>/2 through <i>pi</i>/2.  Special cases:
     * <ul><li>If the argument is NaN, then the result is NaN.
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.</ul>
     *
     * <p>
     * 返回值的反正切值;返回的角度在-π/ 2到π/ 2范围内。特殊情况：<ul> <li>如果参数是NaN,则结果是NaN < li>如果参数为零,则结果为与参数</ul>具有相同符号的零
     * 
     * 
     * @param   a   the value whose arc tangent is to be returned.
     * @return  the arc tangent of the argument.
     */
    public static native double atan(double a);

    /**
     * Converts an angle measured in degrees to an approximately
     * equivalent angle measured in radians.  The conversion from
     * degrees to radians is generally inexact.
     *
     * <p>
     *  将以度为单位测量的角度转换为以弧度为单位的近似等效角度。从度到弧度的转换通常不准确
     * 
     * 
     * @param   angdeg   an angle, in degrees
     * @return  the measurement of the angle {@code angdeg}
     *          in radians.
     */
    public static strictfp double toRadians(double angdeg) {
        // Do not delegate to Math.toRadians(angdeg) because
        // this method has the strictfp modifier.
        return angdeg / 180.0 * PI;
    }

    /**
     * Converts an angle measured in radians to an approximately
     * equivalent angle measured in degrees.  The conversion from
     * radians to degrees is generally inexact; users should
     * <i>not</i> expect {@code cos(toRadians(90.0))} to exactly
     * equal {@code 0.0}.
     *
     * <p>
     *  将以弧度测量的角度转换为以度为单位测量的近似等效角度。从弧度到度的转换通常不精确;用户应该</i>不要{@code cos(toRadians(900))}到{@code 00}
     * 
     * 
     * @param   angrad   an angle, in radians
     * @return  the measurement of the angle {@code angrad}
     *          in degrees.
     */
    public static strictfp double toDegrees(double angrad) {
        // Do not delegate to Math.toDegrees(angrad) because
        // this method has the strictfp modifier.
        return angrad * 180.0 / PI;
    }

    /**
     * Returns Euler's number <i>e</i> raised to the power of a
     * {@code double} value. Special cases:
     * <ul><li>If the argument is NaN, the result is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is negative infinity, then the result is
     * positive zero.</ul>
     *
     * <p>
     * 返回欧拉数</i>升至{@code double}值的大小特殊情况：<ul> <li>如果参数为NaN,则结果为NaN <li>如果参数为正无穷大,则结果为正无穷大<li>如果参数为负无穷大,则结果为正
     * 零</ul>。
     * 
     * 
     * @param   a   the exponent to raise <i>e</i> to.
     * @return  the value <i>e</i><sup>{@code a}</sup>,
     *          where <i>e</i> is the base of the natural logarithms.
     */
    public static native double exp(double a);

    /**
     * Returns the natural logarithm (base <i>e</i>) of a {@code double}
     * value. Special cases:
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is negative infinity.</ul>
     *
     * <p>
     *  返回{@code double}值的自然对数(base e e)特殊情况：<ul> <li>如果参数为NaN或小于零,则结果为NaN <li>如果参数是正无穷大,则结果是正无穷大<li>如果参数是正零
     * 或负零,则结果是负无穷大</ul>。
     * 
     * 
     * @param   a   a value
     * @return  the value ln&nbsp;{@code a}, the natural logarithm of
     *          {@code a}.
     */
    public static native double log(double a);


    /**
     * Returns the base 10 logarithm of a {@code double} value.
     * Special cases:
     *
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is negative infinity.
     * <li> If the argument is equal to 10<sup><i>n</i></sup> for
     * integer <i>n</i>, then the result is <i>n</i>.
     * </ul>
     *
     * <p>
     *  返回{@code double}值的十进制对数特殊情况：
     * 
     * <ul> <li>如果参数为NaN或小于零,则结果为NaN <li>如果参数为正无穷大,则结果为正无穷大<li>如果参数为正零或负零,那么结果是负无穷大<li>如果自变量等于整数<n> n </i>的1
     * 0 <sup> n </sup>,则结果是<n> </i>。
     * </ul>
     * 
     * 
     * @param   a   a value
     * @return  the base 10 logarithm of  {@code a}.
     * @since 1.5
     */
    public static native double log10(double a);

    /**
     * Returns the correctly rounded positive square root of a
     * {@code double} value.
     * Special cases:
     * <ul><li>If the argument is NaN or less than zero, then the result
     * is NaN.
     * <li>If the argument is positive infinity, then the result is positive
     * infinity.
     * <li>If the argument is positive zero or negative zero, then the
     * result is the same as the argument.</ul>
     * Otherwise, the result is the {@code double} value closest to
     * the true mathematical square root of the argument value.
     *
     * <p>
     * 返回{@code double}值的正确舍入的正平方根特殊情况：<ul> <li>如果参数为NaN或小于零,则结果为NaN <li>如果参数为正无穷大,结果为正无穷大<li>如果参数为正零或负零,则结果
     * 与参数</ul>相同。
     * 否则,结果是最接近真实数学平方根的{@code double}值的参数值。
     * 
     * 
     * @param   a   a value.
     * @return  the positive square root of {@code a}.
     */
    public static native double sqrt(double a);

    /**
     * Returns the cube root of a {@code double} value.  For
     * positive finite {@code x}, {@code cbrt(-x) ==
     * -cbrt(x)}; that is, the cube root of a negative value is
     * the negative of the cube root of that value's magnitude.
     * Special cases:
     *
     * <ul>
     *
     * <li>If the argument is NaN, then the result is NaN.
     *
     * <li>If the argument is infinite, then the result is an infinity
     * with the same sign as the argument.
     *
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     *
     * </ul>
     *
     * <p>
     *  返回{@code double}值的立方根。对于正有限{@code x},{@code cbrt(-x)== -cbrt(x)};也就是说,负值的立方根是该值的大小的立方根的负数特殊情况：
     * 
     * <ul>
     * 
     *  <li>如果参数为NaN,则结果为NaN
     * 
     * <li>如果参数是无限的,则结果是与参数具有相同符号的无穷大
     * 
     *  <li>如果参数为零,则结果是与参数具有相同符号的零
     * 
     * </ul>
     * 
     * 
     * @param   a   a value.
     * @return  the cube root of {@code a}.
     * @since 1.5
     */
    public static native double cbrt(double a);

    /**
     * Computes the remainder operation on two arguments as prescribed
     * by the IEEE 754 standard.
     * The remainder value is mathematically equal to
     * <code>f1&nbsp;-&nbsp;f2</code>&nbsp;&times;&nbsp;<i>n</i>,
     * where <i>n</i> is the mathematical integer closest to the exact
     * mathematical value of the quotient {@code f1/f2}, and if two
     * mathematical integers are equally close to {@code f1/f2},
     * then <i>n</i> is the integer that is even. If the remainder is
     * zero, its sign is the same as the sign of the first argument.
     * Special cases:
     * <ul><li>If either argument is NaN, or the first argument is infinite,
     * or the second argument is positive zero or negative zero, then the
     * result is NaN.
     * <li>If the first argument is finite and the second argument is
     * infinite, then the result is the same as the first argument.</ul>
     *
     * <p>
     * 根据IEEE 754标准规定的两个参数计算余数运算余数值在数学上等于<code> f1&nbsp;  - &nbsp; f2 </code>&times;&nbsp; <i> n </i>,其中<i> 
     * n </i>是最接近商{@code f1 / f2}的精确数学值的数学整数,如果两个数学整数同等接近{@code f1 / f2},则<i> n </i>是偶数的整数如果余数为零,其符号与第一个参数的符
     * 号相同特殊情况：<ul> <li>如果任一参数为NaN,或第一个参数为无穷大,或者第二个参数是正零或负零,则结果是NaN <li>如果第一个参数是有限的,第二个参数是无穷大,则结果与第一个参数相同</ul>
     * 。
     * 
     * 
     * @param   f1   the dividend.
     * @param   f2   the divisor.
     * @return  the remainder when {@code f1} is divided by
     *          {@code f2}.
     */
    public static native double IEEEremainder(double f1, double f2);

    /**
     * Returns the smallest (closest to negative infinity)
     * {@code double} value that is greater than or equal to the
     * argument and is equal to a mathematical integer. Special cases:
     * <ul><li>If the argument value is already equal to a
     * mathematical integer, then the result is the same as the
     * argument.  <li>If the argument is NaN or an infinity or
     * positive zero or negative zero, then the result is the same as
     * the argument.  <li>If the argument value is less than zero but
     * greater than -1.0, then the result is negative zero.</ul> Note
     * that the value of {@code StrictMath.ceil(x)} is exactly the
     * value of {@code -StrictMath.floor(-x)}.
     *
     * <p>
     * 返回大于或等于参数的最小(最接近负无穷大)的{@code double}值,等于数学整数特殊情况：<ul> <li>如果参数值已等于数学整数,则结果与参数<li>相同。
     * 如果参数为NaN或无穷大或正零或负零,则结果与参数<li>相同如果参数值小于零,但大于-10,则结果为负零。
     * </ul>请注意,{@code StrictMathceil(x)}的值正是{@code -StrictMathfloor(-x)}的值。
     * 
     * 
     * @param   a   a value.
     * @return  the smallest (closest to negative infinity)
     *          floating-point value that is greater than or equal to
     *          the argument and is equal to a mathematical integer.
     */
    public static double ceil(double a) {
        return floorOrCeil(a, -0.0, 1.0, 1.0);
    }

    /**
     * Returns the largest (closest to positive infinity)
     * {@code double} value that is less than or equal to the
     * argument and is equal to a mathematical integer. Special cases:
     * <ul><li>If the argument value is already equal to a
     * mathematical integer, then the result is the same as the
     * argument.  <li>If the argument is NaN or an infinity or
     * positive zero or negative zero, then the result is the same as
     * the argument.</ul>
     *
     * <p>
     * 返回最小(最接近正无穷大){@code double}值,小于或等于参数,等于数学整数特殊情况：<ul> <li>如果参数值已等于数学整数,则结果与参数<li>相同。
     * 如果参数为NaN或无穷大或正零或负零,则结果与参数</ul>相同。
     * 
     * 
     * @param   a   a value.
     * @return  the largest (closest to positive infinity)
     *          floating-point value that less than or equal to the argument
     *          and is equal to a mathematical integer.
     */
    public static double floor(double a) {
        return floorOrCeil(a, -1.0, 0.0, -1.0);
    }

    /**
     * Internal method to share logic between floor and ceil.
     *
     * <p>
     *  在floor和ceil之间共享逻辑的内部方法
     * 
     * 
     * @param a the value to be floored or ceiled
     * @param negativeBoundary result for values in (-1, 0)
     * @param positiveBoundary result for values in (0, 1)
     * @param increment value to add when the argument is non-integral
     */
    private static double floorOrCeil(double a,
                                      double negativeBoundary,
                                      double positiveBoundary,
                                      double sign) {
        int exponent = Math.getExponent(a);

        if (exponent < 0) {
            /*
             * Absolute value of argument is less than 1.
             * floorOrceil(-0.0) => -0.0
             * floorOrceil(+0.0) => +0.0
             * <p>
             *  参数的绝对值小于1 floorOrceil(-00)=> -00 floorOrceil(+00)=> +00
             * 
             */
            return ((a == 0.0) ? a :
                    ( (a < 0.0) ?  negativeBoundary : positiveBoundary) );
        } else if (exponent >= 52) {
            /*
             * Infinity, NaN, or a value so large it must be integral.
             * <p>
             *  无穷大,NaN或一个这么大的值,它必须是积分的
             * 
             */
            return a;
        }
        // Else the argument is either an integral value already XOR it
        // has to be rounded to one.
        assert exponent >= 0 && exponent <= 51;

        long doppel = Double.doubleToRawLongBits(a);
        long mask   = DoubleConsts.SIGNIF_BIT_MASK >> exponent;

        if ( (mask & doppel) == 0L )
            return a; // integral value
        else {
            double result = Double.longBitsToDouble(doppel & (~mask));
            if (sign*a > 0.0)
                result = result + sign;
            return result;
        }
    }

    /**
     * Returns the {@code double} value that is closest in value
     * to the argument and is equal to a mathematical integer. If two
     * {@code double} values that are mathematical integers are
     * equally close to the value of the argument, the result is the
     * integer value that is even. Special cases:
     * <ul><li>If the argument value is already equal to a mathematical
     * integer, then the result is the same as the argument.
     * <li>If the argument is NaN or an infinity or positive zero or negative
     * zero, then the result is the same as the argument.</ul>
     *
     * <p>
     * 返回值最接近参数并等于数学整数的{@code double}值如果作为数学整数的两个{@code double}值同等接近参数的值,则结果为整数值为偶特殊情况：<ul> <li>如果参数值已经等于数学整
     * 数,则结果与参数<li>相同。
     * 如果参数为NaN或无穷大或正零或负数零,则结果与参数</ul>相同。
     * 
     * 
     * @param   a   a value.
     * @return  the closest floating-point value to {@code a} that is
     *          equal to a mathematical integer.
     * @author Joseph D. Darcy
     */
    public static double rint(double a) {
        /*
         * If the absolute value of a is not less than 2^52, it
         * is either a finite integer (the double format does not have
         * enough significand bits for a number that large to have any
         * fractional portion), an infinity, or a NaN.  In any of
         * these cases, rint of the argument is the argument.
         *
         * Otherwise, the sum (twoToThe52 + a ) will properly round
         * away any fractional portion of a since ulp(twoToThe52) ==
         * 1.0; subtracting out twoToThe52 from this sum will then be
         * exact and leave the rounded integer portion of a.
         *
         * This method does *not* need to be declared strictfp to get
         * fully reproducible results.  Whether or not a method is
         * declared strictfp can only make a difference in the
         * returned result if some operation would overflow or
         * underflow with strictfp semantics.  The operation
         * (twoToThe52 + a ) cannot overflow since large values of a
         * are screened out; the add cannot underflow since twoToThe52
         * is too large.  The subtraction ((twoToThe52 + a ) -
         * twoToThe52) will be exact as discussed above and thus
         * cannot overflow or meaningfully underflow.  Finally, the
         * last multiply in the return statement is by plus or minus
         * 1.0, which is exact too.
         * <p>
         * 如果a的绝对值不小于2 ^ 52,则它是一个有限整数(对于大到具有任何小数部分的数字,双重格式不具有足够的有效位数位),无穷大或NaN在任何在这些情况下,参数的rint是参数
         * 
         *  否则,和(twoToThe52 + a)将适当地舍去自ulp(twoToThe52)== 10的任何小数部分;从这个和减去twoToThe52将是精确的,并留下a的舍入的整数部分
         * 
         * 这个方法不需要声明strictfp来获得完全可重现的结果无论是否声明了一个方法strictfp只能在返回结果中产生差异,如果某些操作会因strictfp语义而溢出或下溢操作(twoToThe52 + a
         * )不能溢出,因为大的a值被筛选出来;由于twoToThe52太大,加法不能下溢减法((twoToThe52 + a) -  twoToThe52)将如上所述是精确的,因此不能溢出或有意义的下溢最后,re
         * turn语句中的最后一个乘法是加或减10,也是确切的。
         * 
         */
        double twoToThe52 = (double)(1L << 52); // 2^52
        double sign = Math.copySign(1.0, a); // preserve sign info
        a = Math.abs(a);

        if (a < twoToThe52) { // E_min <= ilogb(a) <= 51
            a = ((twoToThe52 + a ) - twoToThe52);
        }

        return sign * a; // restore original sign
    }

    /**
     * Returns the angle <i>theta</i> from the conversion of rectangular
     * coordinates ({@code x},&nbsp;{@code y}) to polar
     * coordinates (r,&nbsp;<i>theta</i>).
     * This method computes the phase <i>theta</i> by computing an arc tangent
     * of {@code y/x} in the range of -<i>pi</i> to <i>pi</i>. Special
     * cases:
     * <ul><li>If either argument is NaN, then the result is NaN.
     * <li>If the first argument is positive zero and the second argument
     * is positive, or the first argument is positive and finite and the
     * second argument is positive infinity, then the result is positive
     * zero.
     * <li>If the first argument is negative zero and the second argument
     * is positive, or the first argument is negative and finite and the
     * second argument is positive infinity, then the result is negative zero.
     * <li>If the first argument is positive zero and the second argument
     * is negative, or the first argument is positive and finite and the
     * second argument is negative infinity, then the result is the
     * {@code double} value closest to <i>pi</i>.
     * <li>If the first argument is negative zero and the second argument
     * is negative, or the first argument is negative and finite and the
     * second argument is negative infinity, then the result is the
     * {@code double} value closest to -<i>pi</i>.
     * <li>If the first argument is positive and the second argument is
     * positive zero or negative zero, or the first argument is positive
     * infinity and the second argument is finite, then the result is the
     * {@code double} value closest to <i>pi</i>/2.
     * <li>If the first argument is negative and the second argument is
     * positive zero or negative zero, or the first argument is negative
     * infinity and the second argument is finite, then the result is the
     * {@code double} value closest to -<i>pi</i>/2.
     * <li>If both arguments are positive infinity, then the result is the
     * {@code double} value closest to <i>pi</i>/4.
     * <li>If the first argument is positive infinity and the second argument
     * is negative infinity, then the result is the {@code double}
     * value closest to 3*<i>pi</i>/4.
     * <li>If the first argument is negative infinity and the second argument
     * is positive infinity, then the result is the {@code double} value
     * closest to -<i>pi</i>/4.
     * <li>If both arguments are negative infinity, then the result is the
     * {@code double} value closest to -3*<i>pi</i>/4.</ul>
     *
     * <p>
     * 返回从直角坐标({@code x},{@ code y})到极坐标(r,&lt; theta&lt; / i&gt;)的转换的角度θ方法通过计算在-πpi到p i -i范围内的{@code y / x}
     * 的反正切来计算相位θ。
     * 特殊情况： <ul> <li>如果任一参数是NaN,则结果是NaN <li>如果第一个参数是正零,第二个参数是正的,或者第一个参数是正数和有限,第二个参数是正无穷大,则结果为正零<li>如果第一个参数为
     * 负零,第二个参数为正,或者第一个参数为负数和有限,第二个参数为正无穷大,则结果为负零<li>如果第一个参数是正零,第二个参数是负数,或者第一个参数是正数和有限,第二个参数是负无穷大,则结果是最接近<i>
     *  pi的{@code double} </i> <li>如果第一个参数为负零,第二个参数为负,或者第一个参数为负和有限,第二个参数为负无穷大,则结果是最接近的{@code double} -  <i> 
     * pi </i> <li>如果第一个参数为正,第二个参数为正零或负零,或第一个参数为正无穷大,第二个参数为有限,则结果为{码二}值最接近<i> pi </i> / 2<li>如果第一个参数为负,第二个参数
     * 为正零或负零,或第一个参数为负无穷大,第二个参数为有限,则结果为最接近 -  <i的{@code double}值> pi </i> / 2 <li>如果两个参数都是正无穷大,则结果是最接近<i> pi
     * 的{@code double}值</i>如果第一个参数为正无穷大,第二个参数是负无穷大,则结果是最接近3 * pi </i> / 4 <li>的{@code double}值。
     * 如果第一个参数是负无穷大,无穷大,则结果是最接近 -  <i> pi </i> / 4 <li>的{@code double}值。
     * 如果两个参数都是负无穷大,则结果是最接近 - 3 * pi / 4 </ul>。
     * 
     * 
     * @param   y   the ordinate coordinate
     * @param   x   the abscissa coordinate
     * @return  the <i>theta</i> component of the point
     *          (<i>r</i>,&nbsp;<i>theta</i>)
     *          in polar coordinates that corresponds to the point
     *          (<i>x</i>,&nbsp;<i>y</i>) in Cartesian coordinates.
     */
    public static native double atan2(double y, double x);


    /**
     * Returns the value of the first argument raised to the power of the
     * second argument. Special cases:
     *
     * <ul><li>If the second argument is positive or negative zero, then the
     * result is 1.0.
     * <li>If the second argument is 1.0, then the result is the same as the
     * first argument.
     * <li>If the second argument is NaN, then the result is NaN.
     * <li>If the first argument is NaN and the second argument is nonzero,
     * then the result is NaN.
     *
     * <li>If
     * <ul>
     * <li>the absolute value of the first argument is greater than 1
     * and the second argument is positive infinity, or
     * <li>the absolute value of the first argument is less than 1 and
     * the second argument is negative infinity,
     * </ul>
     * then the result is positive infinity.
     *
     * <li>If
     * <ul>
     * <li>the absolute value of the first argument is greater than 1 and
     * the second argument is negative infinity, or
     * <li>the absolute value of the
     * first argument is less than 1 and the second argument is positive
     * infinity,
     * </ul>
     * then the result is positive zero.
     *
     * <li>If the absolute value of the first argument equals 1 and the
     * second argument is infinite, then the result is NaN.
     *
     * <li>If
     * <ul>
     * <li>the first argument is positive zero and the second argument
     * is greater than zero, or
     * <li>the first argument is positive infinity and the second
     * argument is less than zero,
     * </ul>
     * then the result is positive zero.
     *
     * <li>If
     * <ul>
     * <li>the first argument is positive zero and the second argument
     * is less than zero, or
     * <li>the first argument is positive infinity and the second
     * argument is greater than zero,
     * </ul>
     * then the result is positive infinity.
     *
     * <li>If
     * <ul>
     * <li>the first argument is negative zero and the second argument
     * is greater than zero but not a finite odd integer, or
     * <li>the first argument is negative infinity and the second
     * argument is less than zero but not a finite odd integer,
     * </ul>
     * then the result is positive zero.
     *
     * <li>If
     * <ul>
     * <li>the first argument is negative zero and the second argument
     * is a positive finite odd integer, or
     * <li>the first argument is negative infinity and the second
     * argument is a negative finite odd integer,
     * </ul>
     * then the result is negative zero.
     *
     * <li>If
     * <ul>
     * <li>the first argument is negative zero and the second argument
     * is less than zero but not a finite odd integer, or
     * <li>the first argument is negative infinity and the second
     * argument is greater than zero but not a finite odd integer,
     * </ul>
     * then the result is positive infinity.
     *
     * <li>If
     * <ul>
     * <li>the first argument is negative zero and the second argument
     * is a negative finite odd integer, or
     * <li>the first argument is negative infinity and the second
     * argument is a positive finite odd integer,
     * </ul>
     * then the result is negative infinity.
     *
     * <li>If the first argument is finite and less than zero
     * <ul>
     * <li> if the second argument is a finite even integer, the
     * result is equal to the result of raising the absolute value of
     * the first argument to the power of the second argument
     *
     * <li>if the second argument is a finite odd integer, the result
     * is equal to the negative of the result of raising the absolute
     * value of the first argument to the power of the second
     * argument
     *
     * <li>if the second argument is finite and not an integer, then
     * the result is NaN.
     * </ul>
     *
     * <li>If both arguments are integers, then the result is exactly equal
     * to the mathematical result of raising the first argument to the power
     * of the second argument if that result can in fact be represented
     * exactly as a {@code double} value.</ul>
     *
     * <p>(In the foregoing descriptions, a floating-point value is
     * considered to be an integer if and only if it is finite and a
     * fixed point of the method {@link #ceil ceil} or,
     * equivalently, a fixed point of the method {@link #floor
     * floor}. A value is a fixed point of a one-argument
     * method if and only if the result of applying the method to the
     * value is equal to the value.)
     *
     * <p>
     * 返回第一个参数的值增加到第二个参数的权力特殊情况：
     * 
     *  <ul> <li>如果第二个参数为正或负的零,则结果为10 <li>如果第二个参数为10,则结果与第一个参数相同<li>如果第二个参数为NaN ,那么结果是NaN <li>如果第一个参数是NaN,第二
     * 个参数是非零,那么结果是NaN。
     * 
     *  <li>如果
     * <ul>
     *  <li>第一个参数的绝对值大于1,第二个参数为正无穷大,或<li>第一个参数的绝对值小于1,第二个参数为负无穷大,
     * </ul>
     *  那么结果是正无穷
     * 
     *  <li>如果
     * <ul>
     * <li>第一个参数的绝对值大于1,第二个参数为负无穷大,或<li>第一个参数的绝对值小于1,第二个参数为正无穷大,
     * </ul>
     *  那么结果是正零
     * 
     *  <li>如果第一个参数的绝对值等于1,第二个参数为无穷大,则结果为NaN
     * 
     *  <li>如果
     * <ul>
     *  <li>第一个参数是正零,第二个参数大于零,或<li>第一个参数是正无穷大,第二个参数小于零,
     * </ul>
     *  那么结果是正零
     * 
     *  <li>如果
     * <ul>
     *  <li>第一个参数是正零,第二个参数小于零,或<li>第一个参数是正无穷大,第二个参数大于零,
     * </ul>
     * 那么结果是正无穷
     * 
     *  <li>如果
     * <ul>
     *  <li>第一个参数为负零,第二个参数大于零,但不是有限奇整数,或<li>第一个参数为负无穷大,第二个参数小于零,但不是有限奇整数,
     * </ul>
     *  那么结果是正零
     * 
     *  <li>如果
     * <ul>
     *  <li>第一个参数是负零,第二个参数是正有限奇整数,或<li>第一个参数是负无穷大,第二个参数是负有限奇整数,
     * </ul>
     *  那么结果是负零
     * 
     *  <li>如果
     * <ul>
     * <li>第一个参数为负零,第二个参数小于零,但不是有限奇整数,或<li>第一个参数为负无穷大,第二个参数大于零,但不是有限奇整数,
     * </ul>
     *  那么结果是正无穷
     * 
     *  <li>如果
     * <ul>
     *  <li>第一个参数为负零,第二个参数为负有限奇整数,或<li>第一个参数为负无穷大,第二个参数为正有限奇整数,
     * </ul>
     *  那么结果是负无穷大
     * 
     *  <li>如果第一个参数是有限的并且小于零
     * <ul>
     *  <li>如果第二个参数是有限偶整数,则结果等于将第一个参数的绝对值提高到第二个参数的幂的结果
     * 
     * <li>如果第二个参数是有限奇整数,则结果等于将第一个参数的绝对值提高到第二个参数的幂的结果的负数
     * 
     *  <li>如果第二个参数是有限的而不是整数,则结果为NaN
     * </ul>
     * 
     *  <li>如果两个参数都是整数,则结果完全等于将第一个参数提升为第二个参数的幂的数学结果,如果该结果实际上可以完全表示为{@code double} value < ul>
     * 
     * <p>(在前面的描述中,当且仅当浮点值是有限的并且方法的固定点{@link#ceil ceil}或等效地,固定点的浮点值被认为是整数方法{@link #floor floor}当且仅当将方法应用于值的结
     * 果等于该值时,值是单参数方法的固定点)。
     * 
     * 
     * @param   a   base.
     * @param   b   the exponent.
     * @return  the value {@code a}<sup>{@code b}</sup>.
     */
    public static native double pow(double a, double b);

    /**
     * Returns the closest {@code int} to the argument, with ties
     * rounding to positive infinity.
     *
     * <p>Special cases:
     * <ul><li>If the argument is NaN, the result is 0.
     * <li>If the argument is negative infinity or any value less than or
     * equal to the value of {@code Integer.MIN_VALUE}, the result is
     * equal to the value of {@code Integer.MIN_VALUE}.
     * <li>If the argument is positive infinity or any value greater than or
     * equal to the value of {@code Integer.MAX_VALUE}, the result is
     * equal to the value of {@code Integer.MAX_VALUE}.</ul>
     *
     * <p>
     *  返回最接近参数的{@code int},连接四舍五入到正无穷大
     * 
     * <p>特殊情况：<ul> <li>如果参数是NaN,则结果为0 <li>如果参数为负无穷大或小于或等于{@code IntegerMIN_VALUE}的值的任何值,等于{@code IntegerMIN_VALUE}
     * 的值<li>如果参数是正无穷大或大于或等于{@code IntegerMAX_VALUE}的值的任何值,则结果等于{@code IntegerMAX_VALUE}的值</ul>。
     * 
     * 
     * @param   a   a floating-point value to be rounded to an integer.
     * @return  the value of the argument rounded to the nearest
     *          {@code int} value.
     * @see     java.lang.Integer#MAX_VALUE
     * @see     java.lang.Integer#MIN_VALUE
     */
    public static int round(float a) {
        return Math.round(a);
    }

    /**
     * Returns the closest {@code long} to the argument, with ties
     * rounding to positive infinity.
     *
     * <p>Special cases:
     * <ul><li>If the argument is NaN, the result is 0.
     * <li>If the argument is negative infinity or any value less than or
     * equal to the value of {@code Long.MIN_VALUE}, the result is
     * equal to the value of {@code Long.MIN_VALUE}.
     * <li>If the argument is positive infinity or any value greater than or
     * equal to the value of {@code Long.MAX_VALUE}, the result is
     * equal to the value of {@code Long.MAX_VALUE}.</ul>
     *
     * <p>
     *  返回最接近参数的{@code long},其中连接四舍五入到正无穷大
     * 
     * <p>特殊情况：<ul> <li>如果参数是NaN,则结果为0 <li>如果参数为负无穷大或小于或等于{@code LongMIN_VALUE}的值的任何值,等于{@code LongMIN_VALUE}
     * 的值<li>如果参数是正无穷大或大于或等于{@code LongMAX_VALUE}的值的任何值,则结果等于{@code LongMAX_VALUE}的值</ul>。
     * 
     * 
     * @param   a  a floating-point value to be rounded to a
     *          {@code long}.
     * @return  the value of the argument rounded to the nearest
     *          {@code long} value.
     * @see     java.lang.Long#MAX_VALUE
     * @see     java.lang.Long#MIN_VALUE
     */
    public static long round(double a) {
        return Math.round(a);
    }

    private static final class RandomNumberGeneratorHolder {
        static final Random randomNumberGenerator = new Random();
    }

    /**
     * Returns a {@code double} value with a positive sign, greater
     * than or equal to {@code 0.0} and less than {@code 1.0}.
     * Returned values are chosen pseudorandomly with (approximately)
     * uniform distribution from that range.
     *
     * <p>When this method is first called, it creates a single new
     * pseudorandom-number generator, exactly as if by the expression
     *
     * <blockquote>{@code new java.util.Random()}</blockquote>
     *
     * This new pseudorandom-number generator is used thereafter for
     * all calls to this method and is used nowhere else.
     *
     * <p>This method is properly synchronized to allow correct use by
     * more than one thread. However, if many threads need to generate
     * pseudorandom numbers at a great rate, it may reduce contention
     * for each thread to have its own pseudorandom-number generator.
     *
     * <p>
     *  返回一个带有正号,大于或等于{@code 00}且小于{@code 10}的{@code double}值。返回值使用该范围内的(近似)均匀分布进行伪随机选择
     * 
     *  <p>当这个方法被第一次调用时,它创建一个新的伪随机数生成器,就像通过表达式
     * 
     * <blockquote> {@ code new javautilRandom()} </blockquote>
     * 
     *  这个新的伪随机数发生器此后用于对该方法的所有调用,并且在任何其他地方不使用
     * 
     *  <p>此方法已正确同步以允许由多个线程正确使用。但是,如果许多线程需要以较高速率生成伪随机数,则可以减少每个线程的争用,以具有其自己的伪随机数生成器
     * 
     * 
     * @return  a pseudorandom {@code double} greater than or equal
     * to {@code 0.0} and less than {@code 1.0}.
     * @see Random#nextDouble()
     */
    public static double random() {
        return RandomNumberGeneratorHolder.randomNumberGenerator.nextDouble();
    }

    /**
     * Returns the sum of its arguments,
     * throwing an exception if the result overflows an {@code int}.
     *
     * <p>
     *  返回其参数的总和,如果结果溢出{@code int}则抛出异常,
     * 
     * 
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows an int
     * @see Math#addExact(int,int)
     * @since 1.8
     */
    public static int addExact(int x, int y) {
        return Math.addExact(x, y);
    }

    /**
     * Returns the sum of its arguments,
     * throwing an exception if the result overflows a {@code long}.
     *
     * <p>
     *  返回其参数的总和,如果结果溢出{@code long}则抛出异常,
     * 
     * 
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows a long
     * @see Math#addExact(long,long)
     * @since 1.8
     */
    public static long addExact(long x, long y) {
        return Math.addExact(x, y);
    }

    /**
     * Returns the difference of the arguments,
     * throwing an exception if the result overflows an {@code int}.
     *
     * <p>
     *  返回参数的差异,如果结果溢出{@code int}则抛出异常,
     * 
     * 
     * @param x the first value
     * @param y the second value to subtract from the first
     * @return the result
     * @throws ArithmeticException if the result overflows an int
     * @see Math#subtractExact(int,int)
     * @since 1.8
     */
    public static int subtractExact(int x, int y) {
        return Math.subtractExact(x, y);
    }

    /**
     * Returns the difference of the arguments,
     * throwing an exception if the result overflows a {@code long}.
     *
     * <p>
     * 返回参数的差异,如果结果溢出{@code long}则抛出异常,
     * 
     * 
     * @param x the first value
     * @param y the second value to subtract from the first
     * @return the result
     * @throws ArithmeticException if the result overflows a long
     * @see Math#subtractExact(long,long)
     * @since 1.8
     */
    public static long subtractExact(long x, long y) {
        return Math.subtractExact(x, y);
    }

    /**
     * Returns the product of the arguments,
     * throwing an exception if the result overflows an {@code int}.
     *
     * <p>
     *  返回参数的乘积,如果结果溢出{@code int}则抛出异常,
     * 
     * 
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows an int
     * @see Math#multiplyExact(int,int)
     * @since 1.8
     */
    public static int multiplyExact(int x, int y) {
        return Math.multiplyExact(x, y);
    }

    /**
     * Returns the product of the arguments,
     * throwing an exception if the result overflows a {@code long}.
     *
     * <p>
     *  返回参数的乘积,如果结果溢出{@code long}则抛出异常,
     * 
     * 
     * @param x the first value
     * @param y the second value
     * @return the result
     * @throws ArithmeticException if the result overflows a long
     * @see Math#multiplyExact(long,long)
     * @since 1.8
     */
    public static long multiplyExact(long x, long y) {
        return Math.multiplyExact(x, y);
    }

    /**
     * Returns the value of the {@code long} argument;
     * throwing an exception if the value overflows an {@code int}.
     *
     * <p>
     *  返回{@code long}参数的值;如果值溢出{@code int}则抛出异常,
     * 
     * 
     * @param value the long value
     * @return the argument as an int
     * @throws ArithmeticException if the {@code argument} overflows an int
     * @see Math#toIntExact(long)
     * @since 1.8
     */
    public static int toIntExact(long value) {
        return Math.toIntExact(value);
    }

    /**
     * Returns the largest (closest to positive infinity)
     * {@code int} value that is less than or equal to the algebraic quotient.
     * There is one special case, if the dividend is the
     * {@linkplain Integer#MIN_VALUE Integer.MIN_VALUE} and the divisor is {@code -1},
     * then integer overflow occurs and
     * the result is equal to the {@code Integer.MIN_VALUE}.
     * <p>
     * See {@link Math#floorDiv(int, int) Math.floorDiv} for examples and
     * a comparison to the integer division {@code /} operator.
     *
     * <p>
     *  返回小于或等于代数商的最大(最接近正无穷大)的{@code int}值有一种特殊情况,如果被除数是{@linkplain Integer#MIN_VALUE IntegerMIN_VALUE},除数是
     * {@代码-1},则发生整数溢出,结果等于{@code IntegerMIN_VALUE}。
     * <p>
     * 有关示例和与整数除法{@code /}运算符的比较,请参见{@link Math#floorDiv(int,int)MathfloorDiv}
     * 
     * 
     * @param x the dividend
     * @param y the divisor
     * @return the largest (closest to positive infinity)
     * {@code int} value that is less than or equal to the algebraic quotient.
     * @throws ArithmeticException if the divisor {@code y} is zero
     * @see Math#floorDiv(int, int)
     * @see Math#floor(double)
     * @since 1.8
     */
    public static int floorDiv(int x, int y) {
        return Math.floorDiv(x, y);
    }

    /**
     * Returns the largest (closest to positive infinity)
     * {@code long} value that is less than or equal to the algebraic quotient.
     * There is one special case, if the dividend is the
     * {@linkplain Long#MIN_VALUE Long.MIN_VALUE} and the divisor is {@code -1},
     * then integer overflow occurs and
     * the result is equal to the {@code Long.MIN_VALUE}.
     * <p>
     * See {@link Math#floorDiv(int, int) Math.floorDiv} for examples and
     * a comparison to the integer division {@code /} operator.
     *
     * <p>
     *  返回小于或等于代数商的最大(最接近正无穷大){@code long}值有一种特殊情况,如果被除数是{@linkplain Long#MIN_VALUE LongMIN_VALUE},除数是{@代码-1}
     * ,则发生整数溢出,结果等于{@code LongMIN_VALUE}。
     * <p>
     *  有关示例和与整数除法{@code /}运算符的比较,请参见{@link Math#floorDiv(int,int)MathfloorDiv}
     * 
     * 
     * @param x the dividend
     * @param y the divisor
     * @return the largest (closest to positive infinity)
     * {@code long} value that is less than or equal to the algebraic quotient.
     * @throws ArithmeticException if the divisor {@code y} is zero
     * @see Math#floorDiv(long, long)
     * @see Math#floor(double)
     * @since 1.8
     */
    public static long floorDiv(long x, long y) {
        return Math.floorDiv(x, y);
    }

    /**
     * Returns the floor modulus of the {@code int} arguments.
     * <p>
     * The floor modulus is {@code x - (floorDiv(x, y) * y)},
     * has the same sign as the divisor {@code y}, and
     * is in the range of {@code -abs(y) < r < +abs(y)}.
     * <p>
     * The relationship between {@code floorDiv} and {@code floorMod} is such that:
     * <ul>
     *   <li>{@code floorDiv(x, y) * y + floorMod(x, y) == x}
     * </ul>
     * <p>
     * See {@link Math#floorMod(int, int) Math.floorMod} for examples and
     * a comparison to the {@code %} operator.
     *
     * <p>
     *  返回{@code int}参数的floor模数
     * <p>
     *  底模数是{@code x-(floorDiv(x,y)* y)},与除数{@code y}具有相同的符号,并且在{@code -abs(y)<r <+ abs(y)}
     * <p>
     * {@code floorDiv}和{@code floorMod}之间的关系是这样的：
     * <ul>
     *  <li> {@ code floorDiv(x,y)* y + floorMod(x,y)== x}
     * </ul>
     * <p>
     *  有关示例和与{@code％}运算符的比较,请参见{@link Math#floorMod(int,int)MathfloorMod}
     * 
     * 
     * @param x the dividend
     * @param y the divisor
     * @return the floor modulus {@code x - (floorDiv(x, y) * y)}
     * @throws ArithmeticException if the divisor {@code y} is zero
     * @see Math#floorMod(int, int)
     * @see StrictMath#floorDiv(int, int)
     * @since 1.8
     */
    public static int floorMod(int x, int y) {
        return Math.floorMod(x , y);
    }
    /**
     * Returns the floor modulus of the {@code long} arguments.
     * <p>
     * The floor modulus is {@code x - (floorDiv(x, y) * y)},
     * has the same sign as the divisor {@code y}, and
     * is in the range of {@code -abs(y) < r < +abs(y)}.
     * <p>
     * The relationship between {@code floorDiv} and {@code floorMod} is such that:
     * <ul>
     *   <li>{@code floorDiv(x, y) * y + floorMod(x, y) == x}
     * </ul>
     * <p>
     * See {@link Math#floorMod(int, int) Math.floorMod} for examples and
     * a comparison to the {@code %} operator.
     *
     * <p>
     *  返回{@code long}参数的floor模数
     * <p>
     *  底模数是{@code x-(floorDiv(x,y)* y)},与除数{@code y}具有相同的符号,并且在{@code -abs(y)<r <+ abs(y)}
     * <p>
     *  {@code floorDiv}和{@code floorMod}之间的关系是这样的：
     * <ul>
     *  <li> {@ code floorDiv(x,y)* y + floorMod(x,y)== x}
     * </ul>
     * <p>
     *  有关示例和与{@code％}运算符的比较,请参见{@link Math#floorMod(int,int)MathfloorMod}
     * 
     * 
     * @param x the dividend
     * @param y the divisor
     * @return the floor modulus {@code x - (floorDiv(x, y) * y)}
     * @throws ArithmeticException if the divisor {@code y} is zero
     * @see Math#floorMod(long, long)
     * @see StrictMath#floorDiv(long, long)
     * @since 1.8
     */
    public static long floorMod(long x, long y) {
        return Math.floorMod(x, y);
    }

    /**
     * Returns the absolute value of an {@code int} value.
     * If the argument is not negative, the argument is returned.
     * If the argument is negative, the negation of the argument is returned.
     *
     * <p>Note that if the argument is equal to the value of
     * {@link Integer#MIN_VALUE}, the most negative representable
     * {@code int} value, the result is that same value, which is
     * negative.
     *
     * <p>
     * 返回{@code int}值的绝对值如果参数不是负数,则返回参数如果参数为负,则返回参数的否定
     * 
     *  <p>请注意,如果参数等于{@link Integer#MIN_VALUE}的值,即最可负的可表示{@code int}值,则结果是相同的值,即负数
     * 
     * 
     * @param   a   the  argument whose absolute value is to be determined.
     * @return  the absolute value of the argument.
     */
    public static int abs(int a) {
        return Math.abs(a);
    }

    /**
     * Returns the absolute value of a {@code long} value.
     * If the argument is not negative, the argument is returned.
     * If the argument is negative, the negation of the argument is returned.
     *
     * <p>Note that if the argument is equal to the value of
     * {@link Long#MIN_VALUE}, the most negative representable
     * {@code long} value, the result is that same value, which
     * is negative.
     *
     * <p>
     *  返回{@code long}值的绝对值如果参数不为负,则返回参数如果参数为负,则返回参数的否定
     * 
     *  <p>请注意,如果参数等于{@link Long#MIN_VALUE}的值,即最可负的可表示{@code long}值,则结果是相同的值,即负数
     * 
     * 
     * @param   a   the  argument whose absolute value is to be determined.
     * @return  the absolute value of the argument.
     */
    public static long abs(long a) {
        return Math.abs(a);
    }

    /**
     * Returns the absolute value of a {@code float} value.
     * If the argument is not negative, the argument is returned.
     * If the argument is negative, the negation of the argument is returned.
     * Special cases:
     * <ul><li>If the argument is positive zero or negative zero, the
     * result is positive zero.
     * <li>If the argument is infinite, the result is positive infinity.
     * <li>If the argument is NaN, the result is NaN.</ul>
     * In other words, the result is the same as the value of the expression:
     * <p>{@code Float.intBitsToFloat(0x7fffffff & Float.floatToIntBits(a))}
     *
     * <p>
     * 返回{@code float}值的绝对值如果参数不为负,则返回参数如果参数为负,则返回参数的否定特殊情况：<ul> <li>如果参数为正零或负零,结果为正零<li>如果参数是无穷大,结果是正无穷大<li>
     * 如果参数是NaN,结果是NaN </ul>换句话说,结果是相同的作为表达式的值：<p> {@ code FloatintBitsToFloat(0x7fffffff&FloatfloatToIntBits(a))}
     * 。
     * 
     * 
     * @param   a   the argument whose absolute value is to be determined
     * @return  the absolute value of the argument.
     */
    public static float abs(float a) {
        return Math.abs(a);
    }

    /**
     * Returns the absolute value of a {@code double} value.
     * If the argument is not negative, the argument is returned.
     * If the argument is negative, the negation of the argument is returned.
     * Special cases:
     * <ul><li>If the argument is positive zero or negative zero, the result
     * is positive zero.
     * <li>If the argument is infinite, the result is positive infinity.
     * <li>If the argument is NaN, the result is NaN.</ul>
     * In other words, the result is the same as the value of the expression:
     * <p>{@code Double.longBitsToDouble((Double.doubleToLongBits(a)<<1)>>>1)}
     *
     * <p>
     * 返回{@code double}值的绝对值如果参数不为负,则返回参数如果参数为负,则返回参数的否定特殊情况：<ul> <li>如果参数为正零或负零,结果为正零<li>如果参数是无穷大,结果是正无穷大<li>
     * 如果参数是NaN,结果是NaN </ul>换句话说,结果是相同的作为表达式的值：<p> {@ code DoublelongBitsToDouble((DoubledoubleToLongBits(a)<< 1)>>> 1)}
     * 。
     * 
     * 
     * @param   a   the argument whose absolute value is to be determined
     * @return  the absolute value of the argument.
     */
    public static double abs(double a) {
        return Math.abs(a);
    }

    /**
     * Returns the greater of two {@code int} values. That is, the
     * result is the argument closer to the value of
     * {@link Integer#MAX_VALUE}. If the arguments have the same value,
     * the result is that same value.
     *
     * <p>
     *  返回两个{@code int}值中较大的值。也就是说,结果是更接近{@link Integer#MAX_VALUE}的值的参数。如果参数具有相同的值,结果是相同的值
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the larger of {@code a} and {@code b}.
     */
    public static int max(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * Returns the greater of two {@code long} values. That is, the
     * result is the argument closer to the value of
     * {@link Long#MAX_VALUE}. If the arguments have the same value,
     * the result is that same value.
     *
     * <p>
     * 返回两个{@code long}值中较大的值。也就是说,结果是更接近{@link Long#MAX_VALUE}的值的参数。如果参数具有相同的值,结果是相同的值
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the larger of {@code a} and {@code b}.
        */
    public static long max(long a, long b) {
        return Math.max(a, b);
    }

    /**
     * Returns the greater of two {@code float} values.  That is,
     * the result is the argument closer to positive infinity. If the
     * arguments have the same value, the result is that same
     * value. If either value is NaN, then the result is NaN.  Unlike
     * the numerical comparison operators, this method considers
     * negative zero to be strictly smaller than positive zero. If one
     * argument is positive zero and the other negative zero, the
     * result is positive zero.
     *
     * <p>
     *  返回两个{@code float}值中较大的值。也就是说,结果是更接近正无穷大的参数。如果参数具有相同的值,结果是相同的值。如果任一值为NaN,则结果为NaN。
     * 与数值比较运算符,该方法认为负零严格小于正零如果一个参数是正零,另一个负零,结果是正零。
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the larger of {@code a} and {@code b}.
     */
    public static float max(float a, float b) {
        return Math.max(a, b);
    }

    /**
     * Returns the greater of two {@code double} values.  That
     * is, the result is the argument closer to positive infinity. If
     * the arguments have the same value, the result is that same
     * value. If either value is NaN, then the result is NaN.  Unlike
     * the numerical comparison operators, this method considers
     * negative zero to be strictly smaller than positive zero. If one
     * argument is positive zero and the other negative zero, the
     * result is positive zero.
     *
     * <p>
     * 返回两个{@code double}值中的较大值。也就是说,结果是接近正无穷大的参数。如果参数具有相同的值,则结果是相同的值。如果任一值为NaN,则结果为NaN。
     * 与数值比较运算符,该方法认为负零严格小于正零如果一个参数是正零,另一个负零,结果是正零。
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the larger of {@code a} and {@code b}.
     */
    public static double max(double a, double b) {
        return Math.max(a, b);
    }

    /**
     * Returns the smaller of two {@code int} values. That is,
     * the result the argument closer to the value of
     * {@link Integer#MIN_VALUE}.  If the arguments have the same
     * value, the result is that same value.
     *
     * <p>
     *  返回两个{@code int}值中较小的值,即,参数值接近{@link Integer#MIN_VALUE}的值如果参数具有相同的值,结果是相同的值
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the smaller of {@code a} and {@code b}.
     */
    public static int min(int a, int b) {
        return Math.min(a, b);
    }

    /**
     * Returns the smaller of two {@code long} values. That is,
     * the result is the argument closer to the value of
     * {@link Long#MIN_VALUE}. If the arguments have the same
     * value, the result is that same value.
     *
     * <p>
     * 返回两个{@code long}值中较小的值。也就是说,结果是更接近{@link Long#MIN_VALUE}的值的参数。如果参数具有相同的值,结果是相同的值
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the smaller of {@code a} and {@code b}.
     */
    public static long min(long a, long b) {
        return Math.min(a, b);
    }

    /**
     * Returns the smaller of two {@code float} values.  That is,
     * the result is the value closer to negative infinity. If the
     * arguments have the same value, the result is that same
     * value. If either value is NaN, then the result is NaN.  Unlike
     * the numerical comparison operators, this method considers
     * negative zero to be strictly smaller than positive zero.  If
     * one argument is positive zero and the other is negative zero,
     * the result is negative zero.
     *
     * <p>
     *  返回两个{@code float}值中较小的值。也就是说,结果是更接近负无穷大的值。如果参数具有相同的值,则结果是相同的值。如果任一值为NaN,则结果为NaN。
     * 与数值比较运算符,该方法认为负零严格小于正零如果一个参数为正零,另一个为负零,则结果为负零。
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the smaller of {@code a} and {@code b.}
     */
    public static float min(float a, float b) {
        return Math.min(a, b);
    }

    /**
     * Returns the smaller of two {@code double} values.  That
     * is, the result is the value closer to negative infinity. If the
     * arguments have the same value, the result is that same
     * value. If either value is NaN, then the result is NaN.  Unlike
     * the numerical comparison operators, this method considers
     * negative zero to be strictly smaller than positive zero. If one
     * argument is positive zero and the other is negative zero, the
     * result is negative zero.
     *
     * <p>
     * 返回两个{@code double}值中较小的值。也就是说,结果是更接近负无穷大的值。如果参数具有相同的值,则结果是相同的值。如果任一值为NaN,则结果为NaN。
     * 与数值比较运算符,该方法认为负零严格小于正零如果一个参数为正零,另一个为负零,则结果为负零。
     * 
     * 
     * @param   a   an argument.
     * @param   b   another argument.
     * @return  the smaller of {@code a} and {@code b}.
     */
    public static double min(double a, double b) {
        return Math.min(a, b);
    }

    /**
     * Returns the size of an ulp of the argument.  An ulp, unit in
     * the last place, of a {@code double} value is the positive
     * distance between this floating-point value and the {@code
     * double} value next larger in magnitude.  Note that for non-NaN
     * <i>x</i>, <code>ulp(-<i>x</i>) == ulp(<i>x</i>)</code>.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, then the result is NaN.
     * <li> If the argument is positive or negative infinity, then the
     * result is positive infinity.
     * <li> If the argument is positive or negative zero, then the result is
     * {@code Double.MIN_VALUE}.
     * <li> If the argument is &plusmn;{@code Double.MAX_VALUE}, then
     * the result is equal to 2<sup>971</sup>.
     * </ul>
     *
     * <p>
     *  返回参数的ulp的大小。
     * {@code double}值的最后一位的ulp单位是浮点值和下一个更大的{@code double}值之间的正距离注意,对于非NaNx x,<code> ulp( -  x </i>)== ulp(<x>
     *  x </i>)</code>。
     *  返回参数的ulp的大小。
     * 
     *  <p>特殊情况：
     * <ul>
     * <li>如果参数是NaN,那么结果是NaN <li>如果参数是正或负无穷大,则结果是正无穷大<li>如果参数为正或负零,则结果为{代码DoubleMIN_VALUE} <li>如果参数为&plusmn;
     *  {@ code DoubleMAX_VALUE},则结果等于2 <sup> 971 </sup>。
     * </ul>
     * 
     * 
     * @param d the floating-point value whose ulp is to be returned
     * @return the size of an ulp of the argument
     * @author Joseph D. Darcy
     * @since 1.5
     */
    public static double ulp(double d) {
        return Math.ulp(d);
    }

    /**
     * Returns the size of an ulp of the argument.  An ulp, unit in
     * the last place, of a {@code float} value is the positive
     * distance between this floating-point value and the {@code
     * float} value next larger in magnitude.  Note that for non-NaN
     * <i>x</i>, <code>ulp(-<i>x</i>) == ulp(<i>x</i>)</code>.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, then the result is NaN.
     * <li> If the argument is positive or negative infinity, then the
     * result is positive infinity.
     * <li> If the argument is positive or negative zero, then the result is
     * {@code Float.MIN_VALUE}.
     * <li> If the argument is &plusmn;{@code Float.MAX_VALUE}, then
     * the result is equal to 2<sup>104</sup>.
     * </ul>
     *
     * <p>
     *  返回参数ulp的大小。{@code float}值的最后一位的ulp单位是该浮点值和下一个较大的{@code float}值之间的正距离。
     * 注意：对于非NaNx x,<code> ulp( -  x </i>)== ulp(<x> x </i>)</code>。
     * 
     *  <p>特殊情况：
     * <ul>
     * <li>如果参数是NaN,那么结果是NaN <li>如果参数是正或负无穷大,则结果是正无穷大<li>如果参数为正或负零,则结果为{代码FloatMIN_VALUE} <li>如果参数为&plusmn; 
     * {@ code FloatMAX_VALUE},则结果等于2 <sup> 104 </sup>。
     * </ul>
     * 
     * 
     * @param f the floating-point value whose ulp is to be returned
     * @return the size of an ulp of the argument
     * @author Joseph D. Darcy
     * @since 1.5
     */
    public static float ulp(float f) {
        return Math.ulp(f);
    }

    /**
     * Returns the signum function of the argument; zero if the argument
     * is zero, 1.0 if the argument is greater than zero, -1.0 if the
     * argument is less than zero.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, then the result is NaN.
     * <li> If the argument is positive zero or negative zero, then the
     *      result is the same as the argument.
     * </ul>
     *
     * <p>
     *  返回参数的signum函数;如果参数为零,则为零,如果参数大于零,则为10,如果参数小于零,则为-10
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果参数为NaN,则结果为NaN <li>如果参数为正零或负零,则结果与参数相同
     * </ul>
     * 
     * 
     * @param d the floating-point value whose signum is to be returned
     * @return the signum function of the argument
     * @author Joseph D. Darcy
     * @since 1.5
     */
    public static double signum(double d) {
        return Math.signum(d);
    }

    /**
     * Returns the signum function of the argument; zero if the argument
     * is zero, 1.0f if the argument is greater than zero, -1.0f if the
     * argument is less than zero.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, then the result is NaN.
     * <li> If the argument is positive zero or negative zero, then the
     *      result is the same as the argument.
     * </ul>
     *
     * <p>
     * 返回参数的signum函数;如果参数为零,则为零,如果参数大于零,则为10f,如果参数小于零,则为-10f
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果参数为NaN,则结果为NaN <li>如果参数为正零或负零,则结果与参数相同
     * </ul>
     * 
     * 
     * @param f the floating-point value whose signum is to be returned
     * @return the signum function of the argument
     * @author Joseph D. Darcy
     * @since 1.5
     */
    public static float signum(float f) {
        return Math.signum(f);
    }

    /**
     * Returns the hyperbolic sine of a {@code double} value.
     * The hyperbolic sine of <i>x</i> is defined to be
     * (<i>e<sup>x</sup>&nbsp;-&nbsp;e<sup>-x</sup></i>)/2
     * where <i>e</i> is {@linkplain Math#E Euler's number}.
     *
     * <p>Special cases:
     * <ul>
     *
     * <li>If the argument is NaN, then the result is NaN.
     *
     * <li>If the argument is infinite, then the result is an infinity
     * with the same sign as the argument.
     *
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     *
     * </ul>
     *
     * <p>
     *  返回{@code double}值的双曲正弦值</x>的双曲正弦定义为(<e> x </sup>&lt; sup&gt; -x </sup> </i>)/ 2其中<i> e </i>是{@linkplain Math#E Euler's number}
     * 。
     * 
     *  <p>特殊情况：
     * <ul>
     * 
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数是无限的,则结果是与参数具有相同符号的无穷大
     * 
     * <li>如果参数为零,则结果是与参数具有相同符号的零
     * 
     * </ul>
     * 
     * 
     * @param   x The number whose hyperbolic sine is to be returned.
     * @return  The hyperbolic sine of {@code x}.
     * @since 1.5
     */
    public static native double sinh(double x);

    /**
     * Returns the hyperbolic cosine of a {@code double} value.
     * The hyperbolic cosine of <i>x</i> is defined to be
     * (<i>e<sup>x</sup>&nbsp;+&nbsp;e<sup>-x</sup></i>)/2
     * where <i>e</i> is {@linkplain Math#E Euler's number}.
     *
     * <p>Special cases:
     * <ul>
     *
     * <li>If the argument is NaN, then the result is NaN.
     *
     * <li>If the argument is infinite, then the result is positive
     * infinity.
     *
     * <li>If the argument is zero, then the result is {@code 1.0}.
     *
     * </ul>
     *
     * <p>
     *  返回{@code double}值的双曲余弦值</x>的双曲余弦定义为(e <sup> x </sup>&nbsp; e <sup> -x </sup> </i>)/ 2其中<i> e </i>是{@linkplain Math#E Euler's number}
     * 。
     * 
     *  <p>特殊情况：
     * <ul>
     * 
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数是无穷大,则结果是正无穷大
     * 
     *  <li>如果参数为零,则结果为{@code 10}
     * 
     * </ul>
     * 
     * 
     * @param   x The number whose hyperbolic cosine is to be returned.
     * @return  The hyperbolic cosine of {@code x}.
     * @since 1.5
     */
    public static native double cosh(double x);

    /**
     * Returns the hyperbolic tangent of a {@code double} value.
     * The hyperbolic tangent of <i>x</i> is defined to be
     * (<i>e<sup>x</sup>&nbsp;-&nbsp;e<sup>-x</sup></i>)/(<i>e<sup>x</sup>&nbsp;+&nbsp;e<sup>-x</sup></i>),
     * in other words, {@linkplain Math#sinh
     * sinh(<i>x</i>)}/{@linkplain Math#cosh cosh(<i>x</i>)}.  Note
     * that the absolute value of the exact tanh is always less than
     * 1.
     *
     * <p>Special cases:
     * <ul>
     *
     * <li>If the argument is NaN, then the result is NaN.
     *
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     *
     * <li>If the argument is positive infinity, then the result is
     * {@code +1.0}.
     *
     * <li>If the argument is negative infinity, then the result is
     * {@code -1.0}.
     *
     * </ul>
     *
     * <p>
     * 返回{@code double}值的双曲正切。
     * <x>的双曲正切被定义为(<e> x </sup>&lt; sup&gt; -x </sup> </i>)/(<i> e <sup> x </sup>&nbsp; +&nbsp; e <sup> -x 
     * </sup> </i>),换句话说, {@linkplain Math#sinh sinh(<i> x </i>)} / {@ linkplain Math#cosh cosh(<i> x </i>)}
     * 注意,精确tanh的绝对值总是小于1。
     * 返回{@code double}值的双曲正切。
     * 
     *  <p>特殊情况：
     * <ul>
     * 
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数为零,则结果是与参数具有相同符号的零
     * 
     *  <li>如果参数为正无穷大,则结果为{@code +10}
     * 
     *  <li>如果参数为负无穷大,则结果为{@code -10}
     * 
     * </ul>
     * 
     * 
     * @param   x The number whose hyperbolic tangent is to be returned.
     * @return  The hyperbolic tangent of {@code x}.
     * @since 1.5
     */
    public static native double tanh(double x);

    /**
     * Returns sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)
     * without intermediate overflow or underflow.
     *
     * <p>Special cases:
     * <ul>
     *
     * <li> If either argument is infinite, then the result
     * is positive infinity.
     *
     * <li> If either argument is NaN and neither argument is infinite,
     * then the result is NaN.
     *
     * </ul>
     *
     * <p>
     *  返回无中间溢出或下溢的sqrt(<i> x </i> <sup> 2 </sup>&nbsp; + <i> y </i> <sup>
     * 
     * <p>特殊情况：
     * <ul>
     * 
     *  <li>如果任一参数为无穷大,则结果为正无穷
     * 
     *  <li>如果任一参数是NaN,而且两个参数都是无限的,则结果是NaN
     * 
     * </ul>
     * 
     * 
     * @param x a value
     * @param y a value
     * @return sqrt(<i>x</i><sup>2</sup>&nbsp;+<i>y</i><sup>2</sup>)
     * without intermediate overflow or underflow
     * @since 1.5
     */
    public static native double hypot(double x, double y);

    /**
     * Returns <i>e</i><sup>x</sup>&nbsp;-1.  Note that for values of
     * <i>x</i> near 0, the exact sum of
     * {@code expm1(x)}&nbsp;+&nbsp;1 is much closer to the true
     * result of <i>e</i><sup>x</sup> than {@code exp(x)}.
     *
     * <p>Special cases:
     * <ul>
     * <li>If the argument is NaN, the result is NaN.
     *
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     *
     * <li>If the argument is negative infinity, then the result is
     * -1.0.
     *
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     *
     * </ul>
     *
     * <p>
     *  返回<i> e </i> <sup> x </sup>&nbsp; -1请注意,对于<i> x </i>接近0的值,{@code expm1(x)}& ; + 1更接近<i> e </i> <sup>
     *  x </sup>的真实结果比{@code exp(x)}。
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数为正无穷大,则结果为正无穷
     * 
     *  <li>如果参数为负无穷大,则结果为-10
     * 
     *  <li>如果参数为零,则结果是与参数具有相同符号的零
     * 
     * </ul>
     * 
     * 
     * @param   x   the exponent to raise <i>e</i> to in the computation of
     *              <i>e</i><sup>{@code x}</sup>&nbsp;-1.
     * @return  the value <i>e</i><sup>{@code x}</sup>&nbsp;-&nbsp;1.
     * @since 1.5
     */
    public static native double expm1(double x);

    /**
     * Returns the natural logarithm of the sum of the argument and 1.
     * Note that for small values {@code x}, the result of
     * {@code log1p(x)} is much closer to the true result of ln(1
     * + {@code x}) than the floating-point evaluation of
     * {@code log(1.0+x)}.
     *
     * <p>Special cases:
     * <ul>
     *
     * <li>If the argument is NaN or less than -1, then the result is
     * NaN.
     *
     * <li>If the argument is positive infinity, then the result is
     * positive infinity.
     *
     * <li>If the argument is negative one, then the result is
     * negative infinity.
     *
     * <li>If the argument is zero, then the result is a zero with the
     * same sign as the argument.
     *
     * </ul>
     *
     * <p>
     * 返回参数和的和的自然对数1注意,对于小值{@code x},{@code log1p(x)}的结果更接近ln的真实结果(1 + {@code x })比{@code log(10 + x)}的浮点计算。
     * 
     *  <p>特殊情况：
     * <ul>
     * 
     *  <li>如果参数为NaN或小于-1,则结果为NaN
     * 
     *  <li>如果参数为正无穷大,则结果为正无穷
     * 
     *  <li>如果参数为负数,则结果为负无穷大
     * 
     *  <li>如果参数为零,则结果是与参数具有相同符号的零
     * 
     * </ul>
     * 
     * 
     * @param   x   a value
     * @return the value ln({@code x}&nbsp;+&nbsp;1), the natural
     * log of {@code x}&nbsp;+&nbsp;1
     * @since 1.5
     */
    public static native double log1p(double x);

    /**
     * Returns the first floating-point argument with the sign of the
     * second floating-point argument.  For this method, a NaN
     * {@code sign} argument is always treated as if it were
     * positive.
     *
     * <p>
     *  返回带有第二个浮点数参数的符号的第一个浮点数参数对于此方法,NaN {@code sign}参数总是被视为正数
     * 
     * 
     * @param magnitude  the parameter providing the magnitude of the result
     * @param sign   the parameter providing the sign of the result
     * @return a value with the magnitude of {@code magnitude}
     * and the sign of {@code sign}.
     * @since 1.6
     */
    public static double copySign(double magnitude, double sign) {
        return Math.copySign(magnitude, (Double.isNaN(sign)?1.0d:sign));
    }

    /**
     * Returns the first floating-point argument with the sign of the
     * second floating-point argument.  For this method, a NaN
     * {@code sign} argument is always treated as if it were
     * positive.
     *
     * <p>
     * 返回带有第二个浮点数参数的符号的第一个浮点数参数对于此方法,NaN {@code sign}参数总是被视为正数
     * 
     * 
     * @param magnitude  the parameter providing the magnitude of the result
     * @param sign   the parameter providing the sign of the result
     * @return a value with the magnitude of {@code magnitude}
     * and the sign of {@code sign}.
     * @since 1.6
     */
    public static float copySign(float magnitude, float sign) {
        return Math.copySign(magnitude, (Float.isNaN(sign)?1.0f:sign));
    }
    /**
     * Returns the unbiased exponent used in the representation of a
     * {@code float}.  Special cases:
     *
     * <ul>
     * <li>If the argument is NaN or infinite, then the result is
     * {@link Float#MAX_EXPONENT} + 1.
     * <li>If the argument is zero or subnormal, then the result is
     * {@link Float#MIN_EXPONENT} -1.
     * </ul>
     * <p>
     *  返回在{@code float}表示中使用的无偏指数特殊情况：
     * 
     * <ul>
     *  <li>如果参数为NaN或无限,则结果为{@link Float#MAX_EXPONENT} + 1 <li>如果参数为零或子正常,则结果为{@link Float#MIN_EXPONENT} -1。
     * </ul>
     * 
     * @param f a {@code float} value
     * @return the unbiased exponent of the argument
     * @since 1.6
     */
    public static int getExponent(float f) {
        return Math.getExponent(f);
    }

    /**
     * Returns the unbiased exponent used in the representation of a
     * {@code double}.  Special cases:
     *
     * <ul>
     * <li>If the argument is NaN or infinite, then the result is
     * {@link Double#MAX_EXPONENT} + 1.
     * <li>If the argument is zero or subnormal, then the result is
     * {@link Double#MIN_EXPONENT} -1.
     * </ul>
     * <p>
     *  返回在{@code double}特殊情况下的表示中使用的无偏指数：
     * 
     * <ul>
     *  <li>如果参数为NaN或无限,则结果为{@link Double#MAX_EXPONENT} + 1 <li>如果参数为零或次正常,则结果为{@link Double#MIN_EXPONENT} -
     * 1。
     * </ul>
     * 
     * @param d a {@code double} value
     * @return the unbiased exponent of the argument
     * @since 1.6
     */
    public static int getExponent(double d) {
        return Math.getExponent(d);
    }

    /**
     * Returns the floating-point number adjacent to the first
     * argument in the direction of the second argument.  If both
     * arguments compare as equal the second argument is returned.
     *
     * <p>Special cases:
     * <ul>
     * <li> If either argument is a NaN, then NaN is returned.
     *
     * <li> If both arguments are signed zeros, {@code direction}
     * is returned unchanged (as implied by the requirement of
     * returning the second argument if the arguments compare as
     * equal).
     *
     * <li> If {@code start} is
     * &plusmn;{@link Double#MIN_VALUE} and {@code direction}
     * has a value such that the result should have a smaller
     * magnitude, then a zero with the same sign as {@code start}
     * is returned.
     *
     * <li> If {@code start} is infinite and
     * {@code direction} has a value such that the result should
     * have a smaller magnitude, {@link Double#MAX_VALUE} with the
     * same sign as {@code start} is returned.
     *
     * <li> If {@code start} is equal to &plusmn;
     * {@link Double#MAX_VALUE} and {@code direction} has a
     * value such that the result should have a larger magnitude, an
     * infinity with same sign as {@code start} is returned.
     * </ul>
     *
     * <p>
     * 返回第二个参数方向上与第一个参数相邻的浮点数。如果两个参数都相等,则返回第二个参数
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果任一参数是NaN,则返回NaN
     * 
     *  <li>如果两个参数都是带符号的零,{@code direction}会不变地返回(如果参数比较为相等则返回第二个参数的要求)
     * 
     *  <li>如果{@code start}是&plusmn; {@ link Double#MIN_VALUE}和{@code direction}有一个值,结果应该有一个较小的幅度,然后一个零与{@code start }
     * 返回。
     * 
     * <li>如果{@code start}是无限的,而且{@code direction}有一个值,以使结果的幅度较小,则返回与{@code start}相同符号的{@link Double#MAX_VALUE}
     * 。
     * 
     *  <li>如果{@code start}等于&plusmn; {@link Double#MAX_VALUE}和{@code direction}具有一个值,以便结果应具有较大的幅度,返回与{@code start}
     * 相同符号的无穷大。
     * </ul>
     * 
     * 
     * @param start  starting floating-point value
     * @param direction value indicating which of
     * {@code start}'s neighbors or {@code start} should
     * be returned
     * @return The floating-point number adjacent to {@code start} in the
     * direction of {@code direction}.
     * @since 1.6
     */
    public static double nextAfter(double start, double direction) {
        return Math.nextAfter(start, direction);
    }

    /**
     * Returns the floating-point number adjacent to the first
     * argument in the direction of the second argument.  If both
     * arguments compare as equal a value equivalent to the second argument
     * is returned.
     *
     * <p>Special cases:
     * <ul>
     * <li> If either argument is a NaN, then NaN is returned.
     *
     * <li> If both arguments are signed zeros, a value equivalent
     * to {@code direction} is returned.
     *
     * <li> If {@code start} is
     * &plusmn;{@link Float#MIN_VALUE} and {@code direction}
     * has a value such that the result should have a smaller
     * magnitude, then a zero with the same sign as {@code start}
     * is returned.
     *
     * <li> If {@code start} is infinite and
     * {@code direction} has a value such that the result should
     * have a smaller magnitude, {@link Float#MAX_VALUE} with the
     * same sign as {@code start} is returned.
     *
     * <li> If {@code start} is equal to &plusmn;
     * {@link Float#MAX_VALUE} and {@code direction} has a
     * value such that the result should have a larger magnitude, an
     * infinity with same sign as {@code start} is returned.
     * </ul>
     *
     * <p>
     *  返回在第二个参数方向上与第一个参数相邻的浮点数如果两个参数都相等,则返回等于第二个参数的值
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果任一参数是NaN,则返回NaN
     * 
     *  <li>如果两个参数都是带符号的零,则会返回等效于{@code direction}的值
     * 
     * <li>如果{@code start}是&plusmn; {@ link Float#MIN_VALUE}和{@code direction}有一个值,结果应该有一个较小的幅度,然后一个零与{@code start }
     * 返回。
     * 
     *  <li>如果{@code start}是无限的,而且{@code direction}有一个值,以使结果的幅度较小,则返回与{@code start}相同符号的{@link Float#MAX_VALUE}
     * 。
     * 
     *  <li>如果{@code start}等于&plusmn; {@link Float#MAX_VALUE}和{@code direction}具有一个值,使得结果应具有更大的幅度,返回与{@code start}
     * 具有相同符号的无穷大。
     * </ul>
     * 
     * 
     * @param start  starting floating-point value
     * @param direction value indicating which of
     * {@code start}'s neighbors or {@code start} should
     * be returned
     * @return The floating-point number adjacent to {@code start} in the
     * direction of {@code direction}.
     * @since 1.6
     */
    public static float nextAfter(float start, double direction) {
        return Math.nextAfter(start, direction);
    }

    /**
     * Returns the floating-point value adjacent to {@code d} in
     * the direction of positive infinity.  This method is
     * semantically equivalent to {@code nextAfter(d,
     * Double.POSITIVE_INFINITY)}; however, a {@code nextUp}
     * implementation may run faster than its equivalent
     * {@code nextAfter} call.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, the result is NaN.
     *
     * <li> If the argument is positive infinity, the result is
     * positive infinity.
     *
     * <li> If the argument is zero, the result is
     * {@link Double#MIN_VALUE}
     *
     * </ul>
     *
     * <p>
     * 返回与正无穷大方向相邻的{@code d}的浮点值此方法在语义上等同于{@code nextAfter(d,DoublePOSITIVE_INFINITY)};但是,{@code nextUp}实施的运
     * 行速度可能比其等效的{@code nextAfter}调用快。
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数为正无穷大,则结果为正无穷
     * 
     *  <li>如果参数为零,则结果为{@link Double#MIN_VALUE}
     * 
     * </ul>
     * 
     * 
     * @param d starting floating-point value
     * @return The adjacent floating-point value closer to positive
     * infinity.
     * @since 1.6
     */
    public static double nextUp(double d) {
        return Math.nextUp(d);
    }

    /**
     * Returns the floating-point value adjacent to {@code f} in
     * the direction of positive infinity.  This method is
     * semantically equivalent to {@code nextAfter(f,
     * Float.POSITIVE_INFINITY)}; however, a {@code nextUp}
     * implementation may run faster than its equivalent
     * {@code nextAfter} call.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, the result is NaN.
     *
     * <li> If the argument is positive infinity, the result is
     * positive infinity.
     *
     * <li> If the argument is zero, the result is
     * {@link Float#MIN_VALUE}
     *
     * </ul>
     *
     * <p>
     *  返回在正无穷大方向上与{@code f}相邻的浮点值此方法在语义上等同于{@code nextAfter(f,FloatPOSITIVE_INFINITY)};但是,{@code nextUp}实施的
     * 运行速度可能比其等效的{@code nextAfter}调用快。
     * 
     * <p>特殊情况：
     * <ul>
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数为正无穷大,则结果为正无穷
     * 
     *  <li>如果参数为零,则结果为{@link Float#MIN_VALUE}
     * 
     * </ul>
     * 
     * 
     * @param f starting floating-point value
     * @return The adjacent floating-point value closer to positive
     * infinity.
     * @since 1.6
     */
    public static float nextUp(float f) {
        return Math.nextUp(f);
    }

    /**
     * Returns the floating-point value adjacent to {@code d} in
     * the direction of negative infinity.  This method is
     * semantically equivalent to {@code nextAfter(d,
     * Double.NEGATIVE_INFINITY)}; however, a
     * {@code nextDown} implementation may run faster than its
     * equivalent {@code nextAfter} call.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, the result is NaN.
     *
     * <li> If the argument is negative infinity, the result is
     * negative infinity.
     *
     * <li> If the argument is zero, the result is
     * {@code -Double.MIN_VALUE}
     *
     * </ul>
     *
     * <p>
     *  返回在负无穷大的方向上与{@code d}相邻的浮点值此方法在语义上等同于{@code nextAfter(d,DoubleNEGATIVE_INFINITY)};但是,{@code nextDown}
     * 实现的运行速度可能比其等效的{@code nextAfter}调用更快。
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数为负无穷大,则结果为负无穷大
     * 
     *  <li>如果参数为零,则结果为{@code -DoubleMIN_VALUE}
     * 
     * </ul>
     * 
     * 
     * @param d  starting floating-point value
     * @return The adjacent floating-point value closer to negative
     * infinity.
     * @since 1.8
     */
    public static double nextDown(double d) {
        return Math.nextDown(d);
    }

    /**
     * Returns the floating-point value adjacent to {@code f} in
     * the direction of negative infinity.  This method is
     * semantically equivalent to {@code nextAfter(f,
     * Float.NEGATIVE_INFINITY)}; however, a
     * {@code nextDown} implementation may run faster than its
     * equivalent {@code nextAfter} call.
     *
     * <p>Special Cases:
     * <ul>
     * <li> If the argument is NaN, the result is NaN.
     *
     * <li> If the argument is negative infinity, the result is
     * negative infinity.
     *
     * <li> If the argument is zero, the result is
     * {@code -Float.MIN_VALUE}
     *
     * </ul>
     *
     * <p>
     * 返回在负无穷大的方向上与{@code f}相邻的浮点值此方法在语义上等同于{@code nextAfter(f,FloatNEGATIVE_INFINITY)};但是,{@code nextDown}实
     * 现的运行速度可能比其等效的{@code nextAfter}调用更快。
     * 
     *  <p>特殊情况：
     * <ul>
     *  <li>如果参数为NaN,则结果为NaN
     * 
     *  <li>如果参数为负无穷大,则结果为负无穷大
     * 
     *  <li>如果参数为零,则结果为{@code -FloatMIN_VALUE}
     * 
     * </ul>
     * 
     * 
     * @param f  starting floating-point value
     * @return The adjacent floating-point value closer to negative
     * infinity.
     * @since 1.8
     */
    public static float nextDown(float f) {
        return Math.nextDown(f);
    }

    /**
     * Returns {@code d} &times;
     * 2<sup>{@code scaleFactor}</sup> rounded as if performed
     * by a single correctly rounded floating-point multiply to a
     * member of the double value set.  See the Java
     * Language Specification for a discussion of floating-point
     * value sets.  If the exponent of the result is between {@link
     * Double#MIN_EXPONENT} and {@link Double#MAX_EXPONENT}, the
     * answer is calculated exactly.  If the exponent of the result
     * would be larger than {@code Double.MAX_EXPONENT}, an
     * infinity is returned.  Note that if the result is subnormal,
     * precision may be lost; that is, when {@code scalb(x, n)}
     * is subnormal, {@code scalb(scalb(x, n), -n)} may not equal
     * <i>x</i>.  When the result is non-NaN, the result has the same
     * sign as {@code d}.
     *
     * <p>Special cases:
     * <ul>
     * <li> If the first argument is NaN, NaN is returned.
     * <li> If the first argument is infinite, then an infinity of the
     * same sign is returned.
     * <li> If the first argument is zero, then a zero of the same
     * sign is returned.
     * </ul>
     *
     * <p>
     * 返回{@code d}&times; 2 <sup> {@ code scaleFactor} </sup>四舍五入,如同由单个正确舍入的浮点数执行乘以双值集合的成员有关浮点值集合的讨论,请参阅Java
     * 语言规范如果结果的指数在{@link Double#MIN_EXPONENT}和{@link Double#MAX_EXPONENT}之间,则精确计算答案如果结果的指数大于{@code DoubleMAX_EXPONENT}
     * ,则返回无穷大注意,如果结果是次正常,精度可能会丢失;当{@code scalb(x,n)}是非正规的时,{@code scalb(scalb(x,n), -  n)}可能不等于x </i> NaN,结
     * 果的符号与{@code d}。
     * 
     *  <p>特殊情况：
     * <ul>
     * <li>如果第一个参数是NaN,则返回NaN <li>如果第一个参数是无穷大,则返回相同符号的无穷大<li>如果第一个参数为零,则相同符号的零回
     * </ul>
     * 
     * 
     * @param d number to be scaled by a power of two.
     * @param scaleFactor power of 2 used to scale {@code d}
     * @return {@code d} &times; 2<sup>{@code scaleFactor}</sup>
     * @since 1.6
     */
    public static double scalb(double d, int scaleFactor) {
        return Math.scalb(d, scaleFactor);
    }

    /**
     * Returns {@code f} &times;
     * 2<sup>{@code scaleFactor}</sup> rounded as if performed
     * by a single correctly rounded floating-point multiply to a
     * member of the float value set.  See the Java
     * Language Specification for a discussion of floating-point
     * value sets.  If the exponent of the result is between {@link
     * Float#MIN_EXPONENT} and {@link Float#MAX_EXPONENT}, the
     * answer is calculated exactly.  If the exponent of the result
     * would be larger than {@code Float.MAX_EXPONENT}, an
     * infinity is returned.  Note that if the result is subnormal,
     * precision may be lost; that is, when {@code scalb(x, n)}
     * is subnormal, {@code scalb(scalb(x, n), -n)} may not equal
     * <i>x</i>.  When the result is non-NaN, the result has the same
     * sign as {@code f}.
     *
     * <p>Special cases:
     * <ul>
     * <li> If the first argument is NaN, NaN is returned.
     * <li> If the first argument is infinite, then an infinity of the
     * same sign is returned.
     * <li> If the first argument is zero, then a zero of the same
     * sign is returned.
     * </ul>
     *
     * <p>
     * 返回{@code f}&times; 2 <sup> {@ code scaleFactor} </sup>四舍五入,如同由单个正确舍入的浮点数执行乘以浮点值集合的成员有关浮点值集合的讨论,请参阅Jav
     * a语言规范如果结果的指数在{@link Float#MIN_EXPONENT}和{@link Float#MAX_EXPONENT}之间,则精确计算答案如果结果的指数大于{@code FloatMAX_EXPONENT}
     * ,则返回无穷大注意,如果结果是次正常,精度可能会丢失;当{@code scalb(x,n)}是非正规的时,{@code scalb(scalb(x,n), -  n)}可能不等于x </i> NaN,结
     * 果的符号与{@code f}。
     * 
     * 
     * @param f number to be scaled by a power of two.
     * @param scaleFactor power of 2 used to scale {@code f}
     * @return {@code f} &times; 2<sup>{@code scaleFactor}</sup>
     * @since 1.6
     */
    public static float scalb(float f, int scaleFactor) {
        return Math.scalb(f, scaleFactor);
    }
}
