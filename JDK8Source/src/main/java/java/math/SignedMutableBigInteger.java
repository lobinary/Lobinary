/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
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

package java.math;

/**
 * A class used to represent multiprecision integers that makes efficient
 * use of allocated space by allowing a number to occupy only part of
 * an array so that the arrays do not have to be reallocated as often.
 * When performing an operation with many iterations the array used to
 * hold a number is only increased when necessary and does not have to
 * be the same size as the number it represents. A mutable number allows
 * calculations to occur on the same number without having to create
 * a new number for every step of the calculation as occurs with
 * BigIntegers.
 *
 * Note that SignedMutableBigIntegers only support signed addition and
 * subtraction. All other operations occur as with MutableBigIntegers.
 *
 * <p>
 *  用于表示多精度整数的类,通过允许数字只占用数组的一部分来有效利用分配的空间,使得数组不必频繁重新分配。
 * 当使用多次迭代执行操作时,用于保存数字的数组仅在需要时增加,并且不必与其表示的数字具有相同的大小。
 * 可变数字允许计算发生在相同的数字上,而不必像BigIntegers中那样为计算的每个步骤创建一个新数字。
 * 
 *  注意,SignedMutableBigIntegers只支持带符号的加法和减法。所有其他操作与MutableBigIntegers一样。
 * 
 * 
 * @see     BigInteger
 * @author  Michael McCloskey
 * @since   1.3
 */

class SignedMutableBigInteger extends MutableBigInteger {

   /**
     * The sign of this MutableBigInteger.
     * <p>
     *  这个MutableBigInteger的符号。
     * 
     */
    int sign = 1;

    // Constructors

    /**
     * The default constructor. An empty MutableBigInteger is created with
     * a one word capacity.
     * <p>
     *  默认构造函数。创建一个具有一个字容量的空MutableBigInteger。
     * 
     */
    SignedMutableBigInteger() {
        super();
    }

    /**
     * Construct a new MutableBigInteger with a magnitude specified by
     * the int val.
     * <p>
     *  构造一个新的MutableBigInteger,其大小由int val指定。
     * 
     */
    SignedMutableBigInteger(int val) {
        super(val);
    }

    /**
     * Construct a new MutableBigInteger with a magnitude equal to the
     * specified MutableBigInteger.
     * <p>
     *  构造一个新的MutableBigInteger,其大小等于指定的MutableBigInteger。
     * 
     */
    SignedMutableBigInteger(MutableBigInteger val) {
        super(val);
    }

   // Arithmetic Operations

   /**
     * Signed addition built upon unsigned add and subtract.
     * <p>
     *  带符号的加法建立在无符号加法和减法之上。
     * 
     */
    void signedAdd(SignedMutableBigInteger addend) {
        if (sign == addend.sign)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed addition built upon unsigned add and subtract.
     * <p>
     *  带符号的加法建立在无符号加法和减法之上。
     * 
     */
    void signedAdd(MutableBigInteger addend) {
        if (sign == 1)
            add(addend);
        else
            sign = sign * subtract(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     * <p>
     *  有符号减法建立在无符号加法和减法之上。
     * 
     */
    void signedSubtract(SignedMutableBigInteger addend) {
        if (sign == addend.sign)
            sign = sign * subtract(addend);
        else
            add(addend);

    }

   /**
     * Signed subtraction built upon unsigned add and subtract.
     * <p>
     *  有符号减法建立在无符号加法和减法之上。
     * 
     */
    void signedSubtract(MutableBigInteger addend) {
        if (sign == 1)
            sign = sign * subtract(addend);
        else
            add(addend);
        if (intLen == 0)
             sign = 1;
    }

    /**
     * Print out the first intLen ints of this MutableBigInteger's value
     * array starting at offset.
     * <p>
     *  打印出这个MutableBigInteger的值数组的第一个intLen ints,从offset开始。
     */
    public String toString() {
        return this.toBigInteger(sign).toString();
    }

}
