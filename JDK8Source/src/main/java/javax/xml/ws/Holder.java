/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2005, 2010, Oracle and/or its affiliates. All rights reserved.
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

package javax.xml.ws;

import java.io.Serializable;

/**
 * Holds a value of type <code>T</code>.
 *
 * <p>
 *  保存类型<code> T </code>的值。
 * 
 * 
 * @since JAX-WS 2.0
 */
public final class Holder<T> implements Serializable {

    private static final long serialVersionUID = 2623699057546497185L;

    /**
     * The value contained in the holder.
     * <p>
     *  包含在持有人中的值。
     * 
     */
    public T value;

    /**
     * Creates a new holder with a <code>null</code> value.
     * <p>
     *  创建一个具有<code> null </code>值的新持有人。
     * 
     */
    public Holder() {
    }

    /**
     * Create a new holder with the specified value.
     *
     * <p>
     *  创建具有指定值的新持有人。
     * 
     * @param value The value to be stored in the holder.
     */
    public Holder(T value) {
        this.value = value;
    }
}
