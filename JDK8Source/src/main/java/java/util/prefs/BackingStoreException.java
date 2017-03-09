/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2003, Oracle and/or its affiliates. All rights reserved.
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

package java.util.prefs;

import java.io.NotSerializableException;

/**
 * Thrown to indicate that a preferences operation could not complete because
 * of a failure in the backing store, or a failure to contact the backing
 * store.
 *
 * <p>
 *  抛出以指示由于后备存储库中的故障或无法联系后备存储库而无法完成首选项操作。
 * 
 * 
 * @author  Josh Bloch
 * @since   1.4
 */
public class BackingStoreException extends Exception {
    /**
     * Constructs a BackingStoreException with the specified detail message.
     *
     * <p>
     *  构造具有指定详细消息的BackingStoreException。
     * 
     * 
     * @param s the detail message.
     */
    public BackingStoreException(String s) {
        super(s);
    }

    /**
     * Constructs a BackingStoreException with the specified cause.
     *
     * <p>
     *  构造具有指定原因的BackingStoreException。
     * 
     * @param cause the cause
     */
    public BackingStoreException(Throwable cause) {
        super(cause);
    }

    private static final long serialVersionUID = 859796500401108469L;
}
