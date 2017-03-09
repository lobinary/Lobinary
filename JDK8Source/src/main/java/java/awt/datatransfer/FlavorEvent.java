/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2003, 2006, Oracle and/or its affiliates. All rights reserved.
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
package java.awt.datatransfer;

import java.util.EventObject;


/**
 * <code>FlavorEvent</code> is used to notify interested parties
 * that available {@link DataFlavor}s have changed in the
 * {@link Clipboard} (the event source).
 *
 * <p>
 *  <code> FlavorEvent </code>用于通知相关方{@link剪贴板}(事件源)中的{@link DataFlavor}已更改。
 * 
 * 
 * @see FlavorListener
 *
 * @author Alexander Gerasimov
 * @since 1.5
 */
public class FlavorEvent extends EventObject {
    /**
     * Constructs a <code>FlavorEvent</code> object.
     *
     * <p>
     *  构造一个<code> FlavorEvent </code>对象。
     * 
     * @param source  the <code>Clipboard</code> that is the source of the event
     *
     * @throws IllegalArgumentException if the {@code source} is {@code null}
     */
    public FlavorEvent(Clipboard source) {
        super(source);
    }
}
