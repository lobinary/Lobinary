/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 1999, Oracle and/or its affiliates. All rights reserved.
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

package java.util.zip;

/**
 * An interface representing a data checksum.
 *
 * <p>
 *  表示数据校验和的接口。
 * 
 * 
 * @author      David Connelly
 */
public
interface Checksum {
    /**
     * Updates the current checksum with the specified byte.
     *
     * <p>
     *  使用指定的字节更新当前校验和。
     * 
     * 
     * @param b the byte to update the checksum with
     */
    public void update(int b);

    /**
     * Updates the current checksum with the specified array of bytes.
     * <p>
     *  使用指定的字节数更新当前校验和。
     * 
     * 
     * @param b the byte array to update the checksum with
     * @param off the start offset of the data
     * @param len the number of bytes to use for the update
     */
    public void update(byte[] b, int off, int len);

    /**
     * Returns the current checksum value.
     * <p>
     *  返回当前校验和值。
     * 
     * 
     * @return the current checksum value
     */
    public long getValue();

    /**
     * Resets the checksum to its initial value.
     * <p>
     *  将校验和重置为其初始值。
     */
    public void reset();
}
