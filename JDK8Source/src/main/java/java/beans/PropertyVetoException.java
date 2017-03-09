/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2009, Oracle and/or its affiliates. All rights reserved.
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

package java.beans;


/**
 * A PropertyVetoException is thrown when a proposed change to a
 * property represents an unacceptable value.
 * <p>
 *  当对属性的建议更改表示不可接受的值时,抛出PropertyVetoException。
 * 
 */

public
class PropertyVetoException extends Exception {
    private static final long serialVersionUID = 129596057694162164L;

    /**
     * Constructs a <code>PropertyVetoException</code> with a
     * detailed message.
     *
     * <p>
     *  构造具有详细消息的<code> PropertyVetoException </code>。
     * 
     * 
     * @param mess Descriptive message
     * @param evt A PropertyChangeEvent describing the vetoed change.
     */
    public PropertyVetoException(String mess, PropertyChangeEvent evt) {
        super(mess);
        this.evt = evt;
    }

     /**
     * Gets the vetoed <code>PropertyChangeEvent</code>.
     *
     * <p>
     *  获取被否决的<code> PropertyChangeEvent </code>。
     * 
     * 
     * @return A PropertyChangeEvent describing the vetoed change.
     */
    public PropertyChangeEvent getPropertyChangeEvent() {
        return evt;
    }

    /**
     * A PropertyChangeEvent describing the vetoed change.
     * <p>
     *  描述被否决的更改的PropertyChangeEvent。
     * 
     * @serial
     */
    private PropertyChangeEvent evt;
}
