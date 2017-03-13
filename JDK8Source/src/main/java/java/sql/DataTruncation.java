/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.sql;

/**
 * An exception  thrown as a <code>DataTruncation</code> exception
 * (on writes) or reported as a
 * <code>DataTruncation</code> warning (on reads)
 *  when a data values is unexpectedly truncated for reasons other than its having
 *  exceeded <code>MaxFieldSize</code>.
 *
 * <P>The SQLstate for a <code>DataTruncation</code> during read is <code>01004</code>.
 * <P>The SQLstate for a <code>DataTruncation</code> during write is <code>22001</code>.
 * <p>
 *  当数据值因其他原因而意外截断时,抛出作为<code> DataTruncation </code>异常(写入时)或报告为<code> DataTruncation </code>警告的异常code> 
 * MaxFieldSize </code>。
 * 
 *  <P>读取期间<code> DataTruncation </code>的SQL状态为<code> 01004 </code> <P>写入期间<code> DataTruncation </code>
 * 的SQL状态为<code> 22001 <代码>。
 * 
 */

public class DataTruncation extends SQLWarning {

    /**
     * Creates a <code>DataTruncation</code> object
     * with the SQLState initialized
     * to 01004 when <code>read</code> is set to <code>true</code> and 22001
     * when <code>read</code> is set to <code>false</code>,
     * the reason set to "Data truncation", the
     * vendor code set to 0, and
     * the other fields set to the given values.
     * The <code>cause</code> is not initialized, and may subsequently be
     * initialized by a call to the
     * {@link Throwable#initCause(java.lang.Throwable)} method.
     * <p>
     *
     * <p>
     * 创建<code> DataTruncation </code>对象,当<code>读</code>设置为<code> true </code>时,SQLState初始化为01004,设置<code>读</code>
     * 到<code> false </code>,原因设置为"数据截断",供应商代码设置为0,其他字段设置为给定值<code> cause </code>未初始化,随后通过调用{@link Throwable#initCause(javalangThrowable)}
     * 方法进行初始化。
     * <p>
     * 
     * 
     * @param index The index of the parameter or column value
     * @param parameter true if a parameter value was truncated
     * @param read true if a read was truncated
     * @param dataSize the original size of the data
     * @param transferSize the size after truncation
     */
    public DataTruncation(int index, boolean parameter,
                          boolean read, int dataSize,
                          int transferSize) {
        super("Data truncation", read == true?"01004":"22001");
        this.index = index;
        this.parameter = parameter;
        this.read = read;
        this.dataSize = dataSize;
        this.transferSize = transferSize;

    }

    /**
     * Creates a <code>DataTruncation</code> object
     * with the SQLState initialized
     * to 01004 when <code>read</code> is set to <code>true</code> and 22001
     * when <code>read</code> is set to <code>false</code>,
     * the reason set to "Data truncation", the
     * vendor code set to 0, and
     * the other fields set to the given values.
     * <p>
     *
     * <p>
     *  创建<code> DataTruncation </code>对象,当<code>读</code>设置为<code> true </code>时,SQLState初始化为01004,设置<code>读
     * </code>到<code> false </code>,原因设置为"数据截断",供应商代码设置为0,其他字段设置为给定值。
     * <p>
     * 
     * 
     * @param index The index of the parameter or column value
     * @param parameter true if a parameter value was truncated
     * @param read true if a read was truncated
     * @param dataSize the original size of the data
     * @param transferSize the size after truncation
     * @param cause the underlying reason for this <code>DataTruncation</code>
     * (which is saved for later retrieval by the <code>getCause()</code> method);
     * may be null indicating the cause is non-existent or unknown.
     *
     * @since 1.6
     */
    public DataTruncation(int index, boolean parameter,
                          boolean read, int dataSize,
                          int transferSize, Throwable cause) {
        super("Data truncation", read == true?"01004":"22001",cause);
        this.index = index;
        this.parameter = parameter;
        this.read = read;
        this.dataSize = dataSize;
        this.transferSize = transferSize;
    }

    /**
     * Retrieves the index of the column or parameter that was truncated.
     *
     * <P>This may be -1 if the column or parameter index is unknown, in
     * which case the <code>parameter</code> and <code>read</code> fields should be ignored.
     *
     * <p>
     * 检索已截断的列或参数的索引
     * 
     *  <P>如果列或参数索引未知,这可能为-1,在这种情况下应忽略<code>参数</code>和<code>读取</code>字段
     * 
     * 
     * @return the index of the truncated parameter or column value
     */
    public int getIndex() {
        return index;
    }

    /**
     * Indicates whether the value truncated was a parameter value or
         * a column value.
     *
     * <p>
     *  指示截断的值是参数值还是列值
     * 
     * 
     * @return <code>true</code> if the value truncated was a parameter;
         *         <code>false</code> if it was a column value
     */
    public boolean getParameter() {
        return parameter;
    }

    /**
     * Indicates whether or not the value was truncated on a read.
     *
     * <p>
     *  指示值是否在读取时被截断
     * 
     * 
     * @return <code>true</code> if the value was truncated when read from
         *         the database; <code>false</code> if the data was truncated on a write
     */
    public boolean getRead() {
        return read;
    }

    /**
     * Gets the number of bytes of data that should have been transferred.
     * This number may be approximate if data conversions were being
     * performed.  The value may be <code>-1</code> if the size is unknown.
     *
     * <p>
     *  获取应该传输的数据的字节数如果正在执行数据转换,此数字可能是近似值如果大小未知,值可以是<code> -1 </code>
     * 
     * 
     * @return the number of bytes of data that should have been transferred
     */
    public int getDataSize() {
        return dataSize;
    }

    /**
     * Gets the number of bytes of data actually transferred.
     * The value may be <code>-1</code> if the size is unknown.
     *
     * <p>
     *  获取实际传输的数据的字节数如果大小未知,值可以是<code> -1 </code>
     * 
     * 
     * @return the number of bytes of data actually transferred
     */
    public int getTransferSize() {
        return transferSize;
    }

        /**
        /* <p>
        /* 
        * @serial
        */
    private int index;

        /**
        /* <p>
        /* 
        * @serial
        */
    private boolean parameter;

        /**
        /* <p>
        /* 
        * @serial
        */
    private boolean read;

        /**
        /* <p>
        /* 
        * @serial
        */
    private int dataSize;

        /**
        /* <p>
        /* 
        * @serial
        */
    private int transferSize;

    /**
    /* <p>
    /* 
     * @serial
     */
    private static final long serialVersionUID = 6464298989504059473L;

}
