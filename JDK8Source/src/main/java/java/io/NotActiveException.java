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
 * Thrown when serialization or deserialization is not active.
 *
 * <p>
 *  当序列化或反序列化不活动时抛出。
 * 
 * 
 * @author  unascribed
 * @since   JDK1.1
 */
public class NotActiveException extends ObjectStreamException {

    private static final long serialVersionUID = -3893467273049808895L;

    /**
     * Constructor to create a new NotActiveException with the reason given.
     *
     * <p>
     *  构造器创建一个新的NotActiveException与给出的原因。
     * 
     * 
     * @param reason  a String describing the reason for the exception.
     */
    public NotActiveException(String reason) {
        super(reason);
    }

    /**
     * Constructor to create a new NotActiveException without a reason.
     * <p>
     *  构造器创建一个新的NotActiveException没有原因。
     */
    public NotActiveException() {
        super();
    }
}
