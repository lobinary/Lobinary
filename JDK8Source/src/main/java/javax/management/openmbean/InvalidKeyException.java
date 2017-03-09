/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2007, Oracle and/or its affiliates. All rights reserved.
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

package javax.management.openmbean;

/**
 * This runtime exception is thrown to indicate that a method parameter which was expected to be
 * an item name of a <i>composite data</i> or a row index of a <i>tabular data</i> is not valid.
 *
 *
 * <p>
 *  抛出此运行时异常以指示预期为复合数据</i>的项目名称或表格数据</i>的行索引的方法参数无效。
 * 
 * 
 * @since 1.5
 */
public class InvalidKeyException extends IllegalArgumentException {

    private static final long serialVersionUID = 4224269443946322062L;

    /**
     * An InvalidKeyException with no detail message.
     * <p>
     *  没有详细消息的InvalidKeyException。
     * 
     */
    public InvalidKeyException() {
        super();
    }

    /**
     * An InvalidKeyException with a detail message.
     *
     * <p>
     *  带有详细消息的InvalidKeyException。
     * 
     * @param msg the detail message.
     */
    public InvalidKeyException(String msg) {
        super(msg);
    }

}
