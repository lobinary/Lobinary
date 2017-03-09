/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2004, Oracle and/or its affiliates. All rights reserved.
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
package org.omg.CORBA.portable;

import org.omg.CORBA.TypeCode;
import org.omg.CORBA.Principal;
import org.omg.CORBA.Any;

/**
 * OuputStream is the Java API for writing IDL types
 * to CDR marshal streams. These methods are used by the ORB to
 * marshal IDL types as well as to insert IDL types into Anys.
 * The <code>_array</code> versions of the methods can be directly
 * used to write sequences and arrays of IDL types.
 *
 * <p>
 *  OuputStream是用于将IDL类型写入CDR元数据流的Java API。这些方法由ORB用于编组IDL类型以及将IDL类型插入到Anys中。
 * 方法的<code> _array </code>版本可以直接用于编写IDL类型的序列和数组。
 * 
 * 
 * @since   JDK1.2
 */


public abstract class OutputStream extends java.io.OutputStream
{
    /**
     * Returns an input stream with the same buffer.
     * <p>
     *  返回具有相同缓冲区的输入流。
     * 
     * 
     *@return an input stream with the same buffer.
     */
    public abstract InputStream create_input_stream();

    /**
     * Writes a boolean value to this stream.
     * <p>
     *  将一个布尔值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_boolean(boolean value);
    /**
     * Writes a char value to this stream.
     * <p>
     *  将char值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_char(char value);
    /**
     * Writes a wide char value to this stream.
     * <p>
     *  将宽字符值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_wchar(char value);
    /**
     * Writes a CORBA octet (i.e. byte) value to this stream.
     * <p>
     *  将CORBA八位字节(即字节)值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_octet(byte value);
    /**
     * Writes a short value to this stream.
     * <p>
     *  向此流写入一个短值。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_short(short value);
    /**
     * Writes an unsigned short value to this stream.
     * <p>
     *  将无符号短整型值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_ushort(short value);
    /**
     * Writes a CORBA long (i.e. Java int) value to this stream.
     * <p>
     *  将CORBA长(即Java int)值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_long(int value);
    /**
     * Writes an unsigned CORBA long (i.e. Java int) value to this stream.
     * <p>
     *  将无符号CORBA长(即Java int)值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_ulong(int value);
    /**
     * Writes a CORBA longlong (i.e. Java long) value to this stream.
     * <p>
     *  向此流写入长整型CORBA(即Java long)值。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_longlong(long value);
    /**
     * Writes an unsigned CORBA longlong (i.e. Java long) value to this stream.
     * <p>
     *  将无符号CORBA长整型(即Java long)值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_ulonglong(long value);
    /**
     * Writes a float value to this stream.
     * <p>
     *  将浮点值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_float(float value);
    /**
     * Writes a double value to this stream.
     * <p>
     *  将double值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_double(double value);
    /**
     * Writes a string value to this stream.
     * <p>
     *  将字符串值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_string(String value);
    /**
     * Writes a wide string value to this stream.
     * <p>
     *  将宽字符串值写入此流。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_wstring(String value);

    /**
     * Writes an array of booleans on this output stream.
     * <p>
     *  在此输出流上写入一个布尔数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_boolean_array(boolean[] value, int offset,
                                             int length);
    /**
     * Writes an array of chars on this output stream.
     * <p>
     *  在此输出流上写入一个字符数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_char_array(char[] value, int offset,
                                          int length);
    /**
     * Writes an array of wide chars on this output stream.
     * <p>
     *  在此输出流上写入宽字符数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_wchar_array(char[] value, int offset,
                                           int length);
    /**
     * Writes an array of CORBA octets (bytes) on this output stream.
     * <p>
     *  在此输出流上写入CORBA八位字节(字节)数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_octet_array(byte[] value, int offset,
                                           int length);
    /**
     * Writes an array of shorts on this output stream.
     * <p>
     *  在此输出流上写入一个短整型数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_short_array(short[] value, int offset,
                                           int length);
    /**
     * Writes an array of unsigned shorts on this output stream.
     * <p>
     *  在此输出流上写入一个无符号短整数数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_ushort_array(short[] value, int offset,
                                            int length);
    /**
     * Writes an array of CORBA longs (i.e. Java ints) on this output stream.
     * <p>
     * 在此输出流上写入CORBA长整型数组(即Java ints)。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_long_array(int[] value, int offset,
                                          int length);
    /**
     * Writes an array of unsigned CORBA longs (i.e. Java ints) on this output stream.
     * <p>
     *  在此输出流上写入一个无符号CORBA长整型数组(即Java ints)。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_ulong_array(int[] value, int offset,
                                           int length);
    /**
     * Writes an array of CORBA longlongs (i.e. Java longs) on this output stream.
     * <p>
     *  在此输出流上写入CORBA长整型数组(即Java longsongs)。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_longlong_array(long[] value, int offset,
                                              int length);
    /**
     * Writes an array of unsigned CORBA longlongs (i.e. Java ints) on this output stream.
     * <p>
     *  在此输出流上写入一个无符号CORBA长整型数组(即Java ints)。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_ulonglong_array(long[] value, int offset,
                                               int length);
    /**
     * Writes an array of floats on this output stream.
     * <p>
     *  在此输出流中写入一个浮点数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_float_array(float[] value, int offset,
                                           int length);
    /**
     * Writes an array of doubles on this output stream.
     * <p>
     *  在此输出流上写入一个双精度数组。
     * 
     * 
     * @param value the array to be written.
     * @param offset offset on the stream.
     * @param length length of buffer to write.
     */
    public abstract void write_double_array(double[] value, int offset,
                                            int length);
    /**
     * Writes a CORBA Object on this output stream.
     * <p>
     *  在此输出流上写入CORBA对象。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_Object(org.omg.CORBA.Object value);
    /**
     * Writes a TypeCode on this output stream.
     * <p>
     *  在此输出流上写入TypeCode。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_TypeCode(TypeCode value);
    /**
     * Writes an Any on this output stream.
     * <p>
     *  在此输出流中写入Any。
     * 
     * 
     * @param value the value to be written.
     */
    public abstract void write_any(Any value);

    /**
     * Writes a Principle on this output stream.
     * <p>
     *  在此输出流上写入一个原理。
     * 
     * 
     * @param value the value to be written.
     * @deprecated Deprecated by CORBA 2.2.
     */
    @Deprecated
    public void write_Principal(Principal value) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Writes an integer (length of arrays) onto this stream.
     * <p>
     *  将整数(数组长度)写入此流。
     * 
     * 
     * @param b the value to be written.
     * @throws java.io.IOException if there is an input/output error
     * @see <a href="package-summary.html#unimpl"><code>portable</code>
     * package comments for unimplemented features</a>
     */
    public void write(int b) throws java.io.IOException {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Writes a BigDecimal number.
     * <p>
     *  写入BigDecimal数字。
     * 
     * 
     * @param value a BidDecimal--value to be written.
     */
    public void write_fixed(java.math.BigDecimal value) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Writes a CORBA context on this stream. The
     * Context is marshaled as a sequence of strings.
     * Only those Context values specified in the contexts
     * parameter are actually written.
     * <p>
     *  在此流上写入CORBA上下文。上下文被编组为字符串序列。只有在contexts参数中指定的上下文值才实际写入。
     * 
     * 
     * @param ctx a CORBA context
     * @param contexts a <code>ContextList</code> object containing the list of contexts
     *        to be written
     * @see <a href="package-summary.html#unimpl"><code>portable</code>
     * package comments for unimplemented features</a>
     */
    public void write_Context(org.omg.CORBA.Context ctx,
                              org.omg.CORBA.ContextList contexts) {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }

    /**
     * Returns the ORB that created this OutputStream.
     * <p>
     *  返回创建此OutputStream的ORB。
     * 
     * @return the ORB that created this OutputStream
     * @see <a href="package-summary.html#unimpl"><code>portable</code>
     * package comments for unimplemented features</a>
     */
    public org.omg.CORBA.ORB orb() {
        throw new org.omg.CORBA.NO_IMPLEMENT();
    }
}
