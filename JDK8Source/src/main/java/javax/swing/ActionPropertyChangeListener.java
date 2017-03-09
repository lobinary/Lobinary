/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1999, 2011, Oracle and/or its affiliates. All rights reserved.
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
package javax.swing;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;

/**
 * A package-private PropertyChangeListener which listens for
 * property changes on an Action and updates the properties
 * of an ActionEvent source.
 * <p>
 * Subclasses must override the actionPropertyChanged method,
 * which is invoked from the propertyChange method as long as
 * the target is still valid.
 * </p>
 * <p>
 * WARNING WARNING WARNING WARNING WARNING WARNING:<br>
 * Do NOT create an annonymous inner class that extends this!  If you do
 * a strong reference will be held to the containing class, which in most
 * cases defeats the purpose of this class.
 *
 * <p>
 *  一个package-private PropertyChangeListener,用于监听Action上的属性更改,并更新ActionEvent源的属性。
 * <p>
 *  子类必须重写actionPropertyChanged方法,该方法从propertyChange方法调用,只要目标仍然有效。
 * </p>
 * <p>
 *  警告警告警告警告警告警告：<br>不要创建一个扩展这个的匿名内部类！如果你做一个强引用将被持有到包含的类,这在大多数情况下失败这个类的目的。
 * 
 * 
 * @param T the type of JComponent the underlying Action is attached to
 *
 * @author Georges Saab
 * @see AbstractButton
 */
abstract class ActionPropertyChangeListener<T extends JComponent>
        implements PropertyChangeListener, Serializable {
    private static ReferenceQueue<JComponent> queue;

    // WeakReference's aren't serializable.
    private transient OwnedWeakReference<T> target;
    // The Component's that reference an Action do so through a strong
    // reference, so that there is no need to check for serialized.
    private Action action;

    private static ReferenceQueue<JComponent> getQueue() {
        synchronized(ActionPropertyChangeListener.class) {
            if (queue == null) {
                queue = new ReferenceQueue<JComponent>();
            }
        }
        return queue;
    }

    public ActionPropertyChangeListener(T c, Action a) {
        super();
        setTarget(c);
        this.action = a;
    }

    /**
     * PropertyChangeListener method.  If the target has been gc'ed this
     * will remove the <code>PropertyChangeListener</code> from the Action,
     * otherwise this will invoke actionPropertyChanged.
     * <p>
     */
    public final void propertyChange(PropertyChangeEvent e) {
        T target = getTarget();
        if (target == null) {
            getAction().removePropertyChangeListener(this);
        } else {
            actionPropertyChanged(target, getAction(), e);
        }
    }

    /**
     * Invoked when a property changes on the Action and the target
     * still exists.
     * <p>
     *  PropertyChangeListener方法。
     * 如果目标已经gc'ed这将从Action中删除<code> PropertyChangeListener </code>,否则将调用actionPropertyChanged。
     * 
     */
    protected abstract void actionPropertyChanged(T target, Action action,
                                                  PropertyChangeEvent e);

    private void setTarget(T c) {
        ReferenceQueue<JComponent> queue = getQueue();
        // Check to see whether any old buttons have
        // been enqueued for GC.  If so, look up their
        // PCL instance and remove it from its Action.
        OwnedWeakReference<?> r;
        while ((r = (OwnedWeakReference)queue.poll()) != null) {
            ActionPropertyChangeListener<?> oldPCL = r.getOwner();
            Action oldAction = oldPCL.getAction();
            if (oldAction!=null) {
                oldAction.removePropertyChangeListener(oldPCL);
            }
        }
        this.target = new OwnedWeakReference<T>(c, queue, this);
    }

    public T getTarget() {
        if (target == null) {
            // Will only happen if serialized and real target was null
            return null;
        }
        return this.target.get();
    }

    public Action getAction() {
          return action;
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(getTarget());
    }

    @SuppressWarnings("unchecked")
    private void readObject(ObjectInputStream s)
                     throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        T target = (T)s.readObject();
        if (target != null) {
            setTarget(target);
        }
    }


    private static class OwnedWeakReference<U extends JComponent> extends
                              WeakReference<U> {
        private ActionPropertyChangeListener<?> owner;

        OwnedWeakReference(U target, ReferenceQueue<? super U> queue,
                           ActionPropertyChangeListener<?> owner) {
            super(target, queue);
            this.owner = owner;
        }

        public ActionPropertyChangeListener<?> getOwner() {
            return owner;
        }
    }
}
