/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.nio;


/**
 * A typesafe enumeration for byte orders.
 *
 * <p>
 *  字节顺序的类型安全枚举。
 * 
 * 
 * @author Mark Reinhold
 * @author JSR-51 Expert Group
 * @since 1.4
 */

public final class ByteOrder {

    private String name;

    private ByteOrder(String name) {
        this.name = name;
    }

    /**
     * Constant denoting big-endian byte order.  In this order, the bytes of a
     * multibyte value are ordered from most significant to least significant.
     * <p>
     *  常量表示大端字节顺序。按照此顺序,多字节值的字节从最高有效到最低有效排序。
     * 
     */
    public static final ByteOrder BIG_ENDIAN
        = new ByteOrder("BIG_ENDIAN");

    /**
     * Constant denoting little-endian byte order.  In this order, the bytes of
     * a multibyte value are ordered from least significant to most
     * significant.
     * <p>
     *  常量表示小端字节顺序。按照此顺序,多字节值的字节从最低有效到最高有效排序。
     * 
     */
    public static final ByteOrder LITTLE_ENDIAN
        = new ByteOrder("LITTLE_ENDIAN");

    /**
     * Retrieves the native byte order of the underlying platform.
     *
     * <p> This method is defined so that performance-sensitive Java code can
     * allocate direct buffers with the same byte order as the hardware.
     * Native code libraries are often more efficient when such buffers are
     * used.  </p>
     *
     * <p>
     *  检索基础平台的本机字节顺序。
     * 
     *  <p>定义此方法,以便性能敏感的Java代码可以按照与硬件相同的字节顺序分配直接缓冲区。当使用这样的缓冲器时,本地代码库通常更有效。 </p>
     * 
     * 
     * @return  The native byte order of the hardware upon which this Java
     *          virtual machine is running
     */
    public static ByteOrder nativeOrder() {
        return Bits.byteOrder();
    }

    /**
     * Constructs a string describing this object.
     *
     * <p> This method returns the string <tt>"BIG_ENDIAN"</tt> for {@link
     * #BIG_ENDIAN} and <tt>"LITTLE_ENDIAN"</tt> for {@link #LITTLE_ENDIAN}.
     * </p>
     *
     * <p>
     *  构造一个描述此对象的字符串。
     * 
     *  <p>此方法为{@link #BIG_ENDIAN}返回字符串<tt>"BIG_ENDIAN"</tt>,为{@link #LITTLE_ENDIAN}返回<tt>"LITTLE_ENDIAN"</tt>
     * 
     * @return  The specified string
     */
    public String toString() {
        return name;
    }

}
