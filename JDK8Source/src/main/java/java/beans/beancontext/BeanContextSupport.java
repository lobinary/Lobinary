/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1997, 2013, Oracle and/or its affiliates. All rights reserved.
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

import java.awt.Component;
import java.awt.Container;

import java.beans.Beans;
import java.beans.AppletInitializer;

import java.beans.DesignMode;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.beans.PropertyVetoException;

import java.beans.Visibility;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;


/**
 * This helper class provides a utility implementation of the
 * java.beans.beancontext.BeanContext interface.
 * <p>
 * Since this class directly implements the BeanContext interface, the class
 * can, and is intended to be used either by subclassing this implementation,
 * or via ad-hoc delegation of an instance of this class from another.
 * </p>
 *
 * <p>
 *  此助手类提供了java.beans.beancontext.BeanContext接口的实用程序实现。
 * <p>
 *  因为这个类直接实现了BeanContext接口,所以该类可以,也可以通过子类化这个实现,或者通过从另一个实例委托这个类的实例来使用。
 * </p>
 * 
 * 
 * @author Laurence P. G. Cable
 * @since 1.2
 */
public class      BeanContextSupport extends BeanContextChildSupport
       implements BeanContext,
                  Serializable,
                  PropertyChangeListener,
                  VetoableChangeListener {

    // Fix for bug 4282900 to pass JCK regression test
    static final long serialVersionUID = -4879613978649577204L;

    /**
     *
     * Construct a BeanContextSupport instance
     *
     *
     * <p>
     *  构造BeanContextSupport实例
     * 
     * 
     * @param peer      The peer <tt>BeanContext</tt> we are
     *                  supplying an implementation for,
     *                  or <tt>null</tt>
     *                  if this object is its own peer
     * @param lcle      The current Locale for this BeanContext. If
     *                  <tt>lcle</tt> is <tt>null</tt>, the default locale
     *                  is assigned to the <tt>BeanContext</tt> instance.
     * @param dTime     The initial state,
     *                  <tt>true</tt> if in design mode,
     *                  <tt>false</tt> if runtime.
     * @param visible   The initial visibility.
     * @see java.util.Locale#getDefault()
     * @see java.util.Locale#setDefault(java.util.Locale)
     */
    public BeanContextSupport(BeanContext peer, Locale lcle, boolean dTime, boolean visible) {
        super(peer);

        locale          = lcle != null ? lcle : Locale.getDefault();
        designTime      = dTime;
        okToUseGui      = visible;

        initialize();
    }

    /**
     * Create an instance using the specified Locale and design mode.
     *
     * <p>
     *  使用指定的区域设置和设计模式创建实例。
     * 
     * 
     * @param peer      The peer <tt>BeanContext</tt> we
     *                  are supplying an implementation for,
     *                  or <tt>null</tt> if this object is its own peer
     * @param lcle      The current Locale for this <tt>BeanContext</tt>. If
     *                  <tt>lcle</tt> is <tt>null</tt>, the default locale
     *                  is assigned to the <tt>BeanContext</tt> instance.
     * @param dtime     The initial state, <tt>true</tt>
     *                  if in design mode,
     *                  <tt>false</tt> if runtime.
     * @see java.util.Locale#getDefault()
     * @see java.util.Locale#setDefault(java.util.Locale)
     */
    public BeanContextSupport(BeanContext peer, Locale lcle, boolean dtime) {
        this (peer, lcle, dtime, true);
    }

    /**
     * Create an instance using the specified locale
     *
     * <p>
     *  使用指定的区域设置创建实例
     * 
     * 
     * @param peer      The peer BeanContext we are
     *                  supplying an implementation for,
     *                  or <tt>null</tt> if this object
     *                  is its own peer
     * @param lcle      The current Locale for this
     *                  <tt>BeanContext</tt>. If
     *                  <tt>lcle</tt> is <tt>null</tt>,
     *                  the default locale
     *                  is assigned to the <tt>BeanContext</tt>
     *                  instance.
     * @see java.util.Locale#getDefault()
     * @see java.util.Locale#setDefault(java.util.Locale)
     */
    public BeanContextSupport(BeanContext peer, Locale lcle) {
        this (peer, lcle, false, true);
    }

    /**
     * Create an instance using with a default locale
     *
     * <p>
     *  使用默认语言环境创建实例
     * 
     * 
     * @param peer      The peer <tt>BeanContext</tt> we are
     *                  supplying an implementation for,
     *                  or <tt>null</tt> if this object
     *                  is its own peer
     */
    public BeanContextSupport(BeanContext peer) {
        this (peer, null, false, true);
    }

    /**
     * Create an instance that is not a delegate of another object
     * <p>
     *  创建不是另一个对象的委托的实例
     * 
     */

    public BeanContextSupport() {
        this (null, null, false, true);
    }

    /**
     * Gets the instance of <tt>BeanContext</tt> that
     * this object is providing the implementation for.
     * <p>
     *  获取此对象提供的<tt> BeanContext </tt>实例。
     * 
     * 
     * @return the BeanContext instance
     */
    public BeanContext getBeanContextPeer() { return (BeanContext)getBeanContextChildPeer(); }

    /**
     * <p>
     * The instantiateChild method is a convenience hook
     * in BeanContext to simplify
     * the task of instantiating a Bean, nested,
     * into a <tt>BeanContext</tt>.
     * </p>
     * <p>
     * The semantics of the beanName parameter are defined by java.beans.Beans.instantiate.
     * </p>
     *
     * <p>
     * <p>
     *  instantiateChild方法是BeanContext中的一个方便的挂钩,以简化实例化Bean的任务,嵌套到<tt> BeanContext </tt>中。
     * </p>
     * <p>
     *  beanName参数的语义由java.beans.Beans.instantiate定义。
     * </p>
     * 
     * 
     * @param beanName the name of the Bean to instantiate within this BeanContext
     * @throws IOException if there is an I/O error when the bean is being deserialized
     * @throws ClassNotFoundException if the class
     * identified by the beanName parameter is not found
     * @return the new object
     */
    public Object instantiateChild(String beanName)
           throws IOException, ClassNotFoundException {
        BeanContext bc = getBeanContextPeer();

        return Beans.instantiate(bc.getClass().getClassLoader(), beanName, bc);
    }

    /**
     * Gets the number of children currently nested in
     * this BeanContext.
     *
     * <p>
     *  获取当前嵌套在此BeanContext中的子项数。
     * 
     * 
     * @return number of children
     */
    public int size() {
        synchronized(children) {
            return children.size();
        }
    }

    /**
     * Reports whether or not this
     * <tt>BeanContext</tt> is empty.
     * A <tt>BeanContext</tt> is considered
     * empty when it contains zero
     * nested children.
     * <p>
     *  报告此<tt> BeanContext </tt>是否为空。当<tt> BeanContext </tt>包含零个嵌套子项时,它被认为是空的。
     * 
     * 
     * @return if there are not children
     */
    public boolean isEmpty() {
        synchronized(children) {
            return children.isEmpty();
        }
    }

    /**
     * Determines whether or not the specified object
     * is currently a child of this <tt>BeanContext</tt>.
     * <p>
     *  确定指定的对象是否当前是此<tt> BeanContext </tt>的子级。
     * 
     * 
     * @param o the Object in question
     * @return if this object is a child
     */
    public boolean contains(Object o) {
        synchronized(children) {
            return children.containsKey(o);
        }
    }

    /**
     * Determines whether or not the specified object
     * is currently a child of this <tt>BeanContext</tt>.
     * <p>
     *  确定指定的对象是否当前是此<tt> BeanContext </tt>的子级。
     * 
     * 
     * @param o the Object in question
     * @return if this object is a child
     */
    public boolean containsKey(Object o) {
        synchronized(children) {
            return children.containsKey(o);
        }
    }

    /**
     * Gets all JavaBean or <tt>BeanContext</tt> instances
     * currently nested in this <tt>BeanContext</tt>.
     * <p>
     * 获取当前嵌套在此<tt> BeanContext </tt>中的所有JavaBean或<tt> BeanContext </tt>实例。
     * 
     * 
     * @return an <tt>Iterator</tt> of the nested children
     */
    public Iterator iterator() {
        synchronized(children) {
            return new BCSIterator(children.keySet().iterator());
        }
    }

    /**
     * Gets all JavaBean or <tt>BeanContext</tt>
     * instances currently nested in this BeanContext.
     * <p>
     *  获取当前嵌套在此BeanContext中的所有JavaBean或<tt> BeanContext </tt>实例。
     * 
     */
    public Object[] toArray() {
        synchronized(children) {
            return children.keySet().toArray();
        }
    }

    /**
     * Gets an array containing all children of
     * this <tt>BeanContext</tt> that match
     * the types contained in arry.
     * <p>
     *  获取包含此<tt> BeanContext </tt>的所有子项的数组,它们与arry中包含的类型匹配。
     * 
     * 
     * @param arry The array of object
     * types that are of interest.
     * @return an array of children
     */
    public Object[] toArray(Object[] arry) {
        synchronized(children) {
            return children.keySet().toArray(arry);
        }
    }


    /************************************************************************/

    /**
     * protected final subclass that encapsulates an iterator but implements
     * a noop remove() method.
     * <p>
     *  protected最终子类封装迭代器,但实现noop remove()方法。
     * 
     */

    protected static final class BCSIterator implements Iterator {
        BCSIterator(Iterator i) { super(); src = i; }

        public boolean hasNext() { return src.hasNext(); }
        public Object  next()    { return src.next();    }
        public void    remove()  { /* do nothing */      }

        private Iterator src;
    }

        public void    remove()  { /* <p>
        public void    remove()  { /*  private Iterator src; }}
        public void    remove()  { /* 
        public void    remove()  { /* 
    /************************************************************************/

    /*
     * protected nested class containing per child information, an instance
     * of which is associated with each child in the "children" hashtable.
     * subclasses can extend this class to include their own per-child state.
     *
     * Note that this 'value' is serialized with the corresponding child 'key'
     * when the BeanContextSupport is serialized.
     * <p>
     *  受保护的嵌套类包含每个子项信息,其实例与"子项"散列表中的每个子项相关联。子类可以扩展这个类以包括它们自己的每个子状态。
     * 
     *  注意,当BeanContextSupport序列化时,这个'value'被序列化为相应的子键'key'。
     * 
     */

    protected class BCSChild implements Serializable {

    private static final long serialVersionUID = -5815286101609939109L;

        BCSChild(Object bcc, Object peer) {
            super();

            child     = bcc;
            proxyPeer = peer;
        }

        Object  getChild()                  { return child; }

        void    setRemovePending(boolean v) { removePending = v; }

        boolean isRemovePending()           { return removePending; }

        boolean isProxyPeer()               { return proxyPeer != null; }

        Object  getProxyPeer()              { return proxyPeer; }
        /*
         * fields
         * <p>
         *  字段
         * 
         */


        private           Object   child;
        private           Object   proxyPeer;

        private transient boolean  removePending;
    }

    /**
     * <p>
     * Subclasses can override this method to insert their own subclass
     * of Child without having to override add() or the other Collection
     * methods that add children to the set.
     * </p>
     * <p>
     * <p>
     *  子类可以覆盖此方法以插入他们自己的子类的子类,而无需重写add()或其他将子项添加到集合的集合方法。
     * </p>
     * 
     * @param targetChild the child to create the Child on behalf of
     * @param peer        the peer if the tragetChild and the peer are related by an implementation of BeanContextProxy     * @return Subtype-specific subclass of Child without overriding collection methods
     */

    protected BCSChild createBCSChild(Object targetChild, Object peer) {
        return new BCSChild(targetChild, peer);
    }

    /************************************************************************/

    /**
     * Adds/nests a child within this <tt>BeanContext</tt>.
     * <p>
     * Invoked as a side effect of java.beans.Beans.instantiate().
     * If the child object is not valid for adding then this method
     * throws an IllegalStateException.
     * </p>
     *
     *
     * <p>
     *  在<tt> BeanContext </tt>中添加/嵌套子元素。
     * <p>
     *  作为java.beans.Beans.instantiate()的副作用调用。如果子对象对添加无效,则此方法将抛出IllegalStateException。
     * </p>
     * 
     * 
     * @param targetChild The child objects to nest
     * within this <tt>BeanContext</tt>
     * @return true if the child was added successfully.
     * @see #validatePendingAdd
     */
    public boolean add(Object targetChild) {

        if (targetChild == null) throw new IllegalArgumentException();

        // The specification requires that we do nothing if the child
        // is already nested herein.

        if (children.containsKey(targetChild)) return false; // test before locking

        synchronized(BeanContext.globalHierarchyLock) {
            if (children.containsKey(targetChild)) return false; // check again

            if (!validatePendingAdd(targetChild)) {
                throw new IllegalStateException();
            }


            // The specification requires that we invoke setBeanContext() on the
            // newly added child if it implements the java.beans.beancontext.BeanContextChild interface

            BeanContextChild cbcc  = getChildBeanContextChild(targetChild);
            BeanContextChild  bccp = null;

            synchronized(targetChild) {

                if (targetChild instanceof BeanContextProxy) {
                    bccp = ((BeanContextProxy)targetChild).getBeanContextProxy();

                    if (bccp == null) throw new NullPointerException("BeanContextPeer.getBeanContextProxy()");
                }

                BCSChild bcsc  = createBCSChild(targetChild, bccp);
                BCSChild pbcsc = null;

                synchronized (children) {
                    children.put(targetChild, bcsc);

                    if (bccp != null) children.put(bccp, pbcsc = createBCSChild(bccp, targetChild));
                }

                if (cbcc != null) synchronized(cbcc) {
                    try {
                        cbcc.setBeanContext(getBeanContextPeer());
                    } catch (PropertyVetoException pve) {

                        synchronized (children) {
                            children.remove(targetChild);

                            if (bccp != null) children.remove(bccp);
                        }

                        throw new IllegalStateException();
                    }

                    cbcc.addPropertyChangeListener("beanContext", childPCL);
                    cbcc.addVetoableChangeListener("beanContext", childVCL);
                }

                Visibility v = getChildVisibility(targetChild);

                if (v != null) {
                    if (okToUseGui)
                        v.okToUseGui();
                    else
                        v.dontUseGui();
                }

                if (getChildSerializable(targetChild) != null) serializable++;

                childJustAddedHook(targetChild, bcsc);

                if (bccp != null) {
                    v = getChildVisibility(bccp);

                    if (v != null) {
                        if (okToUseGui)
                            v.okToUseGui();
                        else
                            v.dontUseGui();
                    }

                    if (getChildSerializable(bccp) != null) serializable++;

                    childJustAddedHook(bccp, pbcsc);
                }


            }

            // The specification requires that we fire a notification of the change

            fireChildrenAdded(new BeanContextMembershipEvent(getBeanContextPeer(), bccp == null ? new Object[] { targetChild } : new Object[] { targetChild, bccp } ));

        }

        return true;
    }

    /**
     * Removes a child from this BeanContext.  If the child object is not
     * for adding then this method throws an IllegalStateException.
     * <p>
     *  从此BeanContext中删除一个子项。如果子对象不是用于添加,则此方法将抛出IllegalStateException。
     * 
     * 
     * @param targetChild The child objects to remove
     * @see #validatePendingRemove
     */
    public boolean remove(Object targetChild) {
        return remove(targetChild, true);
    }

    /**
     * internal remove used when removal caused by
     * unexpected <tt>setBeanContext</tt> or
     * by <tt>remove()</tt> invocation.
     * <p>
     *  当由意外<tt> setBeanContext </tt>或<tt> remove()</tt>调用引起的删除时使用的内部删除。
     * 
     * 
     * @param targetChild the JavaBean, BeanContext, or Object to be removed
     * @param callChildSetBC used to indicate that
     * the child should be notified that it is no
     * longer nested in this <tt>BeanContext</tt>.
     * @return whether or not was present before being removed
     */
    protected boolean remove(Object targetChild, boolean callChildSetBC) {

        if (targetChild == null) throw new IllegalArgumentException();

        synchronized(BeanContext.globalHierarchyLock) {
            if (!containsKey(targetChild)) return false;

            if (!validatePendingRemove(targetChild)) {
                throw new IllegalStateException();
            }

            BCSChild bcsc  = (BCSChild)children.get(targetChild);
            BCSChild pbcsc = null;
            Object   peer  = null;

            // we are required to notify the child that it is no longer nested here if
            // it implements java.beans.beancontext.BeanContextChild

            synchronized(targetChild) {
                if (callChildSetBC) {
                    BeanContextChild cbcc = getChildBeanContextChild(targetChild);
                    if (cbcc != null) synchronized(cbcc) {
                        cbcc.removePropertyChangeListener("beanContext", childPCL);
                        cbcc.removeVetoableChangeListener("beanContext", childVCL);

                        try {
                            cbcc.setBeanContext(null);
                        } catch (PropertyVetoException pve1) {
                            cbcc.addPropertyChangeListener("beanContext", childPCL);
                            cbcc.addVetoableChangeListener("beanContext", childVCL);
                            throw new IllegalStateException();
                        }

                    }
                }

                synchronized (children) {
                    children.remove(targetChild);

                    if (bcsc.isProxyPeer()) {
                        pbcsc = (BCSChild)children.get(peer = bcsc.getProxyPeer());
                        children.remove(peer);
                    }
                }

                if (getChildSerializable(targetChild) != null) serializable--;

                childJustRemovedHook(targetChild, bcsc);

                if (peer != null) {
                    if (getChildSerializable(peer) != null) serializable--;

                    childJustRemovedHook(peer, pbcsc);
                }
            }

            fireChildrenRemoved(new BeanContextMembershipEvent(getBeanContextPeer(), peer == null ? new Object[] { targetChild } : new Object[] { targetChild, peer } ));

        }

        return true;
    }

    /**
     * Tests to see if all objects in the
     * specified <tt>Collection</tt> are children of
     * this <tt>BeanContext</tt>.
     * <p>
     *  测试指定<tt>集合</tt>中的所有对象是否都是<tt> BeanContext </tt>的子对象。
     * 
     * 
     * @param c the specified <tt>Collection</tt>
     *
     * @return <tt>true</tt> if all objects
     * in the collection are children of
     * this <tt>BeanContext</tt>, false if not.
     */
    public boolean containsAll(Collection c) {
        synchronized(children) {
            Iterator i = c.iterator();
            while (i.hasNext())
                if(!contains(i.next()))
                    return false;

            return true;
        }
    }

    /**
     * add Collection to set of Children (Unsupported)
     * implementations must synchronized on the hierarchy lock and "children" protected field
     * <p>
     * 添加集合到子集(不支持)实现必须在层次结构锁和"children"保护字段上同步
     * 
     * 
     * @throws UnsupportedOperationException thrown unconditionally by this implementation
     * @return this implementation unconditionally throws {@code UnsupportedOperationException}
     */
    public boolean addAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    /**
     * remove all specified children (Unsupported)
     * implementations must synchronized on the hierarchy lock and "children" protected field
     * <p>
     *  删除所有指定的子(Unsupported)实现必须在层次锁和"children"保护字段上同步
     * 
     * 
     * @throws UnsupportedOperationException thrown unconditionally by this implementation
     * @return this implementation unconditionally throws {@code UnsupportedOperationException}

     */
    public boolean removeAll(Collection c) {
        throw new UnsupportedOperationException();
    }


    /**
     * retain only specified children (Unsupported)
     * implementations must synchronized on the hierarchy lock and "children" protected field
     * <p>
     *  仅保留指定的子(Unsupported)实现必须在层次锁和"children"保护字段上同步
     * 
     * 
     * @throws UnsupportedOperationException thrown unconditionally by this implementation
     * @return this implementation unconditionally throws {@code UnsupportedOperationException}
     */
    public boolean retainAll(Collection c) {
        throw new UnsupportedOperationException();
    }

    /**
     * clear the children (Unsupported)
     * implementations must synchronized on the hierarchy lock and "children" protected field
     * <p>
     *  清除子(Unsupported)实现必须在层次锁和"children"保护字段上同步
     * 
     * 
     * @throws UnsupportedOperationException thrown unconditionally by this implementation
     */
    public void clear() {
        throw new UnsupportedOperationException();
    }

    /**
     * Adds a BeanContextMembershipListener
     *
     * <p>
     *  添加一个BeanContextMembershipListener
     * 
     * 
     * @param  bcml the BeanContextMembershipListener to add
     * @throws NullPointerException if the argument is null
     */

    public void addBeanContextMembershipListener(BeanContextMembershipListener bcml) {
        if (bcml == null) throw new NullPointerException("listener");

        synchronized(bcmListeners) {
            if (bcmListeners.contains(bcml))
                return;
            else
                bcmListeners.add(bcml);
        }
    }

    /**
     * Removes a BeanContextMembershipListener
     *
     * <p>
     *  删除BeanContextMembershipListener
     * 
     * 
     * @param  bcml the BeanContextMembershipListener to remove
     * @throws NullPointerException if the argument is null
     */

    public void removeBeanContextMembershipListener(BeanContextMembershipListener bcml) {
        if (bcml == null) throw new NullPointerException("listener");

        synchronized(bcmListeners) {
            if (!bcmListeners.contains(bcml))
                return;
            else
                bcmListeners.remove(bcml);
        }
    }

    /**
    /* <p>
    /* 
     * @param name the name of the resource requested.
     * @param bcc  the child object making the request.
     *
     * @return  the requested resource as an InputStream
     * @throws  NullPointerException if the argument is null
     */

    public InputStream getResourceAsStream(String name, BeanContextChild bcc) {
        if (name == null) throw new NullPointerException("name");
        if (bcc  == null) throw new NullPointerException("bcc");

        if (containsKey(bcc)) {
            ClassLoader cl = bcc.getClass().getClassLoader();

            return cl != null ? cl.getResourceAsStream(name)
                              : ClassLoader.getSystemResourceAsStream(name);
        } else throw new IllegalArgumentException("Not a valid child");
    }

    /**
    /* <p>
    /* 
     * @param name the name of the resource requested.
     * @param bcc  the child object making the request.
     *
     * @return the requested resource as an InputStream
     */

    public URL getResource(String name, BeanContextChild bcc) {
        if (name == null) throw new NullPointerException("name");
        if (bcc  == null) throw new NullPointerException("bcc");

        if (containsKey(bcc)) {
            ClassLoader cl = bcc.getClass().getClassLoader();

            return cl != null ? cl.getResource(name)
                              : ClassLoader.getSystemResource(name);
        } else throw new IllegalArgumentException("Not a valid child");
    }

    /**
     * Sets the new design time value for this <tt>BeanContext</tt>.
     * <p>
     *  为此<tt> BeanContext </tt>设置新的设计时间值。
     * 
     * 
     * @param dTime the new designTime value
     */
    public synchronized void setDesignTime(boolean dTime) {
        if (designTime != dTime) {
            designTime = dTime;

            firePropertyChange("designMode", Boolean.valueOf(!dTime), Boolean.valueOf(dTime));
        }
    }


    /**
     * Reports whether or not this object is in
     * currently in design time mode.
     * <p>
     *  报告此对象是否当前处于设计时模式。
     * 
     * 
     * @return <tt>true</tt> if in design time mode,
     * <tt>false</tt> if not
     */
    public synchronized boolean isDesignTime() { return designTime; }

    /**
     * Sets the locale of this BeanContext.
     * <p>
     *  设置此BeanContext的语言环境。
     * 
     * 
     * @param newLocale the new locale. This method call will have
     *        no effect if newLocale is <CODE>null</CODE>.
     * @throws PropertyVetoException if the new value is rejected
     */
    public synchronized void setLocale(Locale newLocale) throws PropertyVetoException {

        if ((locale != null && !locale.equals(newLocale)) && newLocale != null) {
            Locale old = locale;

            fireVetoableChange("locale", old, newLocale); // throws

            locale = newLocale;

            firePropertyChange("locale", old, newLocale);
        }
    }

    /**
     * Gets the locale for this <tt>BeanContext</tt>.
     *
     * <p>
     *  获取此<tt> BeanContext </tt>的语言区域。
     * 
     * 
     * @return the current Locale of the <tt>BeanContext</tt>
     */
    public synchronized Locale getLocale() { return locale; }

    /**
     * <p>
     * This method is typically called from the environment in order to determine
     * if the implementor "needs" a GUI.
     * </p>
     * <p>
     * The algorithm used herein tests the BeanContextPeer, and its current children
     * to determine if they are either Containers, Components, or if they implement
     * Visibility and return needsGui() == true.
     * </p>
     * <p>
     * <p>
     *  通常从环境中调用此方法,以便确定实现者是否"需要"GUI。
     * </p>
     * <p>
     *  此处使用的算法测试BeanContextPeer及其当前子项,以确定它们是否为Containers,Components,或者如果它们实现了Visibility并返回needsGui()== true
     * 。
     * </p>
     * 
     * @return <tt>true</tt> if the implementor needs a GUI
     */
    public synchronized boolean needsGui() {
        BeanContext bc = getBeanContextPeer();

        if (bc != this) {
            if (bc instanceof Visibility) return ((Visibility)bc).needsGui();

            if (bc instanceof Container || bc instanceof Component)
                return true;
        }

        synchronized(children) {
            for (Iterator i = children.keySet().iterator(); i.hasNext();) {
                Object c = i.next();

                try {
                        return ((Visibility)c).needsGui();
                    } catch (ClassCastException cce) {
                        // do nothing ...
                    }

                    if (c instanceof Container || c instanceof Component)
                        return true;
            }
        }

        return false;
    }

    /**
     * notify this instance that it may no longer render a GUI.
     * <p>
     *  通知此实例它可能不再渲染GUI。
     * 
     */

    public synchronized void dontUseGui() {
        if (okToUseGui) {
            okToUseGui = false;

            // lets also tell the Children that can that they may not use their GUI's
            synchronized(children) {
                for (Iterator i = children.keySet().iterator(); i.hasNext();) {
                    Visibility v = getChildVisibility(i.next());

                    if (v != null) v.dontUseGui();
               }
            }
        }
    }

    /**
     * Notify this instance that it may now render a GUI
     * <p>
     *  通知此实例,它现在可以渲染GUI
     * 
     */

    public synchronized void okToUseGui() {
        if (!okToUseGui) {
            okToUseGui = true;

            // lets also tell the Children that can that they may use their GUI's
            synchronized(children) {
                for (Iterator i = children.keySet().iterator(); i.hasNext();) {
                    Visibility v = getChildVisibility(i.next());

                    if (v != null) v.okToUseGui();
                }
            }
        }
    }

    /**
     * Used to determine if the <tt>BeanContext</tt>
     * child is avoiding using its GUI.
     * <p>
     *  用于确定<tt> BeanContext </tt>子项是否正在避免使用其GUI。
     * 
     * 
     * @return is this instance avoiding using its GUI?
     * @see Visibility
     */
    public boolean avoidingGui() {
        return !okToUseGui && needsGui();
    }

    /**
     * Is this <tt>BeanContext</tt> in the
     * process of being serialized?
     * <p>
     *  这是<tt> BeanContext </tt>在序列化的过程中吗?
     * 
     * 
     * @return if this <tt>BeanContext</tt> is
     * currently being serialized
     */
    public boolean isSerializing() { return serializing; }

    /**
     * Returns an iterator of all children
     * of this <tt>BeanContext</tt>.
     * <p>
     *  返回此<tt> BeanContext </tt>的所有子元素的迭代器。
     * 
     * 
     * @return an iterator for all the current BCSChild values
     */
    protected Iterator bcsChildren() { synchronized(children) { return children.values().iterator();  } }

    /**
     * called by writeObject after defaultWriteObject() but prior to
     * serialization of currently serializable children.
     *
     * This method may be overridden by subclasses to perform custom
     * serialization of their state prior to this superclass serializing
     * the children.
     *
     * This method should not however be used by subclasses to replace their
     * own implementation (if any) of writeObject().
     * <p>
     * 在defaultWriteObject()之后由writeObject调用,但在当前可序列化子代的序列化之前。
     * 
     *  这个方法可以被子类覆盖以在这个超类序列化子元素之前执行它们的状态的自定义序列化。
     * 
     *  然而,子类不应该使用此方法来替换writeObject()的自己的实现(如果有的话)。
     * 
     * 
     * @param oos the {@code ObjectOutputStream} to use during serialization
     * @throws IOException if serialization failed
     */

    protected void bcsPreSerializationHook(ObjectOutputStream oos) throws IOException {
    }

    /**
     * called by readObject after defaultReadObject() but prior to
     * deserialization of any children.
     *
     * This method may be overridden by subclasses to perform custom
     * deserialization of their state prior to this superclass deserializing
     * the children.
     *
     * This method should not however be used by subclasses to replace their
     * own implementation (if any) of readObject().
     * <p>
     *  调用readObject之后的defaultReadObject(),但在任何孩子的反序列化之前。
     * 
     *  这个方法可以被子类覆盖以在子类反序列化之前对其状态执行定制反序列化。
     * 
     *  然而,子类不应该使用此方法来替换readObject()的自己的实现(如果有的话)。
     * 
     * 
     * @param ois the {@code ObjectInputStream} to use during deserialization
     * @throws IOException if deserialization failed
     * @throws ClassNotFoundException if needed classes are not found
     */

    protected void bcsPreDeserializationHook(ObjectInputStream ois) throws IOException, ClassNotFoundException {
    }

    /**
     * Called by readObject with the newly deserialized child and BCSChild.
     * <p>
     *  由readObject调用新反序列化的孩子和BCSChild。
     * 
     * 
     * @param child the newly deserialized child
     * @param bcsc the newly deserialized BCSChild
     */
    protected void childDeserializedHook(Object child, BCSChild bcsc) {
        synchronized(children) {
            children.put(child, bcsc);
        }
    }

    /**
     * Used by writeObject to serialize a Collection.
     * <p>
     *  由writeObject用于序列化集合。
     * 
     * 
     * @param oos the <tt>ObjectOutputStream</tt>
     * to use during serialization
     * @param coll the <tt>Collection</tt> to serialize
     * @throws IOException if serialization failed
     */
    protected final void serialize(ObjectOutputStream oos, Collection coll) throws IOException {
        int      count   = 0;
        Object[] objects = coll.toArray();

        for (int i = 0; i < objects.length; i++) {
            if (objects[i] instanceof Serializable)
                count++;
            else
                objects[i] = null;
        }

        oos.writeInt(count); // number of subsequent objects

        for (int i = 0; count > 0; i++) {
            Object o = objects[i];

            if (o != null) {
                oos.writeObject(o);
                count--;
            }
        }
    }

    /**
     * used by readObject to deserialize a collection.
     * <p>
     *  由readObject用于反序列化集合。
     * 
     * 
     * @param ois the ObjectInputStream to use
     * @param coll the Collection
     * @throws IOException if deserialization failed
     * @throws ClassNotFoundException if needed classes are not found
     */
    protected final void deserialize(ObjectInputStream ois, Collection coll) throws IOException, ClassNotFoundException {
        int count = 0;

        count = ois.readInt();

        while (count-- > 0) {
            coll.add(ois.readObject());
        }
    }

    /**
     * Used to serialize all children of
     * this <tt>BeanContext</tt>.
     * <p>
     *  用于序列化此<tt> BeanContext </tt>的所有子级。
     * 
     * 
     * @param oos the <tt>ObjectOutputStream</tt>
     * to use during serialization
     * @throws IOException if serialization failed
     */
    public final void writeChildren(ObjectOutputStream oos) throws IOException {
        if (serializable <= 0) return;

        boolean prev = serializing;

        serializing = true;

        int count = 0;

        synchronized(children) {
            Iterator i = children.entrySet().iterator();

            while (i.hasNext() && count < serializable) {
                Map.Entry entry = (Map.Entry)i.next();

                if (entry.getKey() instanceof Serializable) {
                    try {
                        oos.writeObject(entry.getKey());   // child
                        oos.writeObject(entry.getValue()); // BCSChild
                    } catch (IOException ioe) {
                        serializing = prev;
                        throw ioe;
                    }
                    count++;
                }
            }
        }

        serializing = prev;

        if (count != serializable) {
            throw new IOException("wrote different number of children than expected");
        }

    }

    /**
     * Serialize the BeanContextSupport, if this instance has a distinct
     * peer (that is this object is acting as a delegate for another) then
     * the children of this instance are not serialized here due to a
     * 'chicken and egg' problem that occurs on deserialization of the
     * children at the same time as this instance.
     *
     * Therefore in situations where there is a distinct peer to this instance
     * it should always call writeObject() followed by writeChildren() and
     * readObject() followed by readChildren().
     *
     * <p>
     *  序列化BeanContextSupport,如果这个实例有一个不同的对等体(即这个对象充当另一个对象的委托),那么这个实例的孩子不会在这里序列化,因为在孩子的反序列化发生的"鸡和鸡蛋"问题与此实例同时
     * 。
     * 
     *  因此,在存在与此实例不同的对等体的情况下,它应该总是调用writeObject(),后跟writeChildren()和readObject(),然后是readChildren()。
     * 
     * 
     * @param oos the ObjectOutputStream
     */

    private synchronized void writeObject(ObjectOutputStream oos) throws IOException, ClassNotFoundException {
        serializing = true;

        synchronized (BeanContext.globalHierarchyLock) {
            try {
                oos.defaultWriteObject(); // serialize the BeanContextSupport object

                bcsPreSerializationHook(oos);

                if (serializable > 0 && this.equals(getBeanContextPeer()))
                    writeChildren(oos);

                serialize(oos, (Collection)bcmListeners);
            } finally {
                serializing = false;
            }
        }
    }

    /**
     * When an instance of this class is used as a delegate for the
     * implementation of the BeanContext protocols (and its subprotocols)
     * there exists a 'chicken and egg' problem during deserialization
     * <p>
     * 当这个类的实例被用作BeanContext协议(及其子协议)的实现的委托时,在反序列化过程中存在一个"鸡和鸡蛋"的问题
     * 
     * 
     * @param ois the ObjectInputStream to use
     * @throws IOException if deserialization failed
     * @throws ClassNotFoundException if needed classes are not found
     */

    public final void readChildren(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        int count = serializable;

        while (count-- > 0) {
            Object                      child = null;
            BeanContextSupport.BCSChild bscc  = null;

            try {
                child = ois.readObject();
                bscc  = (BeanContextSupport.BCSChild)ois.readObject();
            } catch (IOException ioe) {
                continue;
            } catch (ClassNotFoundException cnfe) {
                continue;
            }


            synchronized(child) {
                BeanContextChild bcc = null;

                try {
                    bcc = (BeanContextChild)child;
                } catch (ClassCastException cce) {
                    // do nothing;
                }

                if (bcc != null) {
                    try {
                        bcc.setBeanContext(getBeanContextPeer());

                       bcc.addPropertyChangeListener("beanContext", childPCL);
                       bcc.addVetoableChangeListener("beanContext", childVCL);

                    } catch (PropertyVetoException pve) {
                        continue;
                    }
                }

                childDeserializedHook(child, bscc);
            }
        }
    }

    /**
     * deserialize contents ... if this instance has a distinct peer the
     * children are *not* serialized here, the peer's readObject() must call
     * readChildren() after deserializing this instance.
     * <p>
     *  反序列化内容...如果这个实例有一个不同的对等体,孩子们在这里没有序列化,对等体的readObject()在反序列化这个实例后必须调用readChildren()。
     * 
     */

    private synchronized void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        synchronized(BeanContext.globalHierarchyLock) {
            ois.defaultReadObject();

            initialize();

            bcsPreDeserializationHook(ois);

            if (serializable > 0 && this.equals(getBeanContextPeer()))
                readChildren(ois);

            deserialize(ois, bcmListeners = new ArrayList(1));
        }
    }

    /**
     * subclasses may envelope to monitor veto child property changes.
     * <p>
     *  子类可以包络以监视否决属性更改。
     * 
     */

    public void vetoableChange(PropertyChangeEvent pce) throws PropertyVetoException {
        String propertyName = pce.getPropertyName();
        Object source       = pce.getSource();

        synchronized(children) {
            if ("beanContext".equals(propertyName) &&
                containsKey(source)                    &&
                !getBeanContextPeer().equals(pce.getNewValue())
            ) {
                if (!validatePendingRemove(source)) {
                    throw new PropertyVetoException("current BeanContext vetoes setBeanContext()", pce);
                } else ((BCSChild)children.get(source)).setRemovePending(true);
            }
        }
    }

    /**
     * subclasses may envelope to monitor child property changes.
     * <p>
     *  子类可以包络以监视子属性更改。
     * 
     */

    public void propertyChange(PropertyChangeEvent pce) {
        String propertyName = pce.getPropertyName();
        Object source       = pce.getSource();

        synchronized(children) {
            if ("beanContext".equals(propertyName) &&
                containsKey(source)                    &&
                ((BCSChild)children.get(source)).isRemovePending()) {
                BeanContext bc = getBeanContextPeer();

                if (bc.equals(pce.getOldValue()) && !bc.equals(pce.getNewValue())) {
                    remove(source, false);
                } else {
                    ((BCSChild)children.get(source)).setRemovePending(false);
                }
            }
        }
    }

    /**
     * <p>
     * Subclasses of this class may override, or envelope, this method to
     * add validation behavior for the BeanContext to examine child objects
     * immediately prior to their being added to the BeanContext.
     * </p>
     *
     * <p>
     * <p>
     *  此类的子类可以覆盖或封装此方法,以便为BeanContext添加验证行为,以便在将子对象添加到BeanContext之前立即检查子对象。
     * </p>
     * 
     * 
     * @param targetChild the child to create the Child on behalf of
     * @return true iff the child may be added to this BeanContext, otherwise false.
     */

    protected boolean validatePendingAdd(Object targetChild) {
        return true;
    }

    /**
     * <p>
     * Subclasses of this class may override, or envelope, this method to
     * add validation behavior for the BeanContext to examine child objects
     * immediately prior to their being removed from the BeanContext.
     * </p>
     *
     * <p>
     * <p>
     *  此类的子类可以覆盖或封装此方法,以便为BeanContext添加验证行为,以便在从BeanContext中删除子对象之前立即检查子对象。
     * </p>
     * 
     * 
     * @param targetChild the child to create the Child on behalf of
     * @return true iff the child may be removed from this BeanContext, otherwise false.
     */

    protected boolean validatePendingRemove(Object targetChild) {
        return true;
    }

    /**
     * subclasses may override this method to simply extend add() semantics
     * after the child has been added and before the event notification has
     * occurred. The method is called with the child synchronized.
     * <p>
     *  子类可以覆盖此方法,以在添加子节点之后和事件通知发生之前简单地扩展add()语义。该方法在子进程同步时调用。
     * 
     * 
     * @param child the child
     * @param bcsc the BCSChild
     */

    protected void childJustAddedHook(Object child, BCSChild bcsc) {
    }

    /**
     * subclasses may override this method to simply extend remove() semantics
     * after the child has been removed and before the event notification has
     * occurred. The method is called with the child synchronized.
     * <p>
     *  子类可以覆盖此方法,以便在子被移除之后并且在事件通知发生之前简单地扩展remove()语义。该方法在子进程同步时调用。
     * 
     * 
     * @param child the child
     * @param bcsc the BCSChild
     */

    protected void childJustRemovedHook(Object child, BCSChild bcsc) {
    }

    /**
     * Gets the Component (if any) associated with the specified child.
     * <p>
     *  获取与指定子项关联的组件(如果有)。
     * 
     * 
     * @param child the specified child
     * @return the Component (if any) associated with the specified child.
     */
    protected static final Visibility getChildVisibility(Object child) {
        try {
            return (Visibility)child;
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * Gets the Serializable (if any) associated with the specified Child
     * <p>
     *  获取与指定子项关联的可序列化(如果有)
     * 
     * 
     * @param child the specified child
     * @return the Serializable (if any) associated with the specified Child
     */
    protected static final Serializable getChildSerializable(Object child) {
        try {
            return (Serializable)child;
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * Gets the PropertyChangeListener
     * (if any) of the specified child
     * <p>
     * 获取指定子项的PropertyChangeListener(如果有)
     * 
     * 
     * @param child the specified child
     * @return the PropertyChangeListener (if any) of the specified child
     */
    protected static final PropertyChangeListener getChildPropertyChangeListener(Object child) {
        try {
            return (PropertyChangeListener)child;
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * Gets the VetoableChangeListener
     * (if any) of the specified child
     * <p>
     *  获取指定子项的VetoableChangeListener(如果有)
     * 
     * 
     * @param child the specified child
     * @return the VetoableChangeListener (if any) of the specified child
     */
    protected static final VetoableChangeListener getChildVetoableChangeListener(Object child) {
        try {
            return (VetoableChangeListener)child;
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * Gets the BeanContextMembershipListener
     * (if any) of the specified child
     * <p>
     *  获取指定子项的BeanContextMembershipListener(如果有)
     * 
     * 
     * @param child the specified child
     * @return the BeanContextMembershipListener (if any) of the specified child
     */
    protected static final BeanContextMembershipListener getChildBeanContextMembershipListener(Object child) {
        try {
            return (BeanContextMembershipListener)child;
        } catch (ClassCastException cce) {
            return null;
        }
    }

    /**
     * Gets the BeanContextChild (if any) of the specified child
     * <p>
     *  获取指定子项的BeanContextChild(如果有)
     * 
     * 
     * @param child the specified child
     * @return  the BeanContextChild (if any) of the specified child
     * @throws  IllegalArgumentException if child implements both BeanContextChild and BeanContextProxy
     */
    protected static final BeanContextChild getChildBeanContextChild(Object child) {
        try {
            BeanContextChild bcc = (BeanContextChild)child;

            if (child instanceof BeanContextChild && child instanceof BeanContextProxy)
                throw new IllegalArgumentException("child cannot implement both BeanContextChild and BeanContextProxy");
            else
                return bcc;
        } catch (ClassCastException cce) {
            try {
                return ((BeanContextProxy)child).getBeanContextProxy();
            } catch (ClassCastException cce1) {
                return null;
            }
        }
    }

    /**
     * Fire a BeanContextshipEvent on the BeanContextMembershipListener interface
     * <p>
     *  在BeanContextMembershipListener接口上触发BeanContextshipEvent
     * 
     * 
     * @param bcme the event to fire
     */

    protected final void fireChildrenAdded(BeanContextMembershipEvent bcme) {
        Object[] copy;

        synchronized(bcmListeners) { copy = bcmListeners.toArray(); }

        for (int i = 0; i < copy.length; i++)
            ((BeanContextMembershipListener)copy[i]).childrenAdded(bcme);
    }

    /**
     * Fire a BeanContextshipEvent on the BeanContextMembershipListener interface
     * <p>
     *  在BeanContextMembershipListener接口上触发BeanContextshipEvent
     * 
     * 
     * @param bcme the event to fire
     */

    protected final void fireChildrenRemoved(BeanContextMembershipEvent bcme) {
        Object[] copy;

        synchronized(bcmListeners) { copy = bcmListeners.toArray(); }

        for (int i = 0; i < copy.length; i++)
            ((BeanContextMembershipListener)copy[i]).childrenRemoved(bcme);
    }

    /**
     * protected method called from constructor and readObject to initialize
     * transient state of BeanContextSupport instance.
     *
     * This class uses this method to instantiate inner class listeners used
     * to monitor PropertyChange and VetoableChange events on children.
     *
     * subclasses may envelope this method to add their own initialization
     * behavior
     * <p>
     *  protected方法从构造函数和readObject调用来初始化BeanContextSupport实例的临时状态。
     * 
     *  此类使用此方法实例化用于监视子节点上的PropertyChange和VetoableChange事件的内部类侦听器。
     * 
     *  子类可以包围这个方法来添加自己的初始化行为
     * 
     */

    protected synchronized void initialize() {
        children     = new HashMap(serializable + 1);
        bcmListeners = new ArrayList(1);

        childPCL = new PropertyChangeListener() {

            /*
             * this adaptor is used by the BeanContextSupport class to forward
             * property changes from a child to the BeanContext, avoiding
             * accidential serialization of the BeanContext by a badly
             * behaved Serializable child.
             * <p>
             *  BeanContextSupport类使用此适配器将属性更改从子项转移到BeanContext,从而避免由严重行为的Serializable子项对BeanContext进行序列化。
             * 
             */

            public void propertyChange(PropertyChangeEvent pce) {
                BeanContextSupport.this.propertyChange(pce);
            }
        };

        childVCL = new VetoableChangeListener() {

            /*
             * this adaptor is used by the BeanContextSupport class to forward
             * vetoable changes from a child to the BeanContext, avoiding
             * accidential serialization of the BeanContext by a badly
             * behaved Serializable child.
             * <p>
             *  这个适配器被BeanContextSupport类用来将一个孩子的vetoable更改转发到BeanContext,避免了一个严重行为Serializable的孩子的BeanContext的序列化。
             * 
             */

            public void vetoableChange(PropertyChangeEvent pce) throws PropertyVetoException {
                BeanContextSupport.this.vetoableChange(pce);
             }
        };
    }

    /**
     * Gets a copy of the this BeanContext's children.
     * <p>
     *  获取这个BeanContext的孩子的副本。
     * 
     * 
     * @return a copy of the current nested children
     */
    protected final Object[] copyChildren() {
        synchronized(children) { return children.keySet().toArray(); }
    }

    /**
     * Tests to see if two class objects,
     * or their names are equal.
     * <p>
     *  测试看看是否有两个类对象,或者它们的名字是否相等。
     * 
     * 
     * @param first the first object
     * @param second the second object
     * @return true if equal, false if not
     */
    protected static final boolean classEquals(Class first, Class second) {
        return first.equals(second) || first.getName().equals(second.getName());
    }


    /*
     * fields
     * <p>
     *  字段
     * 
     */


    /**
     * all accesses to the <code> protected HashMap children </code> field
     * shall be synchronized on that object.
     * <p>
     *  对<code> protected HashMap children </code>字段的所有访问应在该对象上同步。
     * 
     */
    protected transient HashMap         children;

    private             int             serializable  = 0; // children serializable

    /**
     * all accesses to the <code> protected ArrayList bcmListeners </code> field
     * shall be synchronized on that object.
     * <p>
     *  对<code> protected ArrayList bcmListeners </code>字段的所有访问应在该对象上同步。
     * 
     */
    protected transient ArrayList       bcmListeners;

    //

    /**
     * The current locale of this BeanContext.
     * <p>
     * 此BeanContext的当前语言环境。
     * 
     */
    protected           Locale          locale;

    /**
     * A <tt>boolean</tt> indicating if this
     * instance may now render a GUI.
     * <p>
     *  <tt> boolean </tt>表示此实例现在是否可以渲染GUI。
     * 
     */
    protected           boolean         okToUseGui;


    /**
     * A <tt>boolean</tt> indicating whether or not
     * this object is currently in design time mode.
     * <p>
     *  指示此对象当前是否处于设计时模式的<tt>布尔值</tt>。
     * 
     */
    protected           boolean         designTime;

    /*
     * transient
     * <p>
     *  短暂的
     */

    private transient PropertyChangeListener childPCL;

    private transient VetoableChangeListener childVCL;

    private transient boolean                serializing;
}
