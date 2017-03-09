/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 2000, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.accessibility;

/**
 * The AccessibleKeyBinding interface should be supported by any object
 * that has a keyboard bindings such as a keyboard mnemonic and/or keyboard
 * shortcut which can be used to select the object.  This interface provides
 * the standard mechanism for an assistive technology to determine the
 * key bindings which exist for this object.
 * Any object that has such key bindings should support this
 * interface.
 *
 * <p>
 *  任何具有键盘绑定的对象都应该支持AccessibleKeyBinding接口,例如键盘助记符和/或键盘快捷键,可以用来选择对象。此接口为辅助技术提供了标准机制,以确定此对象存在的键绑定。
 * 任何具有此类键绑定的对象都应支持此接口。
 * 
 * 
 * @see Accessible
 * @see Accessible#getAccessibleContext
 * @see AccessibleContext
 *
 * @author      Lynn Monsanto
 * @since 1.4
 */
public interface AccessibleKeyBinding {

    /**
     * Returns the number of key bindings for this object
     *
     * <p>
     *  返回此对象的键绑定数
     * 
     * 
     * @return the zero-based number of key bindings for this object
     */
    public int getAccessibleKeyBindingCount();

    /**
     * Returns a key binding for this object.  The value returned is an
     * java.lang.Object which must be cast to appropriate type depending
     * on the underlying implementation of the key.
     *
     * <p>
     *  返回此对象的键绑定。返回的值是一个java.lang.Object,它必须根据键的底层实现转换为适当的类型。
     * 
     * @param i zero-based index of the key bindings
     * @return a javax.lang.Object which specifies the key binding
     * @see #getAccessibleKeyBindingCount
     */
    public java.lang.Object getAccessibleKeyBinding(int i);
}
