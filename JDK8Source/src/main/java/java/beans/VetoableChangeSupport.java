/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.io.Serializable;
import java.io.ObjectStreamField;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map.Entry;

/**
 * This is a utility class that can be used by beans that support constrained
 * properties.  It manages a list of listeners and dispatches
 * {@link PropertyChangeEvent}s to them.  You can use an instance of this class
 * as a member field of your bean and delegate these types of work to it.
 * The {@link VetoableChangeListener} can be registered for all properties
 * or for a property specified by name.
 * <p>
 * Here is an example of {@code VetoableChangeSupport} usage that follows
 * the rules and recommendations laid out in the JavaBeans&trade; specification:
 * <pre>{@code
 * public class MyBean {
 *     private final VetoableChangeSupport vcs = new VetoableChangeSupport(this);
 *
 *     public void addVetoableChangeListener(VetoableChangeListener listener) {
 *         this.vcs.addVetoableChangeListener(listener);
 *     }
 *
 *     public void removeVetoableChangeListener(VetoableChangeListener listener) {
 *         this.vcs.removeVetoableChangeListener(listener);
 *     }
 *
 *     private String value;
 *
 *     public String getValue() {
 *         return this.value;
 *     }
 *
 *     public void setValue(String newValue) throws PropertyVetoException {
 *         String oldValue = this.value;
 *         this.vcs.fireVetoableChange("value", oldValue, newValue);
 *         this.value = newValue;
 *     }
 *
 *     [...]
 * }
 * }</pre>
 * <p>
 * A {@code VetoableChangeSupport} instance is thread-safe.
 * <p>
 * This class is serializable.  When it is serialized it will save
 * (and restore) any listeners that are themselves serializable.  Any
 * non-serializable listeners will be skipped during serialization.
 *
 * <p>
 *  这是一个可以由支持约束属性的bean使用的实用程序类。它管理一个监听器列表,并向它们发送{@link PropertyChangeEvent}。
 * 您可以使用此类的实例作为您的bean的成员字段,并将这些类型的工作委托给它。可以为所有属性或由名称指定的属性注册{@link VetoableChangeListener}。
 * <p>
 *  这里是一个{@code VetoableChangeSupport}使用的例子,遵循JavaBeans&trade中的规则和建议;规范：<pre> {@ code public class MyBean {private final VetoableChangeSupport vcs = new VetoableChangeSupport(this);。
 * 
 *  public void addVetoableChangeListener(VetoableChangeListener listener){this.vcs.addVetoableChangeListener(listener); }
 * }。
 * 
 *  public void removeVetoableChangeListener(VetoableChangeListener listener){this.vcs.removeVetoableChangeListener(listener); }
 * }。
 * 
 *  private String value;
 * 
 *  public String getValue(){return this.value; }}
 * 
 *  public void setValue(String newValue)throws PropertyVetoException {String oldValue = this.value; this.vcs.fireVetoableChange("value",oldValue,newValue); this.value = newValue; }
 * }。
 * 
 *  [...]}} </pre>
 * <p>
 *  {@code VetoableChangeSupport}实例是线程安全的。
 * <p>
 * 这个类是可序列化的。当它被序列化时,它将保存(和恢复)任何本身可序列化的监听器。在序列化期间将跳过任何不可序列化的侦听器。
 * 
 * 
 * @see PropertyChangeSupport
 */
public class VetoableChangeSupport implements Serializable {
    private VetoableChangeListenerMap map = new VetoableChangeListenerMap();

    /**
     * Constructs a <code>VetoableChangeSupport</code> object.
     *
     * <p>
     *  构造一个<code> VetoableChangeSupport </code>对象。
     * 
     * 
     * @param sourceBean  The bean to be given as the source for any events.
     */
    public VetoableChangeSupport(Object sourceBean) {
        if (sourceBean == null) {
            throw new NullPointerException();
        }
        source = sourceBean;
    }

    /**
     * Add a VetoableChangeListener to the listener list.
     * The listener is registered for all properties.
     * The same listener object may be added more than once, and will be called
     * as many times as it is added.
     * If <code>listener</code> is null, no exception is thrown and no action
     * is taken.
     *
     * <p>
     *  将VetoableChangeListener添加到侦听器列表。侦听器为所有属性注册。相同的侦听器对象可以多次添加,并且将被调用的次数与添加的次数相同。
     * 如果<code> listener </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param listener  The VetoableChangeListener to be added
     */
    public void addVetoableChangeListener(VetoableChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (listener instanceof VetoableChangeListenerProxy) {
            VetoableChangeListenerProxy proxy =
                    (VetoableChangeListenerProxy)listener;
            // Call two argument add method.
            addVetoableChangeListener(proxy.getPropertyName(),
                                      proxy.getListener());
        } else {
            this.map.add(null, listener);
        }
    }

    /**
     * Remove a VetoableChangeListener from the listener list.
     * This removes a VetoableChangeListener that was registered
     * for all properties.
     * If <code>listener</code> was added more than once to the same event
     * source, it will be notified one less time after being removed.
     * If <code>listener</code> is null, or was never added, no exception is
     * thrown and no action is taken.
     *
     * <p>
     *  从侦听器列表中删除VetoableChangeListener。这将删除为所有属性注册的VetoableChangeListener。
     * 如果<code> listener </code>被多次添加到同一个事件源,它会在删除后少一点时间通知。
     * 如果<code> listener </code>为空,或从未添加,则不抛出任何异常,并且不执行任何操作。
     * 
     * 
     * @param listener  The VetoableChangeListener to be removed
     */
    public void removeVetoableChangeListener(VetoableChangeListener listener) {
        if (listener == null) {
            return;
        }
        if (listener instanceof VetoableChangeListenerProxy) {
            VetoableChangeListenerProxy proxy =
                    (VetoableChangeListenerProxy)listener;
            // Call two argument remove method.
            removeVetoableChangeListener(proxy.getPropertyName(),
                                         proxy.getListener());
        } else {
            this.map.remove(null, listener);
        }
    }

    /**
     * Returns an array of all the listeners that were added to the
     * VetoableChangeSupport object with addVetoableChangeListener().
     * <p>
     * If some listeners have been added with a named property, then
     * the returned array will be a mixture of VetoableChangeListeners
     * and <code>VetoableChangeListenerProxy</code>s. If the calling
     * method is interested in distinguishing the listeners then it must
     * test each element to see if it's a
     * <code>VetoableChangeListenerProxy</code>, perform the cast, and examine
     * the parameter.
     *
     * <pre>{@code
     * VetoableChangeListener[] listeners = bean.getVetoableChangeListeners();
     * for (int i = 0; i < listeners.length; i++) {
     *        if (listeners[i] instanceof VetoableChangeListenerProxy) {
     *     VetoableChangeListenerProxy proxy =
     *                    (VetoableChangeListenerProxy)listeners[i];
     *     if (proxy.getPropertyName().equals("foo")) {
     *       // proxy is a VetoableChangeListener which was associated
     *       // with the property named "foo"
     *     }
     *   }
     * }
     * }</pre>
     *
     * <p>
     *  返回使用addVetoableChangeListener()添加到VetoableChangeSupport对象的所有侦听器的数组。
     * <p>
     *  如果一些侦听器已经添加了命名属性,则返回的数组将是VetoableChangeListeners和<code> VetoableChangeListenerProxy </code>的混合。
     * 如果调用方法有兴趣区分侦听器,那么它必须测试每个元素以查看它是否为<code> VetoableChangeListenerProxy </code>,执行转换并检查参数。
     * 
     * <pre> {@ code VetoableChangeListener [] listeners = bean.getVetoableChangeListeners(); for(int i = 0; i <listeners.length; i ++){if(listeners [i] instanceof VetoableChangeListenerProxy){VetoableChangeListenerProxy proxy =(VetoableChangeListenerProxy)listeners [i]; if(proxy.getPropertyName()。
     * equals("foo")){//代理是一个VetoableChangeListener,它与名为"foo"的属性相关联}}}} </pre>。
     * 
     * 
     * @see VetoableChangeListenerProxy
     * @return all of the <code>VetoableChangeListeners</code> added or an
     *         empty array if no listeners have been added
     * @since 1.4
     */
    public VetoableChangeListener[] getVetoableChangeListeners(){
        return this.map.getListeners();
    }

    /**
     * Add a VetoableChangeListener for a specific property.  The listener
     * will be invoked only when a call on fireVetoableChange names that
     * specific property.
     * The same listener object may be added more than once.  For each
     * property,  the listener will be invoked the number of times it was added
     * for that property.
     * If <code>propertyName</code> or <code>listener</code> is null, no
     * exception is thrown and no action is taken.
     *
     * <p>
     *  为特定属性添加VetoableChangeListener。侦听器将仅在fireVetoableChange上的调用命名该特定属性时调用。可以多次添加相同的侦听器对象。
     * 对于每个属性,侦听器将调用它为该属性添加的次数。
     * 如果<code> propertyName </code>或<code> listener </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param propertyName  The name of the property to listen on.
     * @param listener  The VetoableChangeListener to be added
     */
    public void addVetoableChangeListener(
                                String propertyName,
                VetoableChangeListener listener) {
        if (listener == null || propertyName == null) {
            return;
        }
        listener = this.map.extract(listener);
        if (listener != null) {
            this.map.add(propertyName, listener);
        }
    }

    /**
     * Remove a VetoableChangeListener for a specific property.
     * If <code>listener</code> was added more than once to the same event
     * source for the specified property, it will be notified one less time
     * after being removed.
     * If <code>propertyName</code> is null, no exception is thrown and no
     * action is taken.
     * If <code>listener</code> is null, or was never added for the specified
     * property, no exception is thrown and no action is taken.
     *
     * <p>
     *  删除特定属性的VetoableChangeListener。如果<code> listener </code>被多次添加到指定属性的同一个事件源中,则在删除后将少一点时间通知它。
     * 如果<code> propertyName </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 如果<code> listener </code>为null,或者从未为指定的属性添加,则不会抛出任何异常,因此不会执行任何操作。
     * 
     * 
     * @param propertyName  The name of the property that was listened on.
     * @param listener  The VetoableChangeListener to be removed
     */
    public void removeVetoableChangeListener(
                                String propertyName,
                VetoableChangeListener listener) {
        if (listener == null || propertyName == null) {
            return;
        }
        listener = this.map.extract(listener);
        if (listener != null) {
            this.map.remove(propertyName, listener);
        }
    }

    /**
     * Returns an array of all the listeners which have been associated
     * with the named property.
     *
     * <p>
     *  返回与命名属性关联的所有侦听器的数组。
     * 
     * 
     * @param propertyName  The name of the property being listened to
     * @return all the <code>VetoableChangeListeners</code> associated with
     *         the named property.  If no such listeners have been added,
     *         or if <code>propertyName</code> is null, an empty array is
     *         returned.
     * @since 1.4
     */
    public VetoableChangeListener[] getVetoableChangeListeners(String propertyName) {
        return this.map.getListeners(propertyName);
    }

    /**
     * Reports a constrained property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * Any listener can throw a {@code PropertyVetoException} to veto the update.
     * If one of the listeners vetoes the update, this method passes
     * a new "undo" {@code PropertyChangeEvent} that reverts to the old value
     * to all listeners that already confirmed this update
     * and throws the {@code PropertyVetoException} again.
     * <p>
     * No event is fired if old and new values are equal and non-null.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #fireVetoableChange(PropertyChangeEvent)} method.
     *
     * <p>
     * 将已约束的属性更新报告给已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器。
     * <p>
     *  任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * 如果其中一个监听器否决了更新,这个方法传递一个新的"撤销"{@code PropertyChangeEvent},它将恢复为旧的值给所有已经确认此更新的监听器,并再次抛出{@code PropertyVetoException}
     * 。
     *  任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * <p>
     *  如果旧值和新值相等且非空,则不触发事件。
     * <p>
     *  这只是一个更通用的{@link #fireVetoableChange(PropertyChangeEvent)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that is about to change
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     * @throws PropertyVetoException if one of listeners vetoes the property update
     */
    public void fireVetoableChange(String propertyName, Object oldValue, Object newValue)
            throws PropertyVetoException {
        if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
            fireVetoableChange(new PropertyChangeEvent(this.source, propertyName, oldValue, newValue));
        }
    }

    /**
     * Reports an integer constrained property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * Any listener can throw a {@code PropertyVetoException} to veto the update.
     * If one of the listeners vetoes the update, this method passes
     * a new "undo" {@code PropertyChangeEvent} that reverts to the old value
     * to all listeners that already confirmed this update
     * and throws the {@code PropertyVetoException} again.
     * <p>
     * No event is fired if old and new values are equal.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #fireVetoableChange(String, Object, Object)} method.
     *
     * <p>
     *  向已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器报告整数受限属性更新。
     * <p>
     *  任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * 如果其中一个监听器否决了更新,这个方法传递一个新的"撤销"{@code PropertyChangeEvent},它将恢复为旧的值给所有已经确认此更新的监听器,并再次抛出{@code PropertyVetoException}
     * 。
     *  任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * <p>
     *  如果旧值和新值相等,则不会触发事件。
     * <p>
     *  这只是一个更通用的{@link #fireVetoableChange(String,Object,Object)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that is about to change
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     * @throws PropertyVetoException if one of listeners vetoes the property update
     */
    public void fireVetoableChange(String propertyName, int oldValue, int newValue)
            throws PropertyVetoException {
        if (oldValue != newValue) {
            fireVetoableChange(propertyName, Integer.valueOf(oldValue), Integer.valueOf(newValue));
        }
    }

    /**
     * Reports a boolean constrained property update to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * Any listener can throw a {@code PropertyVetoException} to veto the update.
     * If one of the listeners vetoes the update, this method passes
     * a new "undo" {@code PropertyChangeEvent} that reverts to the old value
     * to all listeners that already confirmed this update
     * and throws the {@code PropertyVetoException} again.
     * <p>
     * No event is fired if old and new values are equal.
     * <p>
     * This is merely a convenience wrapper around the more general
     * {@link #fireVetoableChange(String, Object, Object)} method.
     *
     * <p>
     *  向已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器报告布尔约束属性更新。
     * <p>
     * 任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * 如果其中一个监听器否决了更新,这个方法传递一个新的"撤销"{@code PropertyChangeEvent},它将恢复为旧的值给所有已经确认此更新的监听器,并再次抛出{@code PropertyVetoException}
     * 。
     * 任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * <p>
     *  如果旧值和新值相等,则不会触发事件。
     * <p>
     *  这只是一个更通用的{@link #fireVetoableChange(String,Object,Object)}方法的便利包装。
     * 
     * 
     * @param propertyName  the programmatic name of the property that is about to change
     * @param oldValue      the old value of the property
     * @param newValue      the new value of the property
     * @throws PropertyVetoException if one of listeners vetoes the property update
     */
    public void fireVetoableChange(String propertyName, boolean oldValue, boolean newValue)
            throws PropertyVetoException {
        if (oldValue != newValue) {
            fireVetoableChange(propertyName, Boolean.valueOf(oldValue), Boolean.valueOf(newValue));
        }
    }

    /**
     * Fires a property change event to listeners
     * that have been registered to track updates of
     * all properties or a property with the specified name.
     * <p>
     * Any listener can throw a {@code PropertyVetoException} to veto the update.
     * If one of the listeners vetoes the update, this method passes
     * a new "undo" {@code PropertyChangeEvent} that reverts to the old value
     * to all listeners that already confirmed this update
     * and throws the {@code PropertyVetoException} again.
     * <p>
     * No event is fired if the given event's old and new values are equal and non-null.
     *
     * <p>
     *  对已注册以跟踪所有属性或具有指定名称的属性的更新的侦听器触发属性更改事件。
     * <p>
     *  任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * 如果其中一个监听器否决了更新,这个方法传递一个新的"撤销"{@code PropertyChangeEvent},它将恢复为旧的值给所有已经确认此更新的监听器,并再次抛出{@code PropertyVetoException}
     * 。
     *  任何侦听器都可以抛出一个{@code PropertyVetoException}来否决更新。
     * <p>
     *  如果给定事件的旧值和新值相等且非空,则不会触发任何事件。
     * 
     * 
     * @param event  the {@code PropertyChangeEvent} to be fired
     * @throws PropertyVetoException if one of listeners vetoes the property update
     */
    public void fireVetoableChange(PropertyChangeEvent event)
            throws PropertyVetoException {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
        if (oldValue == null || newValue == null || !oldValue.equals(newValue)) {
            String name = event.getPropertyName();

            VetoableChangeListener[] common = this.map.get(null);
            VetoableChangeListener[] named = (name != null)
                        ? this.map.get(name)
                        : null;

            VetoableChangeListener[] listeners;
            if (common == null) {
                listeners = named;
            }
            else if (named == null) {
                listeners = common;
            }
            else {
                listeners = new VetoableChangeListener[common.length + named.length];
                System.arraycopy(common, 0, listeners, 0, common.length);
                System.arraycopy(named, 0, listeners, common.length, named.length);
            }
            if (listeners != null) {
                int current = 0;
                try {
                    while (current < listeners.length) {
                        listeners[current].vetoableChange(event);
                        current++;
                    }
                }
                catch (PropertyVetoException veto) {
                    event = new PropertyChangeEvent(this.source, name, newValue, oldValue);
                    for (int i = 0; i < current; i++) {
                        try {
                            listeners[i].vetoableChange(event);
                        }
                        catch (PropertyVetoException exception) {
                            // ignore exceptions that occur during rolling back
                        }
                    }
                    throw veto; // rethrow the veto exception
                }
            }
        }
    }

    /**
     * Check if there are any listeners for a specific property, including
     * those registered on all properties.  If <code>propertyName</code>
     * is null, only check for listeners registered on all properties.
     *
     * <p>
     *  检查是否有特定属性的任何侦听器,包括在所有属性上注册的侦听器。如果<code> propertyName </code>为null,则只检查在所有属性上注册的侦听器。
     * 
     * 
     * @param propertyName  the property name.
     * @return true if there are one or more listeners for the given property
     */
    public boolean hasListeners(String propertyName) {
        return this.map.hasListeners(propertyName);
    }

    /**
    /* <p>
    /* 
     * @serialData Null terminated list of <code>VetoableChangeListeners</code>.
     * <p>
     * At serialization time we skip non-serializable listeners and
     * only serialize the serializable listeners.
     */
    private void writeObject(ObjectOutputStream s) throws IOException {
        Hashtable<String, VetoableChangeSupport> children = null;
        VetoableChangeListener[] listeners = null;
        synchronized (this.map) {
            for (Entry<String, VetoableChangeListener[]> entry : this.map.getEntries()) {
                String property = entry.getKey();
                if (property == null) {
                    listeners = entry.getValue();
                } else {
                    if (children == null) {
                        children = new Hashtable<>();
                    }
                    VetoableChangeSupport vcs = new VetoableChangeSupport(this.source);
                    vcs.map.set(null, entry.getValue());
                    children.put(property, vcs);
                }
            }
        }
        ObjectOutputStream.PutField fields = s.putFields();
        fields.put("children", children);
        fields.put("source", this.source);
        fields.put("vetoableChangeSupportSerializedDataVersion", 2);
        s.writeFields();

        if (listeners != null) {
            for (VetoableChangeListener l : listeners) {
                if (l instanceof Serializable) {
                    s.writeObject(l);
                }
            }
        }
        s.writeObject(null);
    }

    private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
        this.map = new VetoableChangeListenerMap();

        ObjectInputStream.GetField fields = s.readFields();

        @SuppressWarnings("unchecked")
        Hashtable<String, VetoableChangeSupport> children = (Hashtable<String, VetoableChangeSupport>)fields.get("children", null);
        this.source = fields.get("source", null);
        fields.get("vetoableChangeSupportSerializedDataVersion", 2);

        Object listenerOrNull;
        while (null != (listenerOrNull = s.readObject())) {
            this.map.add(null, (VetoableChangeListener)listenerOrNull);
        }
        if (children != null) {
            for (Entry<String, VetoableChangeSupport> entry : children.entrySet()) {
                for (VetoableChangeListener listener : entry.getValue().getVetoableChangeListeners()) {
                    this.map.add(entry.getKey(), listener);
                }
            }
        }
    }

    /**
     * The object to be provided as the "source" for any generated events.
     * <p>
     *  要作为任何生成的事件的"源"提供的对象。
     * 
     */
    private Object source;

    /**
    /* <p>
    /* 
     * @serialField children                                   Hashtable
     * @serialField source                                     Object
     * @serialField vetoableChangeSupportSerializedDataVersion int
     */
    private static final ObjectStreamField[] serialPersistentFields = {
            new ObjectStreamField("children", Hashtable.class),
            new ObjectStreamField("source", Object.class),
            new ObjectStreamField("vetoableChangeSupportSerializedDataVersion", Integer.TYPE)
    };

    /**
     * Serialization version ID, so we're compatible with JDK 1.1
     * <p>
     *  序列化版本ID,因此我们与JDK 1.1兼容
     * 
     */
    static final long serialVersionUID = -5090210921595982017L;

    /**
     * This is a {@link ChangeListenerMap ChangeListenerMap} implementation
     * that works with {@link VetoableChangeListener VetoableChangeListener} objects.
     * <p>
     * 这是一个与{@link VetoableChangeListener VetoableChangeListener}对象一起使用的{@link ChangeListenerMap ChangeListenerMap}
     * 实现。
     * 
     */
    private static final class VetoableChangeListenerMap extends ChangeListenerMap<VetoableChangeListener> {
        private static final VetoableChangeListener[] EMPTY = {};

        /**
         * Creates an array of {@link VetoableChangeListener VetoableChangeListener} objects.
         * This method uses the same instance of the empty array
         * when {@code length} equals {@code 0}.
         *
         * <p>
         *  创建一个{@link VetoableChangeListener VetoableChangeListener}对象的数组。
         * 当{@code length}等于{@code 0}时,此方法使用空数组的相同实例。
         * 
         * 
         * @param length  the array length
         * @return        an array with specified length
         */
        @Override
        protected VetoableChangeListener[] newArray(int length) {
            return (0 < length)
                    ? new VetoableChangeListener[length]
                    : EMPTY;
        }

        /**
         * Creates a {@link VetoableChangeListenerProxy VetoableChangeListenerProxy}
         * object for the specified property.
         *
         * <p>
         *  为指定的属性创建{@link VetoableChangeListenerProxy VetoableChangeListenerProxy}对象。
         * 
         * 
         * @param name      the name of the property to listen on
         * @param listener  the listener to process events
         * @return          a {@code VetoableChangeListenerProxy} object
         */
        @Override
        protected VetoableChangeListener newProxy(String name, VetoableChangeListener listener) {
            return new VetoableChangeListenerProxy(name, listener);
        }

        /**
         * {@inheritDoc}
         * <p>
         *  {@inheritDoc}
         */
        public final VetoableChangeListener extract(VetoableChangeListener listener) {
            while (listener instanceof VetoableChangeListenerProxy) {
                listener = ((VetoableChangeListenerProxy) listener).getListener();
            }
            return listener;
        }
    }
}
