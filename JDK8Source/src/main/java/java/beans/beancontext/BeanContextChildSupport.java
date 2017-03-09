/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1998, 2013, Oracle and/or its affiliates. All rights reserved.
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

package java.beans.beancontext;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;

import java.beans.PropertyVetoException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <p>
 * This is a general support class to provide support for implementing the
 * BeanContextChild protocol.
 *
 * This class may either be directly subclassed, or encapsulated and delegated
 * to in order to implement this interface for a given component.
 * </p>
 *
 * <p>
 * <p>
 *  这是一个通用的支持类,为实现BeanContextChild协议提供支持。
 * 
 *  这个类可以直接子类化,或者封装和委托,以便为给定的组件实现这个接口。
 * </p>
 * 
 * 
 * @author      Laurence P. G. Cable
 * @since       1.2
 *
 * @see java.beans.beancontext.BeanContext
 * @see java.beans.beancontext.BeanContextServices
 * @see java.beans.beancontext.BeanContextChild
 */

public class BeanContextChildSupport implements BeanContextChild, BeanContextServicesListener, Serializable {

    static final long serialVersionUID = 6328947014421475877L;

    /**
     * construct a BeanContextChildSupport where this class has been
     * subclassed in order to implement the JavaBean component itself.
     * <p>
     *  构造BeanContextChildSupport,其中这个类已经被子类化以实现JavaBean组件本身。
     * 
     */

    public BeanContextChildSupport() {
        super();

        beanContextChildPeer = this;

        pcSupport = new PropertyChangeSupport(beanContextChildPeer);
        vcSupport = new VetoableChangeSupport(beanContextChildPeer);
    }

    /**
     * construct a BeanContextChildSupport where the JavaBean component
     * itself implements BeanContextChild, and encapsulates this, delegating
     * that interface to this implementation
     * <p>
     *  构造一个BeanContextChildSupport,其中JavaBean组件本身实现BeanContextChild,并封装这个,将该接口委托给这个实现
     * 
     * 
     * @param bcc the underlying bean context child
     */

    public BeanContextChildSupport(BeanContextChild bcc) {
        super();

        beanContextChildPeer = (bcc != null) ? bcc : this;

        pcSupport = new PropertyChangeSupport(beanContextChildPeer);
        vcSupport = new VetoableChangeSupport(beanContextChildPeer);
    }

    /**
     * Sets the <code>BeanContext</code> for
     * this <code>BeanContextChildSupport</code>.
     * <p>
     *  为<code> BeanContextChildSupport </code>设置<code> BeanContext </code>。
     * 
     * 
     * @param bc the new value to be assigned to the <code>BeanContext</code>
     * property
     * @throws PropertyVetoException if the change is rejected
     */
    public synchronized void setBeanContext(BeanContext bc) throws PropertyVetoException {
        if (bc == beanContext) return;

        BeanContext oldValue = beanContext;
        BeanContext newValue = bc;

        if (!rejectedSetBCOnce) {
            if (rejectedSetBCOnce = !validatePendingSetBeanContext(bc)) {
                throw new PropertyVetoException(
                    "setBeanContext() change rejected:",
                    new PropertyChangeEvent(beanContextChildPeer, "beanContext", oldValue, newValue)
                );
            }

            try {
                fireVetoableChange("beanContext",
                                   oldValue,
                                   newValue
                );
            } catch (PropertyVetoException pve) {
                rejectedSetBCOnce = true;

                throw pve; // re-throw
            }
        }

        if (beanContext != null) releaseBeanContextResources();

        beanContext       = newValue;
        rejectedSetBCOnce = false;

        firePropertyChange("beanContext",
                           oldValue,
                           newValue
        );

        if (beanContext != null) initializeBeanContextResources();
    }

    /**
     * Gets the nesting <code>BeanContext</code>
     * for this <code>BeanContextChildSupport</code>.
     * <p>
     *  为此<code> BeanContextChildSupport </code>获取嵌套<code> BeanContext </code>。
     * 
     * 
     * @return the nesting <code>BeanContext</code> for
     * this <code>BeanContextChildSupport</code>.
     */
    public synchronized BeanContext getBeanContext() { return beanContext; }

    /**
     * Add a PropertyChangeListener for a specific property.
     * The same listener object may be added more than once.  For each
     * property,  the listener will be invoked the number of times it was added
     * for that property.
     * If <code>name</code> or <code>pcl</code> is null, no exception is thrown
     * and no action is taken.
     *
     * <p>
     *  为特定属性添加PropertyChangeListener。可以多次添加相同的侦听器对象。对于每个属性,侦听器将调用它为该属性添加的次数。
     * 如果<code> name </code>或<code> pcl </code>为null,则不会抛出任何异常,也不会执行任何操作。
     * 
     * 
     * @param name The name of the property to listen on
     * @param pcl The <code>PropertyChangeListener</code> to be added
     */
    public void addPropertyChangeListener(String name, PropertyChangeListener pcl) {
        pcSupport.addPropertyChangeListener(name, pcl);
    }

    /**
     * Remove a PropertyChangeListener for a specific property.
     * If <code>pcl</code> was added more than once to the same event
     * source for the specified property, it will be notified one less time
     * after being removed.
     * If <code>name</code> is null, no exception is thrown
     * and no action is taken.
     * If <code>pcl</code> is null, or was never added for the specified
     * property, no exception is thrown and no action is taken.
     *
     * <p>
     * 删除特定属性的PropertyChangeListener。如果<code> pcl </code>被多次添加到指定属性的同一事件源中,则在删除后将少一次通知它。
     * 如果<code> name </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 如果<code> pcl </code>为空,或者从未为指定的属性添加,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param name The name of the property that was listened on
     * @param pcl The PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(String name, PropertyChangeListener pcl) {
        pcSupport.removePropertyChangeListener(name, pcl);
    }

    /**
     * Add a VetoableChangeListener for a specific property.
     * The same listener object may be added more than once.  For each
     * property,  the listener will be invoked the number of times it was added
     * for that property.
     * If <code>name</code> or <code>vcl</code> is null, no exception is thrown
     * and no action is taken.
     *
     * <p>
     *  为特定属性添加VetoableChangeListener。可以多次添加相同的侦听器对象。对于每个属性,侦听器将调用它为该属性添加的次数。
     * 如果<code> name </code>或<code> vcl </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param name The name of the property to listen on
     * @param vcl The <code>VetoableChangeListener</code> to be added
     */
    public void addVetoableChangeListener(String name, VetoableChangeListener vcl) {
        vcSupport.addVetoableChangeListener(name, vcl);
    }

    /**
     * Removes a <code>VetoableChangeListener</code>.
     * If <code>pcl</code> was added more than once to the same event
     * source for the specified property, it will be notified one less time
     * after being removed.
     * If <code>name</code> is null, no exception is thrown
     * and no action is taken.
     * If <code>vcl</code> is null, or was never added for the specified
     * property, no exception is thrown and no action is taken.
     *
     * <p>
     *  删除<code> VetoableChangeListener </code>。如果<code> pcl </code>被多次添加到指定属性的同一事件源中,则在删除后将少一次通知它。
     * 如果<code> name </code>为null,则不会抛出任何异常,并且不会执行任何操作。
     * 如果<code> vcl </code>为空,或从未为指定的属性添加,则不会抛出任何异常,并且不会执行任何操作。
     * 
     * 
     * @param name The name of the property that was listened on
     * @param vcl The <code>VetoableChangeListener</code> to be removed
     */
    public void removeVetoableChangeListener(String name, VetoableChangeListener vcl) {
        vcSupport.removeVetoableChangeListener(name, vcl);
    }

    /**
     * A service provided by the nesting BeanContext has been revoked.
     *
     * Subclasses may override this method in order to implement their own
     * behaviors.
     * <p>
     *  嵌套BeanContext提供的服务已撤消。
     * 
     *  子类可以重写这个方法,以实现自己的行为。
     * 
     * 
     * @param bcsre The <code>BeanContextServiceRevokedEvent</code> fired as a
     * result of a service being revoked
     */
    public void serviceRevoked(BeanContextServiceRevokedEvent bcsre) { }

    /**
     * A new service is available from the nesting BeanContext.
     *
     * Subclasses may override this method in order to implement their own
     * behaviors
     * <p>
     *  可以从嵌套BeanContext获得新的服务。
     * 
     *  子类可以重写这个方法,以实现自己的行为
     * 
     * 
     * @param bcsae The BeanContextServiceAvailableEvent fired as a
     * result of a service becoming available
     *
     */
    public void serviceAvailable(BeanContextServiceAvailableEvent bcsae) { }

    /**
     * Gets the <tt>BeanContextChild</tt> associated with this
     * <tt>BeanContextChildSupport</tt>.
     *
     * <p>
     *  获取与此<tt> BeanContextChildSupport </tt>关联的<tt> BeanContextChild </tt>。
     * 
     * 
     * @return the <tt>BeanContextChild</tt> peer of this class
     */
    public BeanContextChild getBeanContextChildPeer() { return beanContextChildPeer; }

    /**
     * Reports whether or not this class is a delegate of another.
     *
     * <p>
     * 报告此类是否是另一个的委托。
     * 
     * 
     * @return true if this class is a delegate of another
     */
    public boolean isDelegated() { return !this.equals(beanContextChildPeer); }

    /**
     * Report a bound property update to any registered listeners. No event is
     * fired if old and new are equal and non-null.
     * <p>
     *  向任何已注册的听众报告绑定的属性更新。如果old和new相等且非空,则不触发事件。
     * 
     * 
     * @param name The programmatic name of the property that was changed
     * @param oldValue  The old value of the property
     * @param newValue  The new value of the property
     */
    public void firePropertyChange(String name, Object oldValue, Object newValue) {
        pcSupport.firePropertyChange(name, oldValue, newValue);
    }

    /**
     * Report a vetoable property update to any registered listeners.
     * If anyone vetos the change, then fire a new event
     * reverting everyone to the old value and then rethrow
     * the PropertyVetoException. <P>
     *
     * No event is fired if old and new are equal and non-null.
     * <P>
     * <p>
     *  向任何注册的听众报告vetoable属性更新。如果任何人否决更改,则触发新事件将所有人恢复为旧值,然后重新抛出PropertyVetoException。 <P>
     * 
     *  如果old和new相等且非空,则不触发事件。
     * <P>
     * 
     * @param name The programmatic name of the property that is about to
     * change
     *
     * @param oldValue The old value of the property
     * @param newValue - The new value of the property
     *
     * @throws PropertyVetoException if the recipient wishes the property
     * change to be rolled back.
     */
    public void fireVetoableChange(String name, Object oldValue, Object newValue) throws PropertyVetoException {
        vcSupport.fireVetoableChange(name, oldValue, newValue);
    }

    /**
     * Called from setBeanContext to validate (or otherwise) the
     * pending change in the nesting BeanContext property value.
     * Returning false will cause setBeanContext to throw
     * PropertyVetoException.
     * <p>
     *  从setBeanContext调用以验证(或以其他方式)嵌套BeanContext属性值中的挂起更改。返回false将导致setBeanContext抛出PropertyVetoException。
     * 
     * 
     * @param newValue the new value that has been requested for
     *  the BeanContext property
     * @return <code>true</code> if the change operation is to be vetoed
     */
    public boolean validatePendingSetBeanContext(BeanContext newValue) {
        return true;
    }

    /**
     * This method may be overridden by subclasses to provide their own
     * release behaviors. When invoked any resources held by this instance
     * obtained from its current BeanContext property should be released
     * since the object is no longer nested within that BeanContext.
     * <p>
     *  此方法可能被子类覆盖以提供其自己的发布行为。当被调用时,由从当前BeanContext属性获得的该实例持有的任何资源应被释放,因为对象不再嵌套在该BeanContext中。
     * 
     */

    protected  void releaseBeanContextResources() {
        // do nothing
    }

    /**
     * This method may be overridden by subclasses to provide their own
     * initialization behaviors. When invoked any resources required by the
     * BeanContextChild should be obtained from the current BeanContext.
     * <p>
     *  这个方法可以被子类覆盖以提供它们自己的初始化行为。当被调用时,BeanContextChild所需的任何资源都应该从当前的BeanContext中获取。
     * 
     */

    protected void initializeBeanContextResources() {
        // do nothing
    }

    /**
     * Write the persistence state of the object.
     * <p>
     *  写入对象的持久状态。
     * 
     */

    private void writeObject(ObjectOutputStream oos) throws IOException {

        /*
         * don't serialize if we are delegated and the delegator is not also
         * serializable.
         * <p>
         *  不要序列化,如果我们被委派和委托者也不是可序列化。
         * 
         */

        if (!equals(beanContextChildPeer) && !(beanContextChildPeer instanceof Serializable))
            throw new IOException("BeanContextChildSupport beanContextChildPeer not Serializable");

        else
            oos.defaultWriteObject();

    }


    /**
     * Restore a persistent object, must wait for subsequent setBeanContext()
     * to fully restore any resources obtained from the new nesting
     * BeanContext
     * <p>
     *  恢复持久对象,必须等待后续的setBeanContext()完全恢复从新的嵌套BeanContext获得的任何资源
     * 
     */

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }

    /*
     * fields
     * <p>
     *  字段
     * 
     */

    /**
     * The <code>BeanContext</code> in which
     * this <code>BeanContextChild</code> is nested.
     * <p>
     *  <code> BeanContextChild </code>嵌套在<code> BeanContext </code>中。
     * 
     */
    public    BeanContextChild      beanContextChildPeer;

   /**
    * The <tt>PropertyChangeSupport</tt> associated with this
    * <tt>BeanContextChildSupport</tt>.
    * <p>
    * 与此<tt> BeanContextChildSupport </tt>关联的<tt> PropertyChangeSupport </tt>。
    * 
    */
    protected PropertyChangeSupport pcSupport;

   /**
    * The <tt>VetoableChangeSupport</tt> associated with this
    * <tt>BeanContextChildSupport</tt>.
    * <p>
    *  与此<tt> BeanContextChildSupport </tt>关联的<tt> VetoableChangeSupport </tt>。
    * 
    */
    protected VetoableChangeSupport vcSupport;

    /**
     * The bean context.
     * <p>
     *  bean上下文。
     * 
     */
    protected transient BeanContext           beanContext;

   /**
    * A flag indicating that there has been
    * at least one <code>PropertyChangeVetoException</code>
    * thrown for the attempted setBeanContext operation.
    * <p>
    *  指示尝试的setBeanContext操作抛出的至少一个<code> PropertyChangeVetoException </code>的标志。
    */
    protected transient boolean               rejectedSetBCOnce;

}
