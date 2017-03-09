/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
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
 * A "PropertyChange" event gets fired whenever a bean changes a "bound"
 * property.  You can register a PropertyChangeListener with a source
 * bean so as to be notified of any bound property updates.
 * <p>
 *  每当bean更改"bound"属性时,将触发"PropertyChange"事件。您可以向源bean注册PropertyChangeListener,以便通知任何绑定的属性更新。
 * 
 */

public interface PropertyChangeListener extends java.util.EventListener {

    /**
     * This method gets called when a bound property is changed.
     * <p>
     *  当绑定属性更改时,将调用此方法。
     * 
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */

    void propertyChange(PropertyChangeEvent evt);

}
