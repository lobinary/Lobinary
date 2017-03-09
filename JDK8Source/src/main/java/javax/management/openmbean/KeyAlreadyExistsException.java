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
 * This runtime exception is thrown to indicate that the index of a row to be added to a <i>tabular data</i> instance
 * is already used to refer to another row in this <i>tabular data</i> instance.
 *
 *
 * <p>
 *  抛出此运行时异常以指示要添加到表格数据</i>实例的行的索引已经用于引用此表格数据</i>实例中的另一行。
 * 
 * 
 * @since 1.5
 */
public class KeyAlreadyExistsException extends IllegalArgumentException {

    private static final long serialVersionUID = 1845183636745282866L;

    /**
     * A KeyAlreadyExistsException with no detail message.
     * <p>
     *  没有详细消息的KeyAlreadyExistsException。
     * 
     */
    public KeyAlreadyExistsException() {
        super();
    }

    /**
     * A KeyAlreadyExistsException with a detail message.
     *
     * <p>
     *  带有详细消息的KeyAlreadyExistsException。
     * 
     * @param msg the detail message.
     */
    public KeyAlreadyExistsException(String msg) {
        super(msg);
    }

}
