/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2006, Oracle and/or its affiliates. All rights reserved.
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

package javax.swing.event;

import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeEvent;

import javax.swing.SwingUtilities;

/**
 * This subclass of {@code java.beans.PropertyChangeSupport} is almost
 * identical in functionality. The only difference is if constructed with
 * {@code SwingPropertyChangeSupport(sourceBean, true)} it ensures
 * listeners are only ever notified on the <i>Event Dispatch Thread</i>.
 *
 * <p>
 *  {@code java.beans.PropertyChangeSupport}的这个子类在功能上几乎相同。
 * 唯一的区别是如果使用{@code SwingPropertyChangeSupport(sourceBean,true)}构造,它确保监听器只会在<i>事件分派主题</i>上被通知。
 * 
 * 
 * @author Igor Kushnirskiy
 */

public final class SwingPropertyChangeSupport extends PropertyChangeSupport {

    /**
     * Constructs a SwingPropertyChangeSupport object.
     *
     * <p>
     *  构造SwingPropertyChangeSupport对象。
     * 
     * 
     * @param sourceBean  The bean to be given as the source for any
     *        events.
     * @throws NullPointerException if {@code sourceBean} is
     *         {@code null}
     */
    public SwingPropertyChangeSupport(Object sourceBean) {
        this(sourceBean, false);
    }

    /**
     * Constructs a SwingPropertyChangeSupport object.
     *
     * <p>
     *  构造SwingPropertyChangeSupport对象。
     * 
     * 
     * @param sourceBean the bean to be given as the source for any events
     * @param notifyOnEDT whether to notify listeners on the <i>Event
     *        Dispatch Thread</i> only
     *
     * @throws NullPointerException if {@code sourceBean} is
     *         {@code null}
     * @since 1.6
     */
    public SwingPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT) {
        super(sourceBean);
        this.notifyOnEDT = notifyOnEDT;
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * If {@link #isNotifyOnEDT} is {@code true} and called off the
     * <i>Event Dispatch Thread</i> this implementation uses
     * {@code SwingUtilities.invokeLater} to send out the notification
     * on the <i>Event Dispatch Thread</i>. This ensures  listeners
     * are only ever notified on the <i>Event Dispatch Thread</i>.
     *
     * <p>
     *  {@inheritDoc}
     * 
     * <p>
     *  如果{@link #isNotifyOnEDT}为{@code true},并且调用了<i>事件分派主题</i>,此实施使用{@code SwingUtilities.invokeLater}发送<i>
     * 事件分派主题</i>。
     * 这样可确保只有在<i>事件分派主题</i>上才会通知侦听器。
     * 
     * 
     * @throws NullPointerException if {@code evt} is
     *         {@code null}
     * @since 1.6
     */
    public void firePropertyChange(final PropertyChangeEvent evt) {
        if (evt == null) {
            throw new NullPointerException();
        }
        if (! isNotifyOnEDT()
            || SwingUtilities.isEventDispatchThread()) {
            super.firePropertyChange(evt);
        } else {
            SwingUtilities.invokeLater(
                new Runnable() {
                    public void run() {
                        firePropertyChange(evt);
                    }
                });
        }
    }

    /**
     * Returns {@code notifyOnEDT} property.
     *
     * <p>
     * 
     * @return {@code notifyOnEDT} property
     * @see #SwingPropertyChangeSupport(Object sourceBean, boolean notifyOnEDT)
     * @since 1.6
     */
    public final boolean isNotifyOnEDT() {
        return notifyOnEDT;
    }

    // Serialization version ID
    static final long serialVersionUID = 7162625831330845068L;

    /**
     * whether to notify listeners on EDT
     *
     * <p>
     *  返回{@code notifyOnEDT}属性。
     * 
     * 
     * @serial
     * @since 1.6
     */
    private final boolean notifyOnEDT;
}
