/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2005, Oracle and/or its affiliates. All rights reserved.
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

package java.io;

/**
 * Thrown when control information that was read from an object stream
 * violates internal consistency checks.
 *
 * <p>
 *  从对象流读取的控制信息违反内部一致性检查时抛出。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.1
 */
public class StreamCorruptedException extends ObjectStreamException {

    private static final long serialVersionUID = 8983558202217591746L;

    /**
     * Create a StreamCorruptedException and list a reason why thrown.
     *
     * <p>
     *  创建一个StreamCorruptedException并列出一个原因为什么抛出。
     * 
     * 
     * @param reason  String describing the reason for the exception.
     */
    public StreamCorruptedException(String reason) {
        super(reason);
    }

    /**
     * Create a StreamCorruptedException and list no reason why thrown.
     * <p>
     *  创建一个StreamCorruptedException并且没有列出为什么抛出的原因。
     */
    public StreamCorruptedException() {
        super();
    }
}
